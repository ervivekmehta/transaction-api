import com.revolut.api.bootmanager.ServerBootManager;
import org.junit.rules.ExternalResource;

public class Server extends ExternalResource
{
    private Thread thread;

    @Override
    protected void before() throws Exception
    {
        thread = new Thread(ServerBootManager::start);

        thread.setDaemon(true);

        thread.start();

        Thread.sleep(5000);
    }

    @Override
    protected void after()
    {
        if (thread != null)
        {
            thread.stop();
        }
    }
}

