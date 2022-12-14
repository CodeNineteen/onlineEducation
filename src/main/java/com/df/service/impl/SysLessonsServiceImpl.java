package com.df.service.impl;

import com.df.entity.SysLesson;
import com.df.mapper.SysLessonsMapper;
import com.df.service.SysLessonsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author df
 * @since 2022-03-17
 */
@Service
public class SysLessonsServiceImpl extends ServiceImpl<SysLessonsMapper, SysLesson> implements SysLessonsService {

	@Autowired
	private SysLessonsMapper sysLessonsMapper;


	@Override
	public Integer addSub(Long rowId, Long userId) {
		return sysLessonsMapper.addDesc(rowId, userId);
	}

	@Override
	public Integer delSub(Long rowId, Long userId) {

		return sysLessonsMapper.delDesc(rowId, userId);
	}
}
