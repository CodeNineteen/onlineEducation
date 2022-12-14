package com.df.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author df
 * @since 2022-03-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysLesson extends BaseEntity {

    private Long id;

    private String className;

    private String classRequired;

    private Integer classScore;

    private Integer statu;

    private Integer userId;


}
