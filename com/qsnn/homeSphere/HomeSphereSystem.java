package qsnn.homeSphere;

import qsnn.homeSphere.domain.deviceModule.Device;
import qsnn.homeSphere.domain.house.Household;
import qsnn.homeSphere.domain.users.User;

import java.util.Date;
import java.util.List;

public class HomeSphereSystem{
    private Household household;
    private User currentUser;

    public HomeSphereSystem(Household household) {
        this.household = household;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void login(String loginName, String loginPassword){
        for (User user : household.getUsers()) {
            if (user.getLoginName().equals(loginName)){
                currentUser = user;
            }
        }
    }

    public void logoff(){
        currentUser = null;
    }

    public User register(String loginName, String loginPassword, String userName, String email){
        if (household.containsUser(loginName)){
            return null;
        }else {
            User user = new User(household.getUsers().size() + 1, loginName, loginPassword, userName, email);
            household.addUser(user);
            return user;
        }
    }

    // 或者如果需要更详细的输出，可以这样写：
    public void displayUsers() {
        System.out.println("=== Users List ===");
        household.getUsers().forEach(System.out::println);
        System.out.println("Total: " + household.getUsers().size() + " users");
    }

    public void displayRooms() {
        System.out.println("=== Rooms List ===");
        household.getRooms().forEach(System.out::println);
        System.out.println("Total: " + household.getRooms().size() + " rooms");
    }

    public void displayDevices(){
        System.out.println("=== Devices List ===");
        List<Device> devices = household.listAllDevices();
        devices.forEach(System.out::println);
        System.out.println("Total: " + devices.size() + " devices");
    }

    public void displayAutoScenes() {
        System.out.println("=== Automation Scenes List ===");
        household.getAutoScenes().forEach(System.out::println);
        System.out.println("Total: " + household.getAutoScenes().size() + " scenes");
    }

    public void displayEnergyReportings(Date startTime, Date endTime){

    }

    public void manualTrigSceneById(int sceneId){
        household.getAutoSceneById(sceneId).manualTrig();
    }
}