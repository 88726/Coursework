package Controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Students {

    public static void read() {

        try {
            PreparedStatement ps = Server.Main.db.prepareStatement("SELECT studentID, studentName, studentPassword FROM students");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int studentID = results.getInt(1);
                String studentName = results.getString(2);
                String studentPassword = results.getString(3);
                System.out.println(studentID + " " + studentName + " " + studentPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
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

