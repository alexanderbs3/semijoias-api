package br.leetjourney.semijoiasapi.core.repository;

import br.leetjourney.semijoiasapi.core.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Filtro dinâmico por categoria e nome (ativo = true)
    @Query("SELECT p FROM Product p WHERE p.active = true " +
            "AND (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "AND (:name IS NULL OR LOWER(p.name) LIKE LOWER(concat('%', :name, '%')))")
    List<Product> findByFilters(@Param("categoryId") Long categoryId, @Param("name") String name);

    List<Product> findByActiveTrue();
}