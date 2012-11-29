package com.immomo.matrix.route;

import java.util.List;

import org.apache.commons.lang.math.RandomUtils;


/**
 * @author mixueqiang
 * @since 2012-10-28
 * 
 */
public class RandomStrategy implements RouteStrategy {

    @Override
    public String getTargetURI(List<String> targetURIs) {
        if (targetURIs == null || targetURIs.isEmpty()) {
            return null;
        }

        if (targetURIs.size() == 1) {
            return targetURIs.get(0);
        }

        return targetURIs.get(RandomUtils.nextInt(targetURIs.size()));
    }

}
