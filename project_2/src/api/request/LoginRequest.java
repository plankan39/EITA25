package api.request;

import java.io.Serializable;

public class LoginRequest implements Request, Serializable {
    public final String userName;
    public final String password;

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}