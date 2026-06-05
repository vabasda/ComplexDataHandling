//EVANGELOS BASDAVANOS 4962


public class MBR{

	private int nodeId;
	private double xmin;
	private double ymin;
	private double xmax;
	private double ymax;
	private double minDist;

	public MBR(int nodeId,double xmin, double ymin, double xmax, double ymax){
		this.xmin = xmin;
		this.ymin = ymin;
		this.xmax = xmax;
		this.ymax = ymax;
		this.nodeId = nodeId;
	}

	public MBR(double minDist){
		this.minDist= minDist;
	}
	
	public Double getMinX(){
		return this.xmin;
	}

	public Double getMinY(){
		return this.ymin;
	}

	public Double getMaxX(){
		return this.xmax;
	}

	public Double getMaxY(){
		return this.ymax;
	}

	public Integer getNodeId(){
		return this.nodeId;
	}
}
