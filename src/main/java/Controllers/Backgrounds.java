import javax.ws.rs.GET;
import javax.ws.rs.POST;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Backgrounds {

    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public String read() {
        System.out.println("backgrounds/read");
        JSONArray read = new JSONArray();
        try {
            PreparedStatement ps = Main.db.prepareStatement("SELECT backgroundID, backgroundPrice, backgroundImage FROM backgrounds");

            ResultSet results = ps.executeQuery();
            while (results.next()) {
                JSONObject item = new JSONObject();
                item.put("id", results.getInt(1));
                item.put("backgroundPrice", results.getString(2));
                item.put("backgroundImage",results.getString(3));
                read.add(item);

            }
            return read.toString();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    @POST
    @Path("new")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public static void insert() {

        try {
            PreparedStatement ps = Main.db.prepareStatement("INSERT into backgrounds (backgroundPrice, backgroundImage) VALUES (?,?)");

            ps.setString(1, "hannah123");
            ps.setString(2, "noThankYou");

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void update(String backgroundPrice, String backgroundImage, int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("UPDATE backgrounds SET backgroundPrice = ?, backgroundImage = ? WHERE backgroundID = ?");

            ps.setString(1, backgroundPrice);
            ps.setString(2, backgroundImage);
            ps.setInt(3, backgroundID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }

    public static void delete(int backgroundID) {

        try {
            PreparedStatement ps = Main.db.prepareStatement("DELETE FROM backgrounds WHERE backgroundID = ?");

            ps.setInt(1, backgroundID);

            ps.executeUpdate();
        } catch (Exception exception) {
            System.out.println("Database error" + exception.getMessage());
        }

    }
}
