package cl.citymovil.route_pro.message_listener.domain;

public class DistanceTimeDataComplete {
	
	private long goingDistance;
	private long goingTime;
	private long commingDistance;
	private long commingTime;
	
	public DistanceTimeDataComplete( ){}
	
	public DistanceTimeDataComplete(long goingDistance, long goingTime,long commingDistance, long commingTime )
	{	
		this.goingDistance = goingDistance;
		this.goingTime = goingTime;
		this.commingDistance = commingDistance;
		this.commingTime = commingTime;
		
		
	}
	
	public long getGoingDistance() {
		return goingDistance;
	}
	public void setGoingDistance(long goingDistance) {
		this.goingDistance = goingDistance;
	}
	public long getGoingTime() {
		return goingTime;
	}
	public void setGoingTime(long goingTime) {
		this.goingTime = goingTime;
	}
	public long getCommingDistance() {
		return commingDistance;
	}
	public void setCommingDistance(long commingDistance) {
		this.commingDistance = commingDistance;
	}
	public long getCommingTime() {
		return commingTime;
	}
	public void setCommingTime(long commingTime) {
		this.commingTime = commingTime;
	}
	
	

}
