package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    AccountDAO accDAO;

    public AccountService(){
        accDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accDAO){
        this.accDAO = accDAO;
    }
    
    public Account registerUser(Account acc){
        return accDAO.registerUser(acc);
    }
    public Account logIn(String usr, String pwd){
        return accDAO.logIn(usr, pwd);
    }
}
