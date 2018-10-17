package cn.stormleo.dao;

import cn.stormleo.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    List<Product> findHotProducts() throws SQLException;

    List<Product> findNewProducts() throws SQLException;

    Product findProductById(String pid) throws SQLException;

    int findTotalRecords(String cid) throws SQLException;

    List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws SQLException;
}
