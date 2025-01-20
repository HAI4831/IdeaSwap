package nvh.run.ideaswap.data.mapper;

import nvh.run.ideaswap.data.dto.MessageDTO;
import nvh.run.ideaswap.data.entity.Conversations;
import nvh.run.ideaswap.data.entity.Messages;
import nvh.run.ideaswap.data.entity.Users;
import org.bson.types.ObjectId;

public class MessagesMapper {

    public static MessageDTO toDTO(Messages message) {
        if (message == null) {
            return null;
        }
        return MessageDTO.builder()
                .id(message.getId())
                .senderId(message.getSenderId() != null ? message.getSenderId().getId() : null)
                .content(message.getContent())
                .messageParentId(message.getMessageParentID() != null ? message.getMessageParentID().toHexString() : null)
                .conversationId(message.getConversation() != null ? message.getConversation().getId() : null)
                .fileUrl(message.getFileUrl())
                .type(message.getType())
                .build();
    }

    public static Messages toEntity(MessageDTO dto) {
        if (dto == null) {
            return null;
        }
        return Messages.builder()
                .id(dto.getId())
                .senderId(dto.getSenderId() != null ? Users.builder().id(dto.getSenderId()).build() : null)
                .content(dto.getContent())
                .messageParentID(dto.getMessageParentId() != null ? new ObjectId(dto.getMessageParentId()) : null)
                .conversation(dto.getConversationId() != null ? Conversations.builder().id(dto.getConversationId()).build() : null)
                .fileUrl(dto.getFileUrl())
                .type(dto.getType())
                .build();
    }
}

