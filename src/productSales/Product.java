package productSales;		

/**
 * Class Product contains the attributes necessary to create a Product object.
 * Its fields are code, description, cost, and sales price.
 * @author Alejandro Alberto Ramírez Vilchis & Abel Monsivais Badillo.
 *
 */
public class Product
{
	private int code = 0;
	private String description = "";
	private double cost = 0;
	private double salesPrice = 0;
	

	public Product()
	{
	}
	
	
	public Product(int code, String description, double cost)
	{
		this.code = code;
		this.description = description;
		this.cost = cost;
	}
	
	


	public double getSalesPrice()
	{
		return salesPrice;
	}


	public void setSalesPrice(double salesPrice)
	{
		this.salesPrice = salesPrice;
	}
	
	public int getCode()
	{
		return code;
	}
	public String getDescription()
	{
		return description;
	}
	public double getCost()
	{
		return cost;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public void setCost(double cost)
	{
		this.cost = cost;
	}

	@Override
	public String toString()
	{
		return "Product \ncode:  " + code + " \ndescription:  " + description + " \ncost:  " + cost + "\n";
	}
	
}
