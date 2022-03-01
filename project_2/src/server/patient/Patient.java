package server.patient;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import server.users.Doctor;
import server.users.Nurse;
import server.users.User;

public class Patient {
    private static long logNbr = 0;
    private int ssn;
    private String name;

    private Map<Long, LogEntry> journal;

    public Patient(int ssn, String name) {
        this.ssn = ssn;
        this.name = name;
        this.journal = new HashMap<>();
    }

    public void addJournalEntry(Doctor doctor, Nurse nurse, String log) {
        LogEntry le = new LogEntry(this, doctor, nurse, log, logNbr);
        journal.put(logNbr, le);
        logNbr++;
    }

    public Map<Long, LogEntry> getJournal() {
        return journal;
    }

    public String toString() {
        return "Name: " + name + "\nssn: " + ssn;
    }

    public void deleteJournalEntry(Long lnbr) {
        journal.remove(lnbr);
    }
}
