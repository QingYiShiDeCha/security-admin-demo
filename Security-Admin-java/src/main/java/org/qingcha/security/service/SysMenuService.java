package org.qingcha.security.service;

import org.qingcha.security.entity.SysMenu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author QingCha
* @description 针对表【sys_menu】的数据库操作Service
* @createDate 2024-09-17 15:32:25
*/
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查找用户菜单
     *
     * @param userId 用户id
     * @return List
     */
    List<SysMenu> findMenuByUserId(Long userId);

    /**
     * 查找菜单列表
     *
     * @return List
     */
    List<SysMenu> queryMenuList();
}
