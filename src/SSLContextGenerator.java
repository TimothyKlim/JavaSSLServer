import java.io.File;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import org.apache.mina.filter.ssl.KeyStoreFactory;
import org.apache.mina.filter.ssl.SslContextFactory;

/**
 * @author http://www.techbrainwave.com
 */
public class SSLContextGenerator
{
    public SSLContext getSslContext()
    {
        SSLContext sslContext = null;
        try
        {
            File keyStoreFile = new File("/Users/timothyklim/Development/SSLServer/keystore.jks");
            File trustStoreFile = new File("/Users/timothyklim/Development/SSLServer/truststore.jks");

            if (keyStoreFile.exists() && trustStoreFile.exists())
            {
                final KeyStoreFactory keyStoreFactory = new KeyStoreFactory();
                System.out.println("Url is: " + keyStoreFile.getAbsolutePath());
                keyStoreFactory.setDataFile(keyStoreFile);
                keyStoreFactory.setPassword("localhost");

                final KeyStoreFactory trustStoreFactory = new KeyStoreFactory();
                trustStoreFactory.setDataFile(trustStoreFile);
                trustStoreFactory.setPassword("localhost");

                final SslContextFactory sslContextFactory = new SslContextFactory();
                final KeyStore keyStore = keyStoreFactory.newInstance();
                sslContextFactory.setKeyManagerFactoryKeyStore(keyStore);

                final KeyStore trustStore = trustStoreFactory.newInstance();
                sslContextFactory.setTrustManagerFactoryKeyStore(trustStore);
                sslContextFactory.setKeyManagerFactoryKeyStorePassword("localhost");
                sslContext = sslContextFactory.newInstance();
                System.out.println("SSL provider is: " + sslContext.getProvider());
            }
            else
            {
                System.out.println("Keystore or Truststore file does not exist");
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return sslContext;
    }
}