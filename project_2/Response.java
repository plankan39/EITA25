import java.io.File;
import java.io.FileNotFoundException;

public class Response {
    private String status;
    private File log;

    public Response(String status){
        this.status = status;
    }

    public Response(String status, File log) throws FileNotFoundException{
        this.status = status;
        this.log = log;
    }

    public File getLog(){
        return log;
    }

    public String getStatus(){
        return status;
    }
    
}
