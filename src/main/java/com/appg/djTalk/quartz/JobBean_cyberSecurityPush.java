package com.appg.djTalk.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.appg.djTalk.common.constants.Constants;
import com.appg.djTalk.common.util.DateUtil;
import com.appg.djTalk.common.util.FileLogUtil;
import com.appg.djTalk.service.SvcQuartz;

public class JobBean_cyberSecurityPush extends QuartzJobBean{
	@Override
	protected void executeInternal(JobExecutionContext ecx) throws JobExecutionException{
//		try{
//			ApplicationContext ctx = (ApplicationContext) ecx.getJobDetail().getJobDataMap().get("applicationContext");
//			SvcQuartz svcQuartz = (SvcQuartz) ctx.getBean(SvcQuartz.class);
//			System.out.println("QUARTZ - Running");
//			
//			FileLogUtil.writeFileLog(Constants.LOG_PATH_BASE, "quartz", "JobBean_syncHitChatData", "Quartz has started to run[Cyber] - " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss"));
//
//			svcQuartz.cyberSecPush();
//
//			FileLogUtil.writeFileLog(Constants.LOG_PATH_BASE, "quartz", "JobBean_syncHitChatData", "Quartz has done normally[Cyber] - " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss"));
//		}
//		catch (Exception e){
//			FileLogUtil.writeFileLog(Constants.LOG_PATH_BASE, "quartz", "JobBean_syncHitChatData", "An error Occured[Cyber] - " + DateUtil.getString(DateUtil.getDate(), "yyyy-MM-dd HH:mm:ss") + " \n StackTrace >> \n" + e);
//			e.printStackTrace();
//		}

	}
}