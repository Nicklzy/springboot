package hello.entity;

public abstract class Result<T> {


    protected Result(String status, String msg) {
        this(status, msg, null);
    }

    protected Result(String status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    String status;
    String msg;

    public T getData() { //之前返回里没有data就是因为没有配置getter
        return data;
    }

    T data;

    public String getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

}
