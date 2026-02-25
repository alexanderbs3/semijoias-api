package br.leetjourney.semijoiasapi.core.repository;

import br.leetjourney.semijoiasapi.core.entity.CartItem;
import br.leetjourney.semijoiasapi.core.entity.OrderItem;
import br.leetjourney.semijoiasapi.core.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {
    Optional<CartItem> deleteByUser(User user);

    List<CartItem> findByUser(User user);
}
