package xdean.wechat.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import com.google.common.base.MoreObjects;

import xdean.jex.log.Logable;

public class LoggableDispatcherServlet extends DispatcherServlet implements Logable {

  @Override
  protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (!(request instanceof ContentCachingRequestWrapper)) {
      request = new ContentCachingRequestWrapper(request);
    }
    if (!(response instanceof ContentCachingResponseWrapper)) {
      response = new ContentCachingResponseWrapper(response);
    }
    HandlerExecutionChain handler = getHandler(request);

    try {
      super.doDispatch(request, response);
    } finally {
      log(request, response, handler);
      updateResponse(response);
    }
  }

  private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache, HandlerExecutionChain handler) {
    info(MoreObjects.toStringHelper(HttpServletRequest.class)
        .add("status", responseToCache.getStatus())
        .add("method", requestToCache.getMethod())
        .add("path", requestToCache.getRequestURI())
        .add("client-ip", requestToCache.getRemoteAddr())
        .add("java-method", handler.toString())
        .add("response", getResponsePayload(responseToCache))
        .toString());
  }

  private String getResponsePayload(HttpServletResponse response) {
    ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    if (wrapper != null) {

      byte[] buf = wrapper.getContentAsByteArray();
      if (buf.length > 0) {
        int length = Math.min(buf.length, 5120);
        try {
          return new String(buf, 0, length, wrapper.getCharacterEncoding());
        } catch (UnsupportedEncodingException ex) {
          // NOOP
        }
      }
    }
    return "[unknown]";
  }

  private void updateResponse(HttpServletResponse response) throws IOException {
    ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
    responseWrapper.copyBodyToResponse();
  }

}