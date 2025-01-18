package nvh.run.ideaswap.api.service;


import nvh.run.ideaswap.data.entity.Users;

public interface IUserService {
    Users findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
