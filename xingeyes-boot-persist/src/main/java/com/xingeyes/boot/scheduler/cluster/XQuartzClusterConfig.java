package com.xingeyes.boot.scheduler.cluster;

import com.xingeyes.boot.scheduler.jobs.XQuartzSchedulerJob;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Quartz 的集群化配置类,增加无用job的自动清除
 * 备注：如果需要使用Quartz 的集群功能，需要在数据库中增加一起Quartz提供的数据表
 */
@Configuration
public class XQuartzClusterConfig {

    // 配置文件路径
    static final String QUARTZ_CONFIG = "job/quartz_cluster.properties";

    // 定时任务组名称
    public static final String Quartz_Group_Name = "xeyesQuartzJobGroup";

    //定时任务方法后缀
    public static final String Quartz_Job_Suffix = "_job";

    //定时任务触发器后缀
    public static final String Quartz_Trigger_Suffix = "_trigger";

    @Bean(name = "xQuartzSchedulerFactoryBean")
    public SchedulerFactoryBean schedulerFactoryBean(@Qualifier("xQuartzTriggers") CronTriggerImpl[] triggers)
            throws IOException, ParseException, SchedulerException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();

        // 用于quartz集群,QuartzScheduler,启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factory.setOverwriteExistingJobs(true);

        // QuartzScheduler 延时启动，应用启动完10秒后 QuartzScheduler 再启动
        factory.setStartupDelay(10);

        // 直接使用配置文件,用于quartz集群,加载quartz数据源配置
        factory.setConfigLocation(new ClassPathResource(QUARTZ_CONFIG));
        factory.setAutoStartup(true);

        // 集群需要通过QuartzJobBean注入，需要设置上下文
        factory.setApplicationContextSchedulerContextKey("applicationContext");

        // 注册触发器
        // factory.getScheduler().pauseAll();
        factory.setTriggers(createTriggers());// 直接使用配置文件
        // factory.setConfigLocation(new
        // FileSystemResource(this.getClass().getResource("/quartz_cluster.properties").getPath()));
        return factory;
    }

    /**
     *
     * @return
     * @throws ParseException
     */
    @Bean(name = "xQuartzTriggers")
    public CronTriggerImpl[] createTriggers()
            throws ParseException {
        List<CronTriggerImpl> l = new ArrayList<CronTriggerImpl>();

        l.add(createTrigger(XQuartzSchedulerJob.class, "0/20 * * * * ?"));
        //l.add(createTrigger(JobRefundWeichartBean.class, "0/20 * * * * ?"));
        //按你的需要添加多个任务：任务所在类.class   cron表达式

        return l.toArray(new CronTriggerImpl[l.size()]);
    }

    /**
     * 添加该方法的目的在于一个使用场景。如果代码中删除了不需要的定时任务，但是数据库中不会删除掉，会导致之前
     * 的定时任务一直在运行，如果把定时任务依赖的类删除了，就会导致报错，找不到目标。所以配置动态删除任务
     */
    @Bean
    public String fulsh(@Qualifier("xQuartzSchedulerFactoryBean") SchedulerFactoryBean schedulerFactoryBean,
                        @Qualifier("xQuartzTriggers") CronTriggerImpl[] triggers)
            throws SchedulerException {
        try {
            Scheduler s = schedulerFactoryBean.getScheduler();
            if (null == s) {
                return "Scheduler is null";
            }

            // 最新配置的任务
            List<String> newTriNames = new ArrayList<String>();
            if (null != triggers) {
                for (CronTriggerImpl cronTriggerImpl : triggers) {
                    newTriNames.add(cronTriggerImpl.getName());
                }
            }

            // 现有数据库中已有的任务
            Set<TriggerKey> myGroupTriggers = s.getTriggerKeys(GroupMatcher.triggerGroupEquals(Quartz_Group_Name));
            if (null == myGroupTriggers || myGroupTriggers.size() == 0) {
                return "myGroupTriggers is null";
            }

            if (newTriNames != null && newTriNames.size() > 0) {
                for (TriggerKey triggerKey : myGroupTriggers) {
                    String dbTriggerName = triggerKey.getName();
                    if (!newTriNames.contains(dbTriggerName)) {
                        // 暂停 触发器
                        s.pauseTrigger(triggerKey);
                        Trigger g = s.getTrigger(triggerKey);
                        JobKey jk = null;
                        if (null != g) {
                            jk = g.getJobKey();
                        }
                        // 停止触发器
                        s.pauseTrigger(triggerKey);
                        // 注销 触发器
                        s.unscheduleJob(triggerKey);
                        if (null != jk) {
                            // 暂停任务
                            s.pauseJob(jk);
                            // 删除任务
                            s.deleteJob(jk);
                        }
                    }
                }
            }
            // 重要，如果不恢复所有，会导致无法使用
            s.resumeAll();
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception:" + e.getMessage();
        }
        return "success";
    }


    /**
     * 内部私有方法，
     * @param c
     * @return
     */
    private JobDetail create(Class<? extends Job> c) {
        JobDetailFactoryBean d = new JobDetailFactoryBean();
        d.setDurability(true);
        d.setRequestsRecovery(true);
        d.setJobClass(c);
        d.setName(c.getSimpleName() + Quartz_Job_Suffix);
        d.setGroup(Quartz_Group_Name);
        d.afterPropertiesSet();
        JobDetail jd = d.getObject();
        //jd.getJobDataMap().put("key", 123);//如果想通过jobDataMap传递值，在这里添加
        return jd;
    }

    /**
     * 内部私有方法
     * @param t
     * @param cronExpression
     * @return
     * @throws ParseException
     */
    private CronTriggerImpl createTrigger(Class<? extends Job> t, String cronExpression) throws ParseException {
        CronTriggerFactoryBean c = new CronTriggerFactoryBean();
        c.setJobDetail(create(t));
        c.setCronExpression(cronExpression);
        c.setName(t.getSimpleName() + Quartz_Trigger_Suffix);
        c.setGroup(Quartz_Group_Name);
        c.afterPropertiesSet();
        return (CronTriggerImpl) c.getObject();
    }

}
