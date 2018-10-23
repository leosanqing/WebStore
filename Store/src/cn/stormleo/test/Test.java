package cn.stormleo.test;

import cn.stormleo.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class Test {
    public static void main(String [] args) throws SQLException {
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        String sql="select * from test where id=1";
        Test test=queryRunner.query(sql,new BeanHandler<Test>(Test.class));
        System.out.println(test);
    }
}
