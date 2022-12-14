package com.df.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.df.entity.SysMenu;
import com.df.entity.SysRole;
import com.df.entity.SysUser;
import com.df.mapper.SysUserMapper;
import com.df.service.SysMenuService;
import com.df.service.SysRoleService;
import com.df.service.SysUserService;
import com.df.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {
    @Autowired
    SysRoleService sysRoleService;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    SysMenuService sysMenuService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    SysUserService sysUserService;


    @Override
    public SysUser getByUsername(String username) { return getOne(new QueryWrapper<SysUser>().eq("username",username)); }

    @Override
    public String getUserAuthorityInfo(Long userId) {
        SysUser sysUser = sysUserMapper.selectById(userId);
        //        角色(ROLE_admin)、菜单操作权限(sys:user:list)
        String authority = "";
//        用户权限数据添加缓存
        if(redisUtil.hasKey("GrantedAuthority:" +sysUser.getUsername())) {
         authority = (String) redisUtil.get("GrantedAuthority:" +sysUser.getUsername());
        } else {
            //  获取角色编码
            List<SysRole> roles = sysRoleService.list(new QueryWrapper<SysRole>()
                    .inSql("id", "select role_id from sys_user_role where user_id = " + userId));
            if(roles.size() >0) {

                String roleCodes = roles.stream().map(r ->"ROLE_" + r.getCode()).collect(Collectors.joining(","));
                authority = roleCodes.concat(",");
            }
            //  获取菜单操作编码
            List<Long> menuIds = sysUserMapper.getNavMenuIds(userId);
            if(menuIds.size() >0) {
                List<SysMenu> menus = sysMenuService.listByIds(menuIds);
                String menuPerms = menus.stream().map(m -> m.getPerms()).collect(Collectors.joining(","));
                authority = authority.concat(menuPerms);
            }
            redisUtil.set("GrantedAuthority:" +sysUser.getUsername(),authority,60*60 );
        }
        return authority;
    }
//   CURD所需用户的角色、权限、菜单信息
    @Override
    public void clearUserAuthorityInfo(String username) {
        redisUtil.del("GrantedAuthority:" + username);
    }

    @Override
    public void clearUserAuthorityInfoByRoleId(Long roleId) {
        List<SysUser> sysUsers = this.list(new QueryWrapper<SysUser>()
                .inSql("id", "select user_id from sys_user_role where role_id = " + roleId));
        sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public void clearUserAuthorityInfoByMenuId(Long menuId) {
         List<SysUser> sysUsers = sysUserMapper.listByMenuId(menuId);

         sysUsers.forEach(u -> {
            this.clearUserAuthorityInfo(u.getUsername());
        });
    }

    @Override
    public Long selectUserId(String username) {
        return sysUserMapper.selectUserIdByName(username);
    }

}
