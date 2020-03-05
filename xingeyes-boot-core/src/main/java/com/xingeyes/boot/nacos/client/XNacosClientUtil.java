package com.xingeyes.boot.nacos.client;

import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.listener.EventListener;
import com.alibaba.nacos.api.naming.pojo.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 封装NacosClient客户端的Java API
 *  统一抛出 XComponentException (三方工具/平台异常)
 */
@Service
public class XNacosClientUtil {
    // TODO:
    @Autowired
    private ConfigService configService;

    @Autowired
    private NamingService namingService;

    /**
     * 用于服务启动的时候从 Nacos 获取配置
     * @param dataId 配置 ID，采用类似 package.class（如com.taobao.tc.refund.log.level）的命名规则保证全局唯一性，class 部分建议是配置的业务含义。
     * @param group 配置分组，建议填写产品名:模块名（Nacos:Test）保证唯一性
     * @param timeoutMs 读取配置超时时间，单位 ms，推荐值 3000。
     * @return 配置值
     */
    public String getConfig(String dataId, String group, long timeoutMs) {
        try {
            return configService.getConfig(dataId, group, timeoutMs);
        }catch (NacosException e)
        {
            //TODO:
            return null;
        }
    }

    /**
     * 如果希望 Nacos 推送配置变更，可以使用 Nacos 动态监听配置接口来实现
     * @param dataId
     * @param group
     * @param listener
     */
    public void addListener(String dataId, String group, Listener listener)
    {
        try {
            configService.addListener(dataId,group,listener);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 取消监听配置，取消监听后配置不会再推送
     * @param dataId
     * @param group
     * @param listener
     */
    public void removeListener(String dataId, String group, Listener listener){

    }

    /**
     * 用于通过程序自动发布 Nacos 配置，以便通过自动化手段降低运维成本
     * @param dataId
     * @param group
     * @param content
     * @return
     */
    public boolean publishConfig(String dataId, String group, String content){
        try {
            return configService.publishConfig(dataId,group,content);
        } catch (NacosException e) {
            //TODO:
            return false;
        }
    }

    /**
     * 用于通过程序自动删除 Nacos 配置，以便通过自动化手段降低运维成本
     * @param dataId
     * @param group
     * @return
     */
    public boolean removeConfig(String dataId, String group){
        try {
            return configService.removeConfig(dataId,group);
        } catch (NacosException e) {
            // TODO:
            return false;
        }
    }

    /**
     * 描述注册一个实例到服务
     * @param serviceName
     * @param ip
     * @param port
     */
    public void registerInstance(String serviceName, String ip, int port){
        try {
            namingService.registerInstance(serviceName,ip,port);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 描述注册一个实例到服务
     * @param serviceName
     * @param ip
     * @param port
     * @param clusterName
     */
    public void registerInstance(String serviceName, String ip, int port, String clusterName){
        try {
            namingService.registerInstance(serviceName,ip,port,clusterName);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 描述注册一个实例到服务
     * @param serviceName
     * @param instance
     */
    public void registerInstance(String serviceName, Instance instance){
        try {
            namingService.registerInstance(serviceName,instance);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 删除服务下的一个实例
     * @param serviceName
     * @param ip
     * @param port
     */
    public void deregisterInstance(String serviceName, String ip, int port){
        try {
            namingService.deregisterInstance(serviceName,ip, port);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 删除服务下的一个实例
     * @param serviceName
     * @param ip
     * @param port
     * @param clusterName
     */
    public void deregisterInstance(String serviceName, String ip, int port, String clusterName){
        try {
            namingService.deregisterInstance(serviceName,ip,port,clusterName);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 删除服务下的一个实例
     * @param serviceName
     * @param instance
     */
    public void deregisterInstance(String serviceName, Instance instance){
        try {
            namingService.deregisterInstance(serviceName,instance);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 获取服务下的所有实例
     * @param serviceName
     * @return
     */
    public List<Instance> getAllInstances(String serviceName){
        try {
            return namingService.getAllInstances(serviceName);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 获取服务下的所有实例
     * @param serviceName
     * @param clusters
     * @return
     */
    public List<Instance> getAllInstances(String serviceName, List<String> clusters){
        try {
            return namingService.getAllInstances(serviceName,clusters);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 根据条件获取过滤后的实例列表
     * @param serviceName
     * @param healthy
     * @return
     */
    public List<Instance> selectInstances(String serviceName, boolean healthy){
        try {
            return namingService.selectInstances(serviceName,healthy);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 根据条件获取过滤后的实例列表
     * @param serviceName
     * @param clusters
     * @param healthy
     * @return
     */
    public List<Instance> selectInstances(String serviceName, List<String> clusters, boolean healthy){
        try {
            return namingService.selectInstances(serviceName,clusters,healthy);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 根据负载均衡算法随机获取一个健康实例
     * @param serviceName
     * @return
     */
    public Instance selectOneHealthyInstance(String serviceName){
        try {
            return namingService.selectOneHealthyInstance(serviceName);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 根据负载均衡算法随机获取一个健康实例
     * @param serviceName
     * @param clusters
     * @return
     */
    public Instance selectOneHealthyInstance(String serviceName, List<String> clusters) {
        try {
            return namingService.selectOneHealthyInstance(serviceName,clusters);
        } catch (NacosException e) {
            // TODO:
            return null;
        }
    }

    /**
     * 监听服务下的实例列表变化
     * @param serviceName
     * @param listener
     */
    public void subscribe(String serviceName, EventListener listener){
        try {
            namingService.subscribe(serviceName,listener);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 监听服务下的实例列表变化
     * @param serviceName
     * @param clusters
     * @param listener
     */
    public void subscribe(String serviceName, List<String> clusters, EventListener listener) {
        try {
            namingService.subscribe(serviceName,clusters,listener);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 取消监听服务下的实例列表变化
     * @param serviceName
     * @param listener
     */
    public void unsubscribe(String serviceName, EventListener listener) {
        try {
            namingService.unsubscribe(serviceName,listener);
        } catch (NacosException e) {
            // TODO:
        }
    }

    /**
     * 取消监听服务下的实例列表变化
     * @param serviceName
     * @param clusters
     * @param listener
     */
    public void unsubscribe(String serviceName, List<String> clusters, EventListener listener){
        try {
            namingService.unsubscribe(serviceName,clusters,listener);
        } catch (NacosException e) {
            // TODO:
        }
    }

}
