package br.leetjourney.semijoiasapi.core.repository;

import br.leetjourney.semijoiasapi.core.entity.Order;
import br.leetjourney.semijoiasapi.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {
    List<Order> findByUser(User user);
}
