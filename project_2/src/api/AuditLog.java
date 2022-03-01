package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import server.patient.LogEntry;

public class AuditLog { // The list keeping track of all entries and edits
    // String log; // log to keep track of all the log entries
    //private DateTimeFormatter dtf;
    private StringBuilder logEntry;
    LocalDateTime dateTime;

    //action behöver skrivas som t.ex "attempted to write to" "wrote to" "attempted to read" "read"
    public AuditLog(String user, String action, String patient) {
        dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        logEntry = new StringBuilder(dateTime.format(formatter)+" "+user+" "+action+" the journal of "+patient+"/n");
        addToFile(logEntry);
    }

    private void addToFile(String sb) {
        try {
            FileWriter auditLogEntry = new FileWriter("auditlog.txt");
            auditLogEntry.write(sb);
            auditLogEntry.close();
            System.out.println("Audit log entry created.");
        } catch (IOException e) {
            System.out.println("An error occurred with audit log entry.");
            e.printStackTrace();
        }
    }
}
