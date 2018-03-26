package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message {
  @Getter(onMethod_ = @XmlElement(name = "MsgId"))
  private int id;

  @Getter(onMethod_ = @XmlElement(name = "FromUserName"))
  private String fromUserName;

  @Getter(onMethod_ = @XmlElement(name = "ToUserName"))
  private String toUserName;

  @Getter(onMethod_ = @XmlElement(name = "CreateTime"))
  private int createTime;

  @Getter(onMethod_ = @XmlElement(name = "MsgType"))
  private String messageType;
}
