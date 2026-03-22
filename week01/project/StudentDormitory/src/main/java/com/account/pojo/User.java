package com.account.pojo;
public class User {
    private int id;
    private String account;
    private String password;
    private int competence;
    private String dormitory;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDormitory() {
        return dormitory;
    }
    public String getAccount() {
        return account;
    }

    public void setDormitory(String dormitory) {
        this.dormitory = dormitory;
    }
    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCompetence() {
        return competence;
    }

    public void setCompetence(int competence) {
        this.competence = competence;
    }

    public User(int id, String account, String password, int competence, String dormitory) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.competence = competence;
        this.dormitory = dormitory;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", competence=" + competence +
                ", dormitory='" + dormitory + '\'' +
                '}';
    }
}
