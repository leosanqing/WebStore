package cn.stormleo.service;

import cn.stormleo.domain.User;

import java.sql.SQLException;

public interface UserService {
    void userRegist(User user) throws SQLException;

    boolean active(String code) throws SQLException;

    User userLogin(User user) throws SQLException;
}
