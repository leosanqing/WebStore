package cn.stormleo.dao.impl;

import cn.stormleo.dao.CategoryDao;
import cn.stormleo.domain.Category;
import cn.stormleo.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> getAllCats() throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from category";
       return queryRunner.query(sql,new BeanListHandler<Category>(Category.class));
    }

    @Override
    public void addCategory(Category category) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="insert into category values(?,?)";
        queryRunner.update(sql,category.getCid(),category.getCname());
    }
}
