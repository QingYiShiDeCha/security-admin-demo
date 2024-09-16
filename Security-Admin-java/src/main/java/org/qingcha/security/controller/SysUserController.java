package org.qingcha.security.controller;

import lombok.RequiredArgsConstructor;
import org.qingcha.security.common.result.AjaxResult;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.service.SysUserService;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理
 * @author QingCha
 */
@RestController
@RequestMapping("/sys/user")
@RequiredArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;

    /**
     * 添加用户
     * @param sysUser 用户数据
     * @return AjaxResult
     */
    @PostMapping("/insert")
    public AjaxResult insertUser(@RequestBody SysUser sysUser) {
        boolean save = sysUserService.save(sysUser);
        if (save) {
            return AjaxResult.success();
        }
        return AjaxResult.error();
    }

}
