package com.immomo.matrix.remoting;

import java.net.URI;
import java.net.URISyntaxException;

import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.util.URIUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public abstract class AbstractMatrixClient implements MatrixClient {

    protected MatrixChannelStatus status;
    protected URI targetURI;
    protected int timeout;

    public AbstractMatrixClient(String targetURI) throws InvalidTargetURIException {
        try {
            this.targetURI = new URI(targetURI);
        } catch (URISyntaxException e) {
            // The connection URI is invalid.
            throw new InvalidTargetURIException(targetURI);
        }
    }

    public String getHost() {
        return targetURI.getHost();
    }

    public int getPort() {
        return targetURI.getPort();
    }

    @Override
    public URI getRequestURI() {
        return targetURI;
    }

    @Override
    public int getTimeout() {
        return URIUtils.getIntParameter(targetURI, "timeout", 1000);
    }

    @Override
    public void setStatus(MatrixChannelStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AbstractMatrixClient [requestURI=" + targetURI + ", status=" + status + "]";
    }

}
