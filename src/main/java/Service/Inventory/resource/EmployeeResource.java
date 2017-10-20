package Service.Inventory.resource;

import Service.Inventory.model.Employee;
import Service.Inventory.service.EmployeeService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by Myles on 3/4/17.
 */
@Path("/Employee")
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
public class EmployeeResource {

    private EmployeeService employeeService = new EmployeeService();

    @GET
    public ArrayList<Employee> getAllEmployees()
    {
        ArrayList<Employee> employeeArrayList = employeeService.getAllEmployees();
        return employeeArrayList;
    }

    @GET
    @Path("/{empId}")
    public Employee getEmployee(@PathParam("empId") int empId)
    {
        return employeeService.getEmployee(empId);
    }

}
