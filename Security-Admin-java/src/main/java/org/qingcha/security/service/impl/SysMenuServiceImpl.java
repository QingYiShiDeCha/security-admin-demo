package org.qingcha.security.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.qingcha.security.entity.SysMenu;
import org.qingcha.security.entity.SysRole;
import org.qingcha.security.mapper.SysRoleMapper;
import org.qingcha.security.service.SysMenuService;
import org.qingcha.security.mapper.SysMenuMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
* @author QingCha
* @description 针对表【sys_menu】的数据库操作Service实现
* @createDate 2024-09-17 15:32:25
*/
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu>
    implements SysMenuService{

    private final SysRoleMapper sysRoleMapper;


    /**
     * 查找用户菜单
     *
     * @param userId 用户id
     * @return List
     */
    @Override
    public List<SysMenu> findMenuByUserId(Long userId) {

        String queryRoleSql = "select role_id from sys_user_role where user_id = " + userId;

        List<SysRole> userRoles = sysRoleMapper.selectList(
                new QueryWrapper<SysRole>().inSql(ObjectUtil.isNotNull(userId), "id", queryRoleSql)
        );

        Set<SysMenu> menuSet = new HashSet<>();

        if (ObjectUtil.isNotEmpty(userRoles)) {
            for (SysRole userRole : userRoles) {
                String queryMenuSql = "select menu_id from sys_role_menu where role_id = " + userRole.getId();
                List<SysMenu> menuList = baseMapper.selectList(new QueryWrapper<SysMenu>().inSql("id", queryMenuSql));
                menuSet.addAll(menuList);
            }
        }
        ArrayList<SysMenu> sysMenus = new ArrayList<>(menuSet);
        sysMenus.sort(Comparator.comparing(SysMenu::getOrderNum));

        return buildTree(sysMenus);
    }

    /**
     * 查找菜单列表
     *
     * @return List
     */
    @Override
    public List<SysMenu> queryMenuList() {
        return buildTree(baseMapper.selectList(null));
    }


    private List<SysMenu> buildTree(List<SysMenu> menus) {
        List<SysMenu> tree = new ArrayList<>();
        menus.forEach(menu -> {
            if (menu.getParentId() == 0) {
                // 直接查找当前菜单的子菜单
                menu.setChildren(findChildren(menu.getId(), menus));
                tree.add(menu);
            }
        });
        return tree;
    }

    private List<SysMenu> findChildren(Long parentId, List<SysMenu> menus) {
        // 直接使用子菜单列表
        List<SysMenu> children = new ArrayList<>();
        for (SysMenu menu : menus) {
            if (menu.getParentId().equals(parentId)) {
                // 递归查找子菜单前，判断是否有子节点
                List<SysMenu> childMenus = findChildren(menu.getId(), menus);
                if (!childMenus.isEmpty()) {
                    menu.setChildren(childMenus);
                }
                children.add(menu);
            }
        }
        return children;
    }

}




