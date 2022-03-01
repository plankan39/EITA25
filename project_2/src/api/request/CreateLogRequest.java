package api.request;

public class CreateLogRequest implements Request {
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
