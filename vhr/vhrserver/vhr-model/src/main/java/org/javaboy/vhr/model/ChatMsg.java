package org.javaboy.vhr.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 在线消息Bean
 *
 * @ClassName ChatMsg
 * @Author 周某
 * @Date 2020-05-20 11:08
 **/
public class ChatMsg {

    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickName;

    public String getFromNickName() {
        return fromNickName;
    }

    public void setFromNickName(String fromNickName) {
        this.fromNickName = fromNickName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
