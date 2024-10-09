package fr.unice.polytech.order;

import fr.unice.polytech.user.RegisteredUser;

import java.util.ArrayList;
import java.util.List;

public class GroupOrder {
    private int groupId;
    private List<RegisteredUser> userList;
    private List<Order> groupOrders;

    public GroupOrder(int groupId) {
        this.groupId = groupId;
        this.userList = new ArrayList<>();
        this.groupOrders = new ArrayList<>();
    }

    public void addMember(RegisteredUser user) {
        userList.add(user);
    }

    public void createOrderInGroup(Order order) {
        groupOrders.add(order);
    }

    public List<Order> getOrders() {
        return groupOrders;
    }

    public List<RegisteredUser> getUserList() {
        return userList;
    }
}
