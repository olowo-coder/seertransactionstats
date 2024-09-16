package com.org.seerbitbanking.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.org.seerbitbanking.dto.request.TransactionRequest;
import com.org.seerbitbanking.dto.response.StatsResponse;
import com.org.seerbitbanking.model.Stats;
import com.org.seerbitbanking.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TransactionStatsController.class)
class TransactionStatsControllerTest {

    private final String BASE_PATH = TransactionStatsController.API_BASE;

    private ObjectMapper MAPPER = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService mockTransactionService;

    @Test
    void testAddTransactionIsSuccess() throws Exception {
        TransactionRequest request = new TransactionRequest();
        when(mockTransactionService.addTransaction(any(TransactionRequest.class))).thenReturn(201);

        final MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                        .content(MAPPER.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    void testAddTransactionIsInvalidJson() throws Exception {
        TransactionRequest request = new TransactionRequest();
        when(mockTransactionService.addTransaction(any(TransactionRequest.class))).thenReturn(400);

        final MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                .content(MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void testAddTransactionIsOlder() throws Exception {
        TransactionRequest request = new TransactionRequest();
        when(mockTransactionService.addTransaction(any(TransactionRequest.class))).thenReturn(204);

        final MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                .content(MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testAddTransactionIsFuture() throws Exception {
        TransactionRequest request = new TransactionRequest();
        when(mockTransactionService.addTransaction(any(TransactionRequest.class))).thenReturn(422);

        final MockHttpServletResponse response = mockMvc.perform(post(BASE_PATH)
                .content(MAPPER.writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
            .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }

    @Test
    void testGetStatistics() throws Exception {
        Stats stats = new Stats();
        stats.updateStats(BigDecimal.TEN);
        StatsResponse statsResponse = new StatsResponse(stats);
        when(mockTransactionService.getStats()).thenReturn(statsResponse);

        final MockHttpServletResponse response = mockMvc.perform(get(BASE_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.ALL_VALUE))
                .andReturn().getResponse();

//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(MAPPER.writeValueAsString(statsResponse));
    }

    @Test
    void testDeleteTransactions() throws Exception {
        final MockHttpServletResponse response = mockMvc.perform(delete(BASE_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(mockTransactionService).deleteAllTransactions();
    }
}
