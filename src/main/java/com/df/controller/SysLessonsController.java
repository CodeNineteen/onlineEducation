package com.df.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.df.common.lang.Result;
import com.df.entity.SysLesson;
import com.df.service.SysLessonsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author df
 * @since 2022-03-17
 */
@RestController
@RequestMapping("/sys/student")
public class SysLessonsController extends BaseController {
    @Autowired
    private SysLessonsService sysLessonsService;

    @PreAuthorize("hasAuthority('sys:student:list')")
    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long id) {
        SysLesson sysLesson = sysLessonsService.getById(id);
        return Result.succ(sysLesson);
    }

//    搜索框
    @PreAuthorize("hasAuthority('sys:student:list')")
    @GetMapping("/list")
    public Result list(String name) {
        Page<SysLesson> pageData = sysLessonsService.page(getPage(),new QueryWrapper<SysLesson>()
                .like(StrUtil.isNotBlank(name),"name",name));
        return Result.succ(pageData);
    }

    // 添加订阅
    @PostMapping("/addSub")
    @PreAuthorize("hasAuthority('sys:student:addSub')")
    public Result save(@Validated @RequestBody Integer row) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long userId = sysUserService.selectUserId(username);
        Integer integer = sysLessonsService.addSub(row.longValue(), userId);
        return Result.succ(integer);
    }

    //取消订阅
    @PostMapping("/delSub")
    @PreAuthorize("hasAuthority('sys:student:delSub')")
    public Result update( @RequestBody Integer row) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long userId = sysUserService.selectUserId(username);
        Integer integer = sysLessonsService.addSub(row.longValue(), userId);
        return Result.succ(integer);
    }

    // @Transactional
    // @PostMapping("/delete")
    // @PreAuthorize("hasAuthority('sys:lessons:delete')")
    // public Result delete(@RequestBody Long[] ids) {
    //     sysRoleService.removeByIds(Arrays.asList(ids));
    //     return Result.succ("");
    // }

}
