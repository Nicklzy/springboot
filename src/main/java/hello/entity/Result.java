package hello.entity;

public class Result {


    public Result(String status, String msg, boolean isLogin) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
    }

    public Result(String status, String msg, boolean isLogin, Object data) {
        this.status = status;
        this.msg = msg;
        this.isLogin = isLogin;
        this.data = data;
    }

    String status;
    String msg;

    public Object getData() { //之前返回里没有data就是因为没有配置getter
        return data;
    }

    Object data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public boolean isLogin() {
        return isLogin;
    }

    boolean isLogin;

}
