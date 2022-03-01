package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import server.patient.LogEntry;

public class AuditLog { // The list keeping track of all entries and edits
    // String log; // log to keep track of all the log entries
    DateTimeFormatter dtf;
    StringBuilder sb;

    public AuditLog(LogEntry le, String action) {
        dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        sb = new StringBuilder("Log created: " + timeStamp().toString() + "/n");

    }

    private void addToFile() {
        try {
            FileWriter auditLogEntry = new FileWriter("auditlog.txt");
            auditLogEntry.write("info om vad som har gjorts");
            auditLogEntry.close();
            System.out.println("Audit log entry created.");
        } catch (IOException e) {
            System.out.println("An error occurred with audit log entry.");
            e.printStackTrace();
        }
    }


    

    public AuditLogEntry(LogEntry le, String Action){
        dateTime = timeStamp();
        Patient p = le.get;
        
    }

    private LocalDate timeStamp(LogEntry le) {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

    
}
