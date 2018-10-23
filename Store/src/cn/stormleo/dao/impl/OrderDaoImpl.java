package cn.stormleo.dao.impl;

import cn.stormleo.dao.OrderDao;
import cn.stormleo.domain.Order;
import cn.stormleo.domain.OrderItem;
import cn.stormleo.domain.User;
import cn.stormleo.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public void submitOrder(Order order) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="insert into orders values(?,?,?,?,?,?,?,?)";
        Object param[]={order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),
                        order.getName(),order.getTelephone(),order.getUser().getUid()    };
        queryRunner.update(sql,param);
    }

    @Override
    public void submitOrderItem(OrderItem orderItem) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="insert into orderitem values(?,?,?,?,?)";
        Object param[]={orderItem.getItemid(),orderItem.getQuantity(),orderItem.getTotal(),
                orderItem.getProduct().getPid(),orderItem.getOrder().getOid()};
        queryRunner.update(sql,param);
    }

    @Override
    public int getTotalRecord(User user) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select count(*) from orders where uid=?";
        Long num=(Long)queryRunner.query(sql,new ScalarHandler(),user.getUid());
        return num.intValue();
    }

    @Override
    public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where uid=? limit ?,?";
        List<Order> list=queryRunner.query(sql,new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
        for(Order order:list){
            String oid=order.getOid();
        }

        return list;
    }
}
