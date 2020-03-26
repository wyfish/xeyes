package com.xingeyes.boot.scheduler.task.impl;

import com.xingeyes.boot.scheduler.task.XJobService;
import com.xingeyes.boot.scheduler.task.XSchedulerJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 每一个XSchedulerJobService的实现类代表了Xeyes Boot平台的一个定时调度的任务
 */
@Service
public class XSchedulerJobServiceImpl implements XSchedulerJobService {

    @Autowired
    private XJobService xJobService;

    @Override
    public void executeTask() {
        //TODO: 增加实际的实际逻辑
        xJobService.executeTask();
    }
}
