package com.qsnn.homeSphere.domain.users;

import com.qsnn.homeSphere.log.Log;

import java.util.Comparator;
import java.util.Set;
import java.util.StringJoiner;
import java.util.TreeSet;

public class User {
    private final int userID;
    private String username;
    private String password;
    private String name;
    private String address;
    protected final Set<Log> userLogs = new TreeSet<>(Comparator.comparing(Log::getT)); //使用记录


    public User(int userID, String username, String password, String name, String address) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.name = name;
        this.address = address;
        userLogs.add(new Log(Integer.toString(getUserID()),"注册用户：" + name, Log.LogType.INFO, this.toString())) ;
    }

    public int getUserID() {
        return userID;
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

    public Set<Log> getUserLogs() {
        return userLogs;
    }

    @Override
    public String toString(){
        return new StringJoiner(" - ", "[", "]")
                .add(Integer.toString(getUserID()))
                .add(getName())
                .add(getUsername())
                .add(getAddress())
                .toString();
    }

}
