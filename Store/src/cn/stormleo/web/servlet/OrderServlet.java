package cn.stormleo.web.servlet;

import cn.stormleo.domain.*;
import cn.stormleo.service.OrderService;
import cn.stormleo.service.impl.OrderServiceImpl;
import cn.stormleo.utils.UUIDUtils;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "OrderServlet",urlPatterns = "/OrderServlet")
public class OrderServlet extends BaseServlet {
    public String submitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // 获取用户登录信息
        User user= (User) request.getSession().getAttribute("userLogin");
        if(user==null){
            request.getSession().setAttribute("msg","请登录后再下单");
            return "/jsp/info.jsp";
        }

        // 创建订单对象
        Order order = new Order();

        // 获取购物车对象
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        order.setOid(UUIDUtils.getId());
        order.setOrdertime(new Date());
        order.setTotal(cart.getTotal());
        order.setState(1);
        order.setUser(user);


        // 获取订单中的所有项
        for (CartItem cartItem:cart.getCartItems()) {
            OrderItem orderItem=new OrderItem();
            orderItem.setItemid(UUIDUtils.getId());
            orderItem.setQuantity(cartItem.getNum());
            orderItem.setTotal(cartItem.getTotal());
            orderItem.setOrder(order);
            orderItem.setProduct(cartItem.getProduct());
            // 将订单项添加到订单列表
            order.getList().add(orderItem);
        }

        OrderService orderService =new OrderServiceImpl();
        orderService.submitOrder(order);

        request.getSession().setAttribute("order",order);
        cart.clear();
        return "/jsp/order_info.jsp";
    }

    public String findMyOrderWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {

        // 获取用户信息
        User user=(User)request.getSession().getAttribute("userLogin");

        int curNum=Integer.parseInt(request.getParameter("num"));

        OrderService orderService = new OrderServiceImpl();
        PageModel pageModel= orderService.findMyOrderWithPage(user,curNum);
        request.getSession().setAttribute("page",pageModel);

        return "/jsp/order_list.jsp";
    }

}
