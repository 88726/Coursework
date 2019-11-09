package Controllers;

import Server.Main;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("teacher/")
public class Teachers {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teacherID, teacherTitle, teacherPassword FROM teachers");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int teacherID = results.getInt(1);
                String teacherTitle = results.getString(2);
                String teacherPassword = results.getString(3);
                System.out.println(teacherID + " " + teacherTitle + " " + teacherPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    //This turns the method into a HTTP request handler
    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String insert(@FormDataParam("teacherID") Integer teacherID, @FormDataParam("teacherTitle") String teacherTitle, @FormDataParam("teacherPassword") String teacherPassword) {

        try {

//This stops null data from being added to the database
            if (teacherID == null || teacherTitle == null || teacherPassword == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            PreparedStatement ps = Main.db.prepareStatement("INSERT into teachers (teacherID, teacherTitle, teacherPassword) VALUES (?, ?,?)");

            ps.setInt(1, teacherID);
            ps.setString(2, teacherTitle);
            ps.setString(3, teacherPassword);

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
    public String update(@FormDataParam("teacherTitle") String teacherTitle, @FormDataParam("teacherPassword") String teacherPassword, @FormDataParam("teacherID") Integer teacherID) {

        try {
            //This stops null data from being added to the database
            if (teacherTitle == null || teacherPassword == null || teacherID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + teacherID);

            PreparedStatement ps = Main.db.prepareStatement("UPDATE teachers SET teacherTitle = ?, teacherPassword = ? WHERE teacherID = ?");

            /*The following code will update the entity with the ID specified from the backgrounds table to have the attributes that the user asks for*/
            ps.setString(1, teacherTitle);
            ps.setString(2, teacherPassword);
            ps.setInt(3, teacherID);

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
    public String delete(@FormDataParam("teacherID") Integer teacherID) {

        try {
            //This stops null data from being a delete request
            if (teacherID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + teacherID);

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

}

