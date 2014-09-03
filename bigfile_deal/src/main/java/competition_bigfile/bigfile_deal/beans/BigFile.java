package competition_bigfile.bigfile_deal.beans;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author WaterHsu@xiu8.com
 * @version 2014年9月1日
 */
public class BigFile {

	private Queue<String> queue = new LinkedList<String>();
	private boolean finished = false;
	
	public Queue<String> getQueue() {
		return queue;
	}
	public void setQueue(Queue<String> queue) {
		this.queue = queue;
	}
	public boolean isFinished() {
		return finished;
	}
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
}
