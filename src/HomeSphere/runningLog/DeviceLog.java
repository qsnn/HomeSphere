package OopHW.Exp1.runningLog;

import OopHW.Exp1.domain.devices.Device;

import java.time.format.DateTimeFormatter;

public final class DeviceLog extends Log{
    private Device device;

    public DeviceLog(Device device, Double powerConsumption, String event, LogType eventType, String remarks) {
        super(event, eventType, remarks);
        this.device = device;
        writeLog();
    }

    @Override
    public String toString() {
        // 输出示例：LOG[LOG20240115143025123](客厅电视1534) 2024-01-15T14:30:25 INFO 设备开启 - 客厅灯光已开启 功率：220W
        StringBuilder sb = new StringBuilder();
        sb.append("LOG[").append(logID).append("] ");
        sb.append("(").append(device.getName()).append(":").append(device.getDeviceID()).append(") ");
        sb.append(t.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)).append(" ");
        sb.append(eventType).append(" ");
        sb.append(event).append(" ");
        sb.append("功率：").append(device.getPower()).append("W");
        if (!remarks.isEmpty()) {
            sb.append(" - (").append(remarks).append(")");
        }
        return sb.toString();
    }

}
