package competition_bigfile.bigfile_deal.deal;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import competition_bigfile.bigfile_deal.beans.BigFile;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月3日
 */
public class ReadBigFile implements Runnable {
	
	private BigFile bigFile;
	private static String filePath = "E:\\access-demo.log";
	private static File file = new File(filePath);
	private final int buffer_size = 0x300000;
	
	public ReadBigFile(BigFile bigFile){
		this.bigFile = bigFile;
	}
	
	public void run(){
		
	}
	
	public void nioReadFile() throws Exception{
		MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		byte[] dst = new byte[buffer_size];
		long start = System.currentTimeMillis();
		
		for(int offset = 0; offset < inputBuffer.capacity(); offset += buffer_size){
			if(inputBuffer.capacity() - offset >= buffer_size){
				for(int i = 0; i < buffer_size; i++){
					dst[i] = inputBuffer.get(offset + 1);
				}
			}
			else{
				for(int i = 0; i < inputBuffer.capacity() - offset; i++){
					dst[i] = inputBuffer.get(offset + i);
				}
			}
			
			int length = (inputBuffer.capacity() % buffer_size == 0) ? buffer_size : inputBuffer.capacity() % buffer_size;
			System.out.println(new String(dst, 0, length));
		}
		
		long end = System.currentTimeMillis();
		System.out.println("花费的时间： " + (end - start) + "ms");
	}
	
}
