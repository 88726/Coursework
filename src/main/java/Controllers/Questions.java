package Controllers;

import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("question/")
public class Questions {


//This turns the method into a HTTP request handler
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String read() {

            System.out.println("question/list");
            JSONArray read = new JSONArray();
            try {
                PreparedStatement ps = Main.db.prepareStatement("SELECT questionID, question, answer FROM questions");

                ResultSet results = ps.executeQuery();

                /*The following code will extract the question ID, the question and the answer from each row of the questions table*/
                while (results.next()) {
                    //A JSON array is constructed with the values from the SQL query
                    JSONObject item = new JSONObject();
                    item.put("questionID", results.getInt(1));
                    item.put("question", results.getString(2));
                    item.put("answer", results.getString(3));
                    read.add(item);

                }
                return read.toString();
                /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
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
    public String insert(@FormDataParam("questionID") Integer questionID, @FormDataParam("question") String question, @FormDataParam("answer") String answer,  @FormDataParam("teacherID") String teacherID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into questions (questionID, question, answer, teacherID) VALUES (?, ? ,?, ?)");

            ps.setInt(1, questionID);
            ps.setString(2, question );
            ps.setString(3, answer);
            ps.setString(4, teacherID);

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
    public String update(@FormDataParam("question") String question, @FormDataParam("answer")  String answer, @FormDataParam("questionID") Integer questionID) {

        try {
            //This stops null data from being added to the database
            if (questionID == null || question == null || answer == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }

            System.out.println("thing/update id=" + questionID);
            PreparedStatement ps = Main.db.prepareStatement("UPDATE questions SET question = ?, answer = ? WHERE questionID = ?");

            ps.setString(1, question);
            ps.setString(2, answer);
            ps.setInt(3, questionID);

            ps.executeUpdate();
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

    public String delete(@FormDataParam("questionID") Integer questionID) {

        try {
            //This stops null data from being a delete request
            if (questionID == null) {
                throw new Exception("One or more form data parameters are missing in the HTTP request.");
            }
            System.out.println("thing/delete id=" + questionID);

            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM questions WHERE questionID = ?");

            ps.setInt(1, questionID);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";
        }

    }
}
