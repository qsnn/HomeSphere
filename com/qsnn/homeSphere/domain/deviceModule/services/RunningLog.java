package qsnn.homeSphere.domain.deviceModule.services;


import java.util.Date;

public class RunningLog {
    private final Date dateTime;
    private final String event;
    private final Type type;
    private final String note;

    public RunningLog(Date dateTime, String event, Type type, String note) {
        this.dateTime = dateTime;
        this.event = event;
        this.type = type;
        this.note = note;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getEvent() {
        return event;
    }

    public Type getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

    public enum Type {
        /** 信息日志 */
        INFO,
        /** 警告日志 */
        WARNING,
        /** 错误日志 */
        ERROR
    }
}
