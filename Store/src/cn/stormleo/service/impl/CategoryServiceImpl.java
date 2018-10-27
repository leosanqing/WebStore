package cn.stormleo.service.impl;

import cn.stormleo.dao.CategoryDao;
import cn.stormleo.dao.impl.CategoryDaoImpl;
import cn.stormleo.domain.Category;
import cn.stormleo.service.CategoryService;
import cn.stormleo.utils.BeanFactory;
import cn.stormleo.utils.JedisUtil01;
import redis.clients.jedis.Jedis;

import java.sql.SQLException;
import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao= (CategoryDao) BeanFactory.createObject("CategoryDao");
    @Override
    public List<Category> getAllCats() throws SQLException {
        return  categoryDao.getAllCats();

    }

    @Override
    public void addCategory(Category category) throws SQLException {
        categoryDao.addCategory(category);
        Jedis jedis=JedisUtil01.getJedis();
        jedis.del("allCats");
        JedisUtil01.closeJedis(jedis);
    }
}
