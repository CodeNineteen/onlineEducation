package com.df.service;

import com.df.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getByUsername(String username);

    String getUserAuthorityInfo(Long userId);
//    清除缓存权限数据
    void  clearUserAuthorityInfo(String username);
    void  clearUserAuthorityInfoByRoleId(Long roleId);
    void  clearUserAuthorityInfoByMenuId(Long menuId);

    Long selectUserId(String username);
}
