package com.xingeyes.boot.elasticsearch.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 通用Xeyes Boot ElasticSearchRepository,
 * 针对于具体的实体类，则生成相应的子类即可
 */
public interface XElasticRepository<T, Long> extends ElasticsearchRepository<T, Long> {

    /**
     * Spring Data 一个强大功能，就是根据方法名称自动实现功能。
     * 比如：你的方法名叫做：findByTitle，那么它就知道你是根据title查询，然后自动帮你完成，无需写实现类。
     * 当然，方法名称需要符合一定的约定，请参见官方文档。
     * 例如：下面的函数，根据价格区间查询，
     * @param price1
     * @param price2
     * @return
     */
     // List<T> findByPriceBetween(double price1, double price2);
}
