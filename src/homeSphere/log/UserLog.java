package homeSphere.log;

import homeSphere.domain.users.User;

import java.time.format.DateTimeFormatter;

public final class UserLog extends Log{
    private final User user;
    public UserLog(User user, String event, LogType eventType, String remarks) {
        super(event, eventType, remarks);
        this.user = user;
        writeLog();
    }

    @Override
    public String toString() {
        // 输出示例：LOG[LOG20240115143025123](客厅电视1534) 2024-01-15T14:30:25 INFO 设备开启 - 客厅灯光已开启 功率：220W
        StringBuilder sb = new StringBuilder();
        sb.append("LOG[").append(logID).append("] ");
        sb.append("(").append(user.getUsername()).append(":").append(user.getUserID()).append(") ");
        sb.append(t.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(" ");
        sb.append(eventType).append(" ");
        sb.append(event).append(" ");
        if (!remarks.isEmpty()) {
            sb.append(" - (").append(remarks).append(")");
        }
        return sb.toString();
    }
}
