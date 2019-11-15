package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;

public interface RoleService {

    PageResult findPage(QueryPageBean queryPageBean);

}
