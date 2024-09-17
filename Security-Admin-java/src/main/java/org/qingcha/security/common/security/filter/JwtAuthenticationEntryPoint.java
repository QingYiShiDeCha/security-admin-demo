package org.qingcha.security.common.security.filter;

import com.alibaba.fastjson.JSON;
import org.qingcha.security.common.result.AjaxResult;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Jwt认证失败处理
 * @author QingCha
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        writer.write(JSON.toJSONString(AjaxResult.error(HttpServletResponse.SC_UNAUTHORIZED, "认证失败，请重新登录!")));
        writer.flush();
        writer.close();
    }
}
