package Controllers;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


    @Path("points/")
    public class Points {

        //This turns the method into a HTTP request handler
        @GET
        @Path("list")
        @Produces(MediaType.APPLICATION_JSON)

//The method has to be public in order to allow interaction with the Jersey library
        public String list() {

            System.out.println("points/list");
            JSONArray list = new JSONArray();

            try {
                PreparedStatement ps = Server.Main.db.prepareStatement("SELECT points.daysPoints, points.date, students.studentName, students.studentSurnameInitial FROM points inner join students on students.studentID = points.studentID");

                ResultSet results = ps.executeQuery();
                while (results.next()) {
                    int daysPoints = results.getInt(1);
                    String date = results.getString(2);
                    String studentName = results.getString(3);
                    String studentSurnameInitial = results.getString(4);
                    System.out.println(daysPoints + " " + date + " " + studentName + " " + studentSurnameInitial) ;

                    //A JSON array is constructed with the values from the SQL query
                    JSONObject item = new JSONObject();
                    item.put("daysPoints", results.getInt(1));
                    item.put("date", results.getString(2));
                    item.put("studentName", results.getString(3));
                    item.put("studentSurnameInitial", results.getString(4));
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
        public String insert(@CookieParam("token") String token, @FormDataParam("studentName") String studentName, @FormDataParam("studentPassword") String studentPassword, @FormDataParam("studentID") Integer studentID) {
            if (!studentControl.validToken(token)) {
                return "{\"error\": \"You don't appear to be logged in.\"}";
            }

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
        public String update( @CookieParam("token") String token, @FormDataParam("studentName") String studentName, @FormDataParam("studentPassword") String studentPassword, @FormDataParam("StudentID") Integer studentID) {
            if (!studentControl.validToken(token)) {
                return "{\"error\": \"You don't appear to be logged in.\"}";
            }

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

        public String delete( @CookieParam("token") String token, @FormDataParam("studentID") Integer studentID) {
            if (!studentControl.validToken(token)) {
                return "{\"error\": \"You don't appear to be logged in.\"}";
            }

            try {
                //This stops null data from being a delete request
                if (studentID == null) {
                    throw new Exception("One or more form data parameters are missing in the HTTP request.");
                }
                System.out.println("student/delete id=" + studentID);
                PreparedStatement ps = Server.Main.db.prepareStatement("DELETE FROM students WHERE studentID = ?");

                ps.setInt(1, studentID );

                ps.executeUpdate();
                return "{\"status\": \"OK\"}";
            } catch (Exception exception) {
                System.out.println("Database error " + exception.getMessage());
                //This error statement will make debugging easier
                return "{\"error\": \"Unable to delete item, please see server console for more info.\"}";

            }

        }
    }

