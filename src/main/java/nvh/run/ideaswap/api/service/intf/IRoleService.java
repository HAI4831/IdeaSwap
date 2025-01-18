package nvh.run.ideaswap.api.service;


import nvh.run.ideaswap.data.entity.Roles;

public interface IRoleService {
    Roles findByName(String name);
}
