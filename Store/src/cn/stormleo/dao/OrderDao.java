package cn.stormleo.dao;

import cn.stormleo.domain.Order;
import cn.stormleo.domain.OrderItem;
import cn.stormleo.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    void submitOrder(Order order) throws SQLException;

    void submitOrderItem(OrderItem orderItem) throws SQLException;

    int getTotalRecord(User user) throws SQLException;

    List findMyOrderWithPage(User user, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    Order findOrderByOrderId(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateInfo(Order order) throws SQLException;
}
