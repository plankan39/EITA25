package server.users;

public class Nurse extends User {
    private String division;

    public Nurse(String userName, int ssn, String pw, String division) {
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
