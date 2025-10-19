package qsnn.homeSphere.domain.deviceModule.services;

import java.time.Date;

public class RunningLog {
    private final Date dateTime;
    private final String event;
    private final int type;
    private final String note;

    public RunningLog(Date dateTime, String event, int type, String note) {
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

    public int getType() {
        return type;
    }

    public String getNote() {
        return note;
    }

}
