package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.domain.Painting;
import ru.clevertec.entity.PaintingEntity;
import ru.clevertec.exception.PaintingNotFoundException;
import ru.clevertec.mapper.PaintingMapper;
import ru.clevertec.repository.PaintingRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaintingService {
    private final PaintingRepository paintingRepository;
    private final PaintingMapper paintingMapper;

    public List<Painting> getPaintings() {
        List<PaintingEntity> paintingEntities = paintingRepository.getPaintings();
        return paintingMapper.toPaintings(paintingEntities);
    }

    public Painting getPaintingById(UUID PaintingId) {
        return paintingMapper.toPainting(paintingRepository.getPaintingById(PaintingId)
                .orElseThrow(() -> PaintingNotFoundException.byId(PaintingId)));
    }

    public List<Painting> getPaintingsByDate(LocalDate date) {
        return paintingMapper.toPaintings(paintingRepository.getPaintingByDate(date));
    }

    public Painting create(Painting painting) {
        PaintingEntity paintingEntity = paintingMapper.toEntity(painting);
        return paintingMapper.toPainting(paintingRepository.create(paintingEntity));
    }

    public Painting update(UUID paintingId, Painting newPainting) {
        PaintingEntity paintingEntity = paintingMapper.toEntity(newPainting);
        return paintingMapper.toPainting(paintingRepository.update(paintingId, paintingEntity));
    }

    public void delete(UUID paintingId) {
        paintingRepository.delete(paintingId);
    }
}
