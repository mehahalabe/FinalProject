package server;

public class Item {
	private String name;
	private double highestBid;
	private String description;
	private String highestName;
	private double highestPrice;
	
	public Item()
	{
		setHighestBid(0);
		setHighestName("");
		setDescription("");
		setHighestPrice(0);
	}
	
	public Item(String n, double b, String cn, double p, String d)
	{
		name=n;
		highestBid = b;
		highestName = cn;
		highestPrice = p;
		description = d;
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

	public String getHighestName() {
		return highestName;
	}

	public void setHighestName(String highestName) {
		this.highestName = highestName;
	}

	public double getHighestPrice() {
		return highestPrice;
	}

	private void setHighestPrice(double highestPrice) {
		this.highestPrice = highestPrice;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	

}
