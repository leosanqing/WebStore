package cn.stormleo.dao;

import cn.stormleo.domain.User;

import java.sql.SQLException;

public interface UserDao {

    void userRegist(User user) throws SQLException;

    User active(String code) throws SQLException;

    void update(User user) throws SQLException;

    User userLogin(User user) throws SQLException;
}
