package com.df.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.df.entity.SysUser;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {

    List<Long> getNavMenuIds(Long userId);

    List<SysUser> listByMenuId(Long menuId);

    Long selectUserIdByName(String userName);

}

