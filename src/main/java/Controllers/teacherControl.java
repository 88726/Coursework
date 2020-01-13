package Controllers;


import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

@Path("teacher/")
public class teacherControl {

    @POST
    @Path("login")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String loginteacher(@FormDataParam("teacherSurname") String teacherSurname, @FormDataParam("teacherPassword") String teacherPassword) {

        try {

            System.out.println("teacher/login");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT teacherPassword FROM teachers WHERE teacherSurname = ?");
            ps1.setString(1, teacherSurname);
            ResultSet loginResults = ps1.executeQuery();
            if (loginResults.next()) {

                String correctPassword = loginResults.getString(1);
                if (teacherPassword.equals(correctPassword)) {

                    String token = UUID.randomUUID().toString();

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE teachers SET token = ? WHERE teacherSurname = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, teacherSurname);
                    ps2.executeUpdate();

                    JSONObject teacherDetails = new JSONObject();
                    teacherDetails.put("teacherSurname", teacherSurname);
                    teacherDetails.put("token", token);
                    return teacherDetails.toString();

                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {
                return "{\"error\": \"Unknown teacher!\"}";
            }

        } catch (Exception exception){
            System.out.println("Database error during /teacher/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }
    @Path("logout")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String logoutteacher(@CookieParam("token") String token) {

        try {

            System.out.println("teacher/logout");

            PreparedStatement ps1 = Main.db.prepareStatement("SELECT teacherID FROM teachers WHERE token = ?");
            ps1.setString(1, token);
            ResultSet logoutResults = ps1.executeQuery();
            if (logoutResults.next()) {

                int id = logoutResults.getInt(1);

                PreparedStatement ps2 = Main.db.prepareStatement("UPDATE teachers SET token = NULL WHERE teacherID = ?");
                ps2.setInt(1, id);
                ps2.executeUpdate();

                return "{\"status\": \"OK\"}";
            } else {

                return "{\"error\": \"Invalid token!\"}";

            }

        } catch (Exception exception){
            System.out.println("Database error during /teacher/logout: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }

    }

    public static boolean validToken(String token) {
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teacherID FROM teachers WHERE token = ?");
            ps.setString(1, token);
            ResultSet logoutResults = ps.executeQuery();
            return logoutResults.next();
        } catch (Exception exception) {
            System.out.println("Database error during /teacher/logout: " + exception.getMessage());
            return false;
        }
    }
}
