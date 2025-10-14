package homeSphere.domain.users;

import homeSphere.domain.devices.Device;
import homeSphere.domain.houseSystem.Household;
import homeSphere.domain.houseSystem.Room;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class User {
    private final int userID;
    private Set<Household> households = new HashSet<>();
    private Household currentHousehold;
    private String username;
    private String password;
    private String name;
    private String address;

    public User(int userID, String username, String password, String name, String address) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
    }

    public int getUserID() {
        return userID;
    }

    public Set<Household> getHouseholds() {
        return households;
    }

    public Household getCurrentHousehold() {
        return currentHousehold;
    }

    public void setCurrentHousehold(Household currentHousehold) {
        if(!households.contains(currentHousehold)){
            throw new IllegalArgumentException("用户没有权限进入该房屋！");
        }
        this.currentHousehold = currentHousehold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addHousehold(Household h){
        if(households == null){
            households = new HashSet<>();
        }
        households.add(h);
    }

    public void addDevice(Device d, Room r){
        r.addDevice(d);
    }

    public void removeDevice(Device d, Room r){
        r.removeDevice(this, d);
    }

    @Override
    public String toString(){
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(userID))
                .add(name)
                .add(username)
                .add(address)
                .toString();
    }

}
