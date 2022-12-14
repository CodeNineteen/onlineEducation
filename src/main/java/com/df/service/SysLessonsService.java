package com.df.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.df.entity.SysLesson;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author df
 * @since 2022-03-17
 */
public interface SysLessonsService extends IService<SysLesson> {

	Integer addSub(Long rowId,Long userId);
	Integer delSub(Long rowId,Long userId);

}
