package homeSphere;

import homeSphere.domain.automationScene.AutomationScene;
import homeSphere.domain.devices.OnlineStatusType;
import homeSphere.domain.devices.PowerStatusType;
import homeSphere.domain.devices.airconditioner.AirConditioner;
import homeSphere.domain.devices.airconditioner.AirConditionerModelController;
import homeSphere.domain.houseSystem.HomeSphere;
import homeSphere.domain.houseSystem.Household;
import homeSphere.domain.houseSystem.Room;
import homeSphere.domain.users.User;
import homeSphere.service.manufacturer.Manufacturer;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        HomeSphere home = new HomeSphere();
        User u = home.registerUser("qsnnywx", "YWX123", "杨文轩", "NWPU");
        User u1 = home.registerUser("clone", "YWX123", "clone", "clone");
        Household h = home.createHousehold("家","海2A", u);
        Room kt = new Room(h, "客厅", 200);
        h.changeAdministrator(u, u1);
        System.out.println(h.getAdministrator());
        h.getUsers().forEach(System.out::println);
        u1.getHouseholds().forEach(System.out::println);

        AirConditioner ac = new AirConditioner("格力21843687", "空调", "Android", new Manufacturer("格力"), "Green", 1000);
        kt.addDevice(ac);
        AutomationScene ams = new AutomationScene("睡眠模式");
        Map<String, Object> operations =  new HashMap<>();
        operations.put("onlineStatus", OnlineStatusType.ONLINE);
        operations.put("powerStatus", PowerStatusType.POWERED);
        operations.put("model", AirConditionerModelController.AirConditionerModelType.AUTO);
        operations.put("temperature", 26);
        ams.addOperator(ac, operations);
        ams.execute();
        new Scanner(System.in).nextLine();
        ac.close();
    }
}
