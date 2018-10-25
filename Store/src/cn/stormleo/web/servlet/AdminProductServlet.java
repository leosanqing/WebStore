package cn.stormleo.web.servlet;

import cn.stormleo.domain.Category;
import cn.stormleo.domain.Product;
import cn.stormleo.service.CategoryService;
import cn.stormleo.service.ProductService;
import cn.stormleo.service.impl.CategoryServiceImpl;
import cn.stormleo.service.impl.ProductServiceImpl;
import cn.stormleo.utils.UUIDUtils;
import cn.stormleo.utils.UploadUtils;
import cn.stormleo.web.base.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminProductServlet" ,urlPatterns = "/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
   // 跳转至addProductUI界面
    public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        CategoryService categoryService=new CategoryServiceImpl();
        List<Category> list=categoryService.getAllCats();
        request.getSession().setAttribute("allCategory",list);
        return "admin/product/add.jsp";
    }

    public String addProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        ProductService productService =new ProductServiceImpl();

        // 实现图片上传
        Map<String,String> map=new HashMap<>();
        Product product=new Product();

        try {
            DiskFileItemFactory factory=new DiskFileItemFactory();
            ServletFileUpload servletFileUpload =new ServletFileUpload(factory);
            List<FileItem> list = servletFileUpload.parseRequest(request);

            for (FileItem item:list){
                if(item.isFormField()){
                    // 这个就是普通项
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }else{
                    // 获取原始文件名
                    String oldFileName=item.getName();
                    // 获取到要保存文件的名称
                    String newFileName=UploadUtils.getUUIDName(oldFileName);
                    // 将文件转化为流
                    InputStream is=item.getInputStream();
                    // 获得真实路径
                    String  realPath=getServletContext().getRealPath("/products/3/");
                    // 存放的路径的
                    String dir=UploadUtils.getDir(newFileName);
                    // 存放的完整路径
                    String path= realPath+dir;

                    // 硬盘上创建目录
                    File newDir =new File(path);
                    if(!newDir.exists()){
                        newDir.mkdirs();

                    }

                    // 创建文件
                    File finalFile =new File(newDir,newFileName);
                    if(!finalFile.exists()){
                        finalFile.createNewFile();
                    }
                    OutputStream os=new FileOutputStream(finalFile);
                    IOUtils.copy(is,os);
                    IOUtils.closeQuietly(is);
                    IOUtils.closeQuietly(os);
                    map.put("pimage","products/3"+dir+"/"+newFileName);
                }
            }


            BeanUtils.populate(product,map);
            product.setPid(UUIDUtils.getId());
            product.setPdate(new Date());
            product.setPflag(0);

            ProductService productService1 =new ProductServiceImpl();
            productService.saveProduct(product);

            response.sendRedirect("/AdminCategoryServlet?method=getAllProductWithPage&num=1");
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

}
