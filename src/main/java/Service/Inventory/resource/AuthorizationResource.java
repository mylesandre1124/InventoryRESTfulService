package Service.Inventory.resource;

import Service.Inventory.model.Credentials;
import Service.Inventory.service.AuthorizationService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/Authorization")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class AuthorizationResource {

    AuthorizationService authorizationService = new AuthorizationService();

    @POST
    public String authorize(Credentials credentials)
    {
        authorizationService.setCredentials(credentials);
        String authenticationToken = authorizationService.getAuthenticationToken();
        return authenticationToken;
    }

    @GET
    public void checkToken(@HeaderParam("Authorization") String token)
    {

    }

}
