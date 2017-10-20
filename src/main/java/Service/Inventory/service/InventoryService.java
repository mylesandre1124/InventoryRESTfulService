package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.model.Inventory;

import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
                    "SELECT * FROM Inventory1");
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
                    "SELECT * FROM Inventory1 where barcode = " + barcode);
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
            getStatement().executeUpdate("DELETE FROM Inventory1 WHERE barcode = " + barcode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Inventory updateInventory(long barcode, Inventory inventory)
    {
        Inventory inventory1 = null;
        try {
            getStatement().executeUpdate("UPDATE Inventory1 " +
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
            getStatement().executeUpdate("INSERT INTO Inventory1 " +
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
        return inventory1;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        InventoryService.statement = statement;
    }

    public static void main(String[] args) {

    }
}
