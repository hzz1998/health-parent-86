package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.CheckGroupDao;
import com.itheima.dao.CheckItemMapper;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    CheckGroupDao checkGroupDao;
    @Autowired
    CheckItemMapper checkItemMapper;

    @Override
    public void add(CheckGroup checkGroup) {
        //检查组的基本信息要插入到t_checkgroup（插入之后要返回主键）
        checkGroupDao.add(checkGroup);
        Integer checkGroupId = checkGroup.getId();

        //检查组要和检查项建立关系
        List<Integer> checkitemIds = checkGroup.getCheckitemIds();
        setCheckItemAndCheckGroupRelation(checkitemIds,checkGroupId);
    }

    private void setCheckItemAndCheckGroupRelation(List<Integer> checkitemIds,Integer checkGroupId){
        if(CollectionUtil.isNotEmpty(checkitemIds)){
            //循环插入数据（就要频繁和数据进行网络交互）
//            for (Integer checkitemId : checkitemIds) {
//                checkGroupDao.setCheckItemAndCheckGroupRelation(checkitemId,checkGroupId);
//            }

            //优化一下批量插入
//            insert into t_checkgroup_checkitem (checkgroup_id,checkitem_id) values(5,28),(5,29),(5,30),(5,31)
            List<Map> params = new ArrayList<>();//List[{5,28},{5,29}]
            for (Integer checkitemId : checkitemIds) {
                Map map = new HashMap();
                map.put("checkgroup_id",checkGroupId);
                map.put("checkitem_id",checkitemId);
                params.add(map);
            }
            checkGroupDao.setCheckItemAndCheckGroupRelationBatch(params);
        }
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
//        3、在service第一步需要使用分页插件（告诉分页拦截器我们现在要查询第一页，每页要多少条）
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        //4、写一个普普通通的查询sql即可
        List<CheckGroup> checkGroups = checkGroupDao.findPage(queryPageBean.getQueryString());

        PageResult pageResult =
                 PageResult.builder()
                .rows(checkGroups)
                .total(page.getTotal());

//        PageResult pageResult = PageResult.builder()
//                .rows(checkGroups)
//                .total(page.getTotal())
//                .build();
        return new PageResult(page.getTotal(),checkGroups);
    }

    @Override
    public Map findById4Update(Integer id) {
        //根据检查组的id查询检查组详情
        CheckGroup checkGroup =  checkGroupDao.findById(id);
        //查询所有的检查项
        List<CheckItem> allCheckItems = checkItemMapper.findAll();
        //根据检查组的id查询检查组下面的检查项id集合
        List<Integer> checkItemIds = checkGroupDao.findCheckItemIdsByCheckGroupId(id);


        Map map = new HashMap();
        map.put("checkgroup",checkGroup);
        map.put("allCheckItems",allCheckItems);
        map.put("checkItemIds",checkItemIds);

        return map;
    }

    @Override
    public void edit(CheckGroup checkGroup) {
        //更新检查组的基本信息
        checkGroupDao.edit(checkGroup);
        //根据检查组id删除原来的关系
        checkGroupDao.deleteAssociation(checkGroup.getId());
        //建立新的关系
        setCheckItemAndCheckGroupRelation(checkGroup.getCheckitemIds(),checkGroup.getId());
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }
}
