package roles;

public class Socialstyrelsen extends Person {

    public Socialstyrelsen(String name, long id) {
        super(name, id);
        createLog = false;
        readLog = true;
        writeLog = false;
        delete = true;
    }
}
