package competition_bigfile.bigfile_deal.deal;

import competition_bigfile.bigfile_deal.beans.BigFile;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月3日
 */
public class DealContent implements Runnable {
	
	private ReadBigFile readBigFile;
	
	public DealContent(ReadBigFile readBigFile){
		this.readBigFile = readBigFile;
	}

	public void run() {
		deal();
	}
	
	public void deal(){
		try{
			readBigFile.dealContent();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
