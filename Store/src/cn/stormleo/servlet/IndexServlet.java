package cn.stormleo.servlet;


import cn.stormleo.dao.ProductDao;
import cn.stormleo.dao.impl.ProductDaoImpl;
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
import java.util.List;

@WebServlet(name = "IndexServlet",urlPatterns = "/IndexServlet")
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        ProductService productService = new ProductServiceImpl();
        List<Product> newProducts = productService.findNewProducts();
        List<Product> hotProducts = productService.findHotProducts();

        request.getSession().setAttribute("newProducts",newProducts);
        request.getSession().setAttribute("hotProducts",hotProducts);
        return "/jsp/index.jsp";
    }
}
