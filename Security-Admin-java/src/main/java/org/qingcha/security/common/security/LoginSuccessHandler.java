package org.qingcha.security.common.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import lombok.extern.slf4j.Slf4j;
import org.qingcha.security.common.result.AjaxResult;
import org.qingcha.security.common.utils.JwtUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录成功处理器
 * @author QingCha
 */
@Component
@Slf4j
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        String username = authentication.getName();
        log.info("进入登录处理器");
        try {
            String token = JwtUtils.genrateJwtToken(username);

            Map<String, Object> map = new HashMap<>();
            map.put("token", token);
            map.put("code", HttpServletResponse.SC_OK);
            map.put("message", "登录成功");

            outputStream.write(JSONUtils.toJSONString(map).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (Base64DecodingException e) {
            throw new RuntimeException(e);
        }



    }
}
