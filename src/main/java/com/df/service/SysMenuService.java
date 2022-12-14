package com.df.service;

import com.df.common.dto.SysMenuDto;
import com.df.entity.SysMenu;
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
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenuDto> getCurrentUserNav();

    List<SysMenu> tree();
}
