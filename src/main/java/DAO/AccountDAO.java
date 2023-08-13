package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;

/*  LEGEND/KEYWORDS:
 *  acc     = account
 *  conn    = connection
 *  ps      = prepared statement
 *  rs      = result set
 *  usr     = username
 *  pwd     = password
 */

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
                return new Account(
                    generated_id, 
                    acc.getUsername(), 
                    acc.getPassword());
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    public Account logIn(String usr, String pwd){
        Connection conn = ConnectionUtil.getConnection();
        try{
            String sql = "SELECT * FROM account WHERE username = ? AND password = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usr);
            ps.setString(2, pwd);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next())
                return new Account(
                    rs.getInt("account_id"),
                    rs.getString("username"),
                    rs.getString("password")
                );
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}