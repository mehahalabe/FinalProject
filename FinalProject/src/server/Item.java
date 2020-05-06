package server;

public class Item {
	private String name;
	private int highestBid;
	private int highestName;
	private int highestPrice;
	
	public Item()
	{
		setHighestBid(0);
		setHighestName(-1);
		setHighestPrice(0);
	}
	
	public Item(String n, int b, int cn, int p)
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

	public int getHighestBid() {
		return highestBid;
	}

	public void setHighestBid(int highestBid) {
		this.highestBid = highestBid;
	}

	public int getHighestName() {
		return highestName;
	}

	public void setHighestName(int highestName) {
		this.highestName = highestName;
	}

	public int getHighestPrice() {
		return highestPrice;
	}

	private void setHighestPrice(int highestPrice) {
		this.highestPrice = highestPrice;
	}
	
	
	

}
