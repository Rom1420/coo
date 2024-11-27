package fr.unice.polytech.components;

import fr.unice.polytech.entities.Order;
import fr.unice.polytech.entities.Restaurant;
import fr.unice.polytech.interfaces.OrderInterface;

import java.util.Date;

public class OrderImplem implements OrderInterface {
    @Override
    public void setOrderRestaurant(Restaurant restaurant) {

    }

    @Override
    public void setOrderDeliveryDate(Date deliveryDate) {

    }

    @Override
    public void setOrderDeliveryLocation(String deliveryLocation) {

    }

    @Override
    public void setOrderUser(Integer user) {

    }

    @Override
    public Order getOrder() {
        return null;
    }

    @Override
    public Restaurant getRestaurant() {
        return null;
    }

    @Override
    public Integer getUser() {
        return null;
    }

    @Override
    public Date getOrderDeliveryDate() {
        return null;
    }

    @Override
    public String getOrderDeliveryLocation() {
        return null;
    }

    @Override
    public void validateOrder() {

    }

    @Override
    public String getStatus() {
        return null;
    }

    @Override
    public void setStatus(String status) {

    }

    @Override
    public void closeOrder() {

    }
}
