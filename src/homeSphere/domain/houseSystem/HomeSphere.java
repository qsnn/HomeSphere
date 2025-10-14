package homeSphere.domain.houseSystem;

import homeSphere.domain.users.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class HomeSphere {
    private final String version = "v1.0";
    private final Map<Integer, Household> households = new HashMap<>();
    private final Map<Integer, User> users = new HashMap<>();

    public Household createHousehold(String name, String address, User creator){
        Household h = new Household(createFreeID(households.keySet()), name, address, creator);
        households.put(h.getHouseholdID(), h);
        creator.addHousehold(h);
        return h;
    }
    public Household findHouseholdByID(int householdID){
        return households.get(householdID);
    }

    public User registerUser(String username, String password, String name, String address){
        return new User(createFreeID(users.keySet()), username, password, name, address);
    }

    public User login(int userID, String password){
        if(users.get(userID) == null || !users.get(userID).getPassword().equals(password)){
            return null;
        }
        return users.get(userID);
    }

    public void logout(int userID){
    }

    public static Integer createFreeID(Set<Integer> existingIds) {
        Random random = new Random();
        int maxAttempts = 1000; // 防止无限循环
        int attempts = 0;
        int id = random.nextInt(100000, 1000000000);
        while (attempts < maxAttempts) {
            id = random.nextInt(100000, 1000000000); // 6-9位数字
            if (!existingIds.contains(id)) {
                break;
            }
            attempts++;
        }
        return id;
    }

}
