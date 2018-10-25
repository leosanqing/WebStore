package cn.stormleo.web.servlet;

import cn.stormleo.domain.*;
import cn.stormleo.service.OrderService;
import cn.stormleo.service.impl.OrderServiceImpl;
import cn.stormleo.utils.PaymentUtil;
import cn.stormleo.utils.UUIDUtils;
import cn.stormleo.web.base.BaseServlet;
import com.sun.org.apache.xpath.internal.operations.Or;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

    public String findMyOrderWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {

        // 获取用户信息
        User user=(User)request.getSession().getAttribute("userLogin");

        int curNum=Integer.parseInt(request.getParameter("num"));

        OrderService orderService = new OrderServiceImpl();
        PageModel pageModel= orderService.findMyOrderWithPage(user,curNum);
        request.getSession().setAttribute("page",pageModel);
        System.out.println(pageModel.toString());
        return "/jsp/order_list.jsp";
    }

    public String findOrderByOrderId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {

        String oid=  request.getParameter("oid");

        OrderService orderService=new OrderServiceImpl();
        Order order=orderService.findOrderByOrderId(oid);

        request.getSession().setAttribute("order",order);

        return  "/jsp/order_info.jsp";
    }


    //payment  支付功能
    public String payment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {
        // 获取到传过来的参数
        String name=request.getParameter("name");
        String address=request.getParameter("address");
        String oid=request.getParameter("oid");
        String telephone = request.getParameter("telephone");
        String pd_FrpId = request.getParameter("pd_FrpId");

        // 查询订单数据
        OrderService orderService= new OrderServiceImpl();
        Order order = orderService.findOrderByOrderId(oid);
        order.setAddress(address);
        order.setTelephone(telephone);
        order.setName(name);


        orderService.updateInfo(order);
        // 获取易支付的信息
            String p0_Cmd = "Buy";
            //商户编号
            String p1_MerId = "10001126856";
            //订单编号
            String p2_Order = oid;
            //金额
            String p3_Amt = "0.01";
            String p4_Cur = "CNY";
            String p5_Pid = "";
            String p6_Pcat = "";
            String p7_Pdesc = "";
            //接受响应参数的Servlet
            String p8_Url = "http://localhost:8080/OrderServlet?method=callBack";
            String p9_SAF = "";
            String pa_MP = "";
            String pr_NeedResponse = "1";
            //公司的秘钥
            String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

            //调用易宝的加密算法,对所有数据进行加密,返回电子签名
            String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

            StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
            sb.append("p0_Cmd=").append(p0_Cmd).append("&");
            sb.append("p1_MerId=").append(p1_MerId).append("&");
            sb.append("p2_Order=").append(p2_Order).append("&");
            sb.append("p3_Amt=").append(p3_Amt).append("&");
            sb.append("p4_Cur=").append(p4_Cur).append("&");
            sb.append("p5_Pid=").append(p5_Pid).append("&");
            sb.append("p6_Pcat=").append(p6_Pcat).append("&");
            sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
            sb.append("p8_Url=").append(p8_Url).append("&");
            sb.append("p9_SAF=").append(p9_SAF).append("&");
            sb.append("pa_MP=").append(pa_MP).append("&");
            sb.append("pd_FrpId=").append(pd_FrpId).append("&");
            sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
            sb.append("hmac=").append(hmac);

            System.out.println(sb.toString());
            // 使用重定向：
            response.sendRedirect(sb.toString());

        return null;
    }
    public String callBack(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvocationTargetException, IllegalAccessException {
        String p1_MerId = request.getParameter("p1_MerId");
        String r0_Cmd = request.getParameter("r0_Cmd");
        String r1_Code = request.getParameter("r1_Code");
        String r2_TrxId = request.getParameter("r2_TrxId");
        String r3_Amt = request.getParameter("r3_Amt");
        String r4_Cur = request.getParameter("r4_Cur");
        String r5_Pid = request.getParameter("r5_Pid");
        String r6_Order = request.getParameter("r6_Order");
        String r7_Uid = request.getParameter("r7_Uid");
        String r8_MP = request.getParameter("r8_MP");
        String r9_BType = request.getParameter("r9_BType");
        String rb_BankId = request.getParameter("rb_BankId");
        String ro_BankOrderId = request.getParameter("ro_BankOrderId");
        String rp_PayDate = request.getParameter("rp_PayDate");
        String rq_CardNo = request.getParameter("rq_CardNo");
        String ru_Trxtime = request.getParameter("ru_Trxtime");

        // hmac
        String hmac = request.getParameter("hmac");
        // 利用本地密钥和加密算法 加密数据
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向
                // 更新数据
                OrderService orderService = new OrderServiceImpl();
                Order order=orderService.findOrderByOrderId(r6_Order);
                // 设置订单状态为2
                order.setState(2);
                orderService.updateInfo(order);

                request.getSession().setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);
                response.setContentType("text/html;charset=utf-8");
                return "/jsp/info.jsp";
            } else if (r9_BType.equals("2")) {
                // 修改订单状态:
                // 服务器点对点，来自于易宝的通知
                System.out.println("收到易宝通知，修改订单状态！");//
                // 回复给易宝success，如果不回复，易宝会一直通知
                response.getWriter().print("success");
            }

        } else {
            throw new RuntimeException("数据被篡改！");
        }
        return null;
    }
}
