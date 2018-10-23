package cn.stormleo.service;

import cn.stormleo.dao.OrderDao;
import cn.stormleo.dao.impl.OrderDaoImpl;
import cn.stormleo.domain.Order;
import cn.stormleo.domain.PageModel;
import cn.stormleo.domain.User;

import java.sql.SQLException;

public interface OrderService {
    void submitOrder(Order order);

    PageModel findMyOrderWithPage(User user, int curNum) throws SQLException;
}
