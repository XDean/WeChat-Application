package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlElement;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public abstract class Message extends WeChatEvent {
  @Getter(onMethod_ = @XmlElement(name = "MsgId"))
  int id;

  public Message(int id, String fromUserName, String toUserName, int createTime, String messageType) {
    super(fromUserName, toUserName, createTime, messageType);
    this.id = id;
  }
}
