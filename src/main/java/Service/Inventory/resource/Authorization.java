package Service.Inventory.resource;

import Service.Inventory.model.Credentials;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Authorization")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class Authorization {

    @POST
    public void authorize(Credentials credentials)
    {
        credentials.getUsername();
        credentials.getPassword();
    }

}
