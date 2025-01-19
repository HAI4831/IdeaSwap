package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.RoleDTO;
import nvh.run.ideaswap.data.entity.Roles;
import org.springframework.http.ResponseEntity;

public interface IRoleService {
    ResponseEntity<Object> getAllRoles();

    ResponseEntity<Object> getRoleById(String id);

    ResponseEntity<Object> createRole(RoleDTO roleDTO);

    ResponseEntity<Object> updateRole(String id, RoleDTO roleDTO);

    ResponseEntity<Object> deleteRole(String id);
// _________________________________
    Roles findByName(String name);
}
