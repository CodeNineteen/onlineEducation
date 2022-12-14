package com.df.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.df.entity.SysRole;
import com.df.mapper.SysRoleMapper;
import com.df.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Override
    public List<SysRole> listRolesByUserId(Long userId) {
        List<SysRole> sysRoles = this.list(new QueryWrapper<SysRole>()
                .inSql("id","select role_id from sys_user_role where user_id=" + userId));
        return sysRoles;
    }
}
