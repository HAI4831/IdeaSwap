package nvh.run.ideaswap.security.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.entity.User;
import nvh.run.ideaswap.data.repository.IUserRepository;
import nvh.run.ideaswap.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserDetailsServiceImpl implements UserDetailsService {
    IUserRepository userRepository;
    RoleService roleService;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        Role role = roleService.getRoleById(user.getRoleID());
        return new UserDetailsExtImpl(user, Collections.singleton(new SimpleGrantedAuthority(role.getName())));
    }
}
