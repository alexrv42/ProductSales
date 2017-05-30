package productSales;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;


/**
 * The class ProductSales contains an application that stores the data 
 * of product sales to a text file and then copies them to another text
 *  file that now includes the sales price (to the public).
 *  
 * @author Alejandro Alberto Ramírez Vilchis & Abel Monsivais Badillo.
 * @version 1.0.
 */
public class ProductSales
{
	Product[] products;
	Product mostEconomicalProduct;

	public static void main(String[] args)
	{
		ProductSales session = new ProductSales();
		
		do
		{
			int status = JOptionPane.showConfirmDialog(null, "Do you want to create a new file? \n(Click no to use the existing one)");
			
			if (status == JOptionPane.YES_OPTION)
			{
				try
				{
					session.write();
					session.read();
					break;
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
			}
			else if (status == JOptionPane.NO_OPTION)//if the user says no, just read the existing file
			{
				try
				{
					session.read();
					break;
				}
				catch (IOException e)
				{
					JOptionPane.showMessageDialog(null, "File not found");
				}
			}
			else
			{
				System.exit(0);
			} 
		} while (true);
	}
	
	
	/**
	 * This method asks the user for the attributes of each of
	 *  the product objects that then are written in the text file Products.  
	 *  
	 * @throws FileNotFoundException
	 */
	public void write() throws FileNotFoundException
	{
		File file = new File("./Products.txt");
		FileOutputStream outStream = new FileOutputStream(file);
		PrintWriter writer = new PrintWriter(outStream);
		
		
		String sizeString = null;
		int size;
		
		do
		{
			try
			{
				sizeString = JOptionPane.showInputDialog("How many products are you registering?");
				
				if (sizeString == null)
				{
					System.exit(0);
				}
				
				size = Integer.parseInt(sizeString);
				break;
			}
			catch (Exception e)
			{
				JOptionPane.showMessageDialog(null, "That is not a valid entry, please try again");
			} 
		} while (true);
		
		
		
		
		products = new Product[size];
		
		for (int i = 0; i < products.length; i++)
		{
			int code;
			
			
			do //this coding pattern is just to handle the showInputDialog exceptions that the method may throw
			{
				try
				{
					String codeString = JOptionPane.showInputDialog("What is the code for the " + (i + 1) + "° product?");
					if (codeString == null)
					{
						System.exit(0);
					}
					code = Integer.parseInt(codeString);
					break;
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "That is not a valid entry, please try again");
				}
				
			} while (true);
			
			
			
			String description = JOptionPane.showInputDialog("What is the description for the " + (i+1) + "° product?");
			
			
			
			double cost;
			
			do
			{
				try
				{
					String costString = JOptionPane
							.showInputDialog("What is the cost for the " + (i + 1) + "° product?");
					if (costString == null)
					{
						System.exit(0);
					}
					cost = Double.parseDouble(costString);
					break;
				}
				catch (Exception e)
				{
					JOptionPane.showMessageDialog(null, "That is not a valid entry, please try again");
				} 
			} while (true);
			
			
			
			products[i] = new Product(code, description, cost);
			
			
			writer.println(products[i].toString()); //write each element to the Products file
		}
		
		
		//this is to calculate the most economical product
		mostEconomicalProduct = products[0];
		for (int j = 0; j < products.length; j++)
		{
			if (products[j].getCost() < mostEconomicalProduct.getCost())
			{

				mostEconomicalProduct = products[j];
			}
		}
		
		writer.close();
		
	}
	
	
	
	
	
	public void read() throws IOException
	{
		File file = new File("./Products.txt"); //open the file Products
		FileReader inStream = new FileReader(file);
		BufferedReader reader = new BufferedReader(inStream);
		
		
		File file2 = new File("./SalesList.txt"); //create and open the file SalesList
		FileOutputStream outStream = new FileOutputStream(file2);
		PrintWriter writerFile2 = new PrintWriter(outStream);

		
		
		Product[] products = new Product[10];
		
		for (int i = 0; i < products.length; i++) //this is just to initialize the Product objects
		{
			products[i] = new Product();
		}
		
		

		
		int index = 0;
		
		
		//this block is to "reconstruct" the products array by reading 
		//the Products file and then writing them to SalesProduct.
		while (reader.ready())
		{
			
			writerFile2.println(reader.readLine());//print the "Product" title line, indicating that it is a Product object 
			
			
			
			String codeLine = reader.readLine();
			writerFile2.println(codeLine);//print the code line
			products[index].setCode(Integer.parseInt(codeLine.substring(7, codeLine.length() - 1)));
			
			
			
			String descriptionLine = reader.readLine();
			writerFile2.println(descriptionLine);//print the description line
			products[index].setDescription(descriptionLine.substring(14));
			
			
			
			String costLine = reader.readLine();//print the cost line 
			products[index].setCost(Double.parseDouble(costLine.substring(7))); //jump cost, we want the sales price
			
			
			
			
			products[index].setSalesPrice(products[index].getCost() + (products[index].getCost() * .5) );
			writerFile2.println("sales price: " + products[index].getSalesPrice());
			
			
			
			writerFile2.println("tax: " + products[index].getSalesPrice() * .16);
			
			
			
			writerFile2.println(reader.readLine());//empty line
			
			
			products[index] = new Product(products[index].getCode(), products[index].getDescription(), products[index].getCost());			
			
			
			
			index++;
		}
		
		//This block of code repeats because the user may have 
		// chosen not to write the file, thus preventing the
		// most economical cost to be calculated. 
		mostEconomicalProduct = products[0];
		
		for (int j = 0; j < index; j++)
		{
			if (products[j].getCost() < mostEconomicalProduct.getCost())
			{
				mostEconomicalProduct = products[j];
			}
		}
		
		
		JOptionPane.showMessageDialog(null,"Most economical product:\n"+ mostEconomicalProduct.toString());
		
		writerFile2.close();
		reader.close();
	}
}