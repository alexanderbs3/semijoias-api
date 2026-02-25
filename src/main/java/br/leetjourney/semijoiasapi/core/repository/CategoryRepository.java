package br.leetjourney.semijoiasapi.core.repository;

import br.leetjourney.semijoiasapi.core.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}