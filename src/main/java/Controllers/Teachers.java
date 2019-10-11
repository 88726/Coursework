package Controllers;

import Server.Main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into teachers (teacherTitle, teacherPassword) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String teacherTitle, String teacherPassword, int teacherID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE teachers SET teacherTitle = ?, teacherPassword = ? WHERE teacherID = ?");

            ps.setString(1, teacherTitle);
            ps.setString(2, teacherPassword);
            ps.setInt(3, teacherID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int teacherID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM teachers WHERE teacherID = ?");

            ps.setInt(1, teacherID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

}

