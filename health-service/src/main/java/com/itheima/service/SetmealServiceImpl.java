package com.itheima.service;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.dao.SetmealDao;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckGroup;
import com.itheima.pojo.CheckItem;
import com.itheima.pojo.Setmeal;
import com.itheima.utils.JedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    SetmealDao setmealDao;

    @Autowired
    JedisUtil jedisUtil;
    @Override

    public void add(Setmeal setmeal) {
        //插入套餐基本信息并返回主键（返回主键要和用户勾选的检查组的id建立关系）
        setmealDao.add(setmeal);

        Integer setmealId = setmeal.getId();
        //建立套餐和检查组的关系
        List<Integer> checkgroupIds = setmeal.getCheckgroupIds();
        if(CollectionUtil.isNotEmpty(checkgroupIds)){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setSetmealAndCheckGroupRelation(checkgroupId,setmealId);
            }
        }
        jedisUtil.del("setmealsList");
        jedisUtil.del("SetmealsDetails");
    }

    @Override
    public PageResult findPage(QueryPageBean queryPageBean) {
        Page page = PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        List<Setmeal> setmeals = setmealDao.findPage(queryPageBean.getQueryString());
        return new PageResult(page.getTotal(),setmeals);
    }

    @Override
    public List<Setmeal> getSetmeal() {
        return setmealDao.getSetmeal();
    }

    @Override
    public Setmeal findById(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealDao.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
           List<CheckGroup> checkGroups = setmealDao.findCheckGroupsBySetmealId(id);
           if(CollectionUtil.isNotEmpty(checkGroups)){
               //循环所有的检查组查询对应的检查项集合
               for (CheckGroup checkGroup : checkGroups) {
                   List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupId(checkGroup.getId());
                   checkGroup.setCheckItems(checkItems);
               }
           }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealDao.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
            List<CheckGroup> checkGroups = setmealDao.findCheckGroupsBySetmealId(id);
            if(CollectionUtil.isNotEmpty(checkGroups)){
                //把检查组id集合作为参数批量查询
                List<Integer> ids = getCheckGroupIds(checkGroups);
                List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupIdBatch(ids);

                Map<Integer, List<CheckItem>> map = new HashMap<>();

                //遍历所有的检查组
                for (CheckGroup checkGroup : checkGroups) {
                    Integer checkGroupId = checkGroup.getId();
                    //遍历所有的检查项匹配当前组的id如果满足就装装入child集合
                    List<CheckItem> child = new ArrayList<>();
                    for (CheckItem checkItem : checkItems) {
                        if(checkItem.getCheckGroupId().equals(checkGroupId)){
                            child.add(checkItem);
                        }
                    }
                    map.put(checkGroupId,child);
                }
                //分组
                for (CheckGroup checkGroup : checkGroups) {
                    checkGroup.setCheckItems(map.get(checkGroup.getId()));
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch58(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealDao.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
            List<CheckGroup> checkGroups = setmealDao.findCheckGroupsBySetmealId(id);
            if(CollectionUtil.isNotEmpty(checkGroups)){
                //把检查组id集合作为参数批量查询
                List<Integer> ids = getCheckGroupIds(checkGroups);
                List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupIdBatch(ids);

                Map<Integer, List<CheckItem>> map = new HashMap<>();

                for (CheckItem checkItem : checkItems) {
                    Integer checkGroupId = checkItem.getCheckGroupId();
                    List<CheckItem> list = map.get(checkGroupId);
                    if(null == list){
                        list = new ArrayList<>();
                        map.put(checkGroupId,list);
                    }

                    list.add(checkItem);
                }

                //分组
                for (CheckGroup checkGroup : checkGroups) {
                    checkGroup.setCheckItems(map.get(checkGroup.getId()));
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findByIdBatch58Tanzhe(Integer id) {
        //根据套餐id查询套餐详情
        Setmeal setmeal = setmealDao.findById(id);
        if(null != setmeal){
            //根据套餐id查询套餐下面所有的检查组
            List<CheckGroup> checkGroups = setmealDao.findCheckGroupsBySetmealId(id);
            if(CollectionUtil.isNotEmpty(checkGroups)){
                //把检查组id集合作为参数批量查询
                List<Integer> ids = getCheckGroupIds(checkGroups);

                List<CheckItem> checkItems = setmealDao.findCheckItemsByCheckGroupIdBatch(ids);

                Map<Integer, List<CheckItem>> map = new HashMap<>();

                map = checkItems
                        .stream()
                        .collect(Collectors.groupingBy(CheckItem::getCheckGroupId));

                //分组
                for (CheckGroup checkGroup : checkGroups) {
                    checkGroup.setCheckItems(map.get(checkGroup.getId()));
                }
            }
            setmeal.setCheckGroups(checkGroups);
        }
        return setmeal;
    }

    @Override
    public Setmeal findDetailById(Integer id) {
        return setmealDao.findById(id);
    }

    private List<Integer> getCheckGroupIds(List<CheckGroup> checkGroups) {
        List<Integer> ids = new ArrayList<>();
        for (CheckGroup checkGroup : checkGroups) {
            ids.add(checkGroup.getId());
        }
        return ids;
    }
}
