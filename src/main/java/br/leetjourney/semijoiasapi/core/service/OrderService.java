package br.leetjourney.semijoiasapi.core.service;

import br.leetjourney.semijoiasapi.api.dto.response.OrderResponseDTO;
import br.leetjourney.semijoiasapi.api.exception.BusinessException;
import br.leetjourney.semijoiasapi.api.mapper.OrderMapper;
import br.leetjourney.semijoiasapi.core.entity.*;
import br.leetjourney.semijoiasapi.core.enums.OrderStatus;
import br.leetjourney.semijoiasapi.core.repository.CartItemRepository;
import br.leetjourney.semijoiasapi.core.repository.OrderRepository;
import br.leetjourney.semijoiasapi.core.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final OrderMapper orderMapper;


    @Transactional
    public OrderResponseDTO checkout(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) throw new BusinessException("Carrinho vazio");

        // 1. Validar e Atualizar Estoque
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            if (product.getStockQuantity() < item.getQuantity()) {
                throw new BusinessException("Estoque insuficiente para o produto: " + product.getName());
            }
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);
        }

        // 2. Calcular Valores e Criar Pedido
        BigDecimal subtotal = cartItems.stream()
                .map(item -> item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal shipping = calculateShipping(user.getState());

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .subtotal(subtotal)
                .shippingCost(shipping)
                .total(subtotal.add(shipping))
                .shippingAddress(user.getAddress())
                .city(user.getCity())
                .state(user.getState())
                .zipCode(user.getZipCode())
                .build();

        // 3. Converter itens do carrinho em itens do pedido
        List<OrderItem> orderItems = cartItems.stream().map(cartItem ->
                OrderItem.builder()
                        .order(order)
                        .product(cartItem.getProduct())
                        .quantity(cartItem.getQuantity())
                        .unitPrice(cartItem.getProduct().getPrice())
                        .subtotal(cartItem.getProduct().getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                        .build()
        ).toList();

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        // 4. Limpar Carrinho
        cartItemRepository.deleteByUser(user);

        return orderMapper.toResponse(savedOrder);
    }

    private BigDecimal calculateShipping(String state) {
        return "SP".equalsIgnoreCase(state) ? new BigDecimal("10.00") : new BigDecimal("20.00");
    }

    public List<OrderResponseDTO> listByUser(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(orderMapper::toResponse).toList();
    }
}



