package api.response;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Response implements Serializable {
    public final boolean granted;
    public final String log;

    public Response(boolean status) {
        this.granted = status;
        this.log = null;
    }

    public Response(boolean status, String log) {
        this.granted = status;
        this.log = log;
    }

}
