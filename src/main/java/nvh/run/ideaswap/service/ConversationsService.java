package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.dto.ConversationsDTO;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.repository.ConversationsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationsService {
    ConversationsRepository conversationsRepository;

    public ConversationsDTO getAllConversations() {
        List<Conversations> conversations = conversationsRepository.findAll();
        return ConversationsDTO.builder().build();
    }

    public ConversationsDTO getConversationById(String id) {
        Conversations conversation = conversationsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Conversation not found"));
        return ConversationsDTO.builder().build();
    }

    public ConversationsDTO createConversation(ConversationsDTO conversationsDTO) {
        Conversations conversation = conversationsRepository.save(
                Conversations.builder()
                        .members(conversationsDTO.getMembers())
                        .wallpaperUrl(conversationsDTO.getWallpaperUrl())
                        .build()
        );
        return ConversationsDTO.builder().build();
    }

    public ConversationsDTO updateConversation(String id, ConversationsDTO conversationsDTO) {
        getConversationById(id);
        Conversations updatedConversation = conversationsRepository.save(
                Conversations.builder()
                        .id(id)
                        .members(conversationsDTO.getMembers())
                        .wallpaperUrl(conversationsDTO.getWallpaperUrl())
                        .build()
        );
        return ConversationsDTO.builder().build();
    }

    public ConversationsDTO deleteConversation(String id) {
        getConversationById(id);
        conversationsRepository.deleteById(id);
        return ConversationsDTO.builder().build();
    }
}
