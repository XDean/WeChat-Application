package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@XmlRootElement(name = "xml")
public class TextMessage extends Message {
  @Getter(onMethod_ = @XmlElement(name = "Content"))
  private String content;

  @Builder
  public TextMessage(int id, String fromUserName, String toUserName, int createTime, String content) {
    super(id, fromUserName, toUserName, createTime, "text");
    this.content = content;
  }
}
