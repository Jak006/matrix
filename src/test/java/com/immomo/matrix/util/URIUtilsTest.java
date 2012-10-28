package com.immomo.matrix.util;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class URIUtilsTest {

    @Test
    public void testURIUtils() throws URISyntaxException {
        URI uri = new URI("netty://127.0.0.1:10010?_timeout=1000");

        Assert.assertEquals("//127.0.0.1:10010?_timeout=1000", uri.getSchemeSpecificPart());
        Assert.assertEquals("127.0.0.1", uri.getHost());
        Assert.assertEquals(10010, uri.getPort());
        Assert.assertEquals("_timeout=1000", uri.getQuery());
        Assert.assertEquals("", uri.getPath());

        Assert.assertEquals(1000, URIUtils.getIntParameter(uri, "_timeout", 0));
    }

}
