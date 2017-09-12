package com.appg.djTalk.common.batch.manager;

public class BatchJobRunable implements Runnable
{

	private BatchJobProcessManager	mgr		= null;
	private int						tnum	= -1;

	public BatchJobRunable(int tnum, BatchJobProcessManager mgr) throws Exception
	{
		this.mgr = mgr;
		this.tnum = tnum;
	}

	@Override
	public void run()
	{

		boolean running = true;

		// 쓰레드 대기 루프
		while (running)
		{
			try
			{
				BatchJob job = mgr.queue.take();
				
				System.out.println("Act[" + tnum + "]");

				Object data = (Object) job.getData();

				Object result = job.getExecute().execute(data);

				job.getExecute().postExecute(result);

			}
			catch (InterruptedException e)
			{
				running = false;
				System.out.println("No[" + tnum + "] Thread shutdown");

			}
			catch (Exception e)
			{
				System.out.println("[CRITIAL] exceptions raise.");
				e.printStackTrace();
			}
		}

	}

}