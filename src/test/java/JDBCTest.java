import com.example.project.entity.Account;

import java.sql.*;

public class JDBCTest {
    public static void main(String[] args) {
        Statement statement = null;
        Connection conn = null;
        try {
            // 1、注册
            Driver driver = new com.mysql.cj.jdbc.Driver();
            DriverManager.registerDriver(driver);

            // 2、获取链接
            String url = "jdbc:mysql://127.0.0.1:3306/project";
            String root = "root";
            String password = "123456";
            conn = DriverManager.getConnection(url, root, password);

            // 3、获取数据库操作对象
            statement = conn.createStatement();

            // 4、执行sql

            // 查询
            String sql = "select * from account where username = 'testuser'";
//            boolean b = statement.execute(sql);
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                // 下面的写法 在工作中 经常这么写
                int id=resultSet.getInt("account_id");
                String username=resultSet.getString("username");
                String realName=resultSet.getString("real_name");
                Timestamp createTime = resultSet.getTimestamp("create_time");
                System.out.println("编号："+id+", 用户名："+username+", 真实姓名："+realName+", 创建时间："+createTime);
                System.out.println("-----------------------");
            }

            // 保存
//            String sql = "INSERT INTO `project`.`account`(`account_id`, `role_id`, `username`, `password`, `salt`, `real_name`, `sex`, `email`, `create_time`, `modified_time`, `create_account_id`, `modified_account_id`, `deleted`) " +
//                    "VALUES (4, 1, 'test', '17a1640916cfa8356adc4336a72ac75d', 'ecbe5fac60d1499595fbb98dfa854501', 'test', '男', 'mp@126.com', '2020-11-10 13:46:32', '2020-11-15 17:09:28', NULL, 1, 0);\n";
//            int count = statement.executeUpdate(sql);
//            System.out.println(count == 1? "保存成功！" : "保存失败！");

            // 更新
//            String sql = "update account set username = 'testuser' where username = 'test'";
//            int count = statement.executeUpdate(sql);
//            System.out.println(count == 1? "更新成功！" : "更新失败！");

            // 删除
//            String sql = "delete from account where username = 'test'";
//            int count = statement.executeUpdate(sql);
//            System.out.println(count == 1? "删除成功！" : "删除失败！");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
