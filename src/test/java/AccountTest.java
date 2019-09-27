import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.exception.InsufficientBalanceException;
import com.revolut.api.model.Account;
import com.revolut.api.service.AccountService;
import com.revolut.api.service.impl.AccountServiceImpl;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountTest
{
    private static AccountService _accountService;

    @BeforeClass
    public static void before()
    {
        _accountService = AccountServiceImpl.getInstance();
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_createAccount() throws DuplicateAccountException, AccountNotExistsException
    {
        long accountId = 6;
        double balance = 100;
        Account account = _accountService.createAccount(accountId, balance);

        Assert.assertEquals(account, _accountService.getAccount(accountId));
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_getAccountCurrentBalance() throws AccountNotExistsException, DuplicateAccountException
    {
        long accountId = 7;
        double balance = 100;
        _accountService.createAccount(accountId, balance);

        Assert.assertEquals(balance, _accountService.getCurrentBalance(accountId), 0);
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_withdraw() throws DuplicateAccountException, InsufficientBalanceException, AccountNotExistsException
    {
        long accountId = 8;
        double balance = 100;
        double withdrawnAmount = 50;
        double result = (balance - withdrawnAmount);

        _accountService.createAccount(accountId, balance);
        _accountService.withdraw(accountId, withdrawnAmount);

        Assert.assertEquals(result, _accountService.getCurrentBalance(accountId), 0);
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_deposit() throws DuplicateAccountException, AccountNotExistsException
    {
        long accountId = 9;
        double balance = 100;
        double depositedAmount = 100;
        double result = (balance + depositedAmount);

        _accountService.createAccount(accountId, balance);
        _accountService.deposit(accountId, depositedAmount);

        Assert.assertEquals(result, _accountService.getCurrentBalance(accountId), 0);
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_duplicateAccount() throws DuplicateAccountException
    {
        long accountId = 10;
        double balance = 100;

        _accountService.createAccount(accountId, balance);

        Assert.assertThrows(DuplicateAccountException.class, () -> _accountService.createAccount(accountId, balance));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_accountNotExists()
    {
        long accountId = 11;

        Assert.assertThrows(AccountNotExistsException.class, () -> _accountService.getAccount(accountId));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_getBalanceWhenAccountNotExist()
    {
        Assert.assertThrows(AccountNotExistsException.class, () -> _accountService.getCurrentBalance(100));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_withdrawWithInsufficientBalance() throws DuplicateAccountException
    {
        long accountId = 12;
        double balance = 100;
        double withdrawnAmount = 500;

        _accountService.createAccount(accountId, balance);

        Assert.assertThrows(InsufficientBalanceException.class, () -> _accountService.withdraw(accountId, withdrawnAmount));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_withdrawWhenAccountNotExist()
    {
        Assert.assertThrows(AccountNotExistsException.class, () -> _accountService.withdraw(13, 100));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_deposit_NoSuchAccountException()
    {
        Assert.assertThrows(AccountNotExistsException.class, () -> _accountService.deposit(14, 100));
    }

}
