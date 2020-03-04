package com.xingeyes.boot.dbaccess.jpa.controller;

import com.xingeyes.boot.dbaccess.jpa.dao.UserRepositorySpecification;
import com.xingeyes.boot.dbaccess.jpa.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * JpaSpecificationExecutor的使用示例，单条件查询和多条件查询
 */
public class UserRepositorySpecificationDemo {

    @Autowired
    UserRepositorySpecification userRepositorySpecification;

    /**
     * 单个条件查询示例
     */
    public void testJpaSpecificationExecutor1() {
        /**
         * Specification:用于封装查查询条件
        */
        Specification<SysUser> spec=new Specification<SysUser>() {
            //Predicate：封装了单个查询条件

            /**
             * @param root		对查询对象属性的封装
             * @param criteriaQuery	封装了我们要执行的查询中的各个部分的信息，select from order
             * @param criteriaBuilder	查询条件的构造器
             * @return
             */
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name="张三"
                /**
                 * 参数一：查询的属性
                 * 参数二：条件的值
                 */
                Predicate predicate=criteriaBuilder.equal(root.get("name"),"张三");
                return predicate;
            }
        };

        List<SysUser> list=this.userRepositorySpecification.findAll(spec);
        for (SysUser users:list){
            System.out.println(users);
        }
    }

    /**
     * 多条件查询方式一
     */
    public void testJpaSpecificationExecutor2() {
        /**
         * Specification:用于封装查查询条件
         */
        Specification<SysUser> spec=new Specification<SysUser>() {
            //Predicate：封装了单个查询条件

            /**
             * @param root		对查询对象属性的封装
             * @param criteriaQuery	封装了我们要执行的查询中的各个部分的信息，select from order
             * @param criteriaBuilder	查询条件的构造器
             * @return
             */
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name="张三" and age=20
                /**
                 * 参数一：查询的属性
                 * 参数二：条件的值
                 */
                List<Predicate> list=new ArrayList<>();
                list.add(criteriaBuilder.equal(root.get("name"),"张三"));
                list.add(criteriaBuilder.equal(root.get("age"),20));
                Predicate[] arr=new Predicate[list.size()];
                return criteriaBuilder.and(list.toArray(arr));
            }
        };

        List<SysUser> list=this.userRepositorySpecification.findAll(spec);
        for (SysUser users:list){
            System.out.println(users);
        }
    }

    public void testJpaSpecificationExecutor3() {
        /**
         * Specification:用于封装查查询条件
         */
        Specification<SysUser> spec=new Specification<SysUser>() {
            //Predicate：封装了单个查询条件

            /**
             * @param root		对查询对象属性的封装
             * @param criteriaQuery	封装了我们要执行的查询中的各个部分的信息，select from order
             * @param criteriaBuilder	查询条件的构造器
             * @return
             */
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //where name="张三" and age=20
                /**
                 * 参数一：查询的属性
                 * 参数二：条件的值
                 */
				/*List<Predicate> list=new ArrayList<>();
				list.add(criteriaBuilder.equal(root.get("name"),"张三"));
				list.add(criteriaBuilder.equal(root.get("age"),20));
				Predicate[] arr=new Predicate[list.size()];*/
                //(name='张三' and age=20) or id=2
                return criteriaBuilder.or(criteriaBuilder.and(criteriaBuilder.equal(root.get("name"),"张三"),criteriaBuilder.equal(root.get("age"),20)),criteriaBuilder.equal(root.get("id"),1));
            }
        };

        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        List<SysUser> list=this.userRepositorySpecification.findAll(spec,sort);
        for (SysUser users:list){
            System.out.println(users);
        }
    }

}
