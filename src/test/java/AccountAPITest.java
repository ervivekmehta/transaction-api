import com.google.gson.Gson;
import com.revolut.api.common.ErrorMessages;
import com.revolut.api.common.HttpStatus;
import com.revolut.api.model.Account;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import java.io.IOException;

public class AccountAPITest
{

    @ClassRule
    public static final Server SERVER = new Server();

    private static final String ACCOUNT_OPERATIONS_PATH = "http://localhost:8080/account/";

    private static final Gson gson = new Gson();

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_createAccount() throws IOException
    {
        long accountId = 1;
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.OK, response.getStatusLine().getStatusCode());
    }

    /*
     * Details : Positive Test
     * */
    @Test
    public void test_getAccount() throws IOException
    {
        long accountId = 2;
        double amount = 100;
        Account account = new Account(accountId, amount);

        HttpUriRequest request1 = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId + "&amount=" + amount);
        HttpClientBuilder.create().build().execute(request1);

        HttpUriRequest request2 = new HttpGet(ACCOUNT_OPERATIONS_PATH + accountId);
        HttpResponse response = HttpClientBuilder.create().build().execute(request2);

        Assert.assertEquals(HttpStatus.OK, response.getStatusLine().getStatusCode());

        Account receivedAccount = gson.fromJson(EntityUtils.toString(response.getEntity()), Account.class);

        Assert.assertEquals(account, receivedAccount);
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_accountNotExist() throws IOException
    {
        long accountId = 3;
        HttpUriRequest request = new HttpGet(ACCOUNT_OPERATIONS_PATH + accountId);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.NO_SUCH_ACCOUNT + accountId, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_duplicateAccount() throws IOException
    {
        long accountId = 4;
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId);
        HttpClientBuilder.create().build().execute(request);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.ACCOUNT_ID_ALREADY_EXISTS + accountId, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_negativeAmount() throws IOException
    {
        long accountId = 5;
        long amount = -1;
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId + "&amount=" + amount);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.NEGATIVE_BALANCE, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_invalidID() throws IOException
    {
        long accountId = -1;
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.INVALID_ID + accountId, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_stringInputID() throws IOException
    {
        String accountId = "test";
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.INVALID_ID + accountId, EntityUtils.toString(response.getEntity()));
    }

    /*
     * Details : Negative Test
     * */
    @Test
    public void test_nullID() throws IOException
    {
        HttpUriRequest request = new HttpPost(ACCOUNT_OPERATIONS_PATH + "create");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusLine().getStatusCode());
        Assert.assertEquals(ErrorMessages.NULL_OR_EMPTY_ID, EntityUtils.toString(response.getEntity()));
    }


}
