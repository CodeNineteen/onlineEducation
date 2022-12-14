package com.df.common.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/*        name: 'UserInfo',
          title: '用户信息',
          icon: 'iconfont  icon-liebiao',
          component: 'sys/User',
          path: '/sys/user',
          children: []
*/
@Data
// @Data自动生成get\set方法
public class SysMenuDto implements Serializable {
    private Long id;
    private String name;
    private String title;
    private String icon;
    private String path;
    private String component;
    private List<SysMenuDto> children = new ArrayList<>();

}
