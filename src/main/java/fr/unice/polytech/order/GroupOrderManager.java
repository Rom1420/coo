package fr.unice.polytech.order;

import fr.unice.polytech.restaurant.Article;
import fr.unice.polytech.restaurant.Menu;
import fr.unice.polytech.restaurant.Restaurant;
import fr.unice.polytech.user.RegisteredUser;
import fr.unice.polytech.user.RegisteredUserManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static fr.unice.polytech.user.RegisteredUserManager.getRegisteredUserManagerInstance;

public class GroupOrderManager {

    private static int groupOrderId = 0;

    private HashMap<Integer, GroupOrderImpl> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }
    public Integer  getGroupOrderId() { return groupOrderId; }


    public Integer addGroupOrder() {
        int currentGroupId = groupOrderId;
        groupOrders.put(currentGroupId, new GroupOrderImpl(currentGroupId));
        groupOrderId++;
        return currentGroupId;
    }

    public void setGroupOrderAttributes(Integer groupOrderId, Restaurant restaurant, String deliveryLocation, Date deliveryDate) {
        GroupOrderImpl groupOrder = groupOrders.get(groupOrderId);
        groupOrder.setGroupOrderRestaurant(restaurant);
        groupOrder.setGroupOrderDeliveryLocation(deliveryLocation);
        groupOrder.setGroupOrderDeliveryDate(deliveryDate);
    }

    public void createGroup(Boolean validate, Integer creatorId, Restaurant restaurant, String deliveryLocation, Date deliveryDate, ArrayList<Article> articles, ArrayList<Menu> menus) {
        if (validate) { // On valide la création d'un groupe avec les inputs
            int gid = addGroupOrder();
            setGroupOrderAttributes(gid, restaurant, deliveryLocation, deliveryDate);
            joinGroup(true, creatorId, gid, articles, menus);
        }
    }

    public GroupOrderImpl getGroupOrderById(Integer groupOrderId) {
        return groupOrders.get(groupOrderId);
    }

    public HashMap<Integer, GroupOrderImpl> getGroupOrders() {
        return groupOrders;
    }

    public void removeGroupOrderById(Integer groupOrderId) {
        groupOrders.remove(groupOrderId);
    }

    public void joinGroup(Boolean validate, Integer userId, Integer groupOrderId, ArrayList<Article> articles, ArrayList<Menu> menus) {
        if (validate) {
            // Récupération de l'utilisateur
            RegisteredUserManager registeredUsers = getRegisteredUserManagerInstance();
            RegisteredUser joiner = registeredUsers.getRegisteredUserById(userId);
            // Récupération du groupe et des paramètres utiles
            GroupOrderImpl groupOrder = groupOrders.get(groupOrderId);
            Date deliveryDate = groupOrder.getGroupOrderDeliveryDate();
            String deliveryLocation = groupOrder.getGroupOrderDeliveryLocation();
            Restaurant restaurant = groupOrder.getRestaurant();
            // Ajoute de l'utilisateur et de sa commande au groupe
            if (!groupOrder.getUserList().contains(joiner)) groupOrder.addMember(joiner);
            Order userOrder = new Order(new Date(), deliveryDate, deliveryLocation, restaurant);
            userOrder.setOrderArticlesAndMenus(articles, menus); // création de sa commande avec les articles et menus qu'il a sélectionné
            groupOrder.addOrUpdateUserOrder(joiner, userOrder);
        }
    }
}

