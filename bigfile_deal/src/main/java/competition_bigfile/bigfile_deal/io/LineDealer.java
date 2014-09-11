package competition_bigfile.bigfile_deal.io;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;


/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月10日
 */
public class LineDealer implements Runnable{
	
	private final BlockingQueue<String> sharedQueue;
	
	private final TTTT t;
	
	public LineDealer(BlockingQueue<String> sharedQueue, TTTT t){
		this.sharedQueue = sharedQueue;
		this.t = t;
	}
	
	public void run(){
		try{
			deal();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deal() throws Exception{
		long start = System.currentTimeMillis();
		Map<String, Integer> map = new HashMap<String, Integer>();
		while(t.i == 0 || !sharedQueue.isEmpty()){
			String line = sharedQueue.take();
			
			if(!line.contains(".com")){
				continue;
			}
			
			String temp1 = line.substring(line.indexOf("-") + 1, line.indexOf("[") - 1); 
			String temp2 = line.substring(line.indexOf("\""), line.indexOf("HTTP"));
			
			if(temp2.contains("/")){
				temp2 = temp2.substring(temp2.indexOf("/"));
			}
			
			
			String url = temp1 + temp2;
			if(url.contains("/do_not_delete/noc.gif") || url.contains("/70/70") || url.contains("/50/50")){
				continue;
			}
		
			if(url.contains("?")){
				url = url.substring(0, url.indexOf("?"));
			}
			if(map.keySet().contains(url)){
				int count = map.get(url).intValue() + 1;
				map.put(url, count);
			}
			else{
				map.put(url, new Integer(1));
			}
		}
		
		List<Map<String, Integer>> list = rank(map);
		FileWriter fw = new FileWriter("E:\\out.txt");
		
		for(Map<String, Integer> m : list){
			for(String key : m.keySet()){
				fw.append(key + "  =====》    " + m.get(key).toString() + "\r\n");
			}
		}
		
		fw.flush();
		fw.close();
		long end = System.currentTimeMillis();
		System.out.println("处理： " + (end - start));
	}
	
	private List<Map<String, Integer>> rank(Map<String, Integer> map){
		List<Map<String, Integer>> list = new ArrayList<Map<String,Integer>>();
		
		String temp = null;
		for(int i = 0; i < 20; i++){
			for(String key : map.keySet()){
				if(null == temp){
					temp = key;
					continue;
				}
				if(map.get(key).intValue() > map.get(temp).intValue()){
					temp = key;
				}
			}
			Map<String, Integer> m = new HashMap<String, Integer>();
			m.put(temp, map.get(temp));
			map.put(temp, new Integer(0));
			temp = null;
			list.add(m);
		}
		
	
		return list;
	}
}
