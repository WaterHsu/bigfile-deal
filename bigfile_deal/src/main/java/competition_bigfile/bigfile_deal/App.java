package competition_bigfile.bigfile_deal;

import competition_bigfile.bigfile_deal.beans.BigFile;
import competition_bigfile.bigfile_deal.deal.DealContent;
import competition_bigfile.bigfile_deal.deal.ReadBigFile;
import competition_bigfile.bigfile_deal.deal.ReadBigFile2;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        BigFile bigFile = new BigFile();
        ReadBigFile readBigFile = new ReadBigFile(bigFile);
        
        DealContent dealContent = new DealContent(readBigFile);
        ReadBigFile2 readBigFile2 = new ReadBigFile2(readBigFile);
        
        new Thread(readBigFile2).start();
        new Thread(dealContent).start();
    //	String str = "afdafhttp://djldjlfalsd/adfjs/fd?fj?ff";
    	//System.out.println(str.substring(str.indexOf("http://")).substring(0, str.substring(str.indexOf("http://")).indexOf("?")));
    }
}
