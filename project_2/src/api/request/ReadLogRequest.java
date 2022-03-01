package api.request;

import java.io.Serializable;

public class ReadLogRequest implements Request, Serializable {
    public final int pSSN;
    public final long logID;

    public ReadLogRequest(int pSSN, long logID) {
        this.pSSN = pSSN;
        this.logID = logID;
    }

}
