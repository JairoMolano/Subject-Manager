package co.usco.gestion_asignaturas.services;

import org.springframework.stereotype.Service;
import co.usco.gestion_asignaturas.models.UserModel;
import co.usco.gestion_asignaturas.repositories.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<SimpleGrantedAuthority> authoritirList = userModel.getRoles().stream()
        .map(role -> new SimpleGrantedAuthority(role.getRolename())).collect(Collectors.toSet());

        return new User(userModel.getUsername(), userModel.getPassword(), authoritirList);
    }
}
