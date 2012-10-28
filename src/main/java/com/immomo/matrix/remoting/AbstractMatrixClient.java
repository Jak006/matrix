package com.immomo.matrix.remoting;

import java.net.URI;

import com.immomo.matrix.Response;
import com.immomo.matrix.util.URIUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public abstract class AbstractMatrixClient implements MatrixClient {

    protected MatrixChannelStatus status;
    protected URI requestURI;
    protected int timeout;
    protected Response response;

    public AbstractMatrixClient(URI requestURI) {
        this.requestURI = requestURI;
        this.timeout = URIUtils.getIntParameter(requestURI, "timeout", 1000);
    }

    public String getHost() {
        return requestURI.getHost();
    }

    public int getPort() {
        return requestURI.getPort();
    }

    @Override
    public URI getRequestURI() {
        return requestURI;
    }

    @Override
    public int getTimeout() {
        return timeout;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    @Override
    public void setStatus(MatrixChannelStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AbstractMatrixClient [requestURI=" + requestURI + ", status=" + status + "]";
    }

}
