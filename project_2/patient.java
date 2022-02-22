public class Patient extends Person {
    private List <LogEntry> journal;


//om vi ska skapa en patient utan en log
public Patient(String name, long id){
    super(name, id);
    log = new ArrayList<LogEntry>(); 
}

//om vi ska skapa en patient med en log, behövs detta verkligen?
public Patient(<LogEntry> log, String name, long id){
    super(name, id);
    this.log = log;
}

public <LogEntry>logEntry getLog() {
    return log;
}

//skapar en ny entry i patientens log, behövs det en if-sats som kollar om doktorn är behörig att skapa logen?
public void createLogEntry(Doctor doc, Nurse nurse, String comment){
    LogEntry newLog = new LogEntry(this.Patient, doctor, nurse, comment);
    log.add(newLog);
}


}