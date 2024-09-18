package org.qingcha.security.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.common.result.AjaxResult;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.service.SysMenuService;
import org.qingcha.security.service.SysUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 * @author QingCha
 */
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
@Slf4j
public class SysUserController {
    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;

    /**
     * 获取用户列表
     *
     * @return AjaxResult
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:user:list')")
    public AjaxResult list() {
        log.info("list");
        return AjaxResult.success(sysUserService.list());
    }

    /**
     * 添加用户
     * @param sysUser 用户数据
     * @return AjaxResult
     */
    @PostMapping("/insert")
    @PreAuthorize("hasAuthority('system:user:add')")
    public AjaxResult insertUser(@RequestBody SysUser sysUser) {
        boolean save = sysUserService.save(sysUser);
        if (save) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

    /**
     * 查询用户菜单
     *
     * @param id 用户id
     * @return AjaxResult
     */
    @GetMapping("/userMenu/{id}")
    public AjaxResult findUserMenuByUserId(@PathVariable Long id) {
        return AjaxResult.success(sysMenuService.findMenuByUserId(id));
    }

}
