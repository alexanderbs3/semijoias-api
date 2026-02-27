package br.leetjourney.semijoiasapi.core.service;

import br.leetjourney.semijoiasapi.api.dto.request.CartItemRequestDTO;
import br.leetjourney.semijoiasapi.api.exception.ResourceNotFoundException;
import br.leetjourney.semijoiasapi.core.entity.CartItem;
import br.leetjourney.semijoiasapi.core.entity.Product;
import br.leetjourney.semijoiasapi.core.entity.User;
import br.leetjourney.semijoiasapi.core.repository.CartItemRepository;
import br.leetjourney.semijoiasapi.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void addItem(User user, CartItemRequestDTO dto) {
        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado"));

        // Busca se o usuário já tem esse produto no carrinho
        List<CartItem> currentCart = cartItemRepository.findByUser(user);
        Optional<CartItem> existingItem = currentCart.stream()
                .filter(item -> item.getProduct().getId().equals(dto.productId()))
                .findFirst();

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + dto.quantity());
            cartItemRepository.save(item);
        } else {
            CartItem newItem = CartItem.builder()
                    .user(user)
                    .product(product)
                    .quantity(dto.quantity())
                    .build();
            cartItemRepository.save(newItem);
        }
    }

    public List<CartItem> listByUser(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Transactional
    public void removeItem(User user, Long productId) {
        // Implementação de remoção lógica ou física
        List<CartItem> items = cartItemRepository.findByUser(user);
        items.stream()
                .filter(i -> i.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);
    }
}