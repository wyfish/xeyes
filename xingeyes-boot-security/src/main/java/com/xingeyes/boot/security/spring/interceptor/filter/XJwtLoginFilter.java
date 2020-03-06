package com.xingeyes.boot.security.spring.interceptor.filter;

import com.xingeyes.boot.json.jackson.XJacksonJsonUtil;
import com.xingeyes.boot.security.spring.provider.XJwtAuthenticationToken;
import com.xingeyes.boot.security.spring.util.XHttpUtil;
import com.xingeyes.boot.security.spring.util.XJwtTokenUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 启动登录认证流程过滤器
 * XJwtLoginFilter 是在通过访问 /login 的POST请求是被首先被触发的过滤器，默认实现是 UsernamePasswordAuthenticationFilter，
 * 它继承了 AbstractAuthenticationProcessingFilter，抽象父类的 doFilter 定义了登录认证的大致操作流程，
 * 这里我们的 XJwtLoginFilter 继承了 UsernamePasswordAuthenticationFilter，并进行了两个主要内容的定制。
 *
 * 1. 覆写认证方法，修改用户名、密码的获取方式，具体原因看代码注释
 * 2. 覆写认证成功后的操作，移除后台跳转，添加生成令牌并返回给客户端
 */
public class XJwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    /**
     *
     * @param authManager
     */
    public XJwtLoginFilter(AuthenticationManager authManager) {
        super.setAuthenticationManager(authManager);
    }

    /**
     *
     * @param req
     * @param res
     * @param chain
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        // POST 请求 /login 登录时拦截， 由此方法触发执行登录认证流程，可以在此覆写整个登录认证逻辑
        super.doFilter(req, res, chain);
    }

    /**
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 可以在此覆写尝试进行登录认证的逻辑，登录成功之后等操作不再此方法内
        // 如果使用此过滤器来触发登录认证流程，注意登录请求数据格式的问题
        // 此过滤器的用户名密码默认从request.getParameter()获取，但是这种
        // 读取方式不能读取到如 application/json 等 post 请求数据，需要把
        // 用户名密码的读取逻辑修改为到流中读取request.getInputStream()

        String body = getBody(request);
        // TODO: 检查XJacksonJsonUtil 是否工作 ???
        String username = XJacksonJsonUtil.parse("username",body.getClass());
        String password = XJacksonJsonUtil.parse("password",body.getClass());

        if (username == null) {  username = "";  }
        if (password == null) {  password = "";  }

        username = username.trim();

        XJwtAuthenticationToken authRequest = new XJwtAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        // 存储登录认证信息到上下文
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // 记住我服务
        getRememberMeServices().loginSuccess(request, response, authResult);
        // 触发事件监听器
        if (this.eventPublisher != null) {
            eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        // 生成并返回token给客户端，后续访问携带此token
        XJwtAuthenticationToken token = new XJwtAuthenticationToken(null, null, XJwtTokenUtil.generateToken(authResult));
        XHttpUtil.write(response, token);
    }

    /**
     * 获取请求Body
     * @param request
     * @return
     */
    public String getBody(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = null;
        BufferedReader reader = null;
        try {
            inputStream = request.getInputStream();
            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
