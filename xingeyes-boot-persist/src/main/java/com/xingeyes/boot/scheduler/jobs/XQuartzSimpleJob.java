package com.xingeyes.boot.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *
 */
@Component
public class XQuartzSimpleJob implements Job {

    /**
     * 继承 Job 类，表明子类是一个任务（Job 的子类必须存在一个无参构造。）
     */
    public XQuartzSimpleJob(){}

    /**
     * 需要实现的接口，
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        // TODO:
        System.out.println("执行：" + LocalDateTime.now());
    }
}
