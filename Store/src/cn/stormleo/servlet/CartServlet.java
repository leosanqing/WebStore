package cn.stormleo.servlet;

import cn.stormleo.domain.Cart;
import cn.stormleo.domain.CartItem;
import cn.stormleo.domain.Product;
import cn.stormleo.service.ProductService;
import cn.stormleo.service.impl.ProductServiceImpl;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "CartServlet" ,urlPatterns = "/CartServlet")
public class CartServlet extends BaseServlet {
    public String addItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // 获得 Session
        Cart cart=(Cart)request.getSession().getAttribute("cart");
        if(null==cart){
            cart=new Cart();
            request.getSession().setAttribute("cart",cart);
        }

        // 获得参数
        String pid = request.getParameter("pid");
        int num=Integer.parseInt(request.getParameter("quantity"));

        // 通过商品id查询商品
        ProductService productService = new ProductServiceImpl();
        Product product =productService.findProductById(pid);

        // 将信息放入 购物车项
        CartItem cartItem=new CartItem();
        cartItem.setNum(num);
        cartItem.setProduct(product);

        // 将购物项存到购物车中
        cart.addItemToCart(cartItem);

        response.sendRedirect("/jsp/cart.jsp");
        return null;
    }
}
