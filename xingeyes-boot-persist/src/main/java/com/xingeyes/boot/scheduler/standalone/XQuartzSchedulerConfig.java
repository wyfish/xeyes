package com.xingeyes.boot.scheduler.standalone;

import com.xingeyes.boot.scheduler.jobs.XQuartzSchedulerJob;
import com.xingeyes.boot.scheduler.listener.XQuartzListener;
import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

/**
 * 单机quartz 调度配置类,实现动态配置定时任务(DB方式)
 *
 * 备注：Quartz 核心概念
 *   1.Job 表示一个工作，要执行的具体内容。此接口中只有一个方法，如下：void execute(JobExecutionContext context)
 *   2.JobDetail 表示一个具体的可执行的调度程序，Job 是这个可执行程调度程序所要执行的内容，另外 JobDetail 还包含了这个任务调度的方案和策略。 
 *   3.Trigger 代表一个调度参数的配置，什么时候去调。 
 *   4.Scheduler 代表一个调度容器，一个调度容器中可以注册多个 JobDetail 和 Trigger。当 Trigger 与 JobDetail 组合，就可以被 Scheduler 容器调度了。
 *
 */
@Configuration
@EnableScheduling
public class XQuartzSchedulerConfig {

    private SchedulerFactoryBean factoryBean;

    /**
     * 配置定时任务
     * @param job
     * @return
     */
    @Bean(name = "xQuartzJobDetail")
    public MethodInvokingJobDetailFactoryBean detailFactoryBean(){
        MethodInvokingJobDetailFactoryBean jobDetail = new MethodInvokingJobDetailFactoryBean();
        /*
         * 是否并发执行
         * 例如每5s执行一次任务，但是当前任务还没有执行完，就已经过了5s了，
         * 如果此处为true，则下一个任务会执行，如果此处为false，则下一个任务会等待上一个任务执行完后，再开始执行
         */
        jobDetail.setConcurrent(false);

        //设置定时任务的名字
        jobDetail.setName("quartz_job_demo");
        //设置任务的分组，这些属性都可以在数据库中，在多任务的时候使用
        jobDetail.setGroup("xeyes");

        //为需要执行的实体类对应的对象
        // jobDetail.setTargetObject(job);

        //设置目标对象类
        jobDetail.setTargetClass(XQuartzSchedulerJob.class);

        /*
         * sayHello为需要执行的方法
         * 通过这几个配置，告诉JobDetailFactoryBean我们需要执行定时执行ScheduleTask类中的sayHello方法
         */
        jobDetail.setTargetMethod("sayHello");

        return jobDetail;
    }

    /**
     * 配置定时任务的触发器，也就是什么时候触发执行定时任务
     * @param jobDetail
     * @return
     */
    @Bean(name = "xQuartzJobTrigger")
    public CronTriggerFactoryBean cronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean jobDetail){

        CronTriggerFactoryBean trigger = new CronTriggerFactoryBean();
        trigger.setJobDetail(Objects.requireNonNull(jobDetail.getObject()));

        // TODO: 从数据库中读取配置规则或者改变执行规则
        //初始化的cron表达式(每天上午10:15触发)
        trigger.setCronExpression("0 15 10 * * ?");

        //trigger的name
        trigger.setName("quartz_job_trigger_demo");

        return trigger;
    }

    @Bean(name = "xQuartzSchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean(XAdaptableJobFactory jobFactory) throws IOException{
        factoryBean = new SchedulerFactoryBean();

        // 设置AdaptableJobFactory子类实例，可job对于可以应用于@Autowired 注解
        factoryBean.setJobFactory(jobFactory);

        // 设置单独的Quartz属性配置
        factoryBean.setQuartzProperties(quartzProperties());

        // 用于quartz集群，QuartzScheduler启动时更新已存在的job
        // 覆盖已存在的任务
        factoryBean.setOverwriteExistingJobs(true);

        // 延时启动，应用启动10秒后
        factoryBean.setStartupDelay(10);

        // 启动时自动启动
        factoryBean.setAutoStartup(true);

        // 注册触发器
        // TODO: 这里增加代码注册 Trigger and JobDetail list

        return factoryBean;
    }

    /**
     * 通过SchedulerFactoryBean获取Scheduler的实例
     * 备注：
     *    谁来负责Scheduler的执行: scheduler.start()
     */
    @Bean(name = "xQuartzScheduler")
    public Scheduler scheduler() {
        return factoryBean.getScheduler();
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/job/quartz.properties"));

        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }

    /**
     * quartz初始化监听器（监听容器与DB变化）
     */
    @Bean
    public XQuartzListener executorListener() {
        return new XQuartzListener();
    }

}
