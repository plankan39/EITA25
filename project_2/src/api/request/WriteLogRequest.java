package api.request;

import java.io.Serializable;

public class WriteLogRequest implements Request, Serializable {
    public final int patientSSN;
    public final String input;
    public final long logNbr;

    public WriteLogRequest(int patientSSN, String input, long logNbr){
        this.patientSSN = patientSSN;
        this.input = input;
        this.logNbr = logNbr;
    }

}
