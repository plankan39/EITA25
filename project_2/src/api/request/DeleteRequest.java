package api.request;

import java.io.Serializable;

public class DeleteRequest implements Request, Serializable {
    public final int patientSSN;
    public final Long logNbr;

    public DeleteRequest(int patientSSN, Long logNbr) {
        this.patientSSN = patientSSN;
        this.logNbr = logNbr;
    }
}
