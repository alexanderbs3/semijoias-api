package br.leetjourney.semijoiasapi.api.mapper;

import br.leetjourney.semijoiasapi.api.dto.response.OrderItemResponseDTO;
import br.leetjourney.semijoiasapi.api.dto.response.OrderResponseDTO;
import br.leetjourney.semijoiasapi.core.entity.Order;
import br.leetjourney.semijoiasapi.core.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "items", source = "items")
    OrderResponseDTO toResponse(Order order);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemResponseDTO toItemResponse(OrderItem item);

    List<OrderResponseDTO> toResponseList(List<Order> orders);
}
