package api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter; // Import the FileWriter class
import java.io.IOException; // Import the IOException class to handle errors

import server.patient.LogEntry;

public class AuditLog { // The list keeping track of all entries and edits
    // private String logEntry;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;
    private FileWriter auditLogEntry;

    // action behöver skrivas som t.ex "attempted to write to" "wrote to" "attempted
    // to read" "read"
    public AuditLog(String logPath) throws IOException {
        dateTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        auditLogEntry = new FileWriter(logPath, true);
    }


    public void addLoginToAuditLog(String user, String action, boolean actionGranted) {
        // creates filewriter which can append to our auditlog
        String logEntry = dateTime.format(formatter) + " " + user + " " + action + " " +  booleanStatus(actionGranted) + "\n";
        try {
            auditLogEntry.open();
            auditLogEntry.append(logEntry);
            auditLogEntry.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void addActionToAuditLog(String user, String action, int patientSSN, boolean actionGranted) {
        // creates filewriter which can append to
        String logEntry = dateTime.format(formatter) + " " + user + " " + action + " the journal of " + patientSSN + " " + booleanStatus(actionGranted) + "\n"; // our
        // auditlog
        try {
            auditLogEntry.open();
            auditLogEntry.append(logEntry);
            auditLogEntry.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String booleanStatus(boolean actionGranted){
        if (actionGranted == true){
            return "ACCESS GRANTED";
        }
        return "ACCESS DENIED"
    }

}


