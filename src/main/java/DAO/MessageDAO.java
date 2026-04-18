package DAO;
import Model.Message;
import Model.Account;
import Util.ConnectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.sql.*;

public class MessageDAO {
    MessageDAO messageDAO;

    public Account getAccountById(int id) {
      Connection connection = ConnectionUtil.getConnection();
      try{
        String sql = "select * from Account where account_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));
          return account;
        }
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return null;
    }

    public Message insertMessage(Message message) {
      Connection connection = ConnectionUtil.getConnection();
      try{
        String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.getPosted_by());
        ps.setString(2, message.getMessage_text());
        ps.setLong(3,message.getTime_posted_epoch());
        ps.executeUpdate();
        ResultSet pkeyResultSet = ps.getGeneratedKeys();

        if(pkeyResultSet.next()) {
          int generated_message_id = (int) pkeyResultSet.getLong(1);
          return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
        }
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return null;
    }

    public List<Message> getAllMessages() {
      Connection connection = ConnectionUtil.getConnection();
      List<Message> messages = new ArrayList<>();
      try{
        String sql = "select * from message";
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
          messages.add(message);
        }
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return messages;
    }

    public Message getMessageById(int message_id) {
      Connection connection = ConnectionUtil.getConnection();
      try{
        String sql = "select * from message where message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);

        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
        }
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return null;
    }

    public Message deleteMessageByMessageId(int message_id) {
      Connection connection = ConnectionUtil.getConnection();
      Message message = getMessageById(message_id);
      try{
        String sql = "delete from message where message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, message_id);
        ps.executeUpdate();
        
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return message;
    }

    public Message updateMessageByMessageId(int message_id, Message message) {
      Connection connection = ConnectionUtil.getConnection();
      try{
        String sql = "update message set message_text = ? where message_id = ?";
        PreparedStatement ps = connection.prepareStatement(sql);

        ps.setString(1, message.getMessage_text());
        ps.setInt(2, message_id);
        ps.executeUpdate();

        Message messageFromDatabase = getMessageById(message_id);
        Message updatedMessage = new Message(message_id, messageFromDatabase.getPosted_by(), message.getMessage_text(), messageFromDatabase.getTime_posted_epoch());
        return updatedMessage;
      } catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return null;
    }

    public List<Message> getAllMessagesByAccountId(int account_id) {
      Connection connection = ConnectionUtil.getConnection();
      List<Message> messages = new ArrayList<>();
      try{
        String sql = "select * from message where posted_by = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, account_id);
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
          Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));
          messages.add(message);
        }
      }catch(SQLException e) {
        System.out.println(e.getMessage());
      }
      return messages;
    }

}
