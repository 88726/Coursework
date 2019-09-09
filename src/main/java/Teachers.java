import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Teachers {

    public static void read() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT teacherID, teacherName, teacherPassword FROM teachers");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                int teacherID = results.getInt(1);
                String teacherName = results.getString(2);
                String teacherPassword = results.getString(3);
                System.out.println(teacherID + " " + teacherName + " " + teacherPassword);

            }
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into teachers (teacherName, teacherPassword) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String teacherName, String teacherPassword, int teacherID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE teachers SET teacherName = ?, teacherPassword = ? WHERE teacherID = ?");

            ps.setString(1, teacherName);
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

