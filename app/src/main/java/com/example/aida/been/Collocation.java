package com.example.aida.been;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Collocation {
    @Id(autoincrement = true)
    private Long id;
    private String name;
    private String clothes;
    private String bottoms;
    private String accessories;
    private String shoes;
    private int isdelete;//0不删除 1删除
    @Generated(hash = 1563080526)
    public Collocation(Long id, String name, String clothes, String bottoms,
            String accessories, String shoes, int isdelete) {
        this.id = id;
        this.name = name;
        this.clothes = clothes;
        this.bottoms = bottoms;
        this.accessories = accessories;
        this.shoes = shoes;
        this.isdelete = isdelete;
    }
    @Generated(hash = 107499761)
    public Collocation() {
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
    public String getClothes() {
        return this.clothes;
    }
    public void setClothes(String clothes) {
        this.clothes = clothes;
    }
    public String getBottoms() {
        return this.bottoms;
    }
    public void setBottoms(String bottoms) {
        this.bottoms = bottoms;
    }
    public String getAccessories() {
        return this.accessories;
    }
    public void setAccessories(String accessories) {
        this.accessories = accessories;
    }
    public String getShoes() {
        return this.shoes;
    }
    public void setShoes(String shoes) {
        this.shoes = shoes;
    }
    public int getIsdelete() {
        return this.isdelete;
    }
    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }
}
