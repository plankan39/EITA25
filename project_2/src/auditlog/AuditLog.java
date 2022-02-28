package auditlog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import journal.LogEntry;
import roles.Patient;

public class AuditLog { // The list keeping track of all entries and edits
    // String log; // log to keep track of all the log entries
    DateTimeFormatter dtf;
    StringBuilder sb;

    public AuditLog() {
        dtf = DateTimeFormatter.ofPattern("uuuu/MM/dd");
        sb = new StringBuilder("Log created: " + timeStamp().toString() + "/n");
    }

    public AuditLogEntry(LogEntry le, String Action){
        dateTime = timeStamp();
        Patient p = le.get

    }

    public void createLog(LogEntry le) { // create a new log
        AuditLogEntry(le, "Log created");
    }

    public readLog(LogEntry le){ // read existing log
        AuditLogEntry(le, "Log read");
    }

    public writeLog(LogEntry le){ // write existing log
        AuditLogEntry(le, "Log entry written");
    }

    public deleteLog(LogEntry le){ // delete existing log
        AuditLogEntry(le, "Log deleted");
    }

    private LocalDate timeStamp(LogEntry le) {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }
}
