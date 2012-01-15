
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author http://www.techbrainwave.com
 */
public class SSLServerHandler extends IoHandlerAdapter
{
    private final Logger logger = (Logger) LoggerFactory.getLogger(getClass());
    private int idleTimeout = 10;

    @Override
    public void sessionOpened(IoSession session)
    {
// set idle time to 10 seconds
        session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, idleTimeout);

        session.setAttribute("Values: ");
    }

    @Override
    public void messageReceived(IoSession session, Object message)
    {
        System.out.println("Message received in the server..");
        System.out.println("Message is: " + message.toString());
        session.write("Yeah");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status)
    {
        logger.info("Transaction is idle for " + idleTimeout + "secs, So disconnecting..");
// disconnect an idle client
        session.close();
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause)
    {
// close the connection on exceptional situation
        session.close();
    }
}