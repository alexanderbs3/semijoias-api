package br.leetjourney.semijoiasapi.api.controller;

import br.leetjourney.semijoiasapi.api.dto.request.CartItemRequestDTO;
import br.leetjourney.semijoiasapi.core.entity.CartItem;
import br.leetjourney.semijoiasapi.core.entity.User;
import br.leetjourney.semijoiasapi.core.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<Void> addItem(@AuthenticationPrincipal User user, @Valid @RequestBody CartItemRequestDTO dto) {
        cartService.addItem(user, dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getMyCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.listByUser(user));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeItem(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.removeItem(user, productId);
        return ResponseEntity.noContent().build();
    }
}