package competition_bigfile.bigfile_deal.blockqueue;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.BlockingQueue;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月10日
 */
public class FileReader implements Runnable {

	private final BlockingQueue<String> sharedQueue;
	
	private String filePath = "E:\\access.29.log";
	
	private File file;
	
	private int buffer_size = 0x30000;
	
	private double position = 0;
	
	private double size = 1;
	
	private TTTT t;
	
	public FileReader(BlockingQueue<String> sharedQueue){
		this.sharedQueue = sharedQueue;
		file = new File(filePath);
	}
	
	public FileReader(BlockingQueue<String> sharedQueue, String filePath, int buffer_size, double position, double size){
		this.sharedQueue = sharedQueue;
		this.filePath = filePath;
		this.buffer_size = buffer_size;
		this.position = position;
		this.size = size;
		file = new File(filePath);
	}
	
	public FileReader(BlockingQueue<String> sharedQueue, double position, double size, TTTT t){
		this.sharedQueue = sharedQueue;
		this.position = position;
		this.size = size;
		file = new File(filePath);
		this.t = t;
	}
	
	public void run() {
		try{
			nioReadFile();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	public void nioReadFile() throws Exception{
		MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, new Double(file.length() * position).longValue(), new Double(file.length() * size).longValue());
		byte[] dst = new byte[buffer_size];
		long start = System.currentTimeMillis();
		System.out.println(position + "  " + size + " " + new Double(file.length() * position).longValue() + "   " + new Double(file.length() * size).longValue() + "   " + inputBuffer.capacity());
		for(int offset = 0; offset < inputBuffer.capacity(); offset += buffer_size){
			if(inputBuffer.capacity() - offset >= buffer_size){
				for(int i = 0; i < buffer_size; i++){
					dst[i] = inputBuffer.get(offset + i);
				}
			}
			else{
				for(int i = 0; i < inputBuffer.capacity() - offset; i++){
					dst[i] = inputBuffer.get(offset + i);		
				}
			}
			
			int length = (offset % buffer_size == 0) ? buffer_size : inputBuffer.capacity() % buffer_size;
			sharedQueue.put(new String(dst, 0, length));
		}
		t.add();
		long end = System.currentTimeMillis();
		System.out.println(FileReader.class.getName() + "  position: " + position + "  花费时间： " + (end - start));
	}

}
