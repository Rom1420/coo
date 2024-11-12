package fr.unice.polytech.join;

import fr.unice.polytech.GroupOrderService;
import fr.unice.polytech.utility.order.GroupOrderImpl;
import fr.unice.polytech.utility.order.GroupOrderProxy;
import fr.unice.polytech.utility.order.Order;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class JoinGroupService {

    HashMap<Integer, GroupOrderProxy> groupOrders = GroupOrderService.getGroupOrderServiceInstance().getGroupOrders();


    public void joinGroup(Boolean validate, Integer userId, Integer groupOrderId, ArrayList<String> articles, ArrayList<String> menus) {
        if (validate) {
            if (!groupOrders.containsKey(groupOrderId)) throw new RuntimeException("Idenrifiant de groupe inexistant !");
            // Récupération du groupe et des paramètres utiles
            GroupOrderProxy groupOrder = groupOrders.get(groupOrderId);
            Date deliveryDate = groupOrder.getGroupOrderDeliveryDate();
            String deliveryLocation = groupOrder.getGroupOrderDeliveryLocation();
            String restaurant = groupOrder.getRestaurant();
            // Ajoute de l'utilisateur et de sa commande au groupe
            //if (!groupOrder.getUserList().contains(joiner)) groupOrder.addMember(joiner); ajout fait dans GroupOrderImpl
            Order userOrder = new Order(new Date(), deliveryDate, deliveryLocation, restaurant);
            userOrder.setOrderArticlesAndMenus(articles, menus); // création de sa commande avec les articles et menus qu'il a sélectionné
            groupOrder.addOrUpdateUserOrder(userId, userOrder);
        }
    }
}
