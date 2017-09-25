package XMGJ.base.config;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.coyote.http11.Http11Nio2Protocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * tomcat 配置
 * 		1、正式环境 开启apr 模式  开发环境 默认nio
 * 		2、http 转 https
 * @author lll
 */
@Configuration
public class TomcatConfig {
	
	AprLifecycleListener arpLifecycle = null;

    @Value("${environment}")
    public String environment;
    
    @Value("${tomcat.connection.timeout:20000}")
    public int connectTionTimeout; 
    @Value("${tomcat.max-connections:20480}")
    public int maxConnections; 
    
    
    @Value("${server.http.port}")
	public int httpPort;
	@Value("${server.port}")
	public int httpsPort;
	

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        
        // http to https
//        TomcatContainer tomcat = new TomcatContainer();
        
    	TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//        if (environment.equalsIgnoreCase(Constant.ENV_PRO)) {
//        	arpLifecycle = new AprLifecycleListener();//不初始化会报错
        	tomcat.setProtocol("org.apache.coyote.http11.Http11Nio2Protocol");
            tomcat.addConnectorCustomizers(new MyTomcatConnectorCustomizer());
//        }
        return tomcat;
    }
    

    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer
    {
        public void customize(Connector connector)
        {
        	/**
        	 *  http to https
        	 */
//        	connector.setPort(httpPort);
//    		connector.setSecure(false);
//    		connector.setRedirectPort(httpsPort);
        	
        	Http11Nio2Protocol protocol = (Http11Nio2Protocol) connector.getProtocolHandler();
            //设置最大连接数
            protocol.setMaxConnections(maxConnections);
            protocol.setConnectionTimeout(connectTionTimeout);
            protocol.setProcessorCache(-1);
        }
    }
    
}
