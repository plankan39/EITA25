import java.io.File;
import java.io.FileNotFoundException;

public class Request {
    private String patient;
    private String req;
    private File log;
    private String nurse;

    //konstruktor när en ny fil ska göras (booleanen är med för att skilja på denna och nästa konstruktor)
    public Request(String patient, String req, String nurse, Boolean create){
        this.patient = patient;
        this.req = req;
        this.nurse = nurse;
        log = null;
    }

    //konstruktor när vi vill eller skriva till fil läsa en fil
    public Request(String patient, String req, String path) throws FileNotFoundException {
        this.patient = patient;
        this.req = req;
        try{
            this.log = new File(path);
        } catch (Exception e){
            System.out.println("File " + path + " not found.");
            throw new FileNotFoundException();
        }
    }

    //konstruktor när vi vill radera en fil
    public Request(String patient, String path) throws FileNotFoundException {
        this.patient = patient;
        
    }

    public String getPatient(){
        return patient;
    }

    public String getReq(){
        return req;
    }

    public String getNurse(){
        return nurse;
    }

    public File getLog(){
        return log;
    }

    
}
