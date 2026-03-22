package com.account;

import com.account.pojo.Maintain;
import com.account.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;


public class student {
    public static void changePassword(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入新密码：");
        String newPassword = sc.nextLine();
        role.setPassword(newPassword);
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        sqlSession.update("updatePassword", role);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("密码修改成功！");
    }
    public static void cancelRepairOrder(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要取消的报修单ID：");
        int repairOrderId = sc.nextInt();
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        sqlSession.delete("deleteMaintain", repairOrderId);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("报修单取消成功！");
    }
    public static void viewMyRepairRecords(User role) throws IOException {
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        List<Maintain> repairOrders = sqlSession.selectList("selectAllMaintain", role.getAccount());
        sqlSession.close();
        System.out.println(repairOrders);
    }
    public static void register(int num) throws IOException {
        System.out.println("===== 用户注册 =====\n" +
                "请选择角色（1-学生，2-维修人员）：");
        Scanner sc = new Scanner(System.in);
        int role = sc.nextInt();
        if (role != 1 && role != 2) {
            System.out.println("角色错误，请重新输入！");
            return;
        }
        System.out.println("请输入账号：(学生为3125开头，管理人员为3225开头)");
        sc.nextLine();
        String account = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        User user = sqlSession.selectOne("selectByAccount", account);
        if (user != null) {
            System.out.println("账号已存在！");
            return;
        }
        //注册用户
        user = new User(num, account, password, role, null);
        sqlSession.insert("insertUser", user);
        sqlSession.commit();
        System.out.println("注册成功！");
        //释放资源
        sqlSession.close();
    }
    public static User login() throws IOException {
        System.out.println("===== 用户登录 =====\n" +
                "请输入账号：");
        Scanner sc = new Scanner(System.in);
        String account = sc.nextLine();
        System.out.println("请输入密码：");
        String password = sc.nextLine();
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        User user = sqlSession.selectOne("selectByAccount", account);
        if (user == null) {
            System.out.println("账号不存在！");
            sqlSession.close();
            return user;
        }
        if (!user.getPassword().equals(password)) {
            System.out.println("密码错误！");
            sqlSession.close();
            return user;
        }
        if (user.getCompetence() == 1) {
            System.out.println("登陆成功！你的角色是学生");
            sqlSession.close();
            if (user.getDormitory() == null) {
                System.out.println("请先绑定宿舍！");
                bindDormitory(user.getAccount(), account);
            }
            return user;
        }
        if (user.getCompetence() == 2) {
            System.out.println("登陆成功！你的角色是管理人员");
            sqlSession.close();
            return user;
        }
//        System.out.println(user);
//        UserMapper usersMapper = sqlSession.getMapper(UserMapper.class);
//        List<User> users = usersMapper.selectAll();
        //释放资源
        sqlSession.close();
        return user;
    }
    public static void bindDormitory(String dormitory, String account) throws IOException {
        System.out.println("请输入宿舍号：");
        Scanner sc = new Scanner(System.in);
        dormitory = sc.nextLine();
        User user = new User();
        user.setAccount(account);
        user.setDormitory(dormitory);
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        sqlSession.update("updateDormitory", user);
        sqlSession.commit();
        System.out.println("绑定宿舍成功！");
        //释放资源
        sqlSession.close();
    }
}
