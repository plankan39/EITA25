package server.users;

public class Doctor extends User {
    private String division;

    public Doctor(String userName, int ssn, String pw, String division) {
        super(userName, ssn, pw);
        this.division = division;
    }

    public String getDivision() {
        return division;
    }

    public String toString() {
        return super.toString() + division;
    }

}
