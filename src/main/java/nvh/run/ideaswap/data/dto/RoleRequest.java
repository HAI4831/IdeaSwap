package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record RoleRequest(
        @JsonProperty("_id")
        String id,
        @NotNull(message = "Create name is required",groups = CreateGroup.class)
        @NotNull(message = "Update name is required",groups = UpdateGroup.class)
        String name){}