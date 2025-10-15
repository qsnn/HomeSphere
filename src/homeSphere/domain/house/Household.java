package homeSphere.domain.house;

import java.util.*;

public class Household {
    private final int householdID;
    private String name;
    private String address;
    private Integer administratorID;

    public Household(int householdID, String name, String address, Integer creatorID) {
        this.householdID = householdID;
        this.name = name;
        this.address = address;
        administratorID = creatorID;
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

    @Override
    public String toString() {
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getHouseholdID()))
                .add(getName())
                .add(getAddress())
                .toString();
    }

}
