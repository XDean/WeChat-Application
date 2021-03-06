package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlElement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public abstract class WeChatEvent {
  @Getter(onMethod_ = @XmlElement(name = "FromUserName"))
  String fromUserName;

  @Getter(onMethod_ = @XmlElement(name = "ToUserName"))
  String toUserName;

  @Getter(onMethod_ = @XmlElement(name = "CreateTime"))
  int createTime;

  @Getter(onMethod_ = @XmlElement(name = "MsgType"))
  MessageType type;
}
