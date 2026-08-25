package com.wallet.api.domain.wallet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.api.domain.wallet.dto.CreateWalletRequest;
import com.wallet.api.domain.wallet.dto.WalletResponse;
import com.wallet.api.infra.security.JwtService;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WalletController.class)
@AutoConfigureMockMvc(addFilters = false)
class WalletControllerTest {

    @Autowired private MockMvc mockMvc;

    @Autowired private ObjectMapper objectMapper;

    @MockBean private WalletService walletService;

    @MockBean private JwtService jwtService;

    @Test
    @DisplayName("Deve criar uma carteira com sucesso e retornar status 201")
    void shouldCreateWalletAndReturn201() throws Exception {
        UUID walletId = UUID.randomUUID();
        CreateWalletRequest request =
                new CreateWalletRequest(
                        "João Silva",
                        "12345678901",
                        "joao@email.com",
                        "senha123",
                        new BigDecimal("100.00"),
                        WalletType.USER);

        WalletResponse response =
                new WalletResponse(
                        walletId,
                        request.fullName(),
                        request.cpfCnpj(),
                        request.email(),
                        request.balance(),
                        WalletType.USER);

        when(walletService.createWallet(any(CreateWalletRequest.class))).thenReturn(response);

        mockMvc.perform(
                        post("/wallets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(walletId.toString()))
                .andExpect(jsonPath("$.fullName").value("João Silva"))
                .andExpect(jsonPath("$.balance").value(100.00));
    }

    @Test
    @DisplayName("Deve retornar o saldo de uma carteira com status 200")
    void shouldGetWalletBalanceAndReturn200() throws Exception {
        UUID walletId = UUID.randomUUID();
        BigDecimal balance = new BigDecimal("250.50");

        when(walletService.getBalance(walletId)).thenReturn(balance);

        mockMvc.perform(get("/wallets/{id}/balance", walletId))
                .andExpect(status().isOk())
                .andExpect(content().string("250.50"));
    }
}
