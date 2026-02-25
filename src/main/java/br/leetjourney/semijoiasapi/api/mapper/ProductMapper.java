package br.leetjourney.semijoiasapi.api.mapper;

import br.leetjourney.semijoiasapi.api.dto.request.ProductRequestDTO;
import br.leetjourney.semijoiasapi.api.dto.response.ProductResponseDTO;
import br.leetjourney.semijoiasapi.core.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "categoryId", source = "category.id")
    ProductResponseDTO toResponse(Product product);

    @Mapping(target = "category.id", source = "categoryId")
    Product toEntity(ProductRequestDTO dto);


}
