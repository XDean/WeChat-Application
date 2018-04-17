//package xdean.wechat.common.spring.message;
//
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.URL;
//import java.net.URLConnection;
//import java.security.AccessController;
//import java.security.PrivilegedActionException;
//import java.security.PrivilegedExceptionAction;
//import java.util.Locale;
//import java.util.MissingResourceException;
//import java.util.ResourceBundle;
//
//import org.springframework.context.support.ResourceBundleMessageSource;
//import org.springframework.lang.Nullable;
//import org.springframework.util.Assert;
//
//public class MultipleMessageSource extends ResourceBundleMessageSource {
//
//  @Override
//  protected ResourceBundle doGetBundle(String basename, Locale locale) throws MissingResourceException {
//    ClassLoader classLoader = getBundleClassLoader();
//    Assert.state(classLoader != null, "No bundle ClassLoader set");
//    return ResourceBundle.getBundle(basename, locale, classLoader, new MessageSourceControl());
//  }
//
//  private class MessageSourceControl extends ResourceBundle.Control {
//
//    @Override
//    @Nullable
//    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
//        throws IllegalAccessException, InstantiationException, IOException {
//
//      // Special handling of default encoding
//      if (format.equals("java.properties")) {
//        String bundleName = toBundleName(baseName, locale);
//        final String resourceName = toResourceName(bundleName, "properties");
//        final ClassLoader classLoader = loader;
//        final boolean reloadFlag = reload;
//        InputStream stream;
//        try {
//          stream = AccessController.doPrivileged((PrivilegedExceptionAction<InputStream>) () -> {
//            InputStream is = null;
//            if (reloadFlag) {
//              URL url = classLoader.getResource(resourceName);
//              if (url != null) {
//                URLConnection connection = url.openConnection();
//                if (connection != null) {
//                  connection.setUseCaches(false);
//                  is = connection.getInputStream();
//                }
//              }
//            } else {
//              is = classLoader.getResourceAsStream(resourceName);
//            }
//            return is;
//          });
//        } catch (PrivilegedActionException ex) {
//          throw (IOException) ex.getException();
//        }
//        if (stream != null) {
//          String encoding = getDefaultEncoding();
//          if (encoding == null) {
//            encoding = "ISO-8859-1";
//          }
//          try (InputStreamReader bundleReader = new InputStreamReader(stream, encoding)) {
//            return loadBundle(bundleReader);
//          }
//        } else {
//          return null;
//        }
//      } else {
//        // Delegate handling of "java.class" format to standard Control
//        return super.newBundle(baseName, locale, format, loader, reload);
//      }
//    }
//
//    @Override
//    @Nullable
//    public Locale getFallbackLocale(String baseName, Locale locale) {
//      return (isFallbackToSystemLocale() ? super.getFallbackLocale(baseName, locale) : null);
//    }
//
//    @Override
//    public long getTimeToLive(String baseName, Locale locale) {
//      long cacheMillis = getCacheMillis();
//      return (cacheMillis >= 0 ? cacheMillis : super.getTimeToLive(baseName, locale));
//    }
//
//    @Override
//    public boolean needsReload(
//        String baseName, Locale locale, String format, ClassLoader loader, ResourceBundle bundle, long loadTime) {
//
//      if (super.needsReload(baseName, locale, format, loader, bundle, loadTime)) {
//        cachedBundleMessageFormats.remove(bundle);
//        return true;
//      } else {
//        return false;
//      }
//    }
//  }
//
//}
