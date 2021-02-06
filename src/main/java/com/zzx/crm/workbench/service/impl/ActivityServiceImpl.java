package com.zzx.crm.workbench.service.impl;

import com.zzx.crm.utils.SqlSessionUtil;
import com.zzx.crm.vo.PaginationVO;
import com.zzx.crm.workbench.dao.ActivityDao;
import com.zzx.crm.workbench.domain.Activity;
import com.zzx.crm.workbench.service.ActivityService;

import java.util.List;
import java.util.Map;

public class ActivityServiceImpl implements ActivityService {

    private ActivityDao activityDao = SqlSessionUtil.getSqlSession().getMapper(ActivityDao.class);

    public Boolean saveActivity(Activity activity) {

        Boolean flag = true;

        int count = activityDao.saveActivity(activity);
        if (count!=1){
            flag = false;
        }

        return flag;
    }

    public PaginationVO<Activity> pageList(Map<String, Object> map) {

        //取得total
        int total = activityDao.getTotalByCondition(map);
        //取得dataList
        List<Activity> dataList = activityDao.getActivityListByCondition(map);
        //创建一个vo对象，将total和dataList封装到vo中
        PaginationVO<Activity> vo = new PaginationVO<Activity>();
        vo.setTotal(total);
        vo.setDataList(dataList);

        //将vo返回
        return vo;
    }


}
