package competition_bigfile.bigfile_deal.deal;

public class ReadBigFile2 implements Runnable {
	
	private ReadBigFile readBigFile;
	
	public ReadBigFile2(ReadBigFile readBigFile){
		this.readBigFile = readBigFile;
	}

	public void run() {
		read();
	}

	public void read(){
		try{
			readBigFile.nioReadFile();
		}catch(Exception e){
			
		}
	}
}
