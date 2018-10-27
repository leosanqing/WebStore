package cn.stormleo.utils;

import cn.stormleo.dao.UserDao;
import cn.stormleo.domain.User;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

public class BeanFactory {
    public static Object createObject(String name){
        try {
            //创建对象
            SAXReader saxReader=new SAXReader();
            // 获取到 application.xml 输出流  必须位于Src目录下
            InputStream is=BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
            Document document=saxReader.read(is);
            // 获取到根节点
            Element element=document.getRootElement();
            // 遍历得到所有根节点
            List<Element> list=element.elements();
            for(Element ele:list){
                // 获取到ele的属性值
                String id=ele.attributeValue("id");
                if(id.equals(name)){
                    String str=ele.attributeValue("class");
                    // 通过反射创建对象并返回
                    Class clazz=Class.forName(str);
                    return  clazz.newInstance();


                }
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return  null;
    }
    public static void main(String [] args) throws SQLException {
        UserDao userDao= (UserDao) BeanFactory.createObject("UserDao");
        User user=new User();
        user.setUsername("aaa");
        user.setPassword("aaa");
        User user1=userDao.userLogin(user);
        System.out.println(user1);
    }
}
