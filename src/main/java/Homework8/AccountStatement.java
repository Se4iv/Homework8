package Homework8;

import java.sql.*;

public class AccountStatement {
    private PreparedStatement preparedStatement;
    private AccountConnection accountConnection;

    public AccountStatement() {
        this.accountConnection = new AccountConnection();
        accountConnection.setConnectionDb();
        this.preparedStatement = null;
    }

    public AccountConnection getAccountConnection() {
        return accountConnection;
    }

    public void setAccountConnection(AccountConnection accountConnection) {
        this.accountConnection = accountConnection;
    }

    public PreparedStatement getPreparedStatement() {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

}
