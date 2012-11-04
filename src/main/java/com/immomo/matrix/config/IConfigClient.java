package com.immomo.matrix.config;

/**
 * 配置中心客户端，支持配置信息发布和订阅方式。
 * <ul>
 * <li>发布方式：同步发布；
 * <li>订阅方式：同步订阅和异步订阅。</li>
 * </ul>
 * 
 * @author blackbeans.zc, mixueqiang
 * @since Sep 25, 2012
 */
public interface IConfigClient {

    /**
     * 发布一条数据信息到配置中心。
     */
    void publish(String dataId, String data);

    /**
     * 同步订阅某条数据。只要配置中心有对应的数据，就会返回数据。
     */
    String subscribe(String dataId);

    /**
     * 异步订阅某条数据的变更，通知到的数据将由{@link IConfigListener}处理。
     * <p>
     * 只有在配置中心数据变更时，才会通知到{@link IConfigListener}；否则忽略。
     */
    void subscribe(String dataId, IConfigListener... configListeners);

    /**
     * 异步订阅某条数据的变更，通知到的数据将由{@link IConfigListener}处理。
     * <p>
     * 只有在配置中心数据变更时，才会通知到{@link IConfigListener}；否则忽略。
     */
    void subscribe(String dataId, long initialDelay, long period, IConfigListener... configListeners);

    /**
     * 关闭ConfigClient。
     */
    void stop();

}
