package Service;

import Model.Message;
import DAO.MessageDAO;
import java.util.List;
public class MessageService {
  MessageDAO messageDAO;

  public MessageService() {
    messageDAO = new MessageDAO();
  }

  public MessageService(MessageDAO messageDAO) {
    this.messageDAO = messageDAO;
  }

  public Message addMessage(Message message) {
    if(message.message_text.isBlank()) {
      return null;
    }
    if(message.message_text.length()>255) {
      return null;
    }
    if(messageDAO.getAccountById(message.posted_by) == null) {
      return null;
    }
    return messageDAO.insertMessage(message);
  }

  public List<Message> getAllMessages() {
    return messageDAO.getAllMessages();
  }

  public Message getMessageById(int message_id) {
    return messageDAO.getMessageById(message_id);
  }

  public Message deleteMessageByMessageId(int message_id) {
    return messageDAO.deleteMessageByMessageId(message_id);
  }

  public Message updateMessageByMessageId(int message_id, Message message) {
    if(message.message_text.isBlank() || message.message_text.length() > 255) {
      return null;
    }
    if(getMessageById(message_id) == null) {
      return null;
    }
    return messageDAO.updateMessageByMessageId(message_id, message);
  }

  public List<Message> getAllMessagesByAccountId(int account_id) {
    return messageDAO.getAllMessagesByAccountId(account_id);
  }

}
