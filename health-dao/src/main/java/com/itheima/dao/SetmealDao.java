package com.itheima.dao;

import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SetmealDao {
    void add(Setmeal setmeal);

    void setSetmealAndCheckGroupRelation(@Param("checkgroupId") Integer checkgroupId, @Param("setmealId") Integer setmealId);

    List<Setmeal> findPage(@Param("queryString")String queryString);

    List<Setmeal> getSetmeal();

    Setmeal findById(@Param("id")Integer id);

    List<CheckGroup> findCheckGroupsBySetmealId(@Param("id")Integer id);

    List<CheckItem> findCheckItemsByCheckGroupId(@Param("id")Integer id);


    List<CheckItem> findCheckItemsByCheckGroupIdBatch(List<Integer> ids);
}
