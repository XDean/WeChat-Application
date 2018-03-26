package xdean.wechat.common.model.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Message {
  int id;
  String fromUserName;
  String toUserName;
  int createTime;
  String messageType;
}
