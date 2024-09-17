package org.qingcha.security.common.security.filter;

import com.alibaba.druid.util.StringUtils;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.qingcha.security.common.constant.JwtConstant;
import org.qingcha.security.common.result.CheckResult;
import org.qingcha.security.common.security.UserDetailsServiceImpl;
import org.qingcha.security.common.utils.JwtUtils;
import org.qingcha.security.entity.SysUser;
import org.qingcha.security.service.SysUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Resource
    private SysUserService service;

    @Resource
    private UserDetailsServiceImpl userDetailsService;

    private final String[] WHITELIST = {
            "/login", "/logout", "/captcha", "/password", "/image/**"
    };

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader("Authorization");
        System.out.println("请求url: " + request.getRequestURI());
        // 如果token是空或者url在白名单内， 放行

        if (StringUtils.isEmpty(token) || new ArrayList<String>(Arrays.asList(WHITELIST)).contains(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        CheckResult checkResult;
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        checkResult = JwtUtils.validateJWT(token);
        if (!checkResult.isSuccess()) {
            Integer errCode = checkResult.getErrCode();
            if (Objects.equals(errCode, JwtConstant.JWT_ERRCODE_NULL)) {
                throw new JwtException("token不存在");
            } else if (Objects.equals(errCode, JwtConstant.JWT_ERRCODE_FAIL)) {
                throw new JwtException("token验证不通过");
            } else if (Objects.equals(errCode, JwtConstant.JWT_ERRCODE_EXPIRE)) {
                throw new JwtException("token过期");
            }
            return;
        }
        try {
            Claims claims = JwtUtils.parseJwt(token);
            String username = claims.getSubject();
            SysUser user = service.queryByUsername(username);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, userDetailsService.getUserAuthority(user.getId()))
            );
            chain.doFilter(request, response);

        } catch (Base64DecodingException e) {
            throw new RuntimeException(e);
        }

    }
}
