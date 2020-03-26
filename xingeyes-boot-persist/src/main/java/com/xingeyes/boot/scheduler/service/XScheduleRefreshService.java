package com.xingeyes.boot.scheduler.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Configuration
@EnableScheduling
@Component
public class XScheduleRefreshService {

    // 配置数据库表的封装访问类
    // TODO: 待增加
//    @Autowired
//    private ConfigMapper configMapper;

    @Resource(name = "xQuartzJobDetail")
    private JobDetail jobDetail;

    @Resource(name = "xQuartzJobTrigger")
    private CronTrigger cronTrigger;

    @Resource(name = "xQuartzScheduler")
    private Scheduler scheduler;

    /**
     * 功能：每隔100s查库，并根据查询结果决定是否重新设置定时任务
      */
    @Scheduled(fixedRate = 100000)
    public void scheduleUpdateCronTrigger() throws SchedulerException {
        CronTrigger trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
        //当前Trigger使用的
        String currentCron = trigger.getCronExpression();
        log.info("currentCron Trigger:{}", currentCron);

        // TODO:
        //从数据库查询出来的
//        String searchCron = configMapper.findOne(1).getCron();
        String searchCron = "";
        log.info("searchCron  Trigger:{}", searchCron);

        if (currentCron.equals(searchCron)) {
            // 如果当前使用的cron表达式和从数据库中查询出来的cron表达式一致，则不刷新任务
        } else {
            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(searchCron);
            //按新的cronExpression表达式重新构建trigger
            trigger = (CronTrigger) scheduler.getTrigger(cronTrigger.getKey());
            trigger = trigger.getTriggerBuilder().withIdentity(cronTrigger.getKey()).withSchedule(scheduleBuilder).build();
            // 按新的trigger重新设置job执行
            scheduler.rescheduleJob(cronTrigger.getKey(), trigger);
            currentCron = searchCron;
        }
    }

}
