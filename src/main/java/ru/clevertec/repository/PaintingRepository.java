package ru.clevertec.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.clevertec.entity.PaintingEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PaintingRepository {
    private static final List<PaintingEntity> db = List.of(
            new PaintingEntity().setId(UUID.randomUUID())
                    .setTitle("Title 1")
                    .setDate(LocalDate.of(2021, 12, 03))
                    .setArtist("Artist 1"),

            new PaintingEntity().setId(UUID.randomUUID())
                    .setTitle("Title 2")
                    .setDate(LocalDate.of(2023, 02, 03))
                    .setArtist("Artist 2")
    );

    public List<PaintingEntity> getPaintings() {
        return db;
    }

    public List<PaintingEntity> getPaintingByDate(LocalDate date) {
        return db.stream()
                .filter(painting -> painting.getDate().isAfter(date))
                .toList();
    }

    public Optional<PaintingEntity> getPaintingById(UUID paintingId) {
        return db.stream()
                .filter(painting -> painting.getId().equals(paintingId))
                .findAny();
    }

    public PaintingEntity create(PaintingEntity paintingEntity) {
        return paintingEntity;
    }

    public PaintingEntity update(UUID paintingId, PaintingEntity newPaintingEntity) {
        return newPaintingEntity.setId(paintingId);
    }

    public void delete(UUID paintingId) {

    }
}
