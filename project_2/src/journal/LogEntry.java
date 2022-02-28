package journal;

import roles.Doctor;
import roles.Nurse;
import roles.Patient;

public class LogEntry {
    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;
    private String comment;
    private long logNbr;
    private StringBuilder sb;

    // private AuditLog;

    public LogEntry(Patient patient, Doctor doctor, Nurse nurse, String comment, long logNbr) {
        this.patient = patient;
        this.nurse = nurse;
        this.doctor = doctor;
        this.comment = comment;
        this.logNbr = logNbr;
        sb = new StringBuilder("Patient: " + patient.getName() + "/n" +
                "Doctor: " + doctor.getName() + "/n" +
                "Nurse: " + nurse.getName() + "/n" +
                "Division: " + this.getDivision() + "/n" +
                comment);
    }

    public String getDivision() {
        return doctor.getDivision();
    }

    public void writeLog(String newComment) {
        sb.append(newComment + "/n ");
    }

    public long getNbr() {
        return logNbr;
    }

}
