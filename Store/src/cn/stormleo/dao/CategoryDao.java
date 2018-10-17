package cn.stormleo.dao;

import cn.stormleo.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> getAllCats() throws SQLException;
}
