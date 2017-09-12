package com.appg.djTalk.common.batch.manager;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 2015.02.28 배치 처리해야 할 잡업을 수행한다. 유저정보 삭제처리 메세지결과 입력 처리
 * 
 * @author Administrator MMC 와 NODE 양 쪽에 모두 사용됨
 */
public class BatchJobProcessManager
{

	public BlockingQueue<BatchJob>	queue		= null; // DB 쓰기전에 사용 할 큐

	private ExecutorService			executor	= null;

	private int						nThread		= 0;

	public BatchJobProcessManager(int nThread)
	{
		queue = new LinkedBlockingQueue<BatchJob>();
		this.nThread = nThread;

		// 쓰레드 풀
		executor = Executors.newCachedThreadPool();

		init();
	}

	/**
	 * 배치잡 의뢰하기
	 * 
	 * @param job
	 */
	public void offerJob(BatchJob job)
	{
		queue.offer(job);
	}

	/**
	 * [MONITOR] 현재 대기 중인 배치잡의 갯수
	 * 
	 * @return
	 */
	public int getSizeOfJobWaited()
	{
		return queue.size();
	}

	/**
	 * [MONITOR] 쓰레드 갯수
	 * 
	 * @return
	 */
	public int getCountOfThread()
	{
		return nThread;
	}

	private void init(){
		System.out.println("[BATCH] thread start");

		for (int i = 0; i < nThread; i++){
			try{
				executor.execute(new BatchJobRunable(i, this));
			}
			catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void release(){
		while (getSizeOfJobWaited() > 0){
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}

		executor.shutdownNow();

		System.out.println("[BATCH] thread shutdown");
	}

}