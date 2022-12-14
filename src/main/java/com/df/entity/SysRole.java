package com.df.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author df
 * @since 2022-02-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends BaseEntity {

    private static final long serialVersionUID = 1L;
    @NotBlank(message = "角色名称不能为空")
    private String name;
    private String code;

    /**
     * 备注
     */
    private String remark;
//@TableField(exist = false)声明不存在，不需与数据库一一对应
    @TableField(exist = false)
    private List<Long> menuIds = new ArrayList<>();


}
