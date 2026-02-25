package br.leetjourney.semijoiasapi.api.controller;

import br.leetjourney.semijoiasapi.api.dto.request.ProductRequestDTO;
import br.leetjourney.semijoiasapi.api.dto.response.ProductResponseDTO;
import br.leetjourney.semijoiasapi.core.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAll(
            @RequestParam(required = false)Long categoryId,
            @RequestParam(required = false)String name){
        List<ProductResponseDTO> products = productService.listAll(categoryId, name);
        return ResponseEntity.ok(products);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO dto){
        ProductResponseDTO product = productService.create(dto);
        return ResponseEntity.ok(product);
    }
}
