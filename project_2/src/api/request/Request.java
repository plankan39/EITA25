package api.request;

import java.io.File;
import java.io.FileNotFoundException;

public interface Request {

    public static enum RequestType {
        LOGIN,
        CREATE,
        WRITE,
        READ,
        DELETE
    }

}
