<%@ page import="Service.Inventory.model.Employee" %>
<html>
<body>
    <h2>Jersey RESTful Web Application!</h2>
    <p>Visit <a href="http://jersey.java.net">Project Jersey website</a>
    for more information on Jersey!
    <%
            Employee employee = new Employee();
            employee.setAdmin(1);
            String string = "" + employee.getAdmin();
            %>
        <>
    <%=
             string.toString()

    %>
</body>
</html>
