package hello.entity;

public class LoginResult extends Result<User> {

    public boolean isLogin() {
        return isLogin;
    }

    boolean isLogin;

    protected LoginResult(String status, String msg, User data, boolean isLogin) {
        super(status, msg, data);
        this.isLogin = isLogin;
    }

    public static Result success(String msg, boolean isLogin, User user) {
        return new LoginResult("ok", msg, user, isLogin);
    }

    public static Result success(String msg, boolean isLogin) {
        return success(msg, isLogin, null);
    }

    public static Result fail(String msg) {
        return new LoginResult("fail", msg, null, false);
    }
}
