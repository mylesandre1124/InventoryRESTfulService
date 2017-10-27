package Service.Inventory.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.Serializable;

public class ItemAlreadyScannedException extends WebApplicationException implements Serializable {

    private int count;
    private String scannerName;

    public ItemAlreadyScannedException(String scannerName, int count) {
        super(Response.status(Response.Status.CONFLICT)
                .entity(scannerName + " has already scanned this item in with a count of: " + count + ". ").header("name", scannerName).header("count", count).build());
        this.scannerName = scannerName;
        this.count = count;

    }

    @Override
    public String toString() {
        return "ItemAlreadyScannedException{" +
                "count=" + count +
                ", scannerName='" + scannerName + '\'' +
                '}';
    }
}
