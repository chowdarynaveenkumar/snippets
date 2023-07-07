import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

public class SybaseDatabaseConnection {

    public static void main(String[] args) {
        if(args.length < 6) {
            System.out.println("Usage: java -cp <Full path to jconn jar>;. SybaseDatabaseConnection <Sybase Server> <Sybase Port> <Database name> <User Name> <Password> <Query>");
            return;
        }
        String hostname = args[0];
        String port = args[1];
        String databasename = args[2];
        String query = args[5];

        Properties conProps = new Properties();
        conProps.put("user", args[3]);
        conProps.put("password", args[4]);

        try{  
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");  

            String url = "jdbc:sybase:Tds:" + hostname + ":" + port + "/"  + databasename;
            System.out.println("Connecting with " + url);
            Connection con=DriverManager.getConnection(url,conProps);
            System.out.println("Connection Successful");

            System.out.println("Running query: " + query);
            Statement stmt=con.createStatement();  
            ResultSet rs=stmt.executeQuery(query);  
            while(rs.next())
            {
                System.out.println(rs.getString(1));
            }
            con.close();  
        } catch(Exception e){ 
            System.out.println(e);
            e.getStackTrace();
        }
    }  
}
