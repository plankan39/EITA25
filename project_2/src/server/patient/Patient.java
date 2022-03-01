package server.patient;

import java.util.ArrayList;
import java.util.List;

import server.users.Doctor;
import server.users.Nurse;
import server.users.User;

public class Patient {
    private static long logNbr = 0;
    private int ssn;
    private String name;
    private List<LogEntry> journal;

    public Patient(int ssn, String name) {
        this.ssn = ssn;
        this.name = name;
        this.journal = new ArrayList<>();
    }

    public void addJournalEntry(Doctor doctor, Nurse nurse, String log) {
        LogEntry le = new LogEntry(this, doctor, nurse, log, logNbr++);
        journal.add(le);
    }
}
