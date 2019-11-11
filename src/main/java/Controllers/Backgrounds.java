package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("background/")
public class Backgrounds {

    //This turns the method into a HTTP request handler
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

    //The method has to be public in order to allow interaction with the Jersey library
    public String list() {

        System.out.println("background/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT backgroundID, backgroundPrice, backgroundImage FROM backgrounds");

            ResultSet results = ps.executeQuery();
            while (results.next()) {

                //A JSON array is constructed with the values from the SQL query
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("backgroundPrice", results.getString(2));
                item.put("backgroundImage", results.getString(3));
                list.add(item);

            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());

            //This error statement will make debugging easier
            return "{\"error\": \"Unable to list items, please see server console for more info.\"}";

        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

    //The method has to be public in order to allow interaction with the Jersey library
    public String insert(@FormDataParam("backgroundPrice") Integer backgroundPrice, @FormDataParam("backgroundImage") String backgroundImage, @FormDataParam("backgroundID") Integer backgroundID){

        try {

            //This stops null data from being added to the database
            if (backgroundPrice == null || backgroundImage == null || backgroundID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + backgroundID);

            PreparedStatement ps = Main.db.prepareStatement("INSERT into backgrounds (backgroundPrice, backgroundImage, backgroundID) VALUES (?,?,?)");


            ps.setInt(1, backgroundPrice);
            ps.setString(2, backgroundImage);
            ps.setInt(3, backgroundID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }

    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String update(@FormDataParam("backgroundPrice") String backgroundPrice, @FormDataParam("backgroundImage") String backgroundImage, @FormDataParam("backgroundID") Integer  backgroundID) {

        try {
            if (backgroundPrice == null || backgroundImage == null || backgroundID == null) {
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
