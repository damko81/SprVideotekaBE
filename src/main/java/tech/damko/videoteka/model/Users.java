package tech.damko.videoteka.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private long id;
    private String name;
    private String role;
    private String login;
    private String pwd;
    private String email;
    private String mobile;

    public Users(){}

    public Users(String name,String role,String login,String pwd,String email,String mobile){
        this.name=name;
        this.role=role;
        this.login=login;
        this.pwd=pwd;
        this.email=email;
        this.mobile=mobile;
    }

    public Long getId(){
        return id;
    }
    public void  setId(long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getRole(){
        return role;
    }
    public void setRole(String role){
        this.role = role;
    }

    public String getLogin(){
        return login;
    }
    public void setLogin(String login){
        this.login = login;
    }

    public String getPwd(){
        return pwd;
    }
    public void setPwd(String pwd){
        this.pwd = pwd;
    }

    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        this.email = email;
    }

    public String getMobile(){
        return mobile;
    }
    public void setMobile(String email){
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", role=" + role + ", login=" + login + ", pwd=" + pwd + ", email=" + email + ", mobile=" + mobile + "]";
    }
}
