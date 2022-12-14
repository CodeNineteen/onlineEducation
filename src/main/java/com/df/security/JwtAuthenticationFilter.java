package com.df.security;
//jwt认证过滤器
import cn.hutool.core.util.StrUtil;
import com.df.entity.SysUser;
import com.df.service.SysUserService;
import com.df.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter  extends BasicAuthenticationFilter {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailServiceImpl userDetailsService;
    @Autowired
    SysUserService sysUserService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String jwt = request.getHeader(jwtUtils.getHeader());
        if(StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request,response);
            return;
        }
        Claims claim = jwtUtils.getClaimByToken(jwt);
        if(claim==null) {
            throw  new JwtException("token异常");
        }
        if(jwtUtils.isTokenExpired(claim)) {
            throw  new JwtException("token过期");
        }
        String username = claim.getSubject();
//        获取用户权限的信息
        SysUser sysUser = sysUserService.getByUsername(username);
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username,null,userDetailsService.getUserAuthority(sysUser.getId()));
        SecurityContextHolder.getContext().setAuthentication(token);
        chain.doFilter(request,response);
    }
}
