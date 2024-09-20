package org.qingcha.security.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.entity.SysMenu;
import org.qingcha.security.entity.SysRole;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.entity.vo.UserInfoVo;
import org.qingcha.security.service.SysMenuService;
import org.qingcha.security.service.SysRoleService;
import org.qingcha.security.service.SysUserService;
import org.qingcha.security.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author QingCha
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2024-09-15 16:35:41
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService {

    private final SysRoleService sysRoleService;
    private final SysMenuService sysMenuService;

    /**
     * 通过用户名查询角色
     *
     * @param username 用户名
     * @return SysUser
     */
    @Override
    public SysUser queryByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        return baseMapper.selectOne(queryWrapper);
    }

    /**
     * 根据用户id获取权限编码
     *
     * @param userId 用户id
     * @return String
     */
//    @Override
//    public String queryAuthorityInfo(Long userId) {
//        StringBuilder builder = new StringBuilder();
//        List<SysRole> roleList = sysRoleService.list(
//                new LambdaQueryWrapper<SysRole>()
//                        .inSql(SysRole::getId, "select role_id from sys_user_role where user_id =" + userId));
//        if (roleList != null && !roleList.isEmpty()) {
//            String RoleCodes = roleList.stream().map(r -> "ROLE_" + r.getCode()).collect(Collectors.joining(","));
//            builder.append(RoleCodes);
//        }
//
//        Set<String> menuCodes = new HashSet<>();
//        if (roleList != null) {
//            for (SysRole role : roleList) {
//                List<SysMenu> sysMenuList = sysMenuService.list(
//                        new LambdaQueryWrapper<SysMenu>()
//                                .inSql(SysMenu::getId, "select menu_id from sys_role_menu where role_id =" + role.getId())
//                );
//
//                sysMenuList.stream()
//                        .filter(obj -> ObjectUtil.isNotEmpty(obj.getPerms()))
//                        .forEach(item -> menuCodes.add(item.getPerms()));
//            }
//        }
//
//        if (!menuCodes.isEmpty()) {
//            builder.append(",");
//            builder.append(menuCodes.stream().collect(Collectors.joining(",")));
//        }
//
//        log.info("authority info:{}", builder.toString());
//        return builder.toString();
//    }

    @Override
    public String queryAuthorityInfo(Long userId) {
        // 获取用户的所有角色
        List<SysRole> roleList = sysRoleService.list(
                new LambdaQueryWrapper<SysRole>()
                        .inSql(SysRole::getId, "select role_id from sys_user_role where user_id = " + userId)
        );

        // 如果角色为空，直接返回空字符串
        if (roleList == null || roleList.isEmpty()) {
            return "";
        }

        // 使用流处理角色代码，并加上 "ROLE_" 前缀
        String roleCodes = roleList.stream()
                .map(r -> "ROLE_" + r.getCode())
                .collect(Collectors.joining(","));

        // 获取所有角色对应的菜单权限
        Set<String> menuCodes = roleList.stream()
                .flatMap(role -> sysMenuService.list(
                        new LambdaQueryWrapper<SysMenu>()
                                .inSql(SysMenu::getId, "select menu_id from sys_role_menu where role_id = " + role.getId())
                ).stream())
                .filter(menu -> ObjectUtil.isNotEmpty(menu.getPerms()))
                .map(SysMenu::getPerms)
                .collect(Collectors.toSet());

        // 将权限代码与角色代码拼接
        String authorityInfo = roleCodes;
        if (!menuCodes.isEmpty()) {
            authorityInfo += "," + String.join(",", menuCodes);
        }

        log.info("authority info:{}", authorityInfo);
        return authorityInfo;
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return UserInfoVo
     */
    @Override
    public UserInfoVo queryUserInfoByUserId(Long userId) {
        SysUser user = baseMapper.selectById(userId);

        List<SysRole> sysRoles = queryRolesByUserId(userId);
        return getUserInfoVo(user, sysRoles);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param username 用户名字
     * @return UserInfoVo
     */
    @Override
    public UserInfoVo queryUserInfoByUsername(String username) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ObjectUtil.isNotNull(username), SysUser::getUsername, username);
        SysUser user = baseMapper.selectOne(queryWrapper);
        List<SysRole> sysRoles = queryRolesByUserId(user.getId());
        return getUserInfoVo(user, sysRoles);
    }

    private UserInfoVo getUserInfoVo(SysUser user, List<SysRole> sysRoles) {
        List<String> userRoles = sysRoles.stream().map(SysRole::getCode).collect(Collectors.toList());
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(user.getId());
        userInfoVo.setUsername(user.getUsername());
        userInfoVo.setAvatar(user.getAvatar());
        userInfoVo.setEmail(user.getEmail());
        userInfoVo.setRemark(user.getRemark());
        userInfoVo.setRoles(userRoles);
        return userInfoVo;
    }

    /**
     * 根据用户id获取用户角色
     *
     * @param userId 用户id
     * @return List
     */
    private List<SysRole> queryRolesByUserId(Long userId) {
        return sysRoleService.list(
                new LambdaQueryWrapper<SysRole>()
                        .inSql(SysRole::getId, "select role_id from sys_user_role where user_id = " + userId)
        );
    }

}




