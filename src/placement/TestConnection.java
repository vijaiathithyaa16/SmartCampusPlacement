package placement;

public class TestConnection {
    public static void main(String[] args) {
        try {
            var con = DBConnection.getConnection();
            System.out.println("✅ Connected successfully!");
            con.close();
        } catch (Exception e) {
            System.out.println("❌ Connection failed:");
            e.printStackTrace();
        }
    }
}