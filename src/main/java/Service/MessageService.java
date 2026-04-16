package Service;

import Model.Message;
import DAO.MessageDAO;
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
}
