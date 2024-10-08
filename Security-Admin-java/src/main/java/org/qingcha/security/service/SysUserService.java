package org.qingcha.security.service;

import org.qingcha.security.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.qingcha.security.entity.vo.UserInfoVo;

/**
* @author QingCha
* @description 针对表【sys_user】的数据库操作Service
* @createDate 2024-09-15 16:35:42
*/
public interface SysUserService extends IService<SysUser> {
    /**
     * 通过用户名查询角色
     *
     * @param username 用户名
     * @return SysUser
     */
    SysUser queryByUsername(String username);

    /**
     * 根据用户id获取权限编码
     *
     * @param userId 用户id
     * @return String
     */
    String queryAuthorityInfo(Long userId);

    /**
     * 根据用户id获取用户信息
     *
     * @param userId 用户id
     * @return UserInfoVo
     */
    UserInfoVo queryUserInfoByUserId(Long userId);

    /**
     * 根据用户id获取用户信息
     *
     * @param username 用户名字
     * @return UserInfoVo
     */
    UserInfoVo queryUserInfoByUsername(String username);
}
