package cn.stormleo.servlet;

import cn.stormleo.domain.PageModel02;
import cn.stormleo.domain.Product;
import cn.stormleo.service.ProductService;
import cn.stormleo.service.impl.ProductServiceImpl;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ProductServlet" , urlPatterns = "/ProductServlet")
public class ProductServlet extends BaseServlet {
    public String findProductById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        String pid=request.getParameter("pid");
        ProductService productService = new ProductServiceImpl();
        Product product=productService.findProductById(pid);
        request.getSession().setAttribute("product",product);
        return "/jsp/product_info.jsp";
    }



    public String findProductsByCidWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // 获取Cid和当前页 curNum
        int curNum=Integer.parseInt(request.getParameter("num"));
        String cid=request.getParameter("cid");

        // 调用业务层功能，查询分页下的商品信息
        // 返回PageModel 对象（1.当前商品信息，2.分页3.url）
        ProductService productService = new ProductServiceImpl();
        PageModel02 pm = productService.findProductsByCidWithPage(cid,curNum);

        // 将 pm 存入session中
        request.getSession().setAttribute("page",pm);


        return "/jsp/product_list.jsp";
    }
}
