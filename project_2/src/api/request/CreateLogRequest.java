package api.request;

import java.io.Serializable;

public class CreateLogRequest implements Request, Serializable {
    public final String patientName;
    public final int patientSSN;
    public final String nurse;
    public final String log;

    public CreateLogRequest(String patientName, int patientSSN, String nurse, String log) {
        this.patientName = patientName;
        this.patientSSN = patientSSN;
        this.nurse = nurse;
        this.log = log;
    }

}
