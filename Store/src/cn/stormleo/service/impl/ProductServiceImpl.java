package cn.stormleo.service.impl;

import cn.stormleo.dao.ProductDao;
import cn.stormleo.dao.impl.ProductDaoImpl;
import cn.stormleo.domain.PageModel02;
import cn.stormleo.domain.Product;
import cn.stormleo.service.ProductService;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    ProductDao productDao = new ProductDaoImpl();
    @Override
    public List<Product> findHotProducts() throws SQLException {
        return productDao.findHotProducts();
    }

    @Override
    public List<Product> findNewProducts() throws SQLException {
        return productDao.findNewProducts();
    }

    @Override
    public Product findProductById(String pid) throws SQLException {
        return productDao.findProductById(pid);
    }

    @Override
    public PageModel02 findProductsByCidWithPage(String cid, int curNum) throws SQLException {

        System.out.println("进入findProductServlet");

        // 创建 PageModel
        int totalRecords=productDao.findTotalRecords(cid);
        PageModel02 pageModel02=new PageModel02(curNum,totalRecords,12);

        // 关联集合 select * from product where cid=? limit ?,?
        List list=productDao.findProductsByCidWithPage(cid,pageModel02.getStartIndex(),pageModel02.getPageSize());
        pageModel02.setRecords(list);

        pageModel02.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
        return pageModel02;
    }

}
