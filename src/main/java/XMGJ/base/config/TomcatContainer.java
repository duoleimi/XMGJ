package XMGJ.base.config;

import org.apache.catalina.Context;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;


/**
 * http to https
 * @author lll
 */
public class TomcatContainer extends TomcatEmbeddedServletContainerFactory {

	@Override
	protected void postProcessContext(Context context) {
		super.postProcessContext(context);
		
		SecurityConstraint securityConstraint = new SecurityConstraint();
        securityConstraint.setUserConstraint("CONFIDENTIAL");
        SecurityCollection collection = new SecurityCollection();
        collection.addPattern("/*");
        securityConstraint.addCollection(collection);
        context.addConstraint(securityConstraint);
	}
}