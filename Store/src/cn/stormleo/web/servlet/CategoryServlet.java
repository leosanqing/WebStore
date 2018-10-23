package cn.stormleo.web.servlet;

import cn.stormleo.domain.Category;
import cn.stormleo.service.CategoryService;
import cn.stormleo.service.impl.CategoryServiceImpl;
import cn.stormleo.utils.JedisUtil01;
import cn.stormleo.web.base.BaseServlet;
import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoryServlet" , urlPatterns = "/CategoryServlet")
public class CategoryServlet extends BaseServlet {
    public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        Jedis jedis=JedisUtil01.getJedis();
        String jsonStr=jedis.get("allCats");
        if(null==jsonStr||jsonStr.equals("")){
            CategoryService categoryService =new CategoryServiceImpl();
            List<Category> categories= categoryService.getAllCats();
            jsonStr= JSONArray.fromObject(categories).toString();

            jedis.set("allCats",jsonStr);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jsonStr);
        }else {
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(jsonStr);
        }
        return null;
    }
}
