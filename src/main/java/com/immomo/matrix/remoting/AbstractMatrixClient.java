package com.immomo.matrix.remoting;

import java.net.URL;

import com.immomo.matrix.Response;
import com.immomo.matrix.util.URLUtils;

/**
 * @author mixueqiang
 * @since 2012-10-25
 * 
 */
public abstract class AbstractMatrixClient implements MatrixClient {

    protected MatrixChannelStatus status;
    protected URL requetsURL;
    protected int timeout;
    protected Response response;

    public AbstractMatrixClient(URL requestURL) {
        this.requetsURL = requestURL;
        this.timeout = URLUtils.getIntParameter(requestURL, "timeout", 1000);
    }

    public String getHost() {
        return requetsURL.getHost();
    }

    public int getPort() {
        return requetsURL.getPort();
    }

    @Override
    public URL getRequestURL() {
        return requetsURL;
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
        return "AbstractMatrixClient [requestUrl=" + requetsURL + ", status=" + status + "]";
    }

}
