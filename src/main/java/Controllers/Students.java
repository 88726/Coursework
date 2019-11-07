package Controllers;

import com.sun.jersey.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("student/")
public class Students {

    //This turns the method into a HTTP request handler
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String list() {

        System.out.println("backgrounds/list");
        JSONArray list = new JSONArray();

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT studentID, studentName, studentPassword FROM students");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int studentID = results.getInt(1);
                String studentName = results.getString(2);
                String studentPassword = results.getString(3);
                System.out.println(studentID + " " + studentName + " " + studentPassword);

                //A JSON array is constructed with the values from the SQL query
                JSONObject item = new JSONObject();
                item.put("studentID", results.getInt(1));
                item.put("studentName", results.getString(2));
                item.put("studentPassword", results.getString(3));
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
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String insert(@FormDataParam("studentName") String studentName,@FormDataParam("studentPassword") String studentPassword, @FormDataParam("studentID") Integer studentID) {

        try {
            //This stops null data from being added to the database
            if (studentName == null || studentPassword == null || studentID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT into students (studentName, studentPassword, studentID) VALUES (?,?,?)");

            /*The following code will enter a new entity with the attributes specified into the students table*/
            ps.setString(1, studentName );
            ps.setString(2, studentPassword);
            ps.setInt(3, studentID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";
        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("update")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String update(@FormDataParam("studentName") String studentName, @FormDataParam("studentPassword") String studentPassword, @FormDataParam("StudentID") Integer studentID) {

        try {
            if ( studentName == null || studentPassword == null || studentID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE students SET studentName = ?, studentPassword = ? WHERE studentID = ?");

            ps.setString(1, studentName );
            ps.setString(2, studentPassword);
            ps.setInt(3, studentID);

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

    public String delete(@FormDataParam("studentID") Integer studentID) {

        try {
            //This stops null data from being a delete request
            if (studentID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + studentID);
                PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM students WHERE studentID = ?");

            ps.setInt(1, studentID );

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";

        }

    }
}

