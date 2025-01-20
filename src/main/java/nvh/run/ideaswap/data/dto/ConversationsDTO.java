package nvh.run.ideaswap.data.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotEmpty;
import nvh.run.ideaswap.common.validator.IsObjectID;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConversationsDTO {
    @IsObjectID
    private String id;

    @IsObjectID
    @NotEmpty(message = "Members cannot be empty")
    private List<String> members;

    private String wallpaperUrl;
}
