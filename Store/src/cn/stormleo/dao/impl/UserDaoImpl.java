package cn.stormleo.dao.impl;

import cn.stormleo.dao.UserDao;
import cn.stormleo.domain.User;
import cn.stormleo.utils.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDaoImpl implements UserDao {
    @Override
    public void userRegist(User user) throws SQLException {
        String sql= "insert into user values(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner queryRunner =new QueryRunner(JDBCUtils.getDataSource());
        Object [] param ={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
                user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
        queryRunner.update(sql,param);
    }

    @Override
    public User active(String code) throws SQLException {
        String sql="select * from user where code=? ";
        QueryRunner queryRunner = new QueryRunner(JDBCUtils.getDataSource());
        User user=queryRunner.query(sql,new BeanHandler<User>(User.class),code);
        return user;
    }

    @Override
    public void update(User user) throws SQLException {
        String sql="update user set username= ?,password=?,name=?,email=?,telephone=? ,birthday=?," +
                "sex=?,state=?,code=? where uid=?";
        QueryRunner queryRunner= new QueryRunner(JDBCUtils.getDataSource());
        Object [] params={user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
                user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
        queryRunner.update(sql,params);
    }

    @Override
    public User userLogin(User user) throws SQLException {
        String sql= "select * from user where username=? and password =?";
        QueryRunner queryRunner= new QueryRunner(JDBCUtils.getDataSource());
        return queryRunner.query(sql,new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
    }
}
