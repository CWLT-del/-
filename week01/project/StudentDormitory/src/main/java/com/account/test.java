package com.account;

import com.account.pojo.User;


import java.io.IOException;
import java.util.Scanner;

import static com.account.student.*;
import static com.account.work.*;

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

}


