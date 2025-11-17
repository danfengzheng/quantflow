package com.quantflow.framework.security.handle;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.LocaleResolver;
import com.alibaba.fastjson2.JSON;
import com.quantflow.common.constant.Constants;
import com.quantflow.common.core.domain.AjaxResult;
import com.quantflow.common.core.domain.model.LoginUser;
import com.quantflow.common.utils.MessageUtils;
import com.quantflow.common.utils.ServletUtils;
import com.quantflow.common.utils.StringUtils;
import com.quantflow.common.utils.spring.SpringUtils;
import com.quantflow.framework.manager.AsyncManager;
import com.quantflow.framework.manager.factory.AsyncFactory;
import com.quantflow.framework.web.service.TokenService;

import static com.quantflow.common.constant.MessageKeys.USER_LOGOUT_SUCCESS;

/**
 * 自定义退出处理类 返回成功
 * 
 * @author ruoyi
 */
@Configuration
public class LogoutSuccessHandlerImpl implements LogoutSuccessHandler
{
    @Autowired
    private TokenService tokenService;

    /**
     * 退出处理
     * 
     * @return
     */
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException
    {
        // 确保LocaleContextHolder被正确设置，以便MessageUtils能够获取正确的Locale
        try {
            LocaleResolver localeResolver = SpringUtils.getBean(LocaleResolver.class);
            if (localeResolver != null) {
                java.util.Locale locale = localeResolver.resolveLocale(request);
                LocaleContextHolder.setLocale(locale);
            } else {
                // 如果获取不到LocaleResolver，使用默认Locale
                LocaleContextHolder.setLocale(Constants.DEFAULT_LOCALE);
            }
        } catch (Exception e) {
            // 如果设置Locale失败，使用默认Locale
            LocaleContextHolder.setLocale(Constants.DEFAULT_LOCALE);
        }
        
        LoginUser loginUser = tokenService.getLoginUser(request);
        if (StringUtils.isNotNull(loginUser))
        {
            String userName = loginUser.getUsername();
            // 删除用户缓存记录
            tokenService.delLoginUser(loginUser.getToken());
            // 记录用户退出日志
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(userName, Constants.LOGOUT, MessageUtils.message(USER_LOGOUT_SUCCESS)));
        }
        ServletUtils.renderString(response, JSON.toJSONString(AjaxResult.success(MessageUtils.message(USER_LOGOUT_SUCCESS))));
    }
}
