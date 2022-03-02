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
        // logEntry = dateTime.format(formatter) + " " + user + " " + action + " the
        // journal of " + patient + "\n";
        // addToAuditLog(logEntry);
    }

    public void addToAuditLog(String user, String action, String patient) {

        // creates filewriter which can append to
        String logEntry = dateTime.format(formatter) + " " + user + " " + action + " the journal of " + patient + "\n"; // our
        // auditlog
        try {
            auditLogEntry.append(logEntry);
            auditLogEntry.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
// public class AuditLog { // The list keeping track of all entries and edits
// // String log; // log to keep track of all the log entries
// DateTimeFormatter dtf;
// StringBuilder sb;

// public AuditLog() {
// dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
// sb = new StringBuilder("Log created: " + timeStamp().toString() + "/n");
// }

// public AuditLogEntry(LogEntry le, String Action){
// dateTime = timeStamp();
// Patient p = le.get

// }

// public void createLog(LogEntry le) { // create a new log
// AuditLogEntry(le, "Log created");
// }

// public readLog(LogEntry le){ // read existing log
// AuditLogEntry(le, "Log read");
// }

// public writeLog(LogEntry le){ // write existing log
// AuditLogEntry(le, "Log entry written");
// }

// public deleteLog(LogEntry le){ // delete existing log
// AuditLogEntry(le, "Log deleted");
// }

// private LocalDate timeStamp(LogEntry le) {
// LocalDate localDate = LocalDate.now();
// return localDate;
// }
// }
