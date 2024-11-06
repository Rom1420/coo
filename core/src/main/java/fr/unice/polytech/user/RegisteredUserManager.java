package fr.unice.polytech.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegisteredUserManager {

    Map<Integer, RegisteredUser> registeredUsers;

    private RegisteredUserManager() {
        registeredUsers = new HashMap<>();
    }

    // Singleton
    private static final RegisteredUserManager REGISTERED_USER_MANAGER_INSTANCE = new RegisteredUserManager();

    public static RegisteredUserManager getRegisteredUserManagerInstance() { return REGISTERED_USER_MANAGER_INSTANCE; }

    public RegisteredUser getRegisteredUserById(int userId) { return registeredUsers.get(userId); }

    public void addNewRegisteredUser(RegisteredUser registeredUser) { registeredUsers.put(registeredUser.getId(), registeredUser); }
}
