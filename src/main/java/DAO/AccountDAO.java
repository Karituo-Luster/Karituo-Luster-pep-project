package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

public class AccountDAO {
    public Account registerUser(Account acc){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "INSERT INTO account (username, password) VALUES (?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next() && acc.getPassword().length() > 4 && !acc.getUsername().isBlank()){
                int generated_id = (int) rs.getLong(1);
                return new Account(generated_id, acc.getUsername(), acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account checkCreds(Account acc){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT username, password FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, acc.getUsername());
            ps.setString(2, acc.getPassword());
            ps.executeUpdate();
            return new Account(acc.getUsername(), acc.getPassword());
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
