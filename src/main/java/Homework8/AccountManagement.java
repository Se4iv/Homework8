package Homework8;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManagement implements AccountService {
    Account account = new Account();
    private int getAmount = 0;

    public AccountManagement() throws IOException {
    }

    public void management() throws IOException {
        int i;
        int j;
        int k;
        Scanner sc = new Scanner(System.in);
        System.out.println(" Введите команду: \n");
        try {
            switch (sc.next()) {
                case "balance":
                    AccountManagement action1 = new AccountManagement();
                    i = sc.nextInt();
                    action1.balance(i);
                    account.connection.close();
                    break;
                case "withdraw":
                    AccountManagement action2 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    action2.withdraw(i, j);
                    account.connection.close();
                    break;
                case "transfer":
                    AccountManagement action3 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    k = sc.nextInt();
                    action3.transfer(i, j, k);
                    account.connection.close();
                    break;
                case "deposit":
                    AccountManagement action4 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    action4.deposit(i, j);
                    account.connection.close();
                    break;
                default:
                    throw new UnknownAccountException();
            }
        } catch (UnknownAccountException | NotEnoughMoneyException | SQLException n) {
            System.out.println("Ошибка ввода!");
        }
    }

    @Override
    public void withdraw(int accountId, int amount) throws NotEnoughMoneyException,
            UnknownAccountException, SQLException {
        try {
            balance(accountId);
            if (getAmount > amount) {
                String sqlWithdraw = "UPDATE ACCOUNTS SET amount = amount - ? WHERE id = ?";
                account.preparedStatement = account.connection.prepareStatement(sqlWithdraw);
                account.preparedStatement.setInt(1, amount);
                account.preparedStatement.setInt(2, accountId);
                account.preparedStatement.executeUpdate(sqlWithdraw);
                account.connection.commit();
                System.out.println("Успешно снято " + amount + " со счета " + accountId);
            } else {
                throw new NotEnoughMoneyException();
            }
        } catch (NotEnoughMoneyException | SQLException e) {
            e.printStackTrace();
            System.out.println("Недостаточно средств для снятия!");
        }
    }


    @Override
    public void balance(int accountId) throws UnknownAccountException, SQLException {
        try {
            String sqlBalance = "SELECT amount FROM ACCOUNTS WHERE id = ?";
            account.preparedStatement = account.connection.prepareStatement(sqlBalance);
            account.preparedStatement.setInt(1, accountId);
            ResultSet resultSet = account.preparedStatement.executeQuery();
            resultSet.next();
            getAmount = resultSet.getInt("amount");
            System.out.println(getAmount);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException {
        try {
            balance(from);
            if (getAmount > amount) {
                withdraw(from,amount);
                deposit(to,amount);
                account.connection.commit();
            } else {
                throw new NotEnoughMoneyException();
            }
        } catch (NotEnoughMoneyException | SQLException e) {
            e.printStackTrace();
            System.out.println("Недостаточно средств для перевода!");
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws NotEnoughMoneyException,
            UnknownAccountException{
        try {
                String sqlDeposit = "UPDATE ACCOUNTS SET amount = amount + ? WHERE id = ?";
                account.preparedStatement = account.connection.prepareStatement(sqlDeposit);
                account.preparedStatement.setInt(1, amount);
                account.preparedStatement.setInt(2, accountId);
                account.preparedStatement.executeUpdate(sqlDeposit);
                account.connection.commit();
                System.out.println("Успешно внесено " + amount + " на счет " + accountId);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


