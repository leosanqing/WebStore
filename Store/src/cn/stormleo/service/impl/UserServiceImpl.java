package cn.stormleo.service.impl;

import cn.stormleo.dao.UserDao;
import cn.stormleo.dao.impl.UserDaoImpl;
import cn.stormleo.domain.User;
import cn.stormleo.service.UserService;

import java.sql.SQLException;

public class UserServiceImpl implements UserService {
    @Override
    public void userRegist(User user) throws SQLException {
        // 实现注册
        UserDao userDao= new UserDaoImpl();
        userDao.userRegist(user);
    }

    @Override
    public boolean active(String code) throws SQLException {
        UserDao userDao= new UserDaoImpl();
        User user = userDao.active(code);
        if(null!= user){
            // 如果成功则修改状态为激活，并清空激活码
            user.setState(1);
            user.setCode(null);
            userDao.update(user);
            return true;
        }else {
            return false;
        }
    }

    @Override
    public User userLogin(User user) throws SQLException{
        UserDao userDao=new UserDaoImpl();
        User uu =userDao.userLogin(user);
        if(null==uu){
            throw new RuntimeException("用户不存在");
        }else if(uu.getState()==0){
            throw  new RuntimeException("用户未激活");
        }else{
            return uu;
        }
    }
}
