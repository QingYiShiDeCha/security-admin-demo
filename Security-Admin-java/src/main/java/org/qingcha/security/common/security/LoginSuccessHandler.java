package org.qingcha.security.common.security;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
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

/**
 * 登录成功处理器
 * @author QingCha
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();

        String username = "user";

        try {
            String token = JwtUtils.genrateJwtToken(username);

            Object put = AjaxResult.success("登录成功").put("token", token);

            outputStream.write(JSONUtils.toJSONString(put).getBytes(StandardCharsets.UTF_8));
            outputStream.flush();
            outputStream.close();
        } catch (Base64DecodingException e) {
            throw new RuntimeException(e);
        }



    }
}
