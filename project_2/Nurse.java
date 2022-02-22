public class Nurse extends Person{
    private String division;

    public Nurse(String name, long id, String division) {
        super(name,id);
        this.division = division;
        createLog=false;
        readLog=true;
        writeLog=true;
        delete=false;
    }

    public String getDivision() {
        return division;
    }
}