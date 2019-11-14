package com.itheima.pojo;

import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

public class Test implements Serializable {
    private Integer id;
    private String name;

    private Test(){

    }

    public static Test builder(){
        return new Test();
    }

    public Test id(Integer id){
        this.id = id;
        return this;
    }

    public Test name(String name){
        this.name = name;
        return this;
    }

}
