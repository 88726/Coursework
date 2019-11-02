package Controllers;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Path("student/")
public class Students {

    //This turns the method into a HTTP request handler
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
    public String read() {

        System.out.println("backgrounds/read");
        JSONArray read = new JSONArray();

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
                read.add(item);

            }
            return read.toString();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
            //This error statement will make debugging easier
            return "{\"error\": \"Unable to update item, please see server console for more info.\"}";
        }

    }

    public static void insert(String studentName, String studentPassword, int studentID) {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("INSERT into students (studentName, studentPassword, studentID) VALUES (?,?,?)");

            /*The following code will enter a new entity with the attributes specified into the students table*/
            ps.setString(1, studentName );
            ps.setString(2, studentPassword);
            ps.setInt(3, studentID);

            ps.executeUpdate();

            /*If there is an error, the following code will identify this and will display an error message rather than crashing the program*/
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
    public static void update(String studentName, String studentPassword, int studentID) {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("UPDATE students SET studentName = ?, studentPassword = ? WHERE studentID = ?");

            ps.setString(1, studentName );
            ps.setString(2, studentPassword);
            ps.setInt(3, studentID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
    public static void delete(int studentID) {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM students WHERE studentID = ?");

            ps.setInt(1, studentID );

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}

