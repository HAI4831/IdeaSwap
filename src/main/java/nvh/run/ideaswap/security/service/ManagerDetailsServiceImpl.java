package nvh.run.ideaswap.security.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Manager;
import nvh.run.ideaswap.data.entity.Role;
import nvh.run.ideaswap.data.repository.ManagerRepository;
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
public class ManagerDetailsServiceImpl implements UserDetailsService {
    RoleService roleService;
    ManagerRepository managerRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByUsername(username)
                .orElseThrow(() ->  new UsernameNotFoundException("User Not Found with username: " + username));
        Role role = roleService.getRoleById(manager.getRoleID());
        return new ManagerDetailsExtImpl(manager, Collections.singleton(new SimpleGrantedAuthority(role.getName())));
    }
}
