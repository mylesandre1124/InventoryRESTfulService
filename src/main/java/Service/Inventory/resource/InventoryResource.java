package Service.Inventory.resource;

import Service.Inventory.exceptions.ItemAlreadyScannedException;
import Service.Inventory.model.Inventory;
import Service.Inventory.service.InventoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Myles on 2/25/17.
 */
@Path("/Inventory")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InventoryResource {

    InventoryService inventoryService = new InventoryService();

    @GET
    @Path("/{barcode}")
    public void getInventory(@PathParam("barcode") long barcode)
    {
        throw new ItemAlreadyScannedException("Myles", 5);
        //return inventoryService.getInventory(barcode);
    }

    @GET
    public ArrayList<Inventory> getAllInventory()
    {
        ArrayList<Inventory> inventoryArrayList = inventoryService.getAllInventory();
        return inventoryArrayList;
    }

    @GET
    @Path("/{barcode}/add")
    public Inventory add(@HeaderParam("Authorization") String authorization, @PathParam("barcode") long barcode) throws Exception {
        inventoryService.setAuthorizationToken(authorization);
        Inventory inventory = inventoryService.getInventory(barcode);
        int count = inventory.getCount();
        inventory.setCount(++count);
        return inventoryService.updateInventory(barcode, inventory);
    }

    @DELETE
    @Path("/{barcode}")
    public void deleteInventory(@PathParam("barcode") long barcode)
    {
        inventoryService.removeInventory(barcode);
    }

    @PUT
    @Path("/{barcode}")
    public Inventory updateInventory(@PathParam("barcode") long barcode, Inventory inventory)
    {
        inventory.setBarcode(barcode);
        return inventoryService.updateInventory(barcode, inventory);
    }

    @POST
    public Inventory createInventory(Inventory inventory)
    {
        return inventoryService.createInventory(inventory);
    }
}
