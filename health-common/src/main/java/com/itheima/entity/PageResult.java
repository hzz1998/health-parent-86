package com.itheima.entity;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
//@Builder
public class PageResult implements Serializable{
    private Long total;//总记录数
    private List rows;//当前页结果


    public static PageResult builder(){
        return new PageResult();
    }


    public PageResult total(Long total){
        this.total = total;
        return this;
    }

    public PageResult rows(List rows){
        this.rows = rows;
        return this;
    }

}
