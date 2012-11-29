package com.immomo.matrix.remoting;

import java.net.URI;

import com.immomo.matrix.exception.InvalidTargetURIException;
import com.immomo.matrix.util.URIUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public abstract class AbstractMatrixClient implements MatrixClient {

    protected URI targetURI;
    protected MatrixChannelStatus status;

    public AbstractMatrixClient(URI targetURI) throws InvalidTargetURIException {
        this.targetURI = targetURI;
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
