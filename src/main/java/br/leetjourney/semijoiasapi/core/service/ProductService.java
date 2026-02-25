package br.leetjourney.semijoiasapi.core.service;

import br.leetjourney.semijoiasapi.api.dto.request.ProductRequestDTO;
import br.leetjourney.semijoiasapi.api.dto.response.ProductResponseDTO;
import br.leetjourney.semijoiasapi.api.exception.ResourceNotFoundException;
import br.leetjourney.semijoiasapi.api.mapper.ProductMapper;
import br.leetjourney.semijoiasapi.core.entity.Product;
import br.leetjourney.semijoiasapi.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {


    private final ProductRepository productRepository;
    private final ProductMapper productMapper;



    public List<ProductResponseDTO> listAll(Long categoryId, String name) {
        return productRepository.findByFilters(categoryId, name)
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponseDTO create(ProductRequestDTO dto) {
        Product product = productMapper.toEntity(dto);
        // Garantindo consistência de estado inicial
        product.setActive(true);
        product.setAverageRating(0.0);
        product.setReviewCount(0);
        return productMapper.toResponse(productRepository.save(product));
    }

    @Transactional
    public void delete(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));
        product.setActive(false); // Soft delete para manter histórico de pedidos
        productRepository.save(product);
    }

}
