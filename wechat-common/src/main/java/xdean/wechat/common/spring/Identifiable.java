package xdean.wechat.common.spring;

import java.util.Objects;

public class Identifiable<ID> {
  public final ID id;

  public Identifiable(ID id) {
    this.id = id;
  }

  public ID getId() {
    return id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    } else if (obj.getClass() != getClass()) {
      return false;
    } else {
      return Objects.equals(id, ((Identifiable<?>) obj).id);
    }
  };
}
