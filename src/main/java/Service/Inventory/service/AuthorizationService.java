package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.exceptions.AuthenticationException;
import Service.Inventory.exceptions.ServerException;
import Service.Inventory.model.Credentials;

import java.sql.CallableStatement;
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

    public String getAuthenticationToken()
    {
        String authorizationToken = "";
        try {
            CallableStatement authenticate = loginToDatabase();
            authenticate.execute();
            ResultSet set = authenticate.getResultSet();
            try {
                while (set.next()) {
                    authorizationToken = set.getString(1);
                }
            }
            catch (NullPointerException ex)
            {
                throw new AuthenticationException();
            }
            set.close();
            return authorizationToken;
        } catch (SQLException e) {
            e.printStackTrace();
            return authorizationToken;
        }
    }

    public CallableStatement loginToDatabase()
    {
        CallableStatement authenticate = null;
        try {
            authenticate = statement.getConnection().prepareCall("{call login(?, ?)}");
            authenticate.setString("usernameToCheck", credentials.getUsername());
            authenticate.setString("passwordToCheck", credentials.getPassword());
            return authenticate;
        } catch (SQLException e) {
            e.printStackTrace();
            return authenticate;
        }
    }

    public boolean checkToken(String token) throws ServerException {
        CallableStatement empIdProcedure = null;
        boolean correctToken = false;
        try {
            empIdProcedure = statement.getConnection().prepareCall("{call getEmpId(?)}");
            empIdProcedure.setString("token", token);
            empIdProcedure.execute();
            ResultSet set = empIdProcedure.getResultSet();
            int empId = -1;
            while (set.next())
            {
                empId = set.getInt(1);
            }
            if(empId != -1)
            {
                correctToken = true;
            }
        }
        catch (SQLException ex)
        {
            //throw new ServerException();
        }
        return correctToken;
    }


    public static void main(String[] args) {
        AuthorizationService service = new AuthorizationService();
        boolean log = service.checkToken("91836a35c0d311e7a98742010a80054491836a4dc0d311e7a98742010a80054491836a67c0d311e7a98742010a800544");
        System.out.println(log);
    }

}
