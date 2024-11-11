package fr.unice.polytech.utility.discount;

import fr.unice.polytech.utility.order.GroupOrderImpl;

/*Dans le pattern Strategy, l'idée est d'avoir différentes manières de faire une même chose
(ici, appliquer des réductions).
Chaque stratégie implémente l'interface DiscountStrategy :*/
public interface DiscountStrategy {
    float applyDiscount(GroupOrderImpl groupOrder);
}

/*Ça définit une méthode générique applyDiscount() que toutes les stratégies vont devoir implémenter.
Chaque stratégie calculera la réduction de manière différente.*/