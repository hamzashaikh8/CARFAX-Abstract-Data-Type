package cvrADT;

import java.util.Date;
import java.util.Stack;

public class Vehicle {
	private String vin;
	// SimpleDateFormat dateForm = new SimpleDateFormat("MM/dd/Y");
	private Stack<Date> accidents = new Stack<Date>();

	public Vehicle() {
	}

	public Vehicle(String vin) {
		this.vin = vin;

	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVin() {
		return vin;
	}

	public void printAccidents() {
		Stack<Date> temp = new Stack<Date>();
		while (!accidents.isEmpty()) {
			temp.add(accidents.pop());
			System.out.println(temp.peek());
		}
		while (!temp.isEmpty()) {
			accidents.add(temp.pop());
		}
	}

	public void addAccidents(Date acc) {
		if (accidents.empty()) {
			accidents.push(acc);
		} else if (accidents.peek().before(acc)) {
			accidents.push(acc);

		} else {
			Date temp = accidents.pop();
			accidents.push(acc);
			accidents.push(temp);
		}
	}

	public Stack<Date> getAccidents() {
		return accidents;
	}
}