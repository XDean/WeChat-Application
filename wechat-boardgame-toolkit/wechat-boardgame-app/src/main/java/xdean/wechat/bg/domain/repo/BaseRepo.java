package xdean.wechat.bg.domain.repo;

import org.springframework.data.repository.CrudRepository;

public interface BaseRepo<T, ID> extends CrudRepository<T, ID> {
}
