//EVANGELOS BASDAVANOS 4962

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Node{
	
	private int nodeId;
	private int capacity;
	private int isLeaf;
	private Point[] point;
	private MBR[] mbr;


	public Node(int nodeId,int capacity,int isLeaf, Point[] points){
		this.nodeId = nodeId;
		this.point = points;
		this.capacity = capacity;
		this.isLeaf = isLeaf;
	}

	public Node(int nodeId,int capacity,int isLeaf, MBR[] mbrs){
		this.nodeId = nodeId;
		this.mbr = mbrs;
		this.capacity = capacity;
		this.isLeaf = isLeaf;
	}


	public Integer getNodeId(){
		return this.nodeId;
	}


	public Integer getCapacity(){
		return this.capacity;
	}

	public Integer getIsLeaf(){
		return this.isLeaf;
	}

	public String getPointsString(){
	StringBuilder sb = new StringBuilder();
    sb.append("(");
    for (Point point : this.point) {
        int recordId = point.getRecordId();
        double x = point.getX();
        double y = point.getY();
        sb.append("(").append(recordId).append(", [").append(x).append(", ").append(y).append("]), ");
    }
    if (this.point.length > 0) {
        sb.setLength(sb.length() - 2);
    }
    sb.append(")");
    return sb.toString();
	}

	public String getMBRSString(){
	StringBuilder sb = new StringBuilder();
    sb.append("(");
    for (MBR mbr : this.mbr) {
    	if (mbr != null && mbr.getNodeId() != null){
	        int nodeId = mbr.getNodeId();
	        double minX = mbr.getMinX();
	        double minY = mbr.getMinY();
	        double maxX = mbr.getMaxX();
	        double maxY = mbr.getMaxY();
	        sb.append("(").append(nodeId).append(", [").append(minX).append(", ").append(minY).append(", ").append(maxX).append(", ").append(maxY).append("]), ");
	       }
    }
    if (this.mbr.length > 0) {
        sb.setLength(sb.length() - 2);
    }
    sb.append(")");
    return sb.toString();
	}

	public Point[] getPoints(){
		return this.point;
	}

	public MBR[] getMBRS(){
		return this.mbr;
	}

}
