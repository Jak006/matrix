package com.immomo.matrix.addressing;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.immomo.matrix.exception.URINotFoundException;
import com.immomo.matrix.route.RouteStrategy;
import com.immomo.matrix.service.provider.ServiceInstance;
import com.immomo.matrix.util.ClassLoaderUtils;

/**
 * {@link AddressingComponent} stores all configured application addresses in
 * this application. You can specify a {@link RouteStrategy} for the service
 * addressing at runtime.
 * <p>
 * TODO: address black list and so on.
 * 
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class AddressingComponent implements AddressingService {
    private static final Log LOG = LogFactory.getLog(ServiceInstance.class);

    private Map<String, List<String>> serverURIs = new HashMap<String, List<String>>();
    private RouteStrategy loadBalanceStrategy;

    public AddressingComponent(String propertyFile, RouteStrategy loadBalanceStrategy) {
        this.loadBalanceStrategy = loadBalanceStrategy;
        try {
            Properties properties = new Properties();
            properties.load(ClassLoaderUtils.getContextResource(propertyFile));
            String[] applications = properties.getProperty("Applications").split(",|;");

            for (String applicationName : applications) {
                String[] urls = properties.getProperty(applicationName).split(",|;");
                serverURIs.put(applicationName, Arrays.asList(urls));
            }

        } catch (Exception e) {
            LOG.error("Read property file error: " + propertyFile, e);
            System.exit(1);
        }
    }

    @Override
    public List<String> getAddresses(String applicationName, String serviceName) {
        return serverURIs.get(applicationName);
    }

    @Override
    public String getAddress(String applicationName, String serviceName) throws URINotFoundException {
        List<String> targetURIs = serverURIs.get(applicationName);
        if (targetURIs == null || targetURIs.isEmpty()) {
            throw new URINotFoundException(applicationName);
        }

        String uri = loadBalanceStrategy.getTargetURI(targetURIs);
        if (uri == null) {
            throw new URINotFoundException(applicationName);
        }

        return uri;
    }

}
