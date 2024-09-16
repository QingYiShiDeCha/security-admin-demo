package org.qingcha.security.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.service.SysUserService;
import org.qingcha.security.mapper.SysUserMapper;
import org.springframework.stereotype.Service;

/**
* @author QingCha
* @description 针对表【sys_user】的数据库操作Service实现
* @createDate 2024-09-15 16:35:41
*/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser>
    implements SysUserService{

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
}




