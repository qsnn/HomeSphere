package OopHW.Exp1.domain.houseSystem;

import OopHW.Exp1.domain.devices.Device;
import OopHW.Exp1.domain.users.User;

import java.util.*;
import java.util.stream.Collectors;

import static OopHW.Exp1.domain.houseSystem.HouseholdMembershipType.ADMIN;
import static OopHW.Exp1.domain.houseSystem.HouseholdMembershipType.MEMBER;

public class Household {
    private final int householdID;
    private String name;
    private String address;
    private User administrator;
    private final Set<Room> rooms = new HashSet<>();
    private final Map<User, HouseholdMembershipType> householdMembership = new HashMap<>();

    public Household(int householdID, String name, String address, User creator) {
        this.householdID = householdID;
        this.name = name;
        this.address = address;
        administrator = creator;
        householdMembership.put(creator, ADMIN);
        creator.addHousehold(this);
        creator.setCurrentHousehold(this);
    }

    public int getHouseholdID() {
        return householdID;
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

    public User getAdministrator() {
        return administrator;
    }

    public Set<User> getUsers() {
        return householdMembership.keySet();
    }

    public Set<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room r){
        rooms.add(r);
        r.setHousehold(this);
    }

    public void removeRoom(Room r){
        rooms.remove(r);
    }

    public void addUser(User operator, User user){
        if(!householdMembership.get(operator).equals(ADMIN)){
            return;
        } else {
            householdMembership.put(user, MEMBER);
            user.addHousehold(this);
        }
        if(!user.getHouseholds().contains(this)){
            user.addHousehold(this);
        }
    }

    public Set<Device> getDevices() {
        return rooms.stream()
                .flatMap(r -> r.getDevices().stream())
                .collect(Collectors.toSet());
    }

    public Map<User, HouseholdMembershipType> getHouseholdMembership() {
        return householdMembership;
    }

    public void changeAdministrator(User oldAdministrator, User newAdministrator){
        if(householdMembership.get(oldAdministrator) == ADMIN ){
            if(!householdMembership.containsKey(newAdministrator)){
                addUser(administrator, newAdministrator);
            }
            administrator = newAdministrator;
            householdMembership.put(oldAdministrator, MEMBER);
            householdMembership.put(newAdministrator, ADMIN);
        }
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(householdID))
                .add(name)
                .add(address)
                .add(administrator.getName())
                .toString();
    }
}
