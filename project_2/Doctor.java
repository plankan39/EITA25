public class Doctor extends Person{
    private String division;
}

//konstruktor
public Doctor (String name, long id, String div) {
    super(name, id);
    division = div;
}


public String getDivision(){
    return division;
}
