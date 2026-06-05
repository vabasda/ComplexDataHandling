//EVANGELOS BASDAVANOS 4962

public class Point{
	
	private int recordId;
	private double x;
	private double y;

	public Point(int recordId,double x, double y){
		this.recordId = recordId;
		this.x = x;
		this.y = y;
	}

	public int getRecordId(){
		return this.recordId;
	}

	public Double getX(){
		return this.x;
	}

	public Double getY(){
		return this.y;
	}
}