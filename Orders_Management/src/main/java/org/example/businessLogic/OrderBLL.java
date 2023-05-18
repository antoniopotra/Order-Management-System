package org.example.businessLogic;

import org.example.dataAccess.OrderDAO;
import org.example.model.Order;

import java.sql.SQLException;

/**
 * this class acts as an intermediate layer between the presentation layer and the order dao layer
 */
public class OrderBLL {
    private final OrderDAO orderDAO;

    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    public void insertOrder(Order order) throws SQLException {
        orderDAO.insert(order);
    }
}
