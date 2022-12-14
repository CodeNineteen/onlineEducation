package com.df.security;

import com.df.entity.SysUser;
import com.df.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    SysUserService sysUserService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("正在查询用户信息");
        // 查询数据库用户名是否存在，如果不存在则抛出异常信息
        SysUser sysUser = sysUserService.getByUsername(username);
        System.out.println(sysUser);
        if(sysUser == null) {
            throw new UsernameNotFoundException("用户名不存在");
        }
//        把查询出来的密码(注册时已经加密过的)进行解析，或者直接把密码放入构造方法
        return new AccountUser(sysUser.getId(),sysUser.getUsername(),
                sysUser.getPassword(),getUserAuthority(sysUser.getId()));
    }
//    获取用户权限信息（角色，菜单权限）
    public List<GrantedAuthority> getUserAuthority(Long userId) {
//        角色(ROLE_admin)、菜单操作权限(sys:user:list)
       String authority = sysUserService.getUserAuthorityInfo(userId);
       return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
    }
}
