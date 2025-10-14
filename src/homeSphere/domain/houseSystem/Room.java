package homeSphere.domain.houseSystem;

import homeSphere.domain.devices.Device;
import homeSphere.domain.users.User;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class Room {
    private final String roomID;
    private String name;
    private double area;
    private String introduction = "null";
    private final Set<Device> devices = new HashSet<>();
    private Household household;

    public Room(Household household, String name, double area) {
        this.household = household;
        household.addRoom(this);
        this.name = name;
        this.area = area;
        roomID = createRoomID();
    }

    public String getRoomID() {
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

    public Set<Device> getDevices() {
        return devices;
    }

    public void addDevice(Device d){
        devices.add(d);
    }
    public void removeDevice(User operator, Device d){
        if(!household.getUsers().contains(operator)){
            throw new IllegalArgumentException("用户无权限！");
        }
        devices.remove(d);
    }

    public String createRoomID(){
        return name + (1000 + household.getRooms().size());
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(roomID)
                .add(name)
                .add(Double.toString(area))
                .add(introduction)
                .toString();
    }
}
