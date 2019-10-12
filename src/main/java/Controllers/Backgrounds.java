package Controllers;

import Server.Main;
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

    public static void update(String backgroundPrice, String backgroundImage, int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE backgrounds SET backgroundPrice = ?, backgroundImage = ? WHERE backgroundID = ?");

            /*The following code will update the entity with the ID specified from the backgrounds table to have the attributes that the user asks for*/
            ps.setString(1, backgroundPrice);
            ps.setString(2, backgroundImage);
            ps.setInt(3, backgroundID);

            ps.executeUpdate();

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM backgrounds WHERE backgroundID = ?");

            ps.setInt(1, backgroundID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
