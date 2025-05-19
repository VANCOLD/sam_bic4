package fh.technikum.carsharing.services;

import fh.technikum.carsharing.persistence.entity.dto.DataTransferObject;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for transforming between models/entities and Data Transfer Objects (DTOs).
 * <p>
 * This service utilizes the {@link ModelMapper} library to handle the transformation of objects. It provides
 * methods to convert entities (e.g., models) to DTOs and vice versa.
 * </p>
 */
@Service
public class DtoTransformerService {

    private final ModelMapper modelMapper;

    public DtoTransformerService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    /**
     * Transform an object (such as a model) into a DTO (Data Transfer Object).
     *
     * @param source      The object to be transformed (model or entity).
     * @param targetClass The class type of the target DTO.
     * @param <T>         The target DTO type.
     * @return The transformed DTO.
     */
    public <T extends DataTransferObject> T transformToDto(Object source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }

    /**
     * Transform a DTO back into a model/entity.
     *
     * @param source      The DTO to be transformed back to a model.
     * @param targetClass The class type of the target model/entity.
     * @param <T>         The target model/entity type.
     * @return The transformed model/entity.
     */
    public <T> T transformToModel(DataTransferObject source, Class<T> targetClass) {
        return modelMapper.map(source, targetClass);
    }
}
