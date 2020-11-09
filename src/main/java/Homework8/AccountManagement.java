package Homework8;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManagement implements AccountService {
    private AccountStatement accountStatement;
    private int getAmount = 0;


    public AccountManagement() throws IOException {
        this.accountStatement = new AccountStatement();
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
                    break;
                case "withdraw":
                    AccountManagement action2 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    action2.withdraw(i, j);
                    break;
                case "transfer":
                    AccountManagement action3 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    k = sc.nextInt();
                    action3.transfer(i, j, k);
                    break;
                case "deposit":
                    AccountManagement action4 = new AccountManagement();
                    i = sc.nextInt();
                    j = sc.nextInt();
                    action4.deposit(i, j);
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
                accountStatement.setPreparedStatement(accountStatement.getAccountConnection().getConnection().prepareStatement(sqlWithdraw));
                accountStatement.getPreparedStatement().setInt(1, amount);
                accountStatement.getPreparedStatement().setInt(2, accountId);
                accountStatement.getPreparedStatement().executeUpdate();
                accountStatement.getAccountConnection().getConnection().commit();
                System.out.println("Успешно снято " + amount + " со счета " + accountId);
            } else {
                throw new NotEnoughMoneyException();
            }

        } catch (NotEnoughMoneyException | SQLException e) {
            e.printStackTrace();
            System.out.println("Недостаточно средств для снятия!");
            accountStatement.getAccountConnection().getConnection().close();
        }
    }


    @Override
    public void balance(int accountId) throws UnknownAccountException, SQLException {
        try {
            String sqlBalance = "SELECT amount FROM ACCOUNTS WHERE id = ?";
            accountStatement.setPreparedStatement(accountStatement.getAccountConnection().getConnection().prepareStatement(sqlBalance));
            accountStatement.getPreparedStatement().setInt(1, accountId);
            ResultSet resultSet = accountStatement.getPreparedStatement().executeQuery();
            resultSet.next();
            getAmount = resultSet.getInt("amount");
            System.out.println(getAmount);
        } catch (SQLException e) {
            e.printStackTrace();
            accountStatement.getAccountConnection().getConnection().close();
        }
    }

    @Override
    public void transfer(int from, int to, int amount) throws NotEnoughMoneyException, UnknownAccountException, SQLException {
        try {
            balance(from);
            if (getAmount > amount) {
                withdraw(from, amount);
                deposit(to, amount);
                accountStatement.getAccountConnection().getConnection().commit();
            } else {
                throw new NotEnoughMoneyException();
            }
        } catch (NotEnoughMoneyException | SQLException e) {
            e.printStackTrace();
            System.out.println("Недостаточно средств для перевода!");
            accountStatement.getAccountConnection().getConnection().close();
        }
    }

    @Override
    public void deposit(int accountId, int amount) throws NotEnoughMoneyException,
            UnknownAccountException, SQLException {
        try {
            String sqlDeposit = "UPDATE ACCOUNTS SET amount = amount + ? WHERE id = ?";
            accountStatement.setPreparedStatement(accountStatement.getAccountConnection().getConnection().prepareStatement(sqlDeposit));
            accountStatement.getPreparedStatement().setInt(1, amount);
            accountStatement.getPreparedStatement().setInt(2, accountId);
            accountStatement.getPreparedStatement().executeUpdate();
            accountStatement.getAccountConnection().getConnection().commit();
            System.out.println("Успешно внесено " + amount + " на счет " + accountId);
        } catch (SQLException e) {
            e.printStackTrace();
            accountStatement.getAccountConnection().getConnection().close();
        }
    }
}


