public class Person {
    private String name;
    private long id;
    protected boolean createLog;
    protected boolean readLog;
    protected boolean writeLog;
    protected boolean delete;


    public Person(String name, long id){
        this.name = name;
        this.id = id;
    }
    
    public long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public boolean createLogAllowed() {
        return createLog;
    }

    public boolean readLogAllowed() {
        return readLog;
    }

    public boolean writeLogAllowed() {
        return writeLog;
    }

    public boolean deleteLogAllowed() {
        return delete;
    }
}
