package com.df.service;

import com.df.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> listRolesByUserId(Long userId);
}
