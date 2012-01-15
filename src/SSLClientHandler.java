
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author http://www.techbrainwave.com
 */
public class SSLClientHandler extends IoHandlerAdapter
{
    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    private final String values;
    private boolean finished;

    public SSLClientHandler(String values)
    {
        this.values = values;
    }

    public boolean isFinished()
    {
        return finished;
    }

    @Override
    public void sessionOpened(IoSession session)
    {
        session.write(values);
    }

    @Override
    public void messageReceived(IoSession session, Object message)
    {
        logger.info("Message received in the client..");
        logger.info("Message is: " + message.toString());
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
    {
        session.close();
    }
}