package cn.stormleo.service;

import cn.stormleo.domain.PageModel;
import cn.stormleo.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    List<Product> findHotProducts() throws SQLException;

    List<Product> findNewProducts() throws SQLException;

    Product findProductById(String pid) throws SQLException;

    PageModel findProductsByCidWithPage(String cid, int curNum) throws SQLException;

    PageModel getAllProductWithPage(int curNum) throws SQLException;

    void saveProduct(Product product)throws SQLException;
}
