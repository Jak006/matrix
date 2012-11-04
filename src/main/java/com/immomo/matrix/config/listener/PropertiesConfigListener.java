package com.immomo.matrix.config.listener;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author mixueqiang
 * @since Oct 19, 2012
 * 
 */
public abstract class PropertiesConfigListener extends ConfigListenerAdapter {
    private static final Log LOG = LogFactory.getLog(PropertiesConfigListener.class);

    @Override
    public void dataReceived(String data) {
        if (StringUtils.isEmpty(data)) {
            LOG.warn("Get null or empty message.");
            return;
        }

        Reader reader = null;
        Properties properties = new Properties();
        try {
            reader = new StringReader(data);
            properties.load(reader);
            propertiesReceived(properties);

        } catch (IOException e) {
            LOG.error("Load property messsage failed!", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOG.error("Close reader failed!", e);
                }
            }
        }
    }

    public abstract void propertiesReceived(Properties properties);

}
