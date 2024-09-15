package ru.clevertec.service.util;

import ru.clevertec.domain.Painting;
import ru.clevertec.entity.PaintingEntity;

import java.time.LocalDate;
import java.util.Random;
import java.util.UUID;

public class TestData {
    public static PaintingEntity generatePaintingEntity() {
        return new PaintingEntity()
                .setId(UUID.randomUUID())
                .setTitle("Title " + new Random().nextInt())
                .setArtist("Artist " + new Random().nextInt())
                .setDate(LocalDate.now());
    }

    public static Painting generatePaintingFromPaintingEntity(PaintingEntity paintingEntity) {
        return new Painting()
                .setTitle(paintingEntity.getTitle())
                .setId(paintingEntity.getId())
                .setArtist(paintingEntity.getArtist())
                .setDate(paintingEntity.getDate());
    }

    public static Painting generatePainting() {
        return new Painting().setId(UUID.randomUUID())
                .setTitle("Title " + new Random().nextInt())
                .setArtist("Artist " + new Random().nextInt())
                .setDate(LocalDate.now());
    }
}
