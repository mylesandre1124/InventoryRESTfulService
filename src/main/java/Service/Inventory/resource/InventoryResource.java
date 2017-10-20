package Service.Inventory.resource;

import Service.Inventory.model.Inventory;
import Service.Inventory.model.ObjectIO;
import Service.Inventory.service.InventoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

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
    public Inventory getInventory(@PathParam("barcode") long barcode)
    {
        return inventoryService.getInventory(barcode);
    }

    @GET
    public ArrayList<Inventory> getAllInventory()
    {
        //ArrayList<Inventory> inventoryArrayList = inventoryService.getAllInventory();
        //return inventoryArrayList;
        ArrayList<Inventory> inventories = new ArrayList<>();
        Inventory inventory = new Inventory();
        inventory.setName("Myles");
        inventories.add(inventory);
        return inventories;
    }

    @GET
    @Path("/{barcode}/add")
    public Inventory add(@PathParam("barcode") long barcode)
    {
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
