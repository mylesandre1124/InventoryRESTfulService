package Service.Inventory.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ServerException extends WebApplicationException {

    public ServerException() {
        super(Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("There was a problem with the server.")
                .build());
    }
}
