import java.text.DecimalFormat;
import java.util.ArrayList;

public class Person extends Food implements Calculation <Person>, Comparable<Person> {

	private String name;
	private double moneyOwed;
	private double moneyPaid;
	private int numPerson;
	private double overpay;
	private double percentDebt;
	public static ArrayList<Person> overpaid = new ArrayList<Person>();
	public static ArrayList<Person> owe = new ArrayList<Person>();
	public ArrayList<String> oweString = new ArrayList<String>();
	
	Person() {
		name = "Unknown";
		moneyOwed = 0;
		moneyPaid = 0;
		overpay = 0;
		percentDebt = 0;
		numPerson++;
	}

	Person(String name) {
		this.name = name;
	}

	Person(String name, double paid){
		this.name = name;
		moneyPaid = paid;
	}

	Person(String name, String fName){

		this.name = name;
	}

	Person(String fName, String pName, double fPrice){
		super(fName, fPrice);
		name = pName;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setpercentDebt(double deb){
		percentDebt = deb;
	}

	public void setMoneyPaid(double pay){
		moneyPaid = pay;
	}

	public void setMoneyOwed(double deb){
		moneyOwed = deb;
	}

	public void setNumPerson(int a) {
		numPerson = a;
	}
	
	public void setOverpay(double overpay) {
		this.overpay = overpay;
	}
	

	public String getName() {
		return name;
	}

	public double getMoneyOwed() {
		return moneyOwed;
	}

	public int getNumPerson() {
		return numPerson;
	}

	public double getOverpay() {
		return overpay;
	}
	
	public double getMoneyPaid(){
		return moneyPaid;
	}

	public double getpercentDebt(){
		return percentDebt;
	}

	public void printSummary() {
		System.out.println("\n---BILL SUMMARY---");
		System.out.println("\n------------------");
	}
	
	@Override
    public double calculation (ArrayList <Person> amount, double tax) {
		double overpaySum = 0; //Keep track of total Overpay amount
		DecimalFormat df = new DecimalFormat("###.##"); //Format the value into 2 decimal places
		StringBuilder stringBuild; //String builder to quickly append string

		//Grouping overpaid and underpaid
    	for (int i= 0; i < amount.size(); i ++) {
			final double foodPrice = amount.get(i).getPrice() / amount.get(i).getNumPerson();

    		if (amount.get(i).getMoneyPaid() > foodPrice) {	
    			amount.get(i).setOverpay(amount.get(i).getMoneyPaid() - foodPrice);
    			
				overpaySum += amount.get(i).getOverpay();
				overpaid.add(amount.get(i));
			
    		}
    		else {
    			amount.get(i).setMoneyOwed(foodPrice - amount.get(i).getMoneyPaid());
    			owe.add(amount.get(i));
    		}

		}

		//Calculate weight of owe AND calculate money Owed to specific person, update owe arrayList
		 for(int j = 0; j < owe.size(); j++) {
			 percentDebt = owe.get(j).getMoneyOwed()/overpaySum;
			 
			 for(int k = 0; k < overpaid.size(); k++) {
				 owe.get(j).setMoneyOwed(percentDebt * overpaid.get(k).getOverpay() + (percentDebt * overpaid.get(k).getOverpay() * tax));
				 stringBuild = new StringBuilder(owe.get(j).getName() + " owes " + overpaid.get(k).getName() + ": $" + df.format(owe.get(j).getMoneyOwed()));

				 oweString.add(stringBuild.toString());
			 }
		 }
		
		
		return 0;
	}

	
	@Override //Comparable override
	public int compareTo(Person p){
		return name.compareToIgnoreCase(p.name);
	}

	@Override //Compare the name of food
	public boolean equals(Object p){
		if(p == null){
			return false;
		}else if(!(p instanceof Person)){
			return false;
		}else{
			Person person = (Person)p;
			return getFName().equals(person.getFName());
		}
	}

	@Override
	public String toString(){
		StringBuilder store = new StringBuilder();
		for(int i = 0; i < oweString.size(); i++){
			store.append(oweString.get(i) + "\n");
		}
		
		return store.toString();

	}


	
}