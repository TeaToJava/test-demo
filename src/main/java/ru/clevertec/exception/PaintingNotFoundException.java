package ru.clevertec.exception;

import java.util.UUID;

public class PaintingNotFoundException extends RuntimeException {
    public PaintingNotFoundException(String message) {
        super(message);
    }

    public static PaintingNotFoundException byId(UUID paintingId) {
        return new PaintingNotFoundException("Not found by Id " + paintingId);
    }
}
