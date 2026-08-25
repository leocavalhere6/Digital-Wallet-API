package com.wallet.api.domain.transfer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.api.domain.transfer.dto.TransferRequest;
import com.wallet.api.domain.transfer.dto.TransferResponse;
import com.wallet.api.infra.security.JwtService;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(TransferController.class)
@AutoConfigureMockMvc(addFilters = false)
class TransferControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockitoBean private TransferService transferService;

    @MockitoBean private JwtService jwtService;

    @Test
    @DisplayName(
            "Deve realizar uma transferência e retornar status 200/201 com os dados formatados")
    void shouldProcessTransferAndReturnResponse() throws Exception {
        UUID payerId = UUID.randomUUID();
        UUID payeeId = UUID.randomUUID();
        BigDecimal value = new BigDecimal("150.00");

        TransferRequest request = new TransferRequest(payerId, payeeId, value);
        TransferResponse response =
                new TransferResponse(value, payerId, payeeId, LocalDateTime.now());

        when(transferService.transfer(any(TransferRequest.class))).thenReturn(response);

        mockMvc.perform(
                        post("/transfers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.value").value(150.00))
                .andExpect(jsonPath("$.payer").value(payerId.toString()))
                .andExpect(jsonPath("$.payee").value(payeeId.toString()));
    }
}
