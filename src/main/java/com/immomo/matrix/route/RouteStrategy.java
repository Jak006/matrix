package com.immomo.matrix.route;

import java.util.List;

/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public interface RouteStrategy {

    String getTargetURI(List<String> targetURIs);

}
