package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
  public Account insertAccount(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "insert into account (username, password) values (?,?)";
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, account.username);
      ps.setString(2, account.password);
      ps.executeUpdate();
      ResultSet pkeyResultSet = ps.getGeneratedKeys();
      if(pkeyResultSet.next()) {
        int generated_account_id = (int) pkeyResultSet.getLong(1);
        return new Account(generated_account_id, account.getUsername(), account.getPassword());
      }
    }catch(SQLException e) {
      System.out.println(e.getMessage());
    }
    return account;
  }
}
