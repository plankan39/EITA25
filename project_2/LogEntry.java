public class LogEntry {
    private Patient patient;
    private Doctor doctor;
    private Nurse nurse;
    private String comment;
    private StringBuilder sb; 

    // private AuditLog;

    public LogEntry(Patient patient, Doctor doctor, Nurse nurse, String comment) {
        this.patient = patient;
        this.nurse = nurse;
        this.doctor = doctor;
        this.comment = comment;
        sb = new StringBuilder("Patient: " + patient.getName() + "/n" +
        "Doctor: " + doctor.getName() + "/n" +
        "Nurse: " + nurse.getName() + "/n" +
        comment);
    }

    public String getDivision() {
        return doctor.getDivision();
    }

    public void writeLog(String newComment){
        sb.append(newComment + "/n ");
    }
}
