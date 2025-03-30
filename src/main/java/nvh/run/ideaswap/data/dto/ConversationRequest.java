package nvh.run.ideaswap.data.dto;

import lombok.Builder;
import lombok.Data;
import nvh.run.ideaswap.data.entity.Member;

import java.util.List;

@Data
@Builder
public class ConversationRequest {
    private List<Member> members;
    @Builder.Default
    private String wallpaperUrl="";
}
