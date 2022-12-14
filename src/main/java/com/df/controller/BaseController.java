package com.df.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.df.service.*;
import com.df.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @Autowired
    HttpServletRequest req;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    SysUserRoleService sysUserRoleService;
    @Autowired
    SysRoleMenuService sysRoleMenuService;
    @Autowired
    SysLessonsService sysLessonsService;

//    获取页面分页方法
    public Page getPage() {
        int current = ServletRequestUtils.getIntParameter(req,"current",1);
        int size = ServletRequestUtils.getIntParameter(req,"size",10);
        return new Page(current,size);
    }

}
