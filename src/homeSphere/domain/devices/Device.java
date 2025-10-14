package homeSphere.domain.devices;

import homeSphere.domain.houseSystem.Room;
import homeSphere.domain.users.User;
import homeSphere.runningLog.DeviceLog;
import homeSphere.runningLog.LogType;
import homeSphere.service.manufacturer.Manufacturer;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public abstract class Device implements OnlineStatus, PowerStatus{
    protected final String deviceID;
    protected String name;
    protected String OS;
    protected Manufacturer manufacturer;
    protected String brand;
    protected OnlineStatusType onlineStatus;
    protected PowerStatusType powerStatus;
    protected final double power;
    protected LocalDateTime lastOpenTime;
    protected final Set<Usage> usages = new LinkedHashSet<>();

    public Device(String deviceID, String name, String OS, Manufacturer manufacturer, String brand, double power) {
        this.deviceID = deviceID;
        this.name = name;
        this.OS = OS;
        this.manufacturer = manufacturer;
        this.brand = brand;
        onlineStatus = OnlineStatusType.OUTLINE;
        powerStatus = PowerStatusType.UNPOWERED;
        this.power = power;
        new DeviceLog(this, power,"创建设备：" + name, LogType.INFO, "null");
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOS() {
        return OS;
    }

    public void setOS(String OS) {
        this.OS = OS;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public OnlineStatusType getOnlineStatus() {
        return onlineStatus;
    }

    public PowerStatusType getPowerStatus() {
        return powerStatus;
    }

    public abstract void execute(Device device, Map<String, Object> parameters);

    public double getPower(){
        return power;
    }

    public Set<Usage> getUsages() {
        return usages;
    }

    public void move(User operator, Room from, Room to){
        if(from == null){
            throw new IllegalArgumentException("该房间不存在！");
        } else if(!from.getDevices().contains(this)) {
            throw new IllegalArgumentException("该物品不在该房间内！");
        }else if(!from.getHousehold().getUsers().contains(operator)){
            throw new IllegalArgumentException("权限不足：只有家庭成员方可移动！");
        } else if (!from.getHousehold().getRooms().contains(to)) {
            throw new IllegalArgumentException("目标房间不存在！");
        }else{
            from.removeDevice(operator, this);
            to.addDevice(this);
        }
    }

    public void connect(){
        new DeviceLog(this, this.getPower(),"连接网络", LogType.INFO, "null");
        this.onlineStatus = OnlineStatusType.ONLINE;
    }
    public void disconnect(){
        new DeviceLog(this, this.getPower(),"断开网络", LogType.INFO, "null");
        this.onlineStatus = OnlineStatusType.OUTLINE;
    }

    public void open(){
        new DeviceLog(this, this.getPower(),"连接电源", LogType.INFO, "null");
        if(this.powerStatus == PowerStatusType.UNPOWERED){
            lastOpenTime = LocalDateTime.now();
        }
        this.powerStatus = PowerStatusType.POWERED;
    }
    public void close(){
        Usage u = new Usage(deviceID + usages.size(),this, lastOpenTime, LocalDateTime.now());
        if(this.powerStatus == PowerStatusType.POWERED){
            usages.add(u);
        }
        this.powerStatus = PowerStatusType.UNPOWERED;
        new DeviceLog(this, this.getPower(),"断开电源", LogType.INFO, u.toString());
    }

    public static double calculatePowerConsumption(Device d, LocalDateTime startTime, LocalDateTime endTime) {
        Set<Usage> usages = d.getUsages();
        double totalEnergy = 0.0;

        for (Usage usage : usages) {
            LocalDateTime powerOnTime = usage.getOpenTime();
            LocalDateTime powerOffTime = usage.getCloseTime();
            double power = d.getPower();

            // 检查这个使用记录是否在查询时间范围内有重叠
            if (isUsageInTimeRange(powerOnTime, powerOffTime, startTime, endTime)) {
                // 计算实际在查询时间段内的使用时间
                LocalDateTime actualStart = getLaterTime(powerOnTime, startTime);
                LocalDateTime actualEnd = getEarlierTime(powerOffTime, endTime);

                // 计算时间差（小时）
                Duration duration = Duration.between(actualStart, actualEnd);
                double hours = duration.toSeconds() / 3600.0; // 精确到秒转换为小时

                // 累加能耗
                totalEnergy += power * hours;
            }
        }

        return totalEnergy;
    }

    /**
     * 检查使用记录是否在查询时间范围内有重叠
     */
    private static boolean isUsageInTimeRange(LocalDateTime usageStart, LocalDateTime usageEnd,
                                              LocalDateTime queryStart, LocalDateTime queryEnd) {
        // 使用记录完全在查询时间范围之外的情况
        return !usageEnd.isBefore(queryStart) && !usageStart.isAfter(queryEnd);
    }

    /**
     * 获取两个时间中较晚的一个
     */
    private static LocalDateTime getLaterTime(LocalDateTime time1, LocalDateTime time2) {
        return time1.isAfter(time2) ? time1 : time2;
    }

    /**
     * 获取两个时间中较早的一个
     */
    private static LocalDateTime getEarlierTime(LocalDateTime time1, LocalDateTime time2) {
        return time1.isBefore(time2) ? time1 : time2;
    }

}
