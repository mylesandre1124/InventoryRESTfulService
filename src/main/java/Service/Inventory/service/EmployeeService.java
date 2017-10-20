package Service.Inventory.service;

import Service.Inventory.database.Database;
import Service.Inventory.model.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by Myles on 3/4/17.
 */
public class EmployeeService {

    private Database database;
    private static Statement statement;

    public EmployeeService() {
        database = new Database(1);
        statement = database.getStatement();
    }

    public ArrayList<Employee> getAllEmployees()
    {
        ArrayList<Employee> employeeArrayList = new ArrayList<>();
        try {
            ResultSet set = getStatement().executeQuery("SELECT * FROM employee");
            while(set.next())
            {
                Employee employee = new Employee();
                employee.setEmpId(set.getInt(1));
                employee.setName(set.getString(2));
                employee.setUsername(set.getString(3));
                employee.setPassword(set.getString(4));
                employee.setAdmin(set.getInt(5));
                employeeArrayList.add(employee);
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeArrayList;
    }

    public Employee getEmployee(int empId)
    {
        Employee employee = new Employee();
        try {
            ResultSet set = getStatement().executeQuery("SELECT * FROM employee WHERE empId = " + empId);
            while(set.next())
            {
                employee.setEmpId(set.getInt(1));
                employee.setName(set.getString(2));
                employee.setUsername(set.getString(3));
                employee.setPassword(set.getString(4));
                employee.setAdmin(set.getInt(5));
            }
            set.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employee;
    }

    public Employee updateEmployee(Employee employee)
    {
        try {
            Employee retrievedEmployee = getEmployee(employee.getEmpId());
            if(!retrievedEmployee.isNull())
            {
                getStatement().executeUpdate(
                        "UPDATE employee " +
                        "SET    password = '" + employee.getPassword() + "', " +
                                "username = '" + employee.getUsername() + "', " +
                                "admin = " + employee.getAdmin() + ", " +
                                "name = '" + employee.getName() + "' " +
                        "WHERE empId = " + employee.getEmpId());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getEmployee(employee.getEmpId());
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {
        EmployeeService.statement = statement;
    }
}
