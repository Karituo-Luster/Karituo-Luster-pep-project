package DAO;

import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*  LEGEND/KEYWORDS:
 *  msg     = message 
 *  msgId   = message ID
 *  msgList = message list
 *  acc     = account
 *  accId   = account ID
 *  conn    = connection
 *  ps      = prepared statement
 *  rs      = result set
 */

public class MessageDAO {
    public Message createMessage(Message msg){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, msg.getPosted_by());
            ps.setString(2, msg.getMessage_text());
            ps.setLong(3, msg.getTime_posted_epoch());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next() && msg.getMessage_text().length() < 255 && !msg.getMessage_text().isBlank()){
                int generated_id = (int) rs.getLong(1);
                return new Message(generated_id, msg.getPosted_by(), msg.getMessage_text(), msg.getTime_posted_epoch());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getAllMessages(){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<Message>();
        try{
            String sql = "SELECT * FROM message";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                                          rs.getString("message_text"), rs.getLong("time_posted_epoch"));
                msgList.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgList;
    }
    public Message getMessageById(int msgId){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM message WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, msgId);

            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                                   rs.getString("message_text"),rs.getLong("time_posted_epoch"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public List<Message> getMessagesByAccId(int accId){
        Connection conn = ConnectionUtil.getConnection();
        List<Message> msgList = new ArrayList<Message>();
        try{
            String sql = "SELECT * FROM message WHERE posted_by = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, accId);

            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                Message msg = new Message(rs.getInt("message_id"), rs.getInt("posted_by"),
                                          rs.getString("message_text"),rs.getLong("time_posted_epoch"));
                msgList.add(msg);
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return msgList;
    }
    public Message updateMessage(int msgId, Message msg){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "UPDATE message SET message_text = ? WHERE message_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, msg.getMessage_text());
            ps.setInt(2, msgId);
            ps.executeUpdate();

            String sql2 = "SELECT * FROM message WHERE message_id = ?";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, msgId);

            ResultSet rs = ps2.executeQuery();
            if(rs.next())
                return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), 
                                   rs.getString("message_text"),rs.getLong("time_posted_epoch"));
        }catch(SQLException e){
            System.out.println(e.getMessage());
        } 
        return null;
    }
}
