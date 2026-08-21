package com.wallet.api.domain.wallet;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, UUID> {
    Optional<Wallet> findByCpfCnpj(String cpfCnpj);

    Optional<Wallet> findByEmail(String email);

    boolean existsByCpfCnpj(String cpfCnpj);

    boolean existsByEmail(String email);
}
