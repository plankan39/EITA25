package roles;

import java.util.ArrayList;
import java.util.List;

import journal.LogEntry;

public class Patient extends Person {
    private List<LogEntry> journal;

    // om vi ska skapa en patient utan en log
    public Patient(String name, long id) {
        super(name, id);
        journal = new ArrayList<LogEntry>();
        createLog = false;
        readLog = true;
        writeLog = false;
        delete = false;
    }

    // om vi ska skapa en patient med en log, behövs detta verkligen?
    public Patient(LogEntry log, String name, long id) {
        super(name, id);
        journal = new ArrayList<LogEntry>();
        journal.add(log);
    }

    public void readJournal() {
        journal.forEach(System.out::println);
    }

    public List<LogEntry> getJournal() {
        return journal;
    }

    // skapar en ny entry i patientens log, behövs det en if-sats som kollar om
    // doktorn är behörig att skapa logen?
    public void createLogEntry(Doctor doc, Nurse nurse, String comment, long logNbr) {
        LogEntry newLog = new LogEntry(this, doc, nurse, comment, logNbr);
        journal.add(newLog);
    }

    public void deleteLog(long logNbr) {
        for (LogEntry le : journal) {
            if (le.getNbr() == logNbr) {
                journal.remove(le);
            }

        }
    }

}