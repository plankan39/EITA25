package server.patient;

import server.users.Doctor;
import server.users.Nurse;

public class LogEntry {
    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;
    private String comment;
    private long logNbr;
    // private StringBuilder sb;

    // private AuditLog;

    public LogEntry(Patient patient, Doctor doctor, Nurse nurse, String comment, long logNbr) {
        this.patient = patient;
        this.nurse = nurse;
        this.doctor = doctor;
        this.comment = comment;
        this.logNbr = logNbr;
        // sb = new StringBuilder("Patient: " + patient.getName() + "/n" +
        // "Doctor: " + doctor.getName() + "/n" +
        // "Nurse: " + nurse.getName() + "/n" +
        // "Division: " + this.getDivision() + "/n" +
        // comment);
    }

    public String getDivision() {
        return doctor.getDivision();
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Nurse getNurse() {
        return nurse;
    }

    public void append(String newComment) {
        comment = comment.concat("/n").concat(newComment);
    }

    public long getNbr() {
        return logNbr;
    }

    public String toString() {
        String sb = "Log number :" + logNbr + "\n\n" + patient.toString() + "\n\n" +
                "Doctor: " + doctor.getUserName() + "\n" +
                "Nurse: " + nurse.getUserName() + "\n" +
                "Division: " + this.getDivision() + "\n\n" +
                comment + "\n\n";
        return sb;
    }

}
