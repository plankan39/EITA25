package api.request;

public class LoginRequest implements Request {
    public final String userName;
    public final String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}