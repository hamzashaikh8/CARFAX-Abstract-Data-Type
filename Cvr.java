package cvrADT;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.Collections;

@SuppressWarnings("deprecation")
public class Cvr {
	 
	Scanner scan = new Scanner(System.in);

	private int keyLength;
	private int threshold;
	private String vin;
	private NavigableMap<String,List<Date>> accidentsMap;
	private Sequence accidentsSequence=new Sequence();
	private Hashtable<String,Vehicle> a = new Hashtable<String,Vehicle>();
	private static boolean swapdone=false;
	
	public static void main(String[] args) throws Exception {
		// Data set 1
		Date d1 = new Date(100, 11, 21);
		Date d2 = new Date(); // Current date
		Date d3 = new Date(101, 1, 3);
		Date d4 = new Date(102, 1, 3);
		Date d5 = new Date(103, 1, 3);
		Date d6 = new Date(104, 1, 3);
		Date d7 = new Date(105, 1, 3);
		Date d8 = new Date(106, 1, 3);

		List<Date> dateList1 = new ArrayList<Date>();
		dateList1.add(d1);
		dateList1.add(d2);

		List<Date> dateList2 = new ArrayList<Date>();
		dateList2.add(d3);
		dateList2.add(d4);
		dateList2.add(d5);

		List<Date> dateList3 = new ArrayList<Date>();
		dateList3.add(d6);
		dateList3.add(d7);
		dateList3.add(d8);

		Cvr cvr1 = new Cvr(100);
		cvr1.setKeyLength(11);

		List<String> keys = cvr1.generate(3);
		System.out.println("Keys generated: " + keys);

		// Add accident dates to lists
		for (int i = 0; i < dateList1.size(); i++) {
			cvr1.add(keys.get(0), dateList1.get(i));
		}

		for (int i = 0; i < dateList2.size(); i++) {
			cvr1.add(keys.get(1), dateList2.get(i));
		}

		for (int i = 0; i < dateList3.size(); i++) {
			cvr1.add(keys.get(2), dateList3.get(i));
		}

		List<String> keysMade = cvr1.allKeys();
		System.out.println("Keys sorted: " + keysMade);

		for (int i = 0; i < keysMade.size(); i++) {
			System.out.println("Showing accident dates for: " + keysMade.get(i));
			System.out.println(cvr1.prevAccidents(keysMade.get(i)));
		}
		System.out.println(
				"*****************************************************************************************************************************");
		System.out.println(
				"*****************************************************************************************************************************");
		System.out.println(
				"*****************************************************************************************************************************");
		// sequences

		Cvr cvr2 = new Cvr(200);
		// testing out add method
		cvr2.add("Honda", d1);
		cvr2.add("Toyota", d2);
		cvr2.add("Honda", d4); // doesn't add honda back into sequence
		cvr2.add("Suzuki", d7);
		cvr2.add("Tesla", d4);
		// Testing out generate method
		cvr2.addListtoSeq(cvr2.generate(4));
		System.out.println("Accidents Sequence size: " + cvr2.getSequencesSize());
		// Testing out prevKey method
		System.out.println("Key before Suzuki:" + cvr2.prevKey("Suzuki"));
		// Testing out nextKey method
		System.out.println("Key after Suzuki:" + cvr2.nextKey("Suzuki"));
		// remove suzuki
		cvr2.remove("Suzuki");
		System.out.println("PRINTING OUT EVERYTHING IN SEQUENCE!!!!!!!!!!!");
		cvr2.printSequence();
		System.out.println("PRINTING OUT EVERYTHING IN NAVIGABLE MAP!!!!!!!!!!!");
		System.out.println("See this is empty");
		// Testing out prevAccidents method
		System.out.println("Previous accidents of Honda are: " + cvr2.prevAccidents("Honda"));
		cvr2.addListtoSeq(cvr2.generate(240));
		System.out.println("Accidents Sequence size: " + cvr2.getSequencesSize());
		System.out.println("PRINTING OUT EVERYTHING IN SEQUENCE!!!!!!!!!!!");
		cvr2.printSequence(); // should be empty
		System.out.println("PRINTING OUT EVERYTHING IN NAVIGABLE MAP!!!!!!!!!!!");
		cvr2.printNavigableMap();

	}
	
	public Cvr(int threshold) throws Exception{
		this.keyLength = 10;
		this.accidentsMap = null;
		setThreshold(threshold);
		this.accidentsMap = new TreeMap<String, List<Date>>();
	}
	/**
	 * 
	 * @param threshold to set number of elements of sequence data structure before transitioning to NavigableMap
	 * @throws Exception
	 */
	public void setThreshold(int threshold) throws Exception {
		while(threshold < 100 || threshold > 90000)
		{
			System.out.println("INVALID THRESHOLD! PLEASE ENTER NEW VALUE!");
			try {
			threshold = scan.nextInt();
			}catch(Exception e)
			{
				System.out.println("Cannot enter a non integer input! System is terminating.....");
				e.printStackTrace();
				System.exit(0);
			}
		}
		this.threshold = threshold;
	}
	
	/*
	 * @return will return the size of the sequence i.e. how many elements are stored in it
	 */
	public int getSequencesSize(){
		return this.accidentsSequence.size();
	}
	/*
	 * Will print all elements of the respective Sequence
	 */
	public void printSequence() {
			if (!this.isNavMap()) {
			this.accidentsSequence.printall();
			}
			else
				System.out.println("Cannot print Sequence, Data is in NavigableMap");
	}
	
	public void printNavigableMap() {
		if (this.isNavMap()) {
		List<String>all=this.allKeys();
		for (String s:all)
			System.out.println(s+" => "+this.accidentsMap.get(s));
		}else
			System.out.println("Cannot print NavigableMap, Data is in Sequence");
		
	}
	//This function will place all elements of a list into the sequence
	public void addListtoSeq(List<String> list) {
		for (String s:list)
		{
			accidentsSequence.add(s);
		}
	}
	
	public void setKeyLength(int keyLength) throws Exception {
		if(keyLength < 10 || keyLength > 17)
			throw new Exception("Key Length must be within range");
		this.keyLength = keyLength;
	}
	
	public int getKeyLength() {
		return this.keyLength;
	}
	
	public int getThreshold() {
		return this.threshold;
	}
	
	public String getVin() {
        return vin;
    }
	
	/**
	 * Time Complexity: O(n) 
	 *  Generates list of keys of sized n that aren't already contained yet
	 * @param n
	 * @return
	 */
	public List<String> generate(int n) {
		String vin="";
		List<String> vinList = new ArrayList<String>();
		
		while(n != 0) {
			vin = "";
			for (int j = 0; j < keyLength; j++) {
				int sign = new Random().nextInt(36); //return a random number designating letters and digits from 0 to 9
				if (sign < 10) //if random digit is between 0 and 9, then add digit to generated vin
					vin += sign;
				else
					vin = vin + (char)(sign+55); //add char of random generated letter
			}
			
			// If vin already used, restart loop and generate a new key
			if(vinList.contains(vin) || allKeys().contains(vin)) {
				continue;
			} else {
				vinList.add(vin);
				n--;
				continue;
			}
		}
		return vinList;
	}
	
	/*
	 * This function place all values of the Sequence onto the NavigableMap
	 */
	public void SeqToNav() {
		List<String>allkeys= this.accidentsSequence.allelements();
		for (String s:allkeys)
		{
			if (a.containsKey(s))
			{
				Vehicle v=a.get(s);
				Stack<Date>temp=v.getAccidents();
				List<Date>addedDates=new ArrayList<Date>();
				while (!temp.isEmpty()) 
				{
					addedDates.add(temp.pop());
				}
				this.accidentsMap.put(s, addedDates);
			}
			else
			{
				this.accidentsMap.put(s, null);
			}
		}
		//free unnecessary memory
		a.clear();
	}
	
	public boolean isNavMap() {
		if(accidentsSequence.size() <= this.threshold) {
			return false;
		} else {
			if (swapdone==false){
				swapdone=true;
				this.SeqToNav();
				System.out.println("******************Switched to Navigable map***********************");
			}
			return true;
		}
	}
	
	
	/**
	 * Returns all keys in lexographical order
	 * @return
	 */
	public List<String> allKeys() {
		if(!isNavMap()) {
			//Sequence logic
			return accidentsSequence.allelements();
		} else {
			Set<Entry<String, List<Date>>> entries = accidentsMap.entrySet();
			
			List<String> keys = new ArrayList<String>();
			 
			for(Entry<String, List<Date>> entry : entries) {
				keys.add(entry.getKey());
			}
			
			Collections.sort(keys);
			return keys;
		}
	}
	
	
	/**
	 * Time Complexity: Sequence Data Structure: O(n) / NavigableMap Data Structure: O(1)
	 * Adds VIN with date to sequence or hash map if not already contained
	 * Adds date if VIN is already contained in the map
	 * @param vinKey
	 * @param accidentDate
	 */
	public void add(String vinKey, Date accidentDate) {
		if(!isNavMap()) {
			//Sequence logic
			Vehicle dummy = new Vehicle(vinKey);
			if (!accidentsSequence.hasElement(vinKey) )
			{
				dummy.addAccidents(accidentDate);
				a.put(vinKey,dummy);
				accidentsSequence.add(vinKey);
				System.out.println("VIN:"+vinKey+" DATE:"+accidentDate+" has been added");
			}
			else {
				if (a.containsKey(vinKey) && accidentsSequence.hasElement(vinKey)) //add date to respective vehicle
				{	
					Vehicle active = a.get(vinKey); //returns respective Vehicle
					System.out.println(vinKey+" was already stored, now appending date: "+accidentDate+" to "+vinKey);
					active.addAccidents(accidentDate);
				}
			}
		} else {
			List<Date> dates = accidentsMap.get(vinKey);
			if(dates != null) {
				dates.add(accidentDate);
				accidentsMap.replace(vinKey, dates);
				System.out.println("VIN:"+vinKey+" DATE:"+accidentDate+" has been added");

			} else {
				//no car with that vin found, insert it
				List<Date> dateList = new ArrayList<Date>();
				dateList.add(accidentDate);
				System.out.println(vinKey+" was already stored, now appending date: "+accidentDate+" to "+vinKey);
				accidentsMap.put(vinKey, dateList );
			}
		}
	}
	
	/**
	 * Time Complexity: Sequence Data Structure: O(n) / NavigableMap Data Structure: O(1)
	 * Remove entry for given key
	 * @param vinKey
	 */
	public void remove(String vinKey) {
		if(!isNavMap()) {
			//Sequence logic
			if(a.containsKey(vinKey)) 
			{	
				accidentsSequence.remove(vinKey);
				a.remove(vinKey);
				System.out.println("Successfully removed :"+vinKey);
			}
			else 
			{ 
				System.out.println("Key does not exist");
			}
		} else {
			accidentsMap.remove(vinKey);
		}
	}
	
	/**
	 * Time Complexity: Sequence Data Structure: O(n) / NavigableMap Data Structure: O(1)
	 * Returns VIN of next entry
	 * @param vinKey
	 * @return
	 */
	public String nextKey(String vinKey) {
		if(!isNavMap()) {
			{ 
				List<String>allkeys=accidentsSequence.allelements();
				Collections.sort(allkeys); 
				int index= allkeys.indexOf(vinKey);
				if (index == -1 || index == allkeys.size()-1)
					return "VIN not found!";
				else
					return allkeys.get(index+1);
			}
		}	
		 else {
			return accidentsMap.higherKey(vinKey);
		}
}
	
	/**
	 * Time Complexity: Sequence Data Structure: O(n) / NavigableMap Data Structure: O(1)
	 * Returns VIN of previous entry
	 * @param vinKey
	 * @return
	 */
	public String prevKey(String vinKey) {
		if(!isNavMap()) {
			//Sequence logic
			{ 
				List<String>allkeys=accidentsSequence.allelements();
				Collections.sort(allkeys); 
				int index= allkeys.indexOf(vinKey);
				if (index == -1 || index == 0)
					return "VIN not found!";
				else
					return allkeys.get(index-1);
			}
			
		} else {
		return accidentsMap.lowerKey(vinKey);
	}
	}
	
	/**
	 * Returns dates sorted by most recent date to previous dates
	 * @param vinKey
	 * @return
	 */
	public List<Date> prevAccidents(String vinKey) {
		if(!isNavMap()) {
			//Sequence logic
			List<Date> dates = new ArrayList<Date>();
			if (a.containsKey(vinKey))
			{
				Stack<Date>temp=a.get(vinKey).getAccidents();
				while (!temp.isEmpty())
				{
					dates.add(temp.pop());
				}
				for (Date d:dates) { //add the popped Dates back into the vehicule through the add method
					this.add(vinKey, d); //should go to second case of add method as vinKey already exists in hashtable and sequence
				}
				Collections.sort(dates);
				Collections.reverse(dates);
				return dates;
				
			}
				return null;
			}
			else {
			List<Date> dates = accidentsMap.get(vinKey);
			Collections.sort(dates);
			Collections.reverse(dates);
			return dates;
		}
	}


	

}