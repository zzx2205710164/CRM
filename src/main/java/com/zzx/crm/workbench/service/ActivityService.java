package com.zzx.crm.workbench.service;

import com.zzx.crm.vo.PaginationVO;
import com.zzx.crm.workbench.domain.Activity;

import java.util.Map;

public interface ActivityService {

    Boolean saveActivity(Activity activity);


    PaginationVO<Activity> pageList(Map<String, Object> map);
}
