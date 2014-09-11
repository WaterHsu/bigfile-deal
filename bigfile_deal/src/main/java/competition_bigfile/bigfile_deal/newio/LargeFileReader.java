
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class LargeFileReader implements Runnable {
	
	private final BlockingQueue<String> sharedQueue;
	
	private final TTTT t;
	
	private String fileName = "E:\\access.29.log";
	
	public LargeFileReader(BlockingQueue<String> sharedQueue, TTTT t, String fileName){
		this.sharedQueue = sharedQueue;
		this.t = t;
		this.fileName = fileName;
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
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(this.fileName)));
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
		if(args.length > 0){
			System.out.println(args[0]);
		}
	//	System.out.println(args[1]);
		TTTT t = new TTTT();
		new Thread(new LargeFileReader(sharedQueue, t, args.length == 0 ? "E://access.29.log" : args[0])).start();
		new Thread(new LineDealer(sharedQueue, t)).start();
	}
}
