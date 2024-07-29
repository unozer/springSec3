package it.gp.springsec3.service;

import it.gp.springsec3.model.Authority;
import it.gp.springsec3.model.Customer;
import it.gp.springsec3.repository.CustomerReository;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerAuthenticationProvider implements AuthenticationProvider {

    private CustomerReository customerReository;

    public CustomerAuthenticationProvider(CustomerReository customerReository) {
        this.customerReository = customerReository;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Recupero nome e password
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        // cerco l'utente corrispondente
        Customer customer = customerReository.findCustomerByEmail(username);
        if (customer != null) {
            System.out.println("password inserita: " + password);
            System.out.println("password sul db: " + customer.getPwd());
            // faccio il check delle password
            if (passwordEncoder().matches(password, customer.getPwd())) {
                // Se match è ok allora torno oggeto authentication
                System.out.println("Autenticazione OK");
                return new UsernamePasswordAuthenticationToken(username, password, getGrantedAutorities(customer.getAuthorities()));
            } else {
                // Se match non è ok allora lancio eccezione
                System.out.println("Password non valida");
                throw new BadCredentialsException("Password non valida");
            }
        } else {
            System.out.println("Utente non trovato");
            throw new BadCredentialsException("Utente non trovato");
        }
    }

    public List<GrantedAuthority> getGrantedAutorities(List<Authority> authorities) {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Authority authority : authorities) {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
