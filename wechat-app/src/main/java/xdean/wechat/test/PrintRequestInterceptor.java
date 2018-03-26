package xdean.wechat.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class PrintRequestInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler) {
    System.err.println(request.getContextPath());
    System.out.println(request.getServletPath());
    ContentCachingRequestWrapper requestCacheWrapperObject = new ContentCachingRequestWrapper(request);
    System.err.println(new String(requestCacheWrapperObject.getContentAsByteArray()));
    return true;
  }

  @Override
  public void afterCompletion(
      HttpServletRequest request,
      HttpServletResponse response,
      Object handler,
      Exception ex) {
  }
}