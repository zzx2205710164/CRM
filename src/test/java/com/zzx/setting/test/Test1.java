package com.zzx.setting.test;

public class Test1 {

    public static void main(String[] args) {

        //验证失效时间
        //失效时间
        /*String expirTime = "2019-10-10 10:10:10";

        Date date = new Date();
        //System.out.println(date);
        *//*SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str=sdf.format(date);
        System.out.println(str);*//*

        //当前系统时间
        String currentTime = DateTimeUtil.getSysTime();
        int count = expirTime.compareTo(currentTime);
        System.out.println(count);*/

        /*String lockState = "0";;
        if("0".equals(lockState)){
            System.out.printf("账号已锁定");
        }*/

        //浏览器的ip地址
        /*String ip = "192.168.2";
        //允许访问的ip地址群
        String allowIps = "192.168.1,192.168.1.2";
        if (allowIps.contains(ip)){
            System.out.printf("有效的ip地址，允许访问系统");
        }else{
            System.out.printf("ip地址受限，请联系管理员");
        }*/

        /*String pwd = "1234";
        String pwd1 = MD5Util.getMD5(pwd);
        System.out.printf(pwd1);*/

        /*//创建时间，当前系统时间
        String createTime = DateTimeUtil.getSysTime();
        //创建人，当前登录用户
        String createBy = ((User) request.getSession().getAttribute("user")).getName();*/

    }

}
