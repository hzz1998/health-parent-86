package com.itheima.service;

//import cn.hutool.db.PageResult;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.Setmeal;

import java.util.List;

public interface SetmealService {
    void add(Setmeal setmeal);

    PageResult findPage(QueryPageBean queryPageBean);

    List<Setmeal> getSetmeal();

    Setmeal findById(Integer id);

    Setmeal findByIdBatch(Integer id);

    /**
     * 从406循环降低到58循环
     * @param id
     * @return
     */
    Setmeal findByIdBatch58(Integer id);

    Setmeal findByIdBatch58Tanzhe(Integer id);

    Setmeal findDetailById(Integer id);
}
