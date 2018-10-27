package cn.stormleo.dao.impl;

import cn.stormleo.dao.OrderDao;
import cn.stormleo.domain.Order;
import cn.stormleo.domain.OrderItem;
import cn.stormleo.domain.Product;
import cn.stormleo.domain.User;
import cn.stormleo.utils.JDBCUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
    public List findMyOrderWithPage(User user, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where uid=? limit ?,?";
        // 查询一页的数据
        List<Order> list=queryRunner.query(sql,new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);

        // 获取到每笔订单的 oid
        for(Order order:list){
            String oid=order.getOid();
            sql="select * from orderItem o ,product p where p.pid=o.pid and oid=?";
            List<Map<String,Object>> list02 = queryRunner.query(sql,new MapListHandler(),oid);

            // 遍历List
            for(Map<String,Object> map:list02){
                OrderItem orderItem=new OrderItem();
                Product product =new Product();
                DateConverter dt= new DateConverter();
                dt.setPattern("yyyy-MM-dd");
                ConvertUtils.register(dt,java.util.Date.class);
                BeanUtils.populate(orderItem,map);
                BeanUtils.populate(product,map);

                // 让每个商品和订单项发生关联
                orderItem.setProduct(product);
                // 让每个订单项和订单发生关系
                order.getList().add(orderItem);
                System.out.println(map.keySet()+""+map.values());
            }
        }

        return list;
    }

    @Override
    public Order findOrderByOrderId(String oid) throws SQLException, InvocationTargetException, IllegalAccessException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where oid=? ";
        Order order =queryRunner.query(sql,new BeanHandler<Order>(Order.class),oid);


        sql="select * from orderitem o ,product p where o.pid=p.pid and oid=?";
        List<Map<String,Object>> list02 = queryRunner.query(sql,new MapListHandler(),oid);

        // 遍历List
        for(Map<String,Object> map:list02){
            OrderItem orderItem=new OrderItem();
            Product product =new Product();
            DateConverter dt= new DateConverter();
            dt.setPattern("yyyy-MM-dd");
            ConvertUtils.register(dt,java.util.Date.class);
            BeanUtils.populate(orderItem,map);
            BeanUtils.populate(product,map);

            // 让每个商品和订单项发生关联
            orderItem.setProduct(product);
            // 让每个订单项和订单发生关系
            order.getList().add(orderItem);
        }

        return order;
    }

    @Override
    public void updateInfo(Order order) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="update orders set ordertime=? ,total=? ,state=? ,address=? ,name=? ,telephone=?" +
                " where oid=?";
        Object param[]={order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),
                order.getName(),order.getTelephone(),order.getOid()};
        queryRunner.update(sql,param);
    }

    @Override
    public List<Order> findAllOrder() throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders";
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class));
    }

    @Override
    public List<Order> findAllOrder(String state) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from orders where state=?";
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class),state);
    }
}
