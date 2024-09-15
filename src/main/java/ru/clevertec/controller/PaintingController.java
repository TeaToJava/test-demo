package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.domain.Painting;
import ru.clevertec.exception.PaintingNotFoundException;
import ru.clevertec.service.PaintingService;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/paintings")
public class PaintingController {
    private final PaintingService paintingService;

    @GetMapping
    public ResponseEntity<List<Painting>> getAllPaintings() {
        return ResponseEntity
                .ok()
                .body(paintingService.getPaintings());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Painting> getPainting(@PathVariable("id") String paintingId) {
        Painting painting = paintingService
                .getPaintingById(UUID.fromString(paintingId));
        return ResponseEntity
                .ok()
                .body(painting);
    }

    @PostMapping
    public ResponseEntity<Painting> createPainting(@RequestBody Painting newPainting) {
        Painting painting = paintingService.create(newPainting);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(painting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Painting> updatePainting(@RequestBody Painting painting, @PathVariable("id") String paintingId) {
        Painting updatedPainting = paintingService
                .update(UUID.fromString(paintingId), painting);
        return ResponseEntity
                .ok()
                .body(painting);
    }

    @DeleteMapping("/{id}")
    public void deletePainting(@PathVariable("id") String paintingId) {
        paintingService.delete(UUID.fromString(paintingId));
    }

    @ExceptionHandler(PaintingNotFoundException.class)
    ResponseEntity<String> paintingNotFound(PaintingNotFoundException exception) {
        return ResponseEntity.badRequest().build();
    }
}
