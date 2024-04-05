package tech.damko.videoteka.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private String password;

    public Users(){}

    public Users(Long id,String name,String username,String password){
        this.id = id;
        this.name=name;
        this.username=username;
        this.password=password;
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

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password = password;
    }

    public String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + ", password=" + password + ", username=" + username + "]";
    }
}