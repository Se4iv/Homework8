package Homework8;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException, UnknownAccountException {

        AccountManagement acc = new AccountManagement();
        acc.balance(1);

    }
}
