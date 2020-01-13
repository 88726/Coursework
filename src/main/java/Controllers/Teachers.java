package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("teacher/")
public class Teachers {

    //This turns the method into a HTTP request handler
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String list() {

        System.out.println("teacher/list");
        JSONArray list = new JSONArray();

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teacherID, teacherTitle, teacherPassword, teacherSurname FROM teachers");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                //A JSON array is constructed with the values from the SQL query
                JSONObject item = new JSONObject();
                item.put("teacherID", results.getInt(1));
                item.put("teacherTitle", results.getString(2));
                item.put("teacherPassword", results.getString(3));
                item.put("teacherSurname", results.getString(4));
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
    public String insert(@CookieParam("token") String token, @FormDataParam("teacherID") Integer teacherID, @FormDataParam("teacherTitle") String teacherTitle, @FormDataParam("teacherPassword") String teacherPassword, @FormDataParam("teacherSurname") String teacherSurname) {
        if (!teacherControl.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {

//This stops null data from being added to the database
            if (teacherID == null || teacherTitle == null || teacherPassword == null || teacherSurname == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT into teachers (teacherID, teacherTitle, teacherPassword, teacherSurname) VALUES (?, ?, ?, ?)");

            ps.setInt(1, teacherID);
            ps.setString(2, teacherTitle);
            ps.setString(3, teacherPassword);
            ps.setString(4, teacherSurname);

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
    public String update(@CookieParam("token") String token, @FormDataParam("teacherTitle") String teacherTitle, @FormDataParam("teacherPassword") String teacherPassword, @FormDataParam("teacherID") Integer teacherID, @FormDataParam("teacherSurname") String teacherSurname) {
        if (!teacherControl.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {
            //This stops null data from being added to the database
            if (teacherTitle == null || teacherPassword == null || teacherID == null || teacherSurname == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + teacherID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE teachers SET teacherTitle = ?, teacherPassword = ?, teacherSurname = ? WHERE teacherID = ? ");

            /*The following code will update the entity with the ID specified from the backgrounds table to have the attributes that the user asks for*/
            ps.setString(1, teacherTitle);
            ps.setString(2, teacherPassword);
            ps.setInt(3, teacherID);
            ps.setString(4, teacherSurname);

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
    public String delete(@CookieParam("token") String token, @FormDataParam("teacherID") Integer teacherID) {
        if (!teacherControl.validToken(token)) {
            return "{\"error\": \"You don't appear to be logged in.\"}";
        }

        try {
            //This stops null data from being a delete request
            if (teacherID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("teachers/delete id=" + teacherID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM teachers WHERE teacherID = ?");

            /*The following code will delete the entity with the ID specified from the teachers table*/
            ps.setInt(1, teacherID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }

    }


    //This turns the method into a HTTP request handler
    @GET
    @Path("get/{token}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String get(@PathParam("token") Integer token) {

        System.out.println("teacher/get/" + token);
        JSONObject item = new JSONObject();

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teacherID FROM teachers WHERE token = ?");
            ps.setInt(1, token);
            ResultSet results = ps.executeQuery();

            if (results.next()) {
                // item.put("id", token);
                item.put("teacherID", results.getString(1));

            }
            return item.toString();




        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }
}