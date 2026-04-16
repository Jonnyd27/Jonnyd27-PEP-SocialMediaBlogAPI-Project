package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
  AccountDAO accountDAO;

  public AccountService() {
    accountDAO = new AccountDAO();
  }

  public AccountService(AccountDAO accountDAO) {
    this.accountDAO = accountDAO;
  }

  public Account addAccount(Account account) {
    if(account.username.isBlank()) {
      return null;
    }
    if(account.password.length()>4) {
      return null;
    }
    if(accountDAO.geAccountByUsername(account.username) != null) {
      return null;
    }
    return accountDAO.insertAccount(account);
  }
}
