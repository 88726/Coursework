package Controllers;

import Server.Main;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Questions {


//This turns the method into a HTTP request handler
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String read() {

            System.out.println("backgrounds/read");
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
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)

//The method has to be public in order to allow interaction with the Jersey library
    public String insert(@FormDataParam("questionID") Integer questionID, @FormDataParam("question") String question, @FormDataParam("answer") String answer) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into questions (questionID, question, answer) VALUES (?, ? ,?)");

            ps.setInt(1, questionID);
            ps.setString(1, question );
            ps.setString(2, answer);

            ps.executeUpdate();
            return "{\"status\": \"OK\"}";

        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to create new item, please see server console for more info.\"}";

        }

    }

    public static void update(String question, String answer, int questionID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE questions SET question = ?, answer = ? WHERE questionID = ?");

            ps.setString(1, question);
            ps.setString(2, answer);
            ps.setInt(3, questionID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int questionID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM questions WHERE questionID = ?");

            ps.setInt(1, questionID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
