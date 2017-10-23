package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.model.Credentials;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Myles on 10/21/17.
 */
public class AuthorizationService {

    private Database database;
    private Credentials credentials;
    private static Statement statement;

    public AuthorizationService() {
        database = new Database(1);
        statement = database.getStatement();
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String login()
    {
        try {
            ResultSet set = statement.executeQuery("SELECT password from employee.employee where username = '" + getCredentials().getUsername() + "'");
            while (set.next())
            {
                if(set.getString(1).equals(getCredentials().getPassword()))
                {
                    return "yes";
                }
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "no";
    }

    public static void main(String[] args) {
        AuthorizationService service = new AuthorizationService();
        Credentials credentials = new Credentials();
        credentials.setUsername("mylesandre1124");
        credentials.setPassword("megamacman11");
        service.setCredentials(credentials);
        System.out.println(service.login());
    }

}
