package nvh.run.ideaswap.api.service.intf;

import nvh.run.ideaswap.data.dto.RoleDTO;
import org.springframework.http.ResponseEntity;

public interface IRole {
    ResponseEntity<Object> getAllRoles();

    ResponseEntity<Object> getRoleById(String id);

    ResponseEntity<Object> createRole(RoleDTO roleDTO);

    ResponseEntity<Object> updateRole(String id, RoleDTO roleDTO);

    ResponseEntity<Object> deleteRole(String id);
}
