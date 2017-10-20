package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.model.Inventory;
import Service.Inventory.model.ObjectIO;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Myles on 2/25/17.
 */
public class InventoryService {

    private static Statement statement;
    private Database database;

    public InventoryService()  {
        database = new Database(0);
        statement = database.getStatement();
    }

    public ArrayList<Inventory> getAllInventory()
    {
        ArrayList<Inventory> inventoryArrayList = new ArrayList<>();
        ResultSet set = null;
        try {
            set = getStatement().executeQuery(
                    "SELECT * FROM inventory");
            while(set.next()) {
                Inventory inventory = new Inventory();
                inventory.setBarcode(set.getLong(1));
                inventory.setName(set.getString(2));
                inventory.setCount(set.getInt(3));
                inventory.setVendor(set.getString(4));
                inventory.setPrice(set.getDouble(5));
                inventory.setEmpId(set.getInt(6));
                inventoryArrayList.add(inventory);
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventoryArrayList;
    }


    public Inventory getInventory(long barcode)  {
        Inventory inventory = new Inventory();
        ResultSet set = null;
        try {
            set = getStatement().executeQuery(
                    "SELECT * FROM inventory where barcode = " + barcode);
        while(set.next()) {
                inventory.setBarcode(set.getInt(1));
                inventory.setName(set.getString(2));
                inventory.setCount(set.getInt(3));
                inventory.setVendor(set.getString(4));
                inventory.setPrice(set.getDouble(5));
                inventory.setEmpId(set.getInt(6));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return inventory;
    }

    public void removeInventory(long barcode)
    {
        try {
            getStatement().executeUpdate("DELETE FROM inventory WHERE barcode = " + barcode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Inventory updateInventory(long barcode, Inventory inventory)
    {
        Inventory inventory1 = null;
        try {
            getStatement().executeUpdate("UPDATE inventory " +
                    "SET name = '" + inventory.getName() + "', " +
                    "count = " + inventory.getCount() + ", " +
                    "vendor = '" + inventory.getVendor() + "', " +
                    "price = " + inventory.getPrice() + ", " +
                    "empId = " + inventory.getEmpId() + " " +
                    "WHERE barcode = " + barcode);
            inventory1 = getInventory(barcode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory1;
    }

    public Inventory createInventory(Inventory inventory)
    {
        Inventory inventory1 = null;
        try {
            getStatement().executeUpdate("INSERT INTO inventory " +
                    "SET name = '" + inventory.getName() + "', " +
                    "count = " + inventory.getCount() + ", " +
                    "vendor = '" + inventory.getVendor() + "', " +
                    "price = " + inventory.getPrice() + ", " +
                    "empId = " + inventory.getEmpId() + ", " +
                    "barcode =" + inventory.getBarcode());
            inventory1 = getInventory(inventory.getBarcode());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        InventoryService.statement = statement;
    }

    public static void main(String[] args) {
        InventoryService inventoryService = new InventoryService();
        Inventory inventory = inventoryService.getInventory(5141998);
        int count = inventory.getCount();
        inventory.setCount(++count);
        Inventory inventory1 = inventoryService.updateInventory(5141998, inventory);
        System.out.println(inventory1.toString());
    }
}
