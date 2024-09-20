package org.qingcha.security.common.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.common.utils.JwtUtils;
import org.qingcha.security.entity.SysMenu;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.entity.vo.UserInfoVo;
import org.qingcha.security.service.SysMenuService;
import org.qingcha.security.service.SysUserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 登录成功处理器
 * @author QingCha
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();
        String username = authentication.getName();
        log.info("进入登录处理器");
        try {
            SysUser user = sysUserService.queryByUsername(username);

            String token = JwtUtils.genrateJwtToken(user.getId(), username + UUID.randomUUID());

            List<SysMenu> userMenus = sysMenuService.findMenuByUserId(user.getId());
            UserInfoVo userInfoVo = sysUserService.queryUserInfoByUserId(user.getId());
            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("menu", userMenus);
            data.put("userInfo", userInfoVo);

            Map<String, Object> map = new HashMap<>();
            map.put("data", data);
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登录成功");

            String value = objectMapper.writeValueAsString(map);

            outputStream.write(value.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (Base64DecodingException e) {
            throw new RuntimeException(e);
        }

    }


}
