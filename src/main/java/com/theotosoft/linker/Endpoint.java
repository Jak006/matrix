package com.theotosoft.linker;

import java.net.URL;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public interface Endpoint {

    URL getUrl();

    void send(Object message);

}
