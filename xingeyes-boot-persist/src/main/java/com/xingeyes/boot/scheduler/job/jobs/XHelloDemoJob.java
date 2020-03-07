package com.xingeyes.boot.scheduler.job.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class XHelloDemoJob implements Job {

    /**
     * 继承 Job 类，表明子类是一个任务（Job 的子类必须存在一个无参构造。）
     */
    public XHelloDemoJob(){}

    /**
     * 需要实现的接口，
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("Hello执行：" + LocalDateTime.now());
    }
}
