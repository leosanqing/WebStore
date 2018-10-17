package cn.stormleo.service;

import cn.stormleo.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCats() throws SQLException;
}
