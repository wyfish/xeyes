package com.xingeyes.boot.scheduler.jobs;

import com.xingeyes.boot.scheduler.task.XSchedulerJobService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

/**
 * 抽象类QuartzJobBean(实现了Job接口) 的实现类
 * 备注：Job的实现类中需要注入的类必须通过特定的注入，才能实现
 *      这里是传统的注入方式，如果不能工作，请使用 XEyesSpringContext 来获取特定的实体bean
 */
@PersistJobDataAfterExecution // 修改数据，防止并发
@DisallowConcurrentExecution  // 不允许并发执行
@Component
public class XQuartzSchedulerJob extends QuartzJobBean {

    @Autowired
    private XSchedulerJobService xSchedulerJobService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO: 增加代码处理 JobExecutionContext

        xSchedulerJobService.executeTask();
    }
}
