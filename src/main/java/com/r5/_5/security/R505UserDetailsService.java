package com.r5._5.security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class R505UserDetailsService implements UserDetailsService {

    // Unique utilisateur, codé en dur (mot de passe stocké encodé en BCrypt)
    private final UserDetails admin;

    public R505UserDetailsService(PasswordEncoder passwordEncoder) {
        this.admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles("ADMIN")
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!admin.getUsername().equals(username)) {
            throw new UsernameNotFoundException("Utilisateur inconnu : " + username);
        }
        // Copie pour ne pas exposer l'instance interne (Spring efface le mot de passe après authentification)
        return User.withUserDetails(admin).build();
    }
}
