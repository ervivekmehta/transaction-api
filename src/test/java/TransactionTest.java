import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Transaction;
import com.revolut.api.service.AccountService;
import com.revolut.api.service.TransactionService;
import com.revolut.api.service.impl.AccountServiceImpl;
import com.revolut.api.service.impl.TransactionServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionTest
{
    private static TransactionService _transactionService;

    private static AccountService _accountService;

    @BeforeClass
    public static void before()
    {
        _transactionService = TransactionServiceImpl.getInstance();
        _accountService = AccountServiceImpl.getInstance();
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_doTransaction() throws InsufficientBalanceException, AccountNotExistsException, DuplicateAccountException
    {
        long account_1 = 24;
        long account_2 = 25;
        double balance = 100;
        double transactionAmount = 50;
        double account1_BalanceResult = (balance - transactionAmount);
        double account2_BalanceResult = (balance + transactionAmount);

        _accountService.createAccount(account_1, balance);
        _accountService.createAccount(account_2, balance);
        Transaction transaction = new Transaction(account_1, account_2, transactionAmount);
        _transactionService.doTransaction(transaction);

        Assert.assertEquals(account1_BalanceResult, _accountService.getCurrentBalance(account_1), 0);
        Assert.assertEquals(account2_BalanceResult, _accountService.getCurrentBalance(account_2), 0);
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_accountHasInsufficientBalance() throws DuplicateAccountException
    {
        long account_1 = 26;
        long account_2 = 27;
        double amount = 100;
        double transactionAmount = 500;

        _accountService.createAccount(account_1, amount);
        _accountService.createAccount(account_2, amount);

        Transaction transaction = new Transaction(account_1, account_2, transactionAmount);

        Assert.assertThrows(InsufficientBalanceException.class, () -> _transactionService.doTransaction(transaction));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_SenderAccountNotExists() throws DuplicateAccountException
    {
        long account_1 = 28;
        long account_2 = 29;

        _accountService.createAccount(account_2, 100);
        Transaction transaction = new Transaction(account_1, account_2, 50);

        Assert.assertThrows(AccountNotExistsException.class, () -> _transactionService.doTransaction(transaction));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_ReceiverAccountNotExists() throws DuplicateAccountException
    {
        long account_1 = 30;
        long account_2 = 31;

        _accountService.createAccount(account_1, 100);
        Transaction transaction = new Transaction(account_1, account_2, 50);

        Assert.assertThrows(AccountNotExistsException.class, () -> _transactionService.doTransaction(transaction));
    }
}
