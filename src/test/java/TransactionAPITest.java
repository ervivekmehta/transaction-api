import com.revolut.api.common.ErrorMessages;
import com.revolut.api.common.HttpStatus;
import com.revolut.api.exception.AccountNotExistsException;
import com.revolut.api.exception.DuplicateAccountException;
import com.revolut.api.service.AccountService;
import com.revolut.api.service.impl.AccountServiceImpl;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

public class TransactionAPITest
{
    @ClassRule
    public static final Server SERVER = new Server();

    private static final String TRANSACTION_OPERATIONS_PATH = "http://localhost:8080/transfer/";

    private static AccountService _accountService;

    @BeforeClass
    public static void before() throws Exception
    {
        // rather then use of service we can call Account API as well, but as using same main thread so no need.
        _accountService = AccountServiceImpl.getInstance();
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_transaction() throws IOException, DuplicateAccountException, AccountNotExistsException
    {
        long account_1 = 15;
        double amount = 100;
        long account_2 = 16;

        double transferAmount = 50;

        double account1_result = (amount - transferAmount);
        double account2_result = (amount + transferAmount);

        _accountService.createAccount(account_1, amount);
        _accountService.createAccount(account_2, amount);

        HttpUriRequest request = new HttpPost(TRANSACTION_OPERATIONS_PATH + "?from=" + account_1 + "&to=" + account_2 + "&amount=" + transferAmount);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusLine().getStatusCode());
        Assert.assertEquals(account1_result, _accountService.getCurrentBalance(account_1), 0);
        Assert.assertEquals(account2_result, _accountService.getCurrentBalance(account_2), 0);
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_accountHasInsufficientBalance() throws DuplicateAccountException, IOException
    {
        long account_1 = 17;
        double amount = 100;
        long account_2 = 18;
        double transferAmount = 500;

        _accountService.createAccount(account_1, amount);
        _accountService.createAccount(account_2, amount);

        HttpUriRequest request = new HttpPost(TRANSACTION_OPERATIONS_PATH + "?from=" + account_1 + "&to=" + account_2 + "&amount=" + transferAmount);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.INSUFFICIENT_BALANCE + account_1, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_negativeTransferAmount() throws DuplicateAccountException, IOException
    {
        long account_1 = 19;
        double amount = 100;
        long account_2 = 20;
        double transferAmount = -10;

        _accountService.createAccount(account_1, amount);
        _accountService.createAccount(account_2, amount);

        HttpUriRequest request = new HttpPost(TRANSACTION_OPERATIONS_PATH + "?from=" + account_1 + "&to=" + account_2 + "&amount=" + transferAmount);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.NEGATIVE_TRANSFER_AMOUNT, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_transferWhenAccountNotExists() throws DuplicateAccountException, IOException
    {
        long account_1 = 21;
        double amount = 100;
        long account_2 = 22;
        double transferAmount = 50;

        _accountService.createAccount(account_1, amount);

        HttpUriRequest request = new HttpPost(TRANSACTION_OPERATIONS_PATH + "?from=" + account_1 + "&to=" + account_2 + "&amount=" + transferAmount);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.NO_SUCH_ACCOUNT + account_2, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_invalidAccount() throws DuplicateAccountException, IOException
    {
        long account_1 = 23;
        double amount = 100;
        long account_2 = -10;
        double transferAmount = 50;

        _accountService.createAccount(account_1, amount);

        HttpUriRequest request = new HttpPost(TRANSACTION_OPERATIONS_PATH + "?from=" + account_1 + "&to=" + account_2 + "&amount=" + transferAmount);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.INVALID_ID + account_2, EntityUtils.toString(response.getEntity()));
    }
}
