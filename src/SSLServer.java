
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.filter.ssl.SslFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

/**
 * @author http://www.techbrainwave.com
 */
public class SSLServer
{
    private static final int PORT = 5000;

    private static void addSSLSupport(DefaultIoFilterChainBuilder chain)
    {
        try
        {
            SslFilter sslFilter = new SslFilter(new SSLContextGenerator().getSslContext());
            chain.addLast("sslFilter", sslFilter);
            System.out.println("SSL support is added..");
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException, GeneralSecurityException
    {
        IoAcceptor acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();

        addSSLSupport(chain);

        chain.addLast("logger", new LoggingFilter());
        chain.addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));

        acceptor.setHandler(new SSLServerHandler());
        acceptor.getSessionConfig().setReadBufferSize(2048);
        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
        acceptor.bind(new InetSocketAddress(PORT));
        System.out.println("Server Started..");
    }
}