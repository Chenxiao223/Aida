package com.example.aida.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class ImageList {
    @Id(autoincrement = true)
    private Long id;
    private String imagePath;
    private int flag;
    @Generated(hash = 1966041360)
    public ImageList(Long id, String imagePath, int flag) {
        this.id = id;
        this.imagePath = imagePath;
        this.flag = flag;
    }
    @Generated(hash = 201951210)
    public ImageList() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public int getFlag() {
        return this.flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
    }
}
