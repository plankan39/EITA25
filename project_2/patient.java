public class Patient extends Person {
    private []logEntry log;
}

//om vi ska skapa en patient utan en log
public Patient(String name, long id){
    log = null; 
    super(name, id);
}

//om vi ska skapa en patient med en log
public Patient([]logEntry startLog, String name, long id){
    log = startLog;
    super(name, id);
}

public []logEntry getLog() {
    return log;
}

public void createLogEntry(Doctor doc, String comments, String division){
    
}
