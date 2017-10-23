package Service.Inventory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ItemAlreadyScannedException extends WebApplicationException {

    private int count;
    private String scannerName;

    public ItemAlreadyScannedException(String scannerName, int count) {
        super(scannerName + " has already scanned this item in with a count of: " + count, Response.Status.CONFLICT);
        this.scannerName = scannerName;
        this.count = count;
    }


}
