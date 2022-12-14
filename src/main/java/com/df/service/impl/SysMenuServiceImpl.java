package com.df.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.df.common.dto.SysMenuDto;
import com.df.entity.SysMenu;
import com.df.entity.SysUser;
import com.df.mapper.SysMenuMapper;
import com.df.mapper.SysUserMapper;
import com.df.service.SysMenuService;
import com.df.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Override
    public List<SysMenuDto> getCurrentUserNav() {
        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = sysUserService.getByUsername(username);

//        获取菜单信息
        List<Long> menuIds = sysUserMapper.getNavMenuIds(sysUser.getId());
        List<SysMenu> menus = this.listByIds(menuIds);
//          转树状结构
        List<SysMenu> menuTree = buildTreeMenu(menus);
//        实体转Dto
        return convert(menuTree);
    }

    private List<SysMenuDto> convert(List<SysMenu> menuTree) {
        List<SysMenuDto> menuDtos = new ArrayList<>();
        menuTree.forEach(m -> {
            SysMenuDto dto = new SysMenuDto();
            dto.setId(m.getId());
            dto.setName(m.getPerms());
            dto.setTitle(m.getName());
            dto.setComponent(m.getComponent());
            dto.setPath(m.getPath());
            dto.setIcon(m.getIcon());
            if(m.getChildren().size() > 0) {
//                子节点调用当前方法进行再次转换
                dto.setChildren(convert(m.getChildren()));
            }
            menuDtos.add(dto);
        });
        return menuDtos;
    }

    private List<SysMenu> buildTreeMenu(List<SysMenu> menus) {
        List<SysMenu> finalMenus = new ArrayList<>();

//      各自先寻找自己的孩子
        for (SysMenu menu : menus) {
            for (SysMenu e : menus) {
                if(menu.getId() == e.getParentId()) {
                    menu.getChildren().add(e);
                }
            }
            //      再提取出父节点
            if (menu.getParentId() == 0L) {
                finalMenus.add(menu);
            }
        }
        System.out.println(JSONUtil.toJsonStr(finalMenus));
        return finalMenus;
    }

    @Override
    public List<SysMenu> tree() {
//        获取所有菜单信息
        List<SysMenu> sysMenus = this.list(new QueryWrapper<SysMenu>().orderByAsc("orderNum"));
//        转成树状结构
        return buildTreeMenu(sysMenus);
    }
}
