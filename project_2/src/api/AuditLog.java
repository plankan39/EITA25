package api;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.FileWriter;   // Import the FileWriter class
import java.io.IOException;  // Import the IOException class to handle errors

import server.patient.LogEntry;

public class AuditLog { // The list keeping track of all entries and edits
    private StringBuilder logEntry;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    //action behöver skrivas som t.ex "attempted to write to" "wrote to" "attempted to read" "read"
    public AuditLog(String user, String action, String patient) {
        dateTime = LocalDateTime.now();
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        logEntry = new StringBuilder(dateTime.format(formatter)+" "+user+" "+action+" the journal of "+patient+"/n");
        addToAuditLog(logEntry);
    }

    private void addToAuditLog(String sb) {
        try {
            FileWriter auditLogEntry = new FileWriter("auditlog.txt", true); // creates filewriter which can append to our auditlog
            auditLogEntry.append(sb);
            auditLogEntry.close();
            System.out.println("Audit log entry created.");
        } catch (IOException e) {
            System.out.println("An error occurred with audit log entry.");
            e.printStackTrace();
        }
    }
}
// public class AuditLog { // The list keeping track of all entries and edits
//     // String log; // log to keep track of all the log entries
//     DateTimeFormatter dtf;
//     StringBuilder sb;

//     public AuditLog() {
//         dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
//         sb = new StringBuilder("Log created: " + timeStamp().toString() + "/n");
//     }

//     public AuditLogEntry(LogEntry le, String Action){
//         dateTime = timeStamp();
//         Patient p = le.get

//     }

//     public void createLog(LogEntry le) { // create a new log
//         AuditLogEntry(le, "Log created");
//     }

//     public readLog(LogEntry le){ // read existing log
//         AuditLogEntry(le, "Log read");
//     }

//     public writeLog(LogEntry le){ // write existing log
//         AuditLogEntry(le, "Log entry written");
//     }

//     public deleteLog(LogEntry le){ // delete existing log
//         AuditLogEntry(le, "Log deleted");
//     }

//     private LocalDate timeStamp(LogEntry le) {
//         LocalDate localDate = LocalDate.now();
//         return localDate;
//     }
// }
