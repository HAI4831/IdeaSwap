package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.common.exceptions.exception.custom.auth.UsernameAlreadyExistException;
import nvh.run.ideaswap.data.entity.Users;
import nvh.run.ideaswap.data.repository.IUserRepository;
import org.bson.types.ObjectId;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(UserService.class);
    IUserRepository iUserRepository;
    public List<Users> getAllUsers() {
        List<Users> users ;
        try {
            users = iUserRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Retrieve List Users failed",e);
        }
        return users;
    }

    public Users getUserById(ObjectId id) {
        Users user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
    public Users createUser(Users user) {

        try {
            if(existsByEmail(user.getEmail())) throw new RuntimeException("Email already exist");
            if(existsByPhoneNumber(user.getPhoneNumber())) throw new RuntimeException("Phone number already exist");
            if(existsByUsername(user.getUsername())) throw new RuntimeException("Username already exist");
            user = iUserRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Create User failed",e);
        }
        return Users.builder().build();
    }
    public Users updateUser(ObjectId id, Users user) {
        getUserById(id);
        user.setId(id);
        Users updatedUser ;
        try {
            updatedUser = iUserRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Update User failed",e);
        }
        return updatedUser;
    }

    public Users deleteUser(ObjectId id) {
       Users user= getUserById(id);
        try {
            iUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete User failed",e);
        }
        return user;
    }
//    ____________________________________
    public Users findByUsername(String username) {
        Users user;
        try {
            user=iUserRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("User not found with username:{}"+username));
        }
        catch (Exception e) {
            throw new RuntimeException("Retrieve User By Username failed",e);
        }
        return user;
    }

    public Users findById(ObjectId id) {
        Users user ;
        try {
            user = iUserRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        } catch (Exception e) {
            throw new RuntimeException("Retrieve User By ID failed",e);
        }
        return user;
    }
    public Users findByPhoneNumber(String phoneNumber){
        log.info("Find By Phone Number:{}",phoneNumber);
        Users user ;
        try {
           user = iUserRepository.findByPhoneNumber(phoneNumber).orElseThrow(()->new RuntimeException("User not found with phoneNumber:{}"+phoneNumber));
        } catch (RuntimeException e) {
            throw new RuntimeException("Retrieve User By Phone Number failed",e);
        }
        return user;
    }
    public boolean existsByPhoneNumber(String phoneNumber) {
        return iUserRepository.existsByPhoneNumber(phoneNumber);
    }

    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username).orElseThrow(()->new UsernameAlreadyExistException("User not found with username:{}"+username));
    }


    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }
}
