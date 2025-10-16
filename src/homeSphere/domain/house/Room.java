package homeSphere.domain.house;

import homeSphere.log.Log;

import java.util.*;

public class Room {
    private final int roomID;
    private String name;
    private double area;
    private String introduction = "null";
    protected final Set<Log> roomLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //使用记录

    public Room(int roomID, String name, double area) {
        this.roomID = roomID;
        this.name = name;
        this.area = area;
        roomLogs.add(new Log(Integer.toString(getRoomID()),"创建房间：" + name, Log.LogType.INFO, this.toString())) ;
    }

    public int getRoomID() {
        return roomID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Set<Log> getRoomLogs() {
        return roomLogs;
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(roomID))
                .add(name)
                .add(Double.toString(area))
                .add(introduction)
                .toString();
    }
}
