package com.sdl.ecommerce.dxa;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Preview Detection Interceptor.
 * Detects if current HTTP session is within a XPM session or not.
 *
 * @author nic
 */
public class PreviewDetectionInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // Check if current session has been invoked through XPM to enable sensitive inline editing controls.
        // However this is not a bullet-proof approach but good-enough on a staging site.
        //
        Boolean inXpmSession = (Boolean) request.getSession().getAttribute(ECommerceSessionAttributes.IN_XPM_SESSION);
        if ( inXpmSession == null || inXpmSession == false ) {
            if ( isXpmReferer(request) || isXpmSessionTokenActive(request) ) {
                setXPMStateInSession(request, true);
            }
        }

        // TODO: Add code for detect when leaving XPM

        return true;
    }

    /**
     * Check is referer is the XPM application. This is set when entering and leaving XPM.
     * @param request
     * @return true/false
     */
    private boolean isXpmReferer(HttpServletRequest request) {
        String referer = request.getHeader("Referer");
        return referer != null && referer.contains("/WebUI/Editors/SiteEdit/Views/Editor.aspx");
    }

    /**
     * Check if there is a preview-session-token set. This is first set when you have done a change that triggers update preview.
     * Before that there is no preview session token set.
     *
     * @param request
     * @return
     */
    private boolean isXpmSessionTokenActive(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            Cookie[] var2 = cookies;
            int var3 = cookies.length;

            for(int var4 = 0; var4 < var3; ++var4) {
                Cookie cookie = var2[var4];
                if("preview-session-token".equals(cookie.getName())) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * Set XPM state in the HTTP session so the information can be consumer by controllers etc.
     *
     * @param request
     * @param inXpm
     */
    private void setXPMStateInSession(HttpServletRequest request, boolean inXpm) {
        request.getSession().setAttribute(ECommerceSessionAttributes.IN_XPM_SESSION, inXpm);
        if ( inXpm ) {
            request.getSession().setAttribute(ECommerceSessionAttributes.XPM_SESSION_START_TIME, System.currentTimeMillis());
        }
        else {
            request.getSession().setAttribute(ECommerceSessionAttributes.XPM_SESSION_START_TIME, null);
        }
    }
}
