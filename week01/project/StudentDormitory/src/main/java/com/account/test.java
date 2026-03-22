package com.account;

import com.account.pojo.Maintain;
import com.account.pojo.MaintainMapper;
import com.account.pojo.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class test {
    public static void main(String[] args) throws IOException {
        int num = 2;
        User role = null;
        System.out.println("===========================\n" +
                "\uD83C\uDFE0 宿舍报修管理系统\n" +
                "===========================\n" +
                "1. 登录\n" +
                "2. 注册\n" +
                "3. 退出\n" +
                "请选择操作（输入 1-3）：");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        do {
            switch (choice) {
                case 1:
                    role = login();
                    break;
                case 2:
                    register(num);
                    num++;
                    role = login();
                    break;
                case 3:
                    System.out.println("谢谢使用，再见！");
                    return;
                default:
                    System.out.println("输入错误，请重新输入！");
                    break;
            }
        } while (role.getCompetence() == 0);
        do {

            if (role.getCompetence() == 1) {
                System.out.println("===== 学生菜单 =====\n" +
                        "1. 绑定/修改宿舍\n" +
                        "2. 创建报修单\n" +
                        "3. 查看我的报修记录\n" +
                        "4. 取消报修单\n" +
                        "5. 修改密码\n" +
                        "6. 退出\n" +
                        "请选择操作（输入 1-6）：");
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        System.out.println("请输入宿舍号：");
                        String dormitory = sc.nextLine();
                        bindDormitory(dormitory, role.getAccount());    //绑定/修改宿舍    ok
                        break;
                    case 2:
                        createRepairOrder(role);     //创建报修单    ok
                        break;
                    case 3:
                        viewMyRepairRecords(role);    //查看我的报修记录   不ok
                        break;
                    case 4:
                        cancelRepairOrder(role);    //取消报修单    ok
                        break;
                    case 5:
                        changePassword(role);     //修改密码    ok
                        break;
                    case 6:
                        System.out.println("谢谢使用，再见！");
                        return;
                    default:
                        System.out.println("输入错误，请重新输入！");
                        break;
                }
            }

        if (role.getCompetence() == 2) {
            System.out.println("===== 管理员菜单 =====\n" +
                    "1. 查看所有报修单\n" +
                    "2. 查看报修单详情\n" +
                    "3. 更新报修单状态\n" +
                    "4. 删除报修单\n" +
                    "5. 修改密码\n" +
                    "6. 退出\n" +
                    "请选择操作（输入 1-6）：");
            do {
                choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        viewAllRepairOrder();    //查看所有报修单   ok
                        break;
                    case 2:
                        viewRepairOrderDetail(role.getAccount());    //查看报修单详情   ok
                        break;
                    case 3:
                        updateRepairOrderStatus(role);    //更新报修单状态   ok
                        break;
                    case 4:
                        cancelRepairOrder(role);    //删除报修单   ok
                        break;
                    case 5:
                        changePassword(role);     //修改密码    ok
                        break;
                    case 6:
                        System.out.println("谢谢使用，再见！");
                        return;
                    default:
                        System.out.println("输入错误，请重新输入！");
                        break;
                }
                       } while (choice != 6);
        }
        } while (choice != 6);
    }

    private static void viewRepairOrderDetail(String account) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修单ID：");
        int id = sc.nextInt();
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取Session对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        Maintain maintain = sqlSession.selectOne("selectById", id);
        System.out.println(maintain);
        //释放资源
        sqlSession.close();
    }

    private static void viewAllRepairOrder() throws IOException {
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取Session对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        MaintainMapper maintainMapper = sqlSession.getMapper(MaintainMapper.class);
        //查询所有报修单
        List<Maintain> allMaintains = maintainMapper.selectAllMaintainstatus();
        for (Maintain maintain : allMaintains) {
            System.out.println(maintain.getDormitory() + maintain.getContent());
        }
        //释放资源
        sqlSession.close();
    }


    private static void updateRepairOrderStatus(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修单ID：");
        int id = sc.nextInt();
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取Session对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        Maintain maintain = sqlSession.selectOne("selectById", id);
        System.out.println(maintain);
        //更新报修单状态
        System.out.println("请输入更新后的状态：(1.已处理,2.未处理)");
        int status = sc.nextInt();
        if (status == 1) {
            sqlSession.update("updateMaintain", id);
        } else if (status == 2) {
            sqlSession.update("updateMaintainUnDone", id);
        }
        //提交数据库
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("报修单状态更新成功！");
    }

    private static void viewAllRepairOrders(User role) throws IOException {
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        MaintainMapper maintainMapper = sqlSession.getMapper(MaintainMapper.class);
        //查询所有报修单
        List<Maintain> allMaintains = sqlSession.selectList("selectAllMaintain", role.getAccount());
        for (Maintain maintain : allMaintains) {
            System.out.println(maintain);
        }
    }

    private static void changePassword(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入新密码：");
        String newPassword = sc.nextLine();
        role.setPassword(newPassword);
        //更新数据库
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        sqlSession.update("updatePassword", role);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("密码修改成功！");
    }

    private static void cancelRepairOrder(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入要取消的报修单ID：");
        int repairOrderId = sc.nextInt();
        //更新数据库
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        sqlSession.delete("deleteMaintain", repairOrderId);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("报修单取消成功！");
    }

    private static void viewMyRepairRecords(User role) throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        List<Maintain> repairOrders = sqlSession.selectList("selectAllMaintain", role.getAccount());
        sqlSession.close();
        System.out.println(repairOrders);
    }
    private static void createRepairOrder(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修内容：");
        String repairContent = sc.nextLine();
        Random random = new Random();
        int repairOrderId = random.nextInt(10);     //生成随机报修单ID
        //创建报修单
        Maintain repairOrder = new Maintain(repairOrderId, repairContent, role.getDormitory(), "待处理", role.getAccount());
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        sqlSession.insert("insertMaintain", repairOrder);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("报修单创建成功！");
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
        //判断账号是否已存在
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
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
        //判断账号密码是否正确
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
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

    private static void bindDormitory(String dormitory, String account) throws IOException {
        System.out.println("请输入宿舍号：");
        Scanner sc = new Scanner(System.in);
        dormitory = sc.nextLine();
        User user = new User();
        user.setAccount(account);
        user.setDormitory(dormitory);
        //加载核心文件，获取SqlSessionFactory
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        //获取SqlSession对象，用来执行sql语句
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //执行sql语句
        sqlSession.update("updateDormitory", user);
        sqlSession.commit();
        System.out.println("绑定宿舍成功！");
        //释放资源
        sqlSession.close();
    }
}


