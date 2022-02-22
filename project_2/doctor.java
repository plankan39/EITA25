public class Doctor extends Person{
    private String division;


//konstruktor
public Doctor(String name, long id, String division){
    super(name, id);
    this.division = division;
}
    public String getDivision(){
        return division;
    }

}

