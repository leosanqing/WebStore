package cn.stormleo.web.servlet;

import cn.stormleo.domain.User;
import cn.stormleo.service.UserService;
import cn.stormleo.service.impl.UserServiceImpl;
import cn.stormleo.utils.MailUtils;
import cn.stormleo.utils.MyBeanUtils;
import cn.stormleo.utils.UUIDUtils;
import cn.stormleo.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

@WebServlet(name = "UserServlet",urlPatterns = "/UserServlet")
public class UserServlet extends BaseServlet {
    public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/jsp/register.jsp";
    }


    //userRegist
    public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException {
        // 接受参数
        Map<String, String[]> map = request.getParameterMap();
        for(Object object:map.values()){
            System.out.println(object);
        }
        User user = new User();
        MyBeanUtils.populate(user,map);

        // 为用户设置 uid ， state ， code
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());

        System.out.println(user.toString());

        UserService userService=new UserServiceImpl();
        try {
            userService.userRegist(user);
            MailUtils.sendMail(user.getEmail(),user.getCode());
            request.setAttribute("msg","注册成功，请激活");
        } catch (SQLException e) {
            request.setAttribute("msg","注册失败，请重新注册");
        }finally {
            return "/jsp/info.jsp";
        }


    }

    /**
     * 用户激活
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException, SQLException {
        // 接受参数
        String code = request.getParameter("code");
        UserService userService= new  UserServiceImpl();
        boolean flag= userService.active(code);
        if(flag==true){
            String msg="激活成功，请重新登录";
            request.setAttribute("msg",msg);
            return "/jsp/login.jsp";
        }else{
            String msg="激活失败，请重新激活";
            request.setAttribute("msg",msg);
            return "/jsp/info.jsp";
        }
    }

    public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException, SQLException {
        return "/jsp/login.jsp";
    }

        public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException, SQLException {
        // 获取用户信息
        User user = new User();
        MyBeanUtils.populate(user,request.getParameterMap());
        // 调用
        UserService userService =new UserServiceImpl();

        User user02=new User();
        try {
            user02 = userService.userLogin(user);
            request.getSession().setAttribute("userLogin",user02);
            response.sendRedirect("/index.jsp");
            return null;
        } catch (Exception e) {
            String msg=e.getMessage();
            System.out.println(msg);
            request.setAttribute("msg",msg);
            return "/jsp/login.jsp";
        }
    }

    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, InvocationTargetException, IllegalAccessException, SQLException {
        request.getSession().invalidate();
        response.sendRedirect("/index.jsp");
        return null;
    }

}
