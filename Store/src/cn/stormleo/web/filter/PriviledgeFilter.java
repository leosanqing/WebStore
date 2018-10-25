package cn.stormleo.web.filter;

import cn.stormleo.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "PriviledgeFilter" ,urlPatterns = {"/jsp/cart.jsp","/jsp/order_info.jsp","/jsp/order_list.jsp"})
public class PriviledgeFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)req;
        User user=(User) httpServletRequest.getSession().getAttribute("userLogin");
        if(null!=user){
            chain.doFilter(req,resp) ;
        }else{
            httpServletRequest.getSession().setAttribute("msg","登陆后再进入");
            httpServletRequest.getRequestDispatcher("/jsp/info.jsp").forward(req,resp);
        }
        //chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
