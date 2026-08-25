package com.wallet.api.infra.security;

import com.wallet.api.domain.wallet.WalletRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final WalletRepository walletRepository;

    public UserDetailsServiceImpl(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return walletRepository.findByEmail(username)
                .map(wallet -> new User(
                        wallet.getEmail(),
                        wallet.getPassword(), // Ou o campo correspondente na entidade Wallet
                        Collections.emptyList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + username));
    }
}