public class Socialstyrelsen extends Person{
    
    public Socialstyrelsen(String name, long id) {
        super(name, id);
        createLog=false;
        readLog=false;
        writeLog=false;
        delete=true;
    }
}
