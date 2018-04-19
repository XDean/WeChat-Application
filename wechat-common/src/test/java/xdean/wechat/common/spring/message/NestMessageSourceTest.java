package xdean.wechat.common.spring.message;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import io.reactivex.Observable;
import xdean.auto.message.AutoMessage;
import xdean.wechat.common.spring.LocaledMessageSource;

@AutoMessage(path = "/messages.properties")
public class NestMessageSourceTest {
  NestMessageSource source;
  Locale locale = Locale.getDefault();
  LocaledMessageSource lms = new LocaledMessageSource(() -> source, () -> locale);

  @Before
  public void setup() throws Exception {
    ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
    ms.setBasename("messages");
    source = new NestMessageSource(ms);
  }

  @Test
  public void test() throws Exception {
    assertEquals("Hello Mr.a and Mrs.a and Mr.b and Mrs.b and their son", lms.getMessage(Messages.HELLO_ABC));
    assertEquals("Come to buy <java>(2nd edition) with $123", lms.getMessage(Messages.JAVA_PROMOTE, "123"));
    assertEquals("Come to buy <java>(2nd edition) with $123.5", lms.getMessage(Messages.JAVA_PROMOTE, "123.5"));
  }

  @Test
  public void testError() throws Exception {
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_NOT_CLOSE))
        .test()
        .assertError(t -> t.getMessage().contains("Prefix and Suffix not match"));
  }
}
