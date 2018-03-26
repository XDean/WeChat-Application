package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@XmlRootElement
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TextMessage extends Message {
  String content;

  @Builder
  public TextMessage(int id, String fromUserName, String toUserName, int createTime, String content) {
    super(id, fromUserName, toUserName, createTime, "text");
    this.content = content;
  }
}
