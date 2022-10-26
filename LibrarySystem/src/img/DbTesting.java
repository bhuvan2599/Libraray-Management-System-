package img;
import java.sql.*;
import java.time.LocalDate;

// 1. Load the driver     2. Establish the Connection   3. Create a Statement object   4. Execute a query   5. Process the results   6. Close the connection

public class DbTesting {
    static Connection con;
    void createConnection() throws SQLException
    {
        try
        {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Library","root","root");
            System.out.println("DB Connected...!!");
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    void executeQuery() throws SQLException
    {
        String bid ="LON2022";
        String uid = "VAMSI001";
        Statement statement = con.createStatement();
        String dateQuery = String.format("select dueDate from transactions where bookId ='%s' and userId='%s';", bid,uid);
        ResultSet res = statement.executeQuery(dateQuery);
        res.next();
//        System.out.println(res.getDate(1));
        Date cDate = res.getDate(1);
        System.out.println(cDate+" , "+((Object) cDate).getClass().getSimpleName());

        LocalDate pDate = cDate.toLocalDate();
        System.out.println(pDate+" , "+((Object) pDate).getClass().getSimpleName());

    }
    public static void main(String[] args) throws SQLException
    {
        DbTesting test = new DbTesting();
        test.createConnection();
        test.executeQuery();
        con.close();
    }
}
