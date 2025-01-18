package nvh.run.ideaswap.data.dto.share;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    @Builder.Default
    private int status = 1000;
    @Builder.Default
    private boolean success = true;
    private String message;
    private T data;
    private Class<T> clazz; // Lưu thông tin kiểu generic T
}
