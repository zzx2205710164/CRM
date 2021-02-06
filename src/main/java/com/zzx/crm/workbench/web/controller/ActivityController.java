package com.zzx.crm.workbench.web.controller;

import com.zzx.crm.settings.domain.User;
import com.zzx.crm.settings.service.UserService;
import com.zzx.crm.settings.service.impl.UserServiceImpl;
import com.zzx.crm.utils.*;
import com.zzx.crm.vo.PaginationVO;
import com.zzx.crm.workbench.domain.Activity;
import com.zzx.crm.workbench.service.ActivityService;
import com.zzx.crm.workbench.service.impl.ActivityServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityController extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        System.out.println("进入到市场活动控制器");

        String path = request.getServletPath();

        if ("/workbench/activity/getUserList.do".equals(path)) {
            getUserList(request, response);


        } else if ("/workbench/activity/saveActivity.do".equals(path)) {
            saveActivity(request, response);
        } else if ("/workbench/activity/pageList.do".equals(path)) {
            pageList(request, response);
        }

    }

    private void pageList(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("进入到查询市场活动信息列表的操作（结合条件查询+分页查询）");

        String name = request.getParameter("name");
        String owner = request.getParameter("owner");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");

        String pageNoStr = request.getParameter("pageNo");
        int pageNo = Integer.valueOf(pageNoStr);
        //每页展现记录数
        String pageSizeStr = request.getParameter("pageSize");
        int pageSize = Integer.valueOf(pageSizeStr);
        //计算出略过的记录数
        int skipCount = (pageNo-1)*pageSize;

        Map<String,Object> map = new HashMap<String, Object>();
        map.put("name", name);
        map.put("owner", owner);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("skipCount", skipCount);
        map.put("pageSize", pageSize);

        //System.out.println(map);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        /*
            前端要，市场活动信息列表
                    查询的总条数

                    业务层拿到以上两项信息之后，如何做返回
                    map
                        map.put("dataList":dataList)
                        map.put("total":total)
                        PrintJSON map --> json
                        {"total":?,"dataList":[{市场活动1},{2},{3}]}

                    vo
                    PagintationVO<T>
                        private int total;
                        private list<T> dataList;

                    PagintationVO<Activity> vo = new Pagintation();
                    vo.setTotal(total);
                    vo.setDataList(dataList);
                    PrintJSON vo --> json
                    {"total":?,"dataList":[{市场活动1},{2},{3}]}

                    将来分页查询，每个模块都有，所以我们选择使用一个通用vo，操作起来比较方便

         */
        PaginationVO<Activity> vo = activityService.pageList(map);

        //vo --> {"total":?,"dataList":[{市场活动1},{2},{3}]}
        PrintJson.printJsonObj(response,vo);

    }

    private void saveActivity(HttpServletRequest request, HttpServletResponse response) {

        System.out.println("执行市场活动添加操作");

        //提取数据create-owner,create-name,create-startDate,create-endDate,create-cost,create-description
        String id = UUIDUtil.getUUID();
        String owner = request.getParameter("owner");
        String name = request.getParameter("name");
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String cost = request.getParameter("cost");
        String description = request.getParameter("description");

        //创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人，当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();

        Activity activity =new Activity();
        activity.setId(id);
        activity.setOwner(owner);
        activity.setName(name);
        activity.setStartDate(startDate);
        activity.setEndDate(endDate);
        activity.setCost(cost);
        activity.setDescription(description);
        activity.setCreateTime(createTime);
        activity.setCreateBy(createBy);

        ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());

        Boolean flag = activityService.saveActivity(activity);

        PrintJson.printJsonFlag(response,flag);


    }

    private void getUserList(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("进入模态窗口获得用户信息列表操作");

        //业务时查询用户的业务，不属于市场活动业务
        /*ActivityService activityService = (ActivityService) ServiceFactory.getService(new ActivityServiceImpl());
        List<Activity> activityList = activityService.selectAll();*/

        UserService userService = (UserService) ServiceFactory.getService(new UserServiceImpl());
        List<User> userList = userService.getUserList();

        PrintJson.printJsonObj(response, userList);

    }


}
