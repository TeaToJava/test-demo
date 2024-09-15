package ru.clevertec.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.domain.Painting;
import ru.clevertec.exception.PaintingNotFoundException;
import ru.clevertec.service.PaintingService;
import ru.clevertec.service.util.TestData;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaintingController.class)
class PaintingControllerTest {

    @MockBean
    private PaintingService paintingService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldGetAllPaintings() throws Exception {
        List<Painting> paintings = List.of(TestData.generatePainting());
        when(paintingService.getPaintings()).thenReturn(paintings);
        mockMvc.perform(get("/api/v1/paintings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldGetPaintingById() throws Exception {
        String uuidAsString = "df715351-ca57-43fd-9894-59cb0f6bd88c";
        UUID paintingId = UUID.fromString(uuidAsString);
        Painting painting = new Painting().setId(paintingId)
                .setDate(LocalDate.of(2010, 10, 12))
                .setArtist("Artist 1")
                .setTitle("New painting");
        painting.setId(paintingId);
        when(paintingService.getPaintingById(paintingId)).thenReturn(painting);
        mockMvc.perform(get("/api/v1/paintings/{paintingId}", uuidAsString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.artist").value("Artist 1"))
                .andExpect(jsonPath("$.id").value("df715351-ca57-43fd-9894-59cb0f6bd88c"))
                .andExpect(jsonPath("$.title").value("New painting"))
                .andExpect(jsonPath("$.date").value("2010-10-12"));
    }

    @Test
    void shouldGetPaintingById_NotExist() throws Exception {
        String uuidAsString = "df715351-ca57-43fd-9894-59cb0f6bd88c";
        UUID paintingId = UUID.fromString(uuidAsString);
        when(paintingService.getPaintingById(paintingId)).thenThrow(PaintingNotFoundException.class);
        mockMvc.perform(get("/api/v1/paintings/{paintingId}", uuidAsString))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldCreatePainting() throws Exception {
        mockMvc.perform(post("/api/v1/paintings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":\"c73a1cc7-9d4b-419b-a8ae-ddd180b223b9\"," +
                                "\"title\":\"Title 1\"," +
                                "\"artist\":\"Artist 1\"," +
                                "\"date\":\"2021-12-03\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldUpdatePainting() throws Exception {
        Painting painting = new Painting()
                .setDate(LocalDate.of(2011, 11, 22))
                .setArtist("Artist 2")
                .setTitle("New painting 2");

        String existingPaintingId = "6a494e44-d979-4acf-8884-c50da9c40ab6";
        UUID uuid = UUID.fromString(existingPaintingId);
        when(paintingService.update(uuid, painting)).thenReturn(painting.setId(uuid));
        mockMvc.perform(put("/api/v1/paintings/{paintingId}", existingPaintingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": null," +
                                "\"title\":\"New painting 2\"," +
                                "\"artist\":\"Artist 2\"," +
                                "\"date\":\"2011-11-22\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeletePainting() throws Exception {
        String existingPaintingId = "1e033f67-7a83-4cef-9407-30585f1229d4";
        UUID uuid = UUID.fromString(existingPaintingId);
        doNothing().when(paintingService).delete(uuid);
        mockMvc.perform(delete("/api/v1/paintings/{paintingId}", existingPaintingId))
                .andExpect(status().isOk());
    }
}