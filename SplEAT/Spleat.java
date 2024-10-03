import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.IOException;
import java.io.File;
import java.io.PrintWriter;

public class Spleat extends Person{
	public static void main(String[] args) {
		ArrayList<Person> personStore = new ArrayList<Person>();
		ArrayList<Person> organizedStore = new ArrayList<Person>();
		Person person = new Person();
		
		try{
			File log = new File("LogFile.txt");
			PrintWriter fout = new PrintWriter(log);

		File tax = new File("StateTax.txt");
		Scanner input = new Scanner(System.in);

		String [] states = new String[5];
		double [] taxrate = new double[5];
		// ArrayList<Integer> numOfPeople = new ArrayList<Integer>();

		//FIle IO

			Scanner filein = new Scanner(tax);
			for(int ii = 0; ii < 5; ii++) {
				states[ii] = filein.nextLine();
				taxrate[ii] = filein.nextDouble();
				
				if(filein.hasNextLine()) {
					filein.nextLine();
				}
			}
			filein.close();

		String pName = "";
		String fName = "";
		String eventName = "";
		double fPrice = 0.0;
		double moneyPaid = 0;
		double billTotal = 0.0;
		int foodCount = 0;
		int headCount = 0;
		String proceed = "";
		boolean validPrice = true;
		int counter = 0;
		int foodheadCount = 0; //Counter for specific food
		boolean once = true;
		int initialSize = 0;
		boolean duplicate;
		int stateNum = 0;
		int numPeople = 0;
		int temp2 = 0;
		double tempBill = 0;
		double totalMoney = 0;

		System.out.print("Welcome! Enter event name: ");
		eventName = input.nextLine();
		foodCount++;

		while (!proceed.equalsIgnoreCase("Y") && !proceed.equalsIgnoreCase("N")) {
			System.out.print("\nProceed to split " + eventName + " bill? (Y / N) ");
			proceed = input.nextLine();
		}

		proceed = "Y";
		foodCount++;
		headCount++;
		while (proceed.equalsIgnoreCase("Y")) {

			System.out.print("\nEnter Dish #" + (foodCount - 1) + " name: ");
			fName = input.nextLine();


			do {
				try {

					System.out.print("Enter food price: ");
					fPrice = input.nextDouble();
					billTotal += fPrice;
					validPrice = true;
		

					while (!(fPrice > 0.0)) {
						System.out.print("Please enter a valid food price: ");
						fPrice = input.nextDouble();
						validPrice = true;
					}

				} catch (InputMismatchException e) {
					System.out.println("Error! Please enter numbers only not text.\n");
					input.nextLine();
					validPrice = false;
					
				}

			} while (validPrice == false);
			System.out.println("------------------------- ");
			System.out.println("\nThis dish is shared with: ");
			input.nextLine();
			tempBill = billTotal;
			int j = 1;

			do {
				initialSize = organizedStore.size();
				proceed = "";
				duplicate = false;
				System.out.print("Enter person #" + j + " name: ");
				pName = input.nextLine();
				j++;

				Person temp = new Person(fName, pName, fPrice);
				personStore.add(temp);
				numPeople++;
				
				if(once) {
					organizedStore.add(temp);
					temp2++;
				}
				
				for(int m = 0; m < initialSize; m++) {
					if(temp.compareTo(organizedStore.get(m)) == 0){
						organizedStore.get(m).setPrice(organizedStore.get(m).getPrice() + fPrice);
						duplicate = true;
					}
				}

				if(duplicate != true && once == false) {
					organizedStore.add(temp);
					temp2++;
				}
				
				once = false;
				
				while (!proceed.equalsIgnoreCase("Y") && !proceed.equalsIgnoreCase("N")) {
					System.out.print("\nAdd another person? (Y / N) ");
					proceed = input.nextLine();
					System.out.println();
				}

				headCount++;

				if (proceed.equalsIgnoreCase("n")) {
					
					System.out.println("Dish # " + (foodCount - 1) + " is shared among: ");
					j = 1;
					for (int i = 0; foodheadCount < headCount - 1; foodheadCount++, i++) {
						personStore.get(foodheadCount).setNumPerson(numPeople);
						System.out.println((i + 1) + ". " + personStore.get(foodheadCount).getName());

					}
				}

				
			} while (proceed.equalsIgnoreCase("Y"));
			numPeople = 0;
			proceed = "";
	
			while (!proceed.equalsIgnoreCase("Y") && !proceed.equalsIgnoreCase("N")) {
				System.out.print("\nAdd another dish? (Y / N) ");
				proceed = input.nextLine();
			}
			foodCount++;

		}
		System.out.println();
		System.out.println("Total bill for this event: $" + billTotal);

	
		do {
			validPrice = false;

			while (counter < organizedStore.size()) {

				try {
					System.out.print("How much did " + organizedStore.get(counter).getName() + " pay(" + (tempBill -= moneyPaid) + "): ");
					moneyPaid = input.nextDouble();
					totalMoney += moneyPaid;
					while (!(moneyPaid > 0.0)) {
						System.out.print("Please enter a valid food price: ");
						moneyPaid = input.nextDouble();
						validPrice = true;
					}

					if (moneyPaid > 0) {
						organizedStore.get(counter).setMoneyPaid(moneyPaid);
						counter++;

					}

				} catch (InputMismatchException e) {
					System.out.println("Error! Please enter numbers only not text.\n");
					input.nextLine();
					validPrice = false;
				}

			}

		} while (validPrice == true);
		
		if(totalMoney < billTotal){
			System.out.println("\nPlease wash the dishes in the kitchen, thank you!");
			System.exit(1);
		}

		
		System.out.println("\nState Tax:");
		for(int i = 0; i < states.length; i++){
			System.out.println((i+1) + ": " + taxrate[i] + "(" + states[i] +")");
		}
		
		while(true){
			System.out.print("\nEnter the number for the state: ");
			try{
				stateNum = input.nextInt();
				input.nextLine();
				if(stateNum < 0 || stateNum > states.length){
					throw new IOException("1");
				}
				stateNum--;
				break;

			}catch(InputMismatchException e){
				System.out.println("Please enter number not text!\n");

			}catch(IOException g){
				System.out.println("Number is out the list!\n");
			}

		}

		
		person.calculation(organizedStore, taxrate[stateNum]);
		sortByDishPrice(organizedStore);
		

		System.out.println("\n---BILL SUMMARY---");
		System.out.printf("\nTotal cost of " + eventName + " is: $%.2f", billTotal);
		System.out.println();
		System.out.println("Number of people: " + temp2);
		System.out.println("\n" + person.toString());
		System.out.println("Most expensive dish: " + organizedStore.get(0).getFName() + " $" + organizedStore.get(0).getPrice());
		System.out.println(search(organizedStore));
		System.out.println("------------------");

		//Print to a log file
		fout.println("\n---BILL SUMMARY---");
		fout.printf("\nTotal cost of " + eventName + " is: $%.2f", billTotal);
		fout.println();
		fout.println("Number of people: " + temp2);
		fout.println("\n" + person.toString());
		fout.println(search(organizedStore));
		fout.println("Most expensive dish: " + organizedStore.get(0).getFName() + " $" + organizedStore.get(0).getPrice());
		fout.println("------------------");
		input.close();
		fout.close();
	}catch(IOException j) {
		System.out.println("File doesnt exist.");
	}

	
	}

	public static void sortByDishPrice(ArrayList<Person> p){
		for(int i = 0; i < p.size() - 1; i++){
			for(int k = 0; k < p.size() - i - 1; k++){
				if(p.get(k).getPrice() > p.get(k + 1).getPrice()){
					swap(p.get(k), p.get(k+1));
				}
			}
		}
	}

	public static void swap(Person a, Person b){
		Person temp;
		temp = a;
		a = b;
		b = temp;
	}

	public static String search(ArrayList<Person> a){
		double max;
		int maxIndex = 0;
		int i = 1;
		max = a.get(0).getPrice();
		
		for(; i < a.size() - 1; i++){
			if(a.get(i).getPrice() > max){
				max = a.get(i).getPrice();
				maxIndex = i;
			}
		}

		return a.get(maxIndex).getName() + " paid the most in this event: $" + a.get(maxIndex).getMoneyPaid() + "\n";
	}
}