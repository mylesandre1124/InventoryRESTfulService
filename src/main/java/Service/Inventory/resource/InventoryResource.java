package Service.Inventory.resource;

import Service.Inventory.model.Inventory;
import Service.Inventory.service.InventoryService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Myles on 2/25/17.
 */
@Path("/Inventory")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class InventoryResource {

    TreeMap<Long, Inventory> inventoryTreeMap = new TreeMap<>();

    public InventoryResource() {
        Inventory inventory = new Inventory();
        inventory.setCount(5);
        inventory.setName("Macbook");
        inventory.setBarcode(11241996);
        inventory.setPrice(1500.00);
        inventory.setVendor("Apple");
        Inventory inventory1 = new Inventory();
        inventory1.setName("Windows");
        inventory1.setCount(3);
        inventory1.setBarcode(5141998);
        inventory1.setPrice(299.99);
        inventory1.setVendor("Microsoft");
        inventoryTreeMap.put(inventory.getBarcode(), inventory);
        inventoryTreeMap.put(inventory1.getBarcode(), inventory1);
    }
    /*InventoryService inventoryService = new InventoryService();


    @GET
    @Path("/{barcode}")
    public Inventory getInventory(@PathParam("barcode") long barcode)
    {
        return inventoryService.getInventory(barcode);
    }*/

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
        Inventory inventory = inventoryTreeMap.get(barcode);
        int count = inventory.getCount();
        System.out.println(barcode);
        inventory.setCount(++count);
        inventoryTreeMap.put(inventory.getBarcode(), inventory);
        return inventory;
    }

    /*@DELETE
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
    */
}
