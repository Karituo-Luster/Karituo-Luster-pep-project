package Service;

import DAO.MessageDAO;
import Model.Message;

import java.util.List;

public class MessageService {
    MessageDAO messDAO;

    public MessageService(){
        messDAO = new MessageDAO();
    }
    public MessageService(MessageDAO messDAO){
        this.messDAO = messDAO;
    }

    public Message createMessage(Message msg){
        return messDAO.createMessage(msg);
    }
    public List<Message> getAllMessages(){
        return messDAO.getAllMessages();
    }
    public Message getMessageById(int msgId){
        return messDAO.getMessageById(msgId);
    }
    public List<Message> getMessagesByAccId(int accId){
        if(messDAO.getMessagesByAccId(accId) == null)
            return null;
        return messDAO.getMessagesByAccId(accId);
    }
    public Message updateMessage(int msgId, Message msg){
        if(msg.getMessage_text().length() >= 255 || msg.getMessage_text().isBlank())
            return null;
        return messDAO.updateMessage(msgId, msg);
    }
}
