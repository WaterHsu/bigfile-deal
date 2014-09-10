package competition_bigfile.bigfile_deal.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import competition_bigfile.bigfile_deal.blockqueue.TTTT;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月10日
 */
public class LargeFileReader implements Runnable {
	
	private final BlockingQueue<String> sharedQueue;
	
	private final TTTT t;
	
	public LargeFileReader(BlockingQueue<String> sharedQueue, TTTT t){
		this.sharedQueue = sharedQueue;
		this.t = t;
	} 
	
	public void run(){
		try{
			readFile();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void readFile(){
		try{
			long start = System.currentTimeMillis();
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("E:\\access.29.log")));
			BufferedReader in = new BufferedReader(new InputStreamReader(bis), 10 * 1024 * 1024);
			
			while(in.ready()){
				String line = in.readLine();
				sharedQueue.put(line);
			}
			in.close();
			long end = System.currentTimeMillis();
			t.add();
			System.out.println(end - start);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public static void main(String args[]){
		BlockingQueue<String> sharedQueue = new LinkedBlockingQueue<String>();
		TTTT t = new TTTT();
		new Thread(new LargeFileReader(sharedQueue, t)).start();
		new Thread(new LineDealer(sharedQueue, t)).start();
	}
}
