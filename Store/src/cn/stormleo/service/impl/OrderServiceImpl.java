package cn.stormleo.service.impl;

import cn.stormleo.dao.OrderDao;
import cn.stormleo.dao.impl.OrderDaoImpl;
import cn.stormleo.domain.Order;
import cn.stormleo.domain.OrderItem;
import cn.stormleo.domain.PageModel;
import cn.stormleo.domain.User;
import cn.stormleo.service.OrderService;
import cn.stormleo.utils.JDBCUtils;
import com.sun.org.apache.xpath.internal.operations.Or;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public void submitOrder(Order order) {

        try {
            JDBCUtils.startTransaction();
            OrderDao orderDao=new OrderDaoImpl();
            orderDao.submitOrder(order);
            for (OrderItem orderItem:order.getList()){
                orderDao.submitOrderItem(orderItem);
            }
            JDBCUtils.commitAndClose();
        } catch (SQLException e) {
            JDBCUtils.rollbackAndClose();
        }
    }

    @Override
    public PageModel findMyOrderWithPage(User user, int curNum) throws SQLException, InvocationTargetException, IllegalAccessException {

        OrderDao orderDao=new OrderDaoImpl();
        // 查询全部数据条数
        int totalRecord=orderDao.getTotalRecord(user);

        //创建PageModel
        PageModel pageModel=new PageModel(curNum,totalRecord,3);
        List list= orderDao.findMyOrderWithPage(user,pageModel.getStartIndex(),pageModel.getPageSize());

        pageModel.setRecords(list);

        pageModel.setUrl("OrderServlet?method=findMyOrderWithPage");

        return pageModel;
    }

    @Override
    public Order findOrderByOrderId(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {
        OrderDao orderDao=new OrderDaoImpl();
        return orderDao.findOrderByOrderId(oid);
    }

    @Override
    public void updateInfo(Order order) throws SQLException {
        OrderDao orderDao=new OrderDaoImpl();
        orderDao.updateInfo(order);
    }
}
