import java.io.File;
import java.security.PrivilegedExceptionAction;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

public class SybaseDatabaseConnectionKerberos {
    private Connection conn = null;
    private static String cwd = System.getProperty("user.dir");
    
    public static void main(String[] args) {
        if(args.length < 5) {
            System.out.println("Usage: java -cp <Full path to jconn jar>;. SybaseDatabaseConnectionKerberos <Sybase Server> <Sybase Port> <Database name> <Service Principal Name> <Query>");
            return;
        }
        SybaseDatabaseConnectionKerberos sdck = new SybaseDatabaseConnectionKerberos();

        String hostname = args[0];
        String port = args[1];
        String databasename = args[2];
        String servicePrincipalName = args[3];
        String query = args[4];
        
        String jaasConfig = cwd + File.separator + "JAAS.conf";
        String url = "jdbc:sybase:Tds:" + hostname + ":" + port + "/"  + databasename;
        
        Properties prop = new Properties();
        prop.setProperty("DO_EXTERNAL_AUTH", "true");
        prop.setProperty("REQUEST_KERBEROS_SESSION", "true");
        prop.setProperty("SERVICE_PRINCIPAL_NAME", servicePrincipalName);
        
        System.setProperty("java.security.auth.login.config", jaasConfig);
        try{ 
            Class.forName("com.sybase.jdbc4.jdbc.SybDriver");
            System.out.println("Initializing Login context");
            LoginContext lc = new LoginContext("initiate");
            lc.login();
            System.out.println("Login context initialized Successfully");
            
            System.out.println("Connecting with " + url);
            PrivilegedActionImp pa = sdck.new PrivilegedActionImp(url, prop);
            Subject.doAs(lc.getSubject(), pa);
            System.out.println("Connection Successful");
            
            System.out.println("Running query: " + query);
            Statement stmt= sdck.conn.createStatement();  
            ResultSet rs=stmt.executeQuery(query);  
            while(rs.next())
            {
                System.out.println(rs.getString(1));
            }
            sdck.conn.close();  
        } catch(LoginException le) {
            System.out.println("JAAS authentication failed with exception");
            le.printStackTrace();
        } catch(Exception e){ 
            System.out.println(e);
            e.getStackTrace();
        }  
    }
    
    class PrivilegedActionImp implements PrivilegedExceptionAction<Object>{
        private final String url;
        private final Properties prop;

        PrivilegedActionImp(String url, Properties prop){
            this.url = url;
            this.prop = prop;
        }

        @Override
        public Object run() throws SQLException {
            try {
                conn = DriverManager.getConnection(url, prop);
            } catch (SQLException sqe) {
                sqe.printStackTrace();
            }
            return null;
        }
    }
}
