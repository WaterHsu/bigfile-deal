package competition_bigfile.bigfile_deal.blockqueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import competition_bigfile.bigfile_deal.io.TTTT;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月10日
 */
public class DealTest {
	
	public static void main(String args[]){
		BlockingQueue<String> sharedQueue = new LinkedBlockingDeque<String>();
	
		TTTT t = new TTTT();
		for(int i = 0; i < 10; i++){
			new Thread(new FileReader(sharedQueue, (1.0 / 10) * i, 1.0 / 10, t)).start();
		}
		
		for(int i = 0; i < 1; i++){
			new Thread(new UrlFinder(sharedQueue, i, t)).start();
		}
	}
	
/*	public static void main(String args[]){
		String ss = "sajd\r\nlsdj\r\nieoeajp\r\n";
		System.out.println(ss.length());
		System.out.println(ss.indexOf("\r\n"));
		System.out.println(ss.lastIndexOf("\r\n"));
		String[] temp = ss.split("\r\n");
		
		for(String str : temp){
			System.out.println(str);
		}
		System.out.println(ss.endsWith("\r\fn"));
	}*/
}
