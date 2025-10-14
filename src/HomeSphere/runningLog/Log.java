package OopHW.Exp1.runningLog;

import java.time.LocalDateTime;
import java.util.Random;

public abstract class Log {
    protected final String logID;
    protected final LocalDateTime t;
    protected String event;
    protected LogType eventType;
    protected String remarks = "";

    public Log(String event, LogType eventType, String remarks) {
        t = LocalDateTime.now();
        this.logID = generateLogID();
        this.event = event;
        this.eventType = eventType;
        this.remarks = remarks;
    }

    public String getLogID() {
        return logID;
    }

    public LocalDateTime getT() {
        return t;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public LogType getEventType() {
        return eventType;
    }

    public void setEventType(LogType eventType) {
        this.eventType = eventType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    private String generateLogID() {
        return String.format("LOG%d%02d%02d%02d%02d%02d%03d",
                t.getYear(), t.getMonthValue(), t.getDayOfMonth(),
                t.getHour(), t.getMinute(), t.getSecond(),
                new Random().nextInt(1000));
    }

    public void writeLog(){
        System.out.println(this.toString());
    }
}
