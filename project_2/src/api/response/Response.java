package api.response;

import java.io.File;
import java.io.FileNotFoundException;

public class Response {
    public final boolean granted;
    public final File log;

    public Response(boolean status) {
        this.granted = status;
        this.log = null;
    }

    public Response(boolean status, File log) throws FileNotFoundException {
        this.granted = status;
        this.log = log;
    }

}
