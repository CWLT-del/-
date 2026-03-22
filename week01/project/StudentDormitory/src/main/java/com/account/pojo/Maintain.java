package com.account.pojo;
public class Maintain {
    private int id;
    private String content;
    private String dormitory;
    private String status;
    private String updateUser;
    @Override
    public String toString() {
        return "Maintain{" +
                "序号=" + id +
                ", 内容='" + content + '\'' +
                ", 状态='" + status + '\'' +
                ", 提交人='" + updateUser + '\'' +
                ", 宿舍='" + dormitory + '\'' +
                '}';
    }

    public Maintain(int id, String content, String dormitory, String status, String updateUser) {
        this.id = id;
        this.content = content;
        this.dormitory = dormitory;
        this.status = status;
        this.updateUser = updateUser;
    }
    public Maintain() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDormitory() {
        return dormitory;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }
}
