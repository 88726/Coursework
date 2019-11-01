package Controllers;

import Server.Main;
import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Backgrounds {

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public String read() {

        System.out.println("backgrounds/read");
        JSONArray read = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT backgroundID, backgroundPrice, backgroundImage FROM backgrounds");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("backgroundPrice", results.getString(2));
                item.put("backgroundImage", results.getString(3));
                read.add(item);

            }
            return read.toString();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return null;
        }

    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into backgrounds (backgroundPrice, backgroundImage, backgroundID) VALUES (?,?,?)");

            ps.setInt(1, 2);
            ps.setString(2, "image.jpg");
            ps.setInt(2, 2);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@FormDataParam("backgroundPrice") String backgroundPrice, @FormDataParam("backgroundImage") String backgroundImage, @FormDataParam("backgroundID") Integer  backgroundID) {

        try {
            if (backgroundPrice == null || backgroundImage == null || backgroundImage == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + backgroundID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE backgrounds SET backgroundPrice = ?, backgroundImage = ? WHERE backgroundID = ?");

            /*The following code will update the entity with the ID specified from the backgrounds table to have the attributes that the user asks for*/
            ps.setString(1, backgroundPrice);
            ps.setString(2, backgroundImage);
            ps.setInt(3, backgroundID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    public String delete(@FormDataParam("backgroundID") Integer backgroundID) {

        try {
            if (backgroundID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + backgroundID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM backgrounds WHERE backgroundID = ?");

            ps.setInt(1, backgroundID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";

        }

    }
}
