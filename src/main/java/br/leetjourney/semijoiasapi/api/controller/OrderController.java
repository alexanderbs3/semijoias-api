package br.leetjourney.semijoiasapi.api.controller;

import br.leetjourney.semijoiasapi.api.dto.response.OrderResponseDTO;
import br.leetjourney.semijoiasapi.core.entity.User;
import br.leetjourney.semijoiasapi.core.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderResponseDTO> checkout(@AuthenticationPrincipal User user){
        OrderResponseDTO order = orderService.checkout(user);
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getMyOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.listByUser(user));
    }
}
