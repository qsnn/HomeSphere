package homeSphere.domain.house;

import homeSphere.log.Log;

import java.util.*;

public class Household {
    private final int householdID;
    private String name;
    private String address;
    private Integer administratorID;
    protected final Set<Log> householdLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //使用记录


    public Household(int householdID, String name, String address, Integer creatorID) {
        this.householdID = householdID;
        this.name = name;
        this.address = address;
        administratorID = creatorID;
        householdLogs.add(new Log(Integer.toString(getHouseholdID()),"创建家庭：" + name, Log.LogType.INFO, this.toString()));
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

    public Integer getAdministratorID() {
        return administratorID;
    }

    public Set<Log> getHouseholdLogs() {
        return householdLogs;
    }

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getHouseholdID()))
                .add(getName())
                .add(getAddress())
                .toString();
    }

}
