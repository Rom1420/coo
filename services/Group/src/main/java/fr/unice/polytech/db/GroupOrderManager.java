package fr.unice.polytech.db;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.components.GroupOrderImpl;
import fr.unice.polytech.components.GroupOrderProxy;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fr.unice.polytech.server.CreateGroupService.logger;

public class GroupOrderManager {

    private static final String GROUP_DB_PATH = "groupDB.json";
    private static final String GROUP_ID_DB_PATH = "groupIdDB.json";

    private static int groupOrderId = 0;
    private HashMap<Integer, GroupOrderProxy> groupOrders;

    private GroupOrderManager() {
        groupOrders = new HashMap<>();
    }

    // Singleton
    private static final GroupOrderManager GROUP_ORDER_MANAGER_INSTANCE = new GroupOrderManager();

    public static GroupOrderManager getGroupOrderManagerInstance() {
        return GROUP_ORDER_MANAGER_INSTANCE;
    }

    public HashMap<Integer, GroupOrderProxy> getGroupOrders() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(GROUP_DB_PATH);
            if (!file.exists()) {
                return new HashMap<>();
            }

            Map<Integer, GroupOrderImpl> rawGroups = objectMapper.readValue(file, new TypeReference<Map<Integer, GroupOrderImpl>>() {});
            HashMap<Integer, GroupOrderProxy> proxyGroups = new HashMap<>();

            rawGroups.forEach((id, impl) -> proxyGroups.put(id, new GroupOrderProxy(impl)));
            logger.info("Nombre de groupes :" + proxyGroups.size());
            return proxyGroups;
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public Integer getGroupOrderId() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(GROUP_ID_DB_PATH);
            if (!file.exists()) {
                writeGroupOrderId(1);
                return 1;
            }

            return objectMapper.readValue(file, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
    }

    public Integer getGroupOrderIdAndIncrease() {
        Integer currentId = getGroupOrderId();
        writeGroupOrderId(currentId + 1);
        return currentId;
    }

    public void addGroup(Integer groupId, GroupOrderImpl groupOrder) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, GroupOrderImpl> existingGroups = getRawGroupOrders();
        existingGroups.put(groupId, groupOrder);

        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(GROUP_DB_PATH), existingGroups);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<Integer, GroupOrderImpl> getRawGroupOrders() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            File file = new File(GROUP_DB_PATH);
            if (!file.exists()) {
                return new HashMap<>();
            }

            return objectMapper.readValue(file, new TypeReference<Map<Integer, GroupOrderImpl>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    private void writeGroupOrderId(Integer id) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(GROUP_ID_DB_PATH), id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateGroupStatusInDB(int groupId, String newStatus) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, GroupOrderImpl> existingGroups = getRawGroupOrders();

        GroupOrderImpl groupOrder = existingGroups.get(groupId);
        if (groupOrder != null) {
            groupOrder.setStatus(newStatus);
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(GROUP_DB_PATH), existingGroups);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("In updateStatus, Group ID " + groupId + " not found in the database.");
        }
    }

    public void addOrderToExistingGroup(int groupId, int orderId, int preparationTime) {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<Integer, GroupOrderImpl> existingGroups = getRawGroupOrders();

        GroupOrderImpl groupOrder = existingGroups.get(groupId);
        if (groupOrder != null) {
            groupOrder.addOrUpdateUserOrder(orderId, preparationTime);
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(GROUP_DB_PATH), existingGroups);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("In add, Group ID " + groupId + " not found in the database.");
        }
    }


}
