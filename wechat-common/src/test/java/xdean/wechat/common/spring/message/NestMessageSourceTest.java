package xdean.wechat.common.spring.message;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

import io.reactivex.Observable;
import xdean.auto.message.AutoMessage;

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
    assertEquals("Come to buy <java>(2nd edition) with $null", lms.getMessage(Messages.JAVA_PROMOTE, ""));
  }

  @Test
  public void testError() throws Exception {
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_NOT_CLOSE))
        .test()
        .assertError(t -> t.getMessage().contains("Prefix and Suffix not match"));
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_END_ESCAPER))
        .test()
        .assertError(t -> t.getMessage().contains("Can't end with escaper"));
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_END_QUOTER))
        .test()
        .assertError(t -> t.getMessage().contains("Quote not closed"));
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_ARG_FORMAT))
        .test()
        .assertError(t -> t.getMessage().contains("must only follow a integer"));
    Observable.fromCallable(() -> lms.getMessage(Messages.ERROR_ARG_INDEX))
        .test()
        .assertError(t -> t.getMessage().contains("not exist"));
  }
}
