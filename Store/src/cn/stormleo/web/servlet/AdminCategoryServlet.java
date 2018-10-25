package cn.stormleo.web.servlet;

import cn.stormleo.domain.Category;
import cn.stormleo.domain.PageModel;
import cn.stormleo.service.CategoryService;
import cn.stormleo.service.ProductService;
import cn.stormleo.service.impl.CategoryServiceImpl;
import cn.stormleo.service.impl.ProductServiceImpl;
import cn.stormleo.utils.UUIDUtils;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "AdminCategoryServlet",urlPatterns = "/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    public String getAllCats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        CategoryService categoryService=new CategoryServiceImpl();
        List<Category> list=categoryService.getAllCats();
        request.getSession().setAttribute("category",list);

        return "/admin/category/list.jsp";
    }

    //admin/category/add.jsp
    //跳转到添加分类的UI界面
    public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        return "admin/category/add.jsp";
    }
    public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        // 获取JSP页面传回的数据
        String cname=request.getParameter("cname");
        String cid=UUIDUtils.getId();
        Category category=new Category();
        category.setCid(cid);
        category.setCname(cname);

        // 添加商品
        CategoryService categoryService=new CategoryServiceImpl();
        categoryService.addCategory(category);

        // 查询分类信息，并重定向页面
        List<Category> list=categoryService.getAllCats();
        request.getSession().setAttribute("category",list);
        response.sendRedirect("/AdminCategoryServlet?method=getAllCats");
        return null;
    }

    //admin/product/list.jsp
    public String getAllProductWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int curNum=Integer.parseInt(request.getParameter("num"));
        ProductService productService=new ProductServiceImpl();
        PageModel pageModel =productService.getAllProductWithPage(curNum);
        request.getSession().setAttribute("page",pageModel);
        return "admin/product/list.jsp";
    }
}
