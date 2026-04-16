package DAO;
import Model.Message;
import Model.Account;
import Util.ConnectionUtil;

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
        String sql = "insert into message (posted_by, message_text, time_posted_epoch) values (?,?,?)";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setInt(1, message.posted_by);
        ps.setString(2, message.message_text);
        ps.setLong(3,message.time_posted_epoch);
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
}
