package api.request;

public class CreateLogRequest implements Request {

    public final int patientSSN;
    public final int nurseSSN;
    public final String doctor;
    public final String log;

    public CreateLogRequest(int patientSSN, String doctor, int nurseSSN, String log) {
        this.patientSSN = patientSSN;
        this.doctor = doctor;
        this.nurseSSN = nurseSSN;
        this.log = log;
    }

}
