package org.javaboy.vhr.model;

/**
 * @ClassName RespBean
 * @Author 周某
 * @Date 2020-03-25 17:19
 **/
public class RespBean {
    private Integer status;
    private String msg;
    private Object obj;

    public static RespBean build(){
        return new RespBean();
    }

    public static RespBean ok(String msg) {
        return new RespBean(200, msg, null);
    }
    public static RespBean ok(String msg, Object obj) {
        return new RespBean(200, msg, obj);
    }
    public static RespBean error(String msg) {
        return new RespBean(500, msg, null);
    }
    public static RespBean error(String msg, Object obj) {
        return new RespBean(500, msg, obj);
    }

    private RespBean() {

    }

    private RespBean(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "RespBean{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public RespBean setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public RespBean setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getObj() {
        return obj;
    }

    public RespBean setObj(Object obj) {
        this.obj = obj;
        return this;
    }
}
