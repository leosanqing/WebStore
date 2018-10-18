package cn.stormleo.service.impl;

import cn.stormleo.dao.ProductDao;
import cn.stormleo.dao.impl.ProductDaoImpl;
import cn.stormleo.domain.PageModel;
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

    /**
     * 分页显示产品，传入 分类id cid和当前页数 curNum
     * @param cid
     * @param curNum
     * @return
     * @throws SQLException
     */
    @Override
    public PageModel findProductsByCidWithPage(String cid, int curNum) throws SQLException {


        // 创建 PageModel
        int totalRecords=productDao.findTotalRecords(cid);
        PageModel pageModel =new PageModel(curNum,totalRecords,12);

        // 关联集合 select * from product where cid=? limit ?,?
        List list=productDao.findProductsByCidWithPage(cid, pageModel.getStartIndex(), pageModel.getPageSize());
        pageModel.setRecords(list);

        pageModel.setUrl("ProductServlet?method=findProductsByCidWithPage&cid="+cid);
        return pageModel;
    }

}
