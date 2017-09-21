package Service.Inventory.resource;

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
    public Inventory getInventory(@PathParam("barcode") int barcode)
    {
        return inventoryService.getInventory(barcode);
    }

    @GET
    public ArrayList<Inventory> getAllInventory()
    {
        ArrayList<Inventory> inventoryArrayList = inventoryService.getAllInventory();
        return inventoryArrayList;
    }

    @DELETE
    @Path("/{barcode}")
    public void deleteInventory(@PathParam("barcode") int barcode)
    {
        inventoryService.removeInventory(barcode);
    }

    @PUT
    @Path("/{barcode}")
    public Inventory updateInventory(@PathParam("barcode") int barcode, Inventory inventory)
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
