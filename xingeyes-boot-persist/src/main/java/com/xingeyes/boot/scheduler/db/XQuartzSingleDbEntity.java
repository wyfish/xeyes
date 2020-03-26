package com.xingeyes.boot.scheduler.db;

import lombok.Data;

/**
 * 该实体代表Quartz 定时任务表中的一行数据
 *
 */
@Data
public class XQuartzSingleDbEntity {

    private Integer id;
    private String cron;  //cron job 的规则表达式
}
