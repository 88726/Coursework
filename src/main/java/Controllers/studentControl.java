package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;


    @Path("student/")
    public class studentControl {
        @POST
        @Path("login")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String loginUser(@FormDataParam("studentName") String studentName, @FormDataParam("studentPassword") String studentPassword) {

            try {

                System.out.println("student/login");

                PreparedStatement ps1 = Main.db.prepareStatement("SELECT studentPassword FROM students WHERE studentName = ?");
                ps1.setString(1, studentName);
                ResultSet loginResults = ps1.executeQuery();
                if (loginResults.next()) {

                    String correctPassword = loginResults.getString(1);


                    if (studentPassword.equals(correctPassword)) {

                    String token = UUID.randomUUID().toString();
                        System.out.println("you did it");
                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE students SET token = ? WHERE studentName = ?");
                    ps2.setString(1, token);
                    ps2.setString(2, studentName);
                    ps2.executeUpdate();

                    JSONObject userDetails = new JSONObject();

                    userDetails.put("studentName", studentName);
                    userDetails.put("token", token);
                        System.out.println("you did it again");
                    return userDetails.toString();

                } else {
                    return "{\"error\": \"Incorrect password!\"}";
                }

            } else {

                return "{\"error\": \"Unknown user!\"}";
            }

        } catch (Exception exception){
            System.out.println("Database error during /student/login: " + exception.getMessage());
            return "{\"error\": \"Server side error!\"}";
        }
    }
        @POST
        @Path("logout")
        @Consumes(MediaType.MULTIPART_FORM_DATA)
        @Produces(MediaType.APPLICATION_JSON)
        public String logoutUser(@CookieParam("token") String token) {

            try {

                System.out.println("student/logout");

                PreparedStatement ps1 = Main.db.prepareStatement("SELECT studentID FROM students WHERE token = ?");
                ps1.setString(1, token);
                ResultSet logoutResults = ps1.executeQuery();
                if (logoutResults.next()) {

                    int id = logoutResults.getInt(1);

                    PreparedStatement ps2 = Main.db.prepareStatement("UPDATE students SET token = NULL WHERE studentID = ?");
                    ps2.setInt(1, id);
                    ps2.executeUpdate();

                    return "{\"status\": \"OK\"}";
                } else {

                    return "{\"error\": \"Invalid token!\"}";

                }

            } catch (Exception exception){
                System.out.println("Database error during /user/logout: " + exception.getMessage());
                return "{\"error\": \"Server side error!\"}";
            }

        }

        public static boolean validToken(String token) {
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT studentID FROM students WHERE token = ?");
                ps.setString(1, token);
                ResultSet logoutResults = ps.executeQuery();
                return logoutResults.next();
            } catch (Exception exception) {
                System.out.println("Database error during /student/logout: " + exception.getMessage());
                return false;
            }
        }
    }
