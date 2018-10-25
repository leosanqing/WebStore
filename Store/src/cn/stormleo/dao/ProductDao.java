package cn.stormleo.dao;

import cn.stormleo.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> findHotProducts() throws SQLException;

    List<Product> findNewProducts() throws SQLException;

    Product findProductById(String pid) throws SQLException;

    // 查询一个分类下的所有商品的个数
    int findTotalRecords(String cid) throws SQLException;

    List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException;

    // 查询所有商品个数
    int findTotalRecords()throws SQLException;

    // 安页数查询商品
    List<Product> findProductsByCidWithPage(int startIndex, int pageSize) throws SQLException;

    void saveProduct(Product product) throws SQLException;
}
