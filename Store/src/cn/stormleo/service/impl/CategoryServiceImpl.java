package cn.stormleo.service.impl;

import cn.stormleo.dao.CategoryDao;
import cn.stormleo.dao.impl.CategoryDaoImpl;
import cn.stormleo.domain.Category;
import cn.stormleo.service.CategoryService;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    @Override
    public List<Category> getAllCats() throws SQLException {
        CategoryDao categoryDao=new CategoryDaoImpl();
        return  categoryDao.getAllCats();

    }
}
