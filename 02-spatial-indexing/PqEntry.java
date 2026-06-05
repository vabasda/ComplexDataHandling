//EVANGELOS BASDAVANOS 4962

public class PqEntry{
	
	private double minDist;
	private int nodeId;
	private int isLeaf;
	private double x;
	private double y;


	public PqEntry(double minDist, int nodeId, int isLeaf, double x, double y){
		this.minDist = minDist;
		this.nodeId = nodeId;
		this.isLeaf = isLeaf;
		this.x = x;
		this.y = y;
	}

	public Double getMinDist(){
		return this.minDist;
	}

	public Integer getNodeId(){
		return this.nodeId;
	}

	public Integer getIsLeaf(){
		return this.isLeaf;
	}

	public Double getX(){
		return this.x;
	}

	public Double getY(){
		return this.y;
	}

}
