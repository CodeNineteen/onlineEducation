package com.df.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.df.entity.SysLesson;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author df
 * @since 2022-03-17
 */
public interface SysLessonsMapper extends BaseMapper<SysLesson> {
	//添加订阅
	Integer addDesc(@Param("rowId") Long rowId, @Param("userId") Long userId);

	//取消订阅
	Integer delDesc(@Param("rowId") Long rowId,@Param("userId") Long userId);



}
