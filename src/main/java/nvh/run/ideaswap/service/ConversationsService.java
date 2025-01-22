package nvh.run.ideaswap.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.repository.ConversationsRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ConversationsService {
    ConversationsRepository conversationsRepository;

    public List<Conversations> getAllConversations() {
        List<Conversations> conversations;
        try {
            conversations = conversationsRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Get all conversations failed",e);
        }
        return conversations;
    }

    public Conversations getConversationById(ObjectId id) {
        Conversations conversation;
        try {
            conversation = conversationsRepository.findById(id).orElseThrow(() -> new RuntimeException("Conversation not found"));
        } catch (Exception e) {
            throw new RuntimeException("Get conversation failed",e);
        }
        return conversation;
    }

    public Conversations createConversation(Conversations conversation) {
        try {
            conversation = conversationsRepository.save(conversation);
        } catch (Exception e) {
            throw new RuntimeException("Create conversation failed",e);
        }
        return conversation;
    }

    public Conversations updateConversation(ObjectId id, Conversations conversation) {
        getConversationById(id);
        Conversations updatedConversation;
        try {
            updatedConversation = conversationsRepository.save(conversation);
        } catch (Exception e) {
            throw new RuntimeException("Update conversation failed",e);
        }
        return updatedConversation;
    }

    public Conversations deleteConversation(ObjectId id) {
        Conversations conversation = getConversationById(id);
        try {
            conversationsRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Delete conversation failed",e);
        }
        return conversation;
    }
}
