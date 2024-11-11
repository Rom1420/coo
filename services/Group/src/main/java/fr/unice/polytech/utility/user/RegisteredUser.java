package fr.unice.polytech.utility.user;

public class RegisteredUser extends UserAccount {
    private boolean isGroupMember;
    private boolean isRestaurantManager;

    public RegisteredUser(String name, int id, String password) {
        super(name, id, password);
        this.isGroupMember = false;
        this.isRestaurantManager = false;
    }

    public boolean isGroupMember() {
        return isGroupMember;
    }

    public void setGroupMember(boolean isGroupMember) {
        this.isGroupMember = isGroupMember;
    }
    public boolean isARestaurantManager(){
        return isRestaurantManager;
    }
    public void setRestaurantManager(){
        this.isRestaurantManager=!isRestaurantManager;
    }
}
