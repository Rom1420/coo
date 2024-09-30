package fr.unice.polytech.user;

public class RegisteredUser extends UserAccount {
    private boolean isGroupMember;

    public RegisteredUser(String name, int id, String password) {
        super(name, id, password);
        this.isGroupMember = false;
    }

    public boolean isGroupMember() {
        return isGroupMember;
    }

    public void setGroupMember(boolean isGroupMember) {
        this.isGroupMember = isGroupMember;
    }
}
