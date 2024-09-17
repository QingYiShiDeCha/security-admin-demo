package org.qingcha.security.common.security;

import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.common.exception.UserCountLockException;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.service.SysUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 自定义UserDetails
 * @author QingCha
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final SysUserService service;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = service.queryByUsername(username);

        log.info("登录用户： {}", username);
        if (ObjectUtil.isEmpty(user)) {
            throw new UsernameNotFoundException("用户名或者密码错误");
        } else if (user.getStatus().equals("1")) {
            throw new UserCountLockException("该用户账号被封禁，具体请联系管理员");
        }
        return new User(user.getUsername(), user.getPassword(), getUserAuthority(user.getId()));
    }

    public List<? extends GrantedAuthority> getUserAuthority(Long userId) {

        String authoritys = service.queryAuthorityInfo(userId);

        return AuthorityUtils.commaSeparatedStringToAuthorityList(authoritys);
    }
}
