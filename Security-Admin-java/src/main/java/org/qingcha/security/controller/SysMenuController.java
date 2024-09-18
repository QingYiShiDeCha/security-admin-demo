package org.qingcha.security.controller;

import lombok.RequiredArgsConstructor;
import org.qingcha.security.common.result.AjaxResult;
import org.qingcha.security.service.SysMenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 菜单管理
 */
@RestController
@RequestMapping("/sys/menu")
@RequiredArgsConstructor
public class SysMenuController {
    private final SysMenuService sysMenuService;

    /**
     * 查询菜单列表
     *
     * @return AjaxResult
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('system:user:list')")
    public AjaxResult list() {
        return AjaxResult.success(sysMenuService.queryMenuList());
    }

}
