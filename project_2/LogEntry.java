public class LogEntry {
    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;
    private String comment;

    // private AuditLog;

    public LogEntry(Patient patient, Doctor doctor, Nurse nurse, String comment) {
        this.patient = patient;
        this.nurse = nurse;
        this.doctor = doctor;
        this.comment = comment;
    }

    public String getDivision() {
        return doctor.getDivision();
    }
}
