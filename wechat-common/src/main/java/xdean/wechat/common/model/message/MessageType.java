package xdean.wechat.common.model.message;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum MessageType {
  @XmlEnumValue("text")
  TEXT,
  @XmlEnumValue("unknown")
  UNKNOWN;
}
