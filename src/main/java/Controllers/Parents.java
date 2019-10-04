package Controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Parents {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT parentID, parentName, parentPassword FROM parents");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int parentID = results.getInt(1);
                String parentName = results.getString(2);
                String parentPassword = results.getString(3);
                System.out.println(parentID + " " + parentName + " " + parentPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into parents (parentName, parentPassword) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String parentName, String parentPassword, int parentID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE parents SET parentName = ?, parentPassword = ? WHERE parentID = ?");

            ps.setString(1, parentName);
            ps.setString(2, parentPassword);
            ps.setInt(3, parentID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int parentID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM parents WHERE parentID = ?");

            ps.setInt(1, parentID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
