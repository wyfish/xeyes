package com.xingeyes.boot.elasticsearch;

import com.xingeyes.boot.elasticsearch.repository.XElasticRepository;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Spring-Boot下 ElasticSearch的使用工具类
 * 封装各类Repository 和 ElasticSearchTemplate的方法调用，
 */
@Service
public class XElasticSearchUtil {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private XElasticRepository xElasticRepository;

    //-------------------- 1. 创建索引 -----------------------
    /**
     * 创建索引,手动指定indexName
     * @param indexName
     */
    public boolean createIndex(String indexName){
        return elasticsearchTemplate.createIndex(indexName);
    }

    /**
     * 根据对象的字节码来创建索引，根据Object类的@Document注解信息来创建
     * @param clazz
     * @return
     */
    public <T> boolean createIndex(Class<T> clazz){
        return elasticsearchTemplate.createIndex(clazz);
    }

    //-------------------- 2. 删除索引 -----------------------

    /**
     * 根据索引名来删除索引
     * @param indexName
     */
    public boolean deleteIndex (String indexName){
        // TODO:
        return elasticsearchTemplate.deleteIndex(indexName);
    }

    /**
     * 根据类名来删除索引
     * @param clazz
     */
    public <T> boolean deleteIndex (Class<T> clazz) {
        // TODO:
        return elasticsearchTemplate.deleteIndex(clazz);
    }

    //-------------------- 3. 新增/批量增加/更校报文档数据 -----------------------

    /**
     * 新增数据
     * @param obj
     */
    public void insertData(Object obj){
        // TODO:
        xElasticRepository.save(obj);
    }

    /**
     * 批量新增
     * @param list
     */
    public void insertList(List<Object> list) {
        xElasticRepository.saveAll(list);
    }

    /**
     * 更新或者修改数据 （本质上是删除再新增）
     * @param obj
     */
    public void updateData(Object obj) {
        xElasticRepository.save(obj);
    }

    //------------ 4. 基本查询/自定义方法/自定义查询/分页查询/排序 文档数据 --------------

    /**
     * ElasticSearch 的基本查询方法, 这里需要根据需要新增
     * 包括：
     *  findAll()
     *  findAll(Sort sort)
     *  findAll(Pageable pageable)
     *  findAllById(Iterable iterable)
     *  findById(Object id)
     * @return
     */
    public  Iterable<Object>  query(){
        return xElasticRepository.findAll();
    }

    /**
     * 自定义查询：QueryBuilders.matchQuery() 方法
     * @param name
     * @param object
     * @return Page<item>：默认是分页查询，因此返回的是一个分页的结果对象，
     *       包含属性：
     *            totalElements：总条数
     *            totalPages：总页数
     *            Iterator：迭代器，本身实现了Iterator接口，因此可直接迭代得到当前页的数据
     *            其它属性
     */
    public Page<Object> matchQuery(String name, Object object){
        // 构建查询条件
        // NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加基本分词查询
        // QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
        queryBuilder.withQuery(QueryBuilders.matchQuery(name,object));

        // 搜索，并获取结果
        return xElasticRepository.search(queryBuilder.build());
    }

    /**
     * 自定义查询：QueryBuilders.termQuery() 方法
     *           termQuery:功能更强大，除了匹配字符串以外，还可以匹配
     *                     int/long/double/float/....
     * @param name
     * @param object
     * @return
     */
    public Page<Object> termQuery(String name, Object object){
        // 构建查询条件
        // NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加基本分词查询
        // QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
        // 这里，termQuery可以包括多种参数类型，
        queryBuilder.withQuery(QueryBuilders.termQuery(name,object));

        // 搜索，并获取结果
        return xElasticRepository.search(queryBuilder.build());
    }

    /**
     * 自定义布尔查询：QueryBuilders.booleanQuery() 方法
     * @param name
     * @param object
     * @return
     */
    public Page<Object> booleanQuery(String name, Object object){
        // 构建查询条件
        // NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加布尔查询
        // must()方法可以一直增加下去
        queryBuilder.withQuery(
                QueryBuilders.boolQuery()
                        .must(QueryBuilders.matchQuery(name,object))
                        .must(QueryBuilders.matchQuery(name,object)));

        // 搜索，并获取结果
        return xElasticRepository.search(queryBuilder.build());
    }

    /**
     * 自定义模糊查询：QueryBuilders.fuzzyQuery() 方法
     * @param name
     * @param object
     * @return
     */
    public Page<Object> fuzzyQuery(String name, Object object){
        // 构建查询条件
        // NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加基本分词查询
        // QueryBuilders提供了大量的静态方法，用于生成各种不同类型的查询
        // 这里，fuzzyQuery() 包括模糊查询的取值，
        queryBuilder.withQuery(QueryBuilders.fuzzyQuery(name,object));

        // 搜索，并获取结果
        return xElasticRepository.search(queryBuilder.build());
    }

    /**
     * 自定义查询并返回排序结果
     *     QueryBuilders.termQuery() 方法
     *     SortBuilders.fieldSort() 方法
     * @param name
     * @param object
     * @return
     */
    public Page<Object> queryAndSort(String name, Object object){
        // 构建查询条件
        // NativeSearchQueryBuilder：Spring提供的一个查询条件构建器，帮助构建json格式的请求体
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();

        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery(name,object));

        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort(name).order(SortOrder.ASC));

        // 搜索，并获取结果
        return xElasticRepository.search(queryBuilder.build());
    }

    //----------------- 5. 聚合功能相关函数  ------------------------
    // 在ES中，需要进行聚合、排序、过滤的字段其处理方式比较特殊，因此不能被分词。
    // 我们可以将特殊的字段设置为keyword类型，这种类型不会被分词，将来就可以参与聚合

    /**
     * 根据特定的名称进行分组,其中聚合类型为terms，聚合字段为字符串类型
     * @param aggName
     * @param fieldName
     * @return AggregatedPage<StringTerms.Bucket>
     *         可以扩展其它的返回类型，根据聚合的字段类型
     */
    public List<StringTerms.Bucket> aggregateByTerms (String aggName, String fieldName){

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        // AggregationBuilders：聚合的构建工厂类。所有聚合都由这个类来构建
        queryBuilder.addAggregation(
                AggregationBuilders.terms(aggName).field(fieldName));

        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Object> aggPage =  (AggregatedPage<Object>)xElasticRepository.search(queryBuilder.build());

        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation(aggName);
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();

        return buckets;
    }

    /**
     * 嵌套聚合，（代码中注释部分示例求平均值）
     * 这是个示例，可自行扩展更多嵌套聚合功能
     * @param aggName
     * @param fieldName
     * @param subAggName
     * @param subFieldName
     * @return
     */
    public List<StringTerms.Bucket> subAggregateByTerms(String aggName, String fieldName, String subAggName, String subFieldName){
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));

        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms(aggName).field(fieldName)
                        .subAggregation(AggregationBuilders.avg(subAggName).field(subFieldName)) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Object> aggPage = (AggregatedPage<Object>) this.xElasticRepository.search(queryBuilder.build());

        // 3、解析
        // 3.1、从结果中取出名为aggName的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation(aggName);
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();

//        // 3.3、遍历，这里演示获取子聚合 并求平均值，
//        for (StringTerms.Bucket bucket : buckets) {
//            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
//            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");
//
//            // 3.6.获取子聚合结果：
//            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
//            System.out.println("平均售价：" + avg.getValue());
//        }

        return buckets;
    }

}
