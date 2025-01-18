package nvh.run.authsystemgradle.api.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.authsystemgradle.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.authsystemgradle.data.entity.User;
import nvh.run.authsystemgradle.data.repository.IUserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService implements IUserService {
    IUserRepository iUserRepository;
    @Override
    public User findByUsername(String username) {
        return iUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:{}"+username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username).orElseThrow(()->new UsernameAlreadyExistException("User not found with username:{}"+username));
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
}
