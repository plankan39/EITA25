package server.patient;

import java.util.ArrayList;
import java.util.List;

import server.users.User;

public class Patient {
    private int ssn;
    private String name;
    private List<LogEntry> journal;

    public Patient(int ssn, String name) {
        this.ssn = ssn;
        this.name = name;
        this.journal = new ArrayList<>();
    }

    public void addJournalEntry(Patient patient, User user, User user2, String log) {
    }
}
