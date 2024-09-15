package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.domain.Painting;
import ru.clevertec.entity.PaintingEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaintingMapper {
    Painting toPainting(PaintingEntity paintingEntity);

    List<Painting> toPaintings(List<PaintingEntity> paintingEntities);

    PaintingEntity toEntity(Painting painting);

    List<PaintingEntity> toDomains(List<Painting> paintings);
}
