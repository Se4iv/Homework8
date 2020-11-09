package Homework8;

import java.io.IOException;
import java.sql.SQLException;

public interface AccountService {

    void withdraw(int accountId, int amount) throws
            NotEnoughMoneyException, UnknownAccountException, IOException, SQLException;

    void balance(int accountId) throws UnknownAccountException, IOException, SQLException;

    void deposit(int accountId, int amount) throws
            NotEnoughMoneyException, UnknownAccountException, IOException, SQLException;

    void transfer(int from, int to, int amount) throws
            NotEnoughMoneyException, UnknownAccountException, SQLException;
}