package com.xingeyes.boot.mongodb.controller;

import com.xingeyes.boot.mongodb.entity.EntityTest1;
import com.xingeyes.boot.mongodb.entity.MongoEntityTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MongoController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping(value = "/")
    public String index(){
        return "redirect:/find";
    }

    //新增文档
    @GetMapping(value = "/insert")
    public String insert(){
        //insert方法并不提供级联类的保存，所以级联类需要先自己先保存
        EntityTest1 entityTest1_1 = new EntityTest1(1000L);
        //执行完insert后对象entityTest1将会获得保存后的id
        mongoTemplate.insert(entityTest1_1);
        //再添加一条
        EntityTest1 entityTest1_2 = new EntityTest1(1001L);
        mongoTemplate.insert(entityTest1_2);
        //创建列表并将保存后的关联对象添加进去
        ArrayList<EntityTest1> entityTest1List = new ArrayList<EntityTest1>();
        entityTest1List.add(entityTest1_1);
        entityTest1List.add(entityTest1_2);

        //新增主体对象
        MongoEntityTest entityTest = new MongoEntityTest(100L,"test",new Date(),10,entityTest1List);
        //新增数据的主键已经存在，则会抛DuplicateKeyException异常
        mongoTemplate.insert(entityTest);
        return "redirect:/find";
    }

    //保存文档
    //保存与新增的主要区别在于，如果主键已经存在，新增抛出异常，保存修改数据
    @GetMapping(value = "/save")
    public String save(){
        try{
            //查询最后一条数据并更新
            MongoEntityTest entityTest = mongoTemplate.findOne(Query.query(Criteria.where("")),MongoEntityTest.class);
            entityTest.setParameter4(3000);
            //保存数据的主键已经存在，则会对已经存在的数据修改
            mongoTemplate.save(entityTest);
        }catch (Exception e){
            // TODO:
        }

        return "redirect:/find";
    }

    //删除文档
    @GetMapping(value = "/delete")
    public String delete(){
        //查询第一条数据并删除
        MongoEntityTest entityTest = mongoTemplate.findOne(Query.query(Criteria.where("")),MongoEntityTest.class);
        //remove方法不支持级联删除所以要单独删除子数据
        List<EntityTest1> entityTest1List = entityTest.getParameter5();
        for(EntityTest1 entityTest1:entityTest1List){
            mongoTemplate.remove(entityTest1);
        }
        //删除主数据
        mongoTemplate.remove(entityTest);

        return "redirect:/find";
    }

    //更新文档
    @GetMapping(value = "/update")
    public String update(){
        //将查询条件符合的全部文档更新
        Query query = new Query();
        Update update = Update.update("parameter2_","update");
        mongoTemplate.updateMulti(query,update, MongoEntityTest.class);
        return "redirect:/find";
    }

    //查询文档
    @GetMapping(value = "/find")
    public String find(Model model){
        //查询小于当前时间的数据，并按时间倒序排列
        List<MongoEntityTest> findTestList = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date())) ,MongoEntityTest.class);
        model.addAttribute("findTestList",findTestList);

        //使用findOne查询如果结果极为多条，则返回排序在最上面的一条
        MongoEntityTest findOneTest = mongoTemplate.findOne(Query.query(Criteria.where("parameter3").lt(new Date())) ,MongoEntityTest.class);
        model.addAttribute("findOneTest",findOneTest);

        //模糊查询
        List<MongoEntityTest> findTestList1 = mongoTemplate.find(Query.query(Criteria.where("parameter3").lt(new Date()).and("parameter2").regex("es")) ,MongoEntityTest.class);
        model.addAttribute("findTestList1",findTestList1);

        return "/index";
    }
}