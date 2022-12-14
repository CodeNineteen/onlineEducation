package com.df.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
@Data
public class SysRoleMenu  {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    private Long roleId;

    private Long menuId;


}
