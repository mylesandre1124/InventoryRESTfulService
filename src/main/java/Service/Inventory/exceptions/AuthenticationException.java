package Service.Inventory.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by Myles on 10/24/17.
 */
public class AuthenticationException extends WebApplicationException {

    public AuthenticationException() {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity("Failed to get Authentication Token with credentials provided. ")
                .build());
    }
}
