

public abstract class Food {
	private double price;
	private String fName;
    private int personAte;


	public Food() {
		price = 0.0;
		fName = " ";
	}

	public Food(String name, double fPrice){
		this.fName = name;
		price = fPrice;
	}

	public double getPrice() {
		return price;
	}
	
	public int getPersonAte(){
		return personAte;
	}
	
	public String getFName() {
		return fName;
	}
	
	public void setPrice(double p) {
		price = p;
	}
	

	public void setFName(String s) {
		fName = s;
	}
	
	public void incrementPersonAte(){
		personAte++;
	}

}