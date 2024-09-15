package ru.clevertec.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.domain.Painting;
import ru.clevertec.entity.PaintingEntity;
import ru.clevertec.exception.PaintingNotFoundException;
import ru.clevertec.mapper.PaintingMapper;
import ru.clevertec.repository.PaintingRepository;
import ru.clevertec.service.util.TestData;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaintingServiceTest {

    @Mock
    PaintingRepository paintingRepository;

    @Mock
    PaintingMapper paintingMapper;
    @InjectMocks
    PaintingService paintingService;

    @Test
    void shouldGetPaintings() {
        List<PaintingEntity> expectedPaintingEntities = List.of(TestData.generatePaintingEntity());
        List<Painting> paintings = expectedPaintingEntities.stream()
                .map(TestData::generatePaintingFromPaintingEntity)
                .collect(Collectors.toList());
        when(paintingRepository.getPaintings()).thenReturn(expectedPaintingEntities);
        when(paintingMapper.toPaintings(expectedPaintingEntities)).thenReturn(paintings);
        List<Painting> actualPaintings = paintingService.getPaintings();
        assertThat(paintings.equals(actualPaintings)).isTrue();
    }

    void shouldGetPaintings_whenPaintingsNotExist() {
        List<PaintingEntity> expectedPaintingEntities = new ArrayList<>();
        List<Painting> paintings = new ArrayList<>();
        when(paintingRepository.getPaintings()).thenReturn(expectedPaintingEntities);
        when(paintingMapper.toPaintings(expectedPaintingEntities)).thenReturn(paintings);
        List<Painting> actualPaintings = paintingService.getPaintings();
        assertThat(actualPaintings.isEmpty()).isTrue();
    }

    @Test
    void shouldGetPaintingById() {
        PaintingEntity paintingEntity = TestData.generatePaintingEntity();
        UUID uuid = paintingEntity.getId();
        Painting painting = TestData.generatePaintingFromPaintingEntity(paintingEntity);

        when(paintingRepository.getPaintingById(uuid)).thenReturn(Optional.of(paintingEntity));
        when(paintingMapper.toPainting(paintingEntity)).thenReturn(painting);
        Painting actualpainting = paintingService.getPaintingById(uuid);
        assertThat(painting.getId().equals(actualpainting.getId())).isTrue();
    }

    @Test
    void shouldGetPaintingById_whenPaintingNotExist() {
        UUID uuid = UUID.randomUUID();
        when(paintingRepository.getPaintingById(uuid)).thenReturn(Optional.empty());
        assertThatExceptionOfType(PaintingNotFoundException.class)
                .isThrownBy(() -> paintingService.getPaintingById(uuid));
    }

    @Test
    void create() {
        PaintingEntity paintingEntity = TestData.generatePaintingEntity();
        Painting painting = TestData.generatePaintingFromPaintingEntity(paintingEntity);
        when(paintingMapper.toEntity(painting)).thenReturn(paintingEntity);
        when(paintingRepository.create(paintingEntity)).thenReturn(paintingEntity);
        when(paintingMapper.toPainting(paintingEntity)).thenReturn(painting);

        Painting actualPainting = paintingService.create(painting);
        assertThat(painting.getId().equals(actualPainting.getId())).isTrue();
    }

    @Test
    void update() {
        PaintingEntity paintingEntity = TestData.generatePaintingEntity();
        Painting painting = TestData.generatePaintingFromPaintingEntity(paintingEntity);
        UUID paintingId = UUID.randomUUID();
        when(paintingMapper.toEntity(painting)).thenReturn(paintingEntity);
        when(paintingRepository.update(paintingId, paintingEntity)).thenReturn(paintingEntity);
        when(paintingMapper.toPainting(paintingEntity)).thenReturn(painting);

        Painting actualpainting = paintingService.update(paintingId, painting);
        assertThat(painting.getId().equals(actualpainting.getId())).isTrue();
    }

    @Test
    void delete() {
        UUID paintingId = UUID.randomUUID();
        doNothing().when(paintingRepository).delete(paintingId);
        paintingService.delete(paintingId);
        verify(paintingRepository).delete(paintingId);
    }

}