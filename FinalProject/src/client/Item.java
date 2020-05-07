package client;

public class Item {
	private String name;
	private double highestBid;
	private int highestName;
	private double highestPrice;
	
	public Item()
	{
		setHighestBid(0);
		setHighestName(-1);
		setHighestPrice(0);
	}
	
	public Item(String n, double b, int cn, double p)
	{
		name=n;
		highestBid = b;
		highestName = cn;
		highestPrice = p;
	}
	
	public String getName()
	{
		return name;
	}

	public double getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(double highestBid) {
		this.highestBid = highestBid;
	}

	public int getHighestName() {
		return highestName;
	}

	public void setHighestName(int highestName) {
		this.highestName = highestName;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	private void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}
	
	
	

}
