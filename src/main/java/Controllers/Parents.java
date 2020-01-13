package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


@Path("parent/")
public class Parents {

    //This turns the method into a HTTP request handler
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String list() {

        System.out.println("parent/list");
        JSONArray list = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT parentID, parentName, parentPassword FROM parents");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                //A JSON array is constructed with the values from the SQL query
                JSONObject item = new JSONObject();
                item.put("parentID", results.getInt(1));
                item.put( "parentName", results.getString(2));
                item.put("parentPassword", results.getString(3));

                list.add(item);
            }
            return list.toString();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";

        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("add")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String insert(@CookieParam("token") String token, @FormDataParam("parentID") Integer parentID, @FormDataParam("parentName")String parentName, @FormDataParam("parentPassword") String parentPassword) {

        try {
            //This stops null data from being added to the database
            if (parentID == null || parentName == null || parentPassword == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT into parents (parentID, parentName, parentPassword) VALUES (?, ?,?)");

            ps.setInt(1, parentID);
            ps.setString(2, parentName);
            ps.setString(3, parentPassword);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";

        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String update(@CookieParam("token") String token, @FormDataParam("parentName")String parentName, @FormDataParam("parentPassword")String parentPassword, @FormDataParam("parentID") Integer parentID) {

        try {

//This stops null data from being added to the database
            if (parentName == null || parentPassword == null || parentID  == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + parentID);
                PreparedStatement ps = Main.db.prepareStatement("UPDATE parents SET parentName = ?, parentPassword = ? WHERE parentID = ?");

            ps.setString(1, parentName);
            ps.setString(2, parentPassword);
            ps.setInt(3, parentID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("delete")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String delete(@CookieParam("token") String token, @FormDataParam("parentID") Integer parentID) {
        if (!studentControl.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {
            //This stops null data from being a delete request
            if (parentID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM parents WHERE parentID = ?");

            ps.setInt(1, parentID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }

    }
}
