package server.patient;

import java.util.ArrayList;
import java.util.List;

public class Patient {
    private int ssn;
    private String name;
    private List<LogEntry> journal;

    public Patient(int ssn, String name) {
        this.ssn = ssn;
        this.name = name;
        this.journal = new ArrayList<>();
    }
}
