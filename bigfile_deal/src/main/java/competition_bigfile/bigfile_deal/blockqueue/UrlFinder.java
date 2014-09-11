package competition_bigfile.bigfile_deal.blockqueue;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.BlockingQueue;

import competition_bigfile.bigfile_deal.io.TTTT;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月10日
 */
public class UrlFinder implements Runnable {

	private final BlockingQueue<String> sharedQueue;
	
	private int i;
	
	private TTTT t;
	
	public UrlFinder(BlockingQueue<String> sharedQueue, int i, TTTT t){
		this.sharedQueue = sharedQueue;
		this.i = i;
		this.t = t;
	}
	
	public void run() {
		try{
			deal();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deal() throws Exception{
	//	long start = System.currentTimeMillis();
		FileOutputStream out = new FileOutputStream(new File("E:/test.txt"));
		while(true){
		//	System.out.println("Deal: " + sharedQueue.take());
		//	out.write(("\r\n" + "线程" + i + " ；" + sharedQueue.take()).getBytes());
			if(t.i == 10 && sharedQueue.take() == null){
				break;
			}
			//System.out.println(t.i);
		}
		
		
		//System.out.println(UrlFinder.class.getName() + " deal   " + " 花费时间: " + (end - start));
	}

}
