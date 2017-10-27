package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.exceptions.AuthenticationException;
import Service.Inventory.exceptions.ItemAlreadyScannedException;
import Service.Inventory.model.Employee;
import Service.Inventory.model.Inventory;
import Service.Inventory.model.ObjectIO;

import javax.xml.crypto.Data;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Created by Myles on 2/25/17.
 */
public class InventoryService {

    private static Statement statement;
    private Database database;
    private String authorizationToken;

    public InventoryService()  {
        database = new Database(0);
        statement = database.getStatement();
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
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

    public Inventory updateInventory(long barcode, Inventory inventory) throws AuthenticationException {
        Inventory oldInventory= getInventory(barcode);
        Inventory inventory1 = null;
        if(authorizationToken != null) {
            throw new AuthenticationException();
        }
        inventory.setEmpId(getEmpId(authorizationToken));
        checkIfItemAlreadyScanned(oldInventory, inventory.getEmpId());
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

    public void checkIfItemAlreadyScanned(Inventory oldInventory, int newEmpId)
    {
        if(oldInventory.getEmpId() != newEmpId)
        {
            EmployeeService employeeService = new EmployeeService();
            Employee employee = employeeService.getEmployee(oldInventory.getEmpId());
            throw new ItemAlreadyScannedException(employee.getName(), oldInventory.getCount());
        }
    }

    public int getEmpId(String authorizationToken) throws AuthenticationException {
        int empId = -1;
        if(authorizationToken == null)
        {
            throw new AuthenticationException();
        }
        try {
            CallableStatement empIdStatement = getStatement().getConnection().prepareCall("{call login(?)}");
            empIdStatement.setString("token", authorizationToken);
            empIdStatement.execute();
            ResultSet empIdSet = empIdStatement.getResultSet();
            while (empIdSet.next()) {
                empId = empIdSet.getInt(1);
            }
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        if (empId == -1) {
            throw new AuthenticationException();
        }
        return empId;
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
