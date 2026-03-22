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

public class work {
    public static void viewRepairOrderDetail(String account) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修单ID：");
        int id = sc.nextInt();
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        Maintain maintain = sqlSession.selectOne("selectById", id);
        System.out.println(maintain);
        //释放资源
        sqlSession.close();
    }
    public static void viewAllRepairOrder() throws IOException {
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        MaintainMapper maintainMapper = sqlSession.getMapper(MaintainMapper.class);
        //查询所有报修单
        List<Maintain> allMaintains = maintainMapper.selectAllMaintainstatus();
        for (Maintain maintain : allMaintains) {
            System.out.println("报修单ID：" + maintain.getId() + "  宿舍号：" + maintain.getDormitory() + "  报修内容：" + maintain.getContent());
        }
        //释放资源
        sqlSession.close();
    }
    public static void updateRepairOrderStatus(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修单ID：");
        int id = sc.nextInt();
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
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
    public static void createRepairOrder(User role) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入报修内容：");
        String repairContent = sc.nextLine();
        Random random = new Random();
        int repairOrderId = random.nextInt(10);     //生成随机报修单ID
        //创建报修单
        Maintain repairOrder = new Maintain(repairOrderId, repairContent, role.getDormitory(), "待处理", role.getAccount());
        //------------------
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactorys = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactorys.openSession();
        //------------------
        sqlSession.insert("insertMaintain", repairOrder);
        //提交事务
        sqlSession.commit();
        //释放资源
        sqlSession.close();
        System.out.println("报修单创建成功！");
    }
}
