package cn.stormleo.web.servlet;

import cn.stormleo.domain.Order;
import cn.stormleo.service.OrderService;
import cn.stormleo.service.impl.OrderServiceImpl;
import cn.stormleo.utils.BeanFactory;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet",urlPatterns = "/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
    ///AdminOrderServlet?method=findAllOrder

    // 查询所有订单
    public String findAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        OrderService orderService= new OrderServiceImpl();
        String state=request.getParameter("state");
        System.out.println(state);
        List<Order> orderList=null;
        if(null==state||state.equals("")){
            orderList=orderService.findAllOrder();
        }else {
            orderList=orderService.findAllOrder(state);
        }
        request.getSession().setAttribute("allOrder",orderList);
        return "/admin/order/list.jsp";
    }

}
