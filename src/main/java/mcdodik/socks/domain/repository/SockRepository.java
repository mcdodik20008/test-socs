package mcdodik.socks.domain.repository;

import mcdodik.socks.domain.model.entity.Sock;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SockRepository extends JpaRepository<Sock, Long> {

    List<Sock> findAll(Specification<Sock> spec);

    List<Sock> findAll(Specification<Sock> spec, Sort sort);

}

