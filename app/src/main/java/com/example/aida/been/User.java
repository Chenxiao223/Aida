package com.example.aida.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String pwd;
    private String imagePath;
    @Generated(hash = 859397157)
    public User(Long id, String name, String pwd, String imagePath) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
        this.imagePath = imagePath;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPwd() {
        return this.pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

}
