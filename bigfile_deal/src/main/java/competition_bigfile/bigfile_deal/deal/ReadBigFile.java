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
public class ReadBigFile {
	
	private BigFile bigFile;
	private static String filePath = "E:\\access-demo.log";
	private static File file = new File(filePath);
	private final int buffer_size = 0x300000;
	
	public ReadBigFile(BigFile bigFile){
		this.bigFile = bigFile;
	}
	
	public synchronized void dealContent() throws Exception{
		//String preLast = bigFile.getQueue().element().split("\r\n")[bigFile.getQueue().element().split("\r\n").length - 1];
		String str;
		int i = 0;
		while((str = bigFile.getQueue().poll()) != null){
			i += str.split("\r\n").length;
			
			System.out.println("行数: " + str.split("\r\n").length);
			System.out.println(str.split("\r\n")[1]);
		}
		System.out.println("总行数: " + i);
	}
	
	private void getURLNum(String str){
		String subStr = str.substring(str.indexOf("http://"));
		subStr = subStr.substring(0, subStr.indexOf("?"));
		
	}
	
	public synchronized void nioReadFile() throws Exception{
		MappedByteBuffer inputBuffer = new RandomAccessFile(file, "r").getChannel().map(FileChannel.MapMode.READ_ONLY, file.length() * 9 / 10, file.length() / 10);
		byte[] dst = new byte[buffer_size];
		long start = System.currentTimeMillis();
		
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
		//	System.out.println("NNNN");
			
			int length = (offset % buffer_size == 0) ? buffer_size : inputBuffer.capacity() % buffer_size;
			//bigFile.getQueue().add(new String(dst, 0, length));
			bigFile.getQueue().offer(new String(dst, 0, length));
			//	System.out.println(length);
		//	System.out.println(new String(dst, 0, length));
		}
		
		long end = System.currentTimeMillis();
		System.out.println("ggg: " + inputBuffer.capacity() + "   " + inputBuffer.capacity() % buffer_size);
	/*	String str;
		int i = 0;
		while((str = bigFile.getQueue().poll()) != null){
			System.out.println(i++);
			System.out.println(str);
		}
		System.out.println("花费的时间： " + (end - start) + "ms");*/
	}
	
	public static void main(String args[]){
	/*	BigFile bigFile = new BigFile();
		ReadBigFile readBigFile = new ReadBigFile(bigFile);
		DealContent dealContent = new DealContent(bigFile);
		System.out.println("======读取打印大文件开始======");
		new Thread(readBigFile).start();
		new Thread(dealContent).start();
		System.out.println("======读取打印大文件结束======");*/
	}
}
