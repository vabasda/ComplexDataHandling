//EVANGELOS BASDAVANOS 4962

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.time.Instant;
import java.time.Duration;

public class exA{

	private static Double T;
	private boolean flag = true;
	private Double pFemaleMax =  Double.MIN_VALUE;
	private Double pMaleMax =  Double.MIN_VALUE;
	private Double pMaleCur = 0.0;
	private Double pFemaleCur = 0.0;
	private int counterMale = 0;
	private int counterFemale = 0;



	public ArrayList<String> readFile(BufferedReader brMale,BufferedReader brFemale) throws IOException{
		ArrayList<String> valuesForHashtables = new ArrayList<>();
		
		if (flag == true){
	        String line = brMale.readLine();
	        if (line != null) {
	        	String[] elements = line.split(",");
	        	while (elements[8].startsWith(" Married") || Integer.parseInt(elements[1].trim()) < 18){
	        		if((line = brMale.readLine()) != null){
	        			elements = line.split(",");
	        		}
	        		else{
	        			return null;
	        		}
	            }
	           	valuesForHashtables.add(elements[0]);
	           	valuesForHashtables.add(elements[1]);
	           	valuesForHashtables.add(elements[8]);
	           	valuesForHashtables.add(elements[25]);
	           	valuesForHashtables.add("Male");
	           	flag = false;
	        }
	    }
		else{
	        String line = brFemale.readLine();
	        if (line != null) {
	        	String[] elements = line.split(",");
	        	while (elements[8].startsWith(" Married")|| Integer.parseInt(elements[1].trim()) < 18){
	        		if((line = brFemale.readLine()) != null){
	        			elements = line.split(",");
	        		}
	        		else{
	        			return null;
	        		}
	            }
	           	valuesForHashtables.add(elements[0]);
	           	valuesForHashtables.add(elements[1]);
	           	valuesForHashtables.add(elements[8]);
	           	valuesForHashtables.add(elements[25]);
	           	valuesForHashtables.add("Female");
	           	
	           	flag = true;
	        }
		}
		return valuesForHashtables;
	}

	public Double findT(Double maleWeight, Double femaleWeight){

		return 0.5*(maleWeight) + 0.5*(femaleWeight);
	}

	public void findTopCouple(HashMap<String, ArrayList<ArrayList<String>>> hashMapMale, HashMap<String, ArrayList<ArrayList<String>>> hashMapFemale,BufferedReader brMale,BufferedReader brFemale,PriorityQueue<ArrayList<String>> priorityQueue ) throws IOException{
		ArrayList<String> data = readFile(brMale,brFemale);
		if(data != null && !data.isEmpty()){
			ArrayList<ArrayList<String>> joins = new ArrayList<>();
			if (data.get(4).equals("Male") ){
				counterMale++;
				updateHashmaps(data,hashMapMale);
				this.pMaleCur = Double.parseDouble(data.get(3));
				this.T = Math.max(findT(this.pMaleMax,this.pFemaleCur), findT(this.pMaleCur,this.pFemaleMax));
				if (hashMapFemale.containsKey(data.get(1).trim())){
					ArrayList<ArrayList<String>> matches = hashMapFemale.get((data.get(1).trim()));
					for (ArrayList<String> match: matches){
						ArrayList<String> x = new ArrayList<>();
						x.add(String.valueOf(Double.parseDouble(data.get(3).trim()) + Double.parseDouble(match.get(3).trim())));
						x.addAll(data);
						x.addAll(match);
						joins.add(x);
					}
				}
			}
			if (data.get(4).equals("Female") ){
				counterFemale++;
				updateHashmaps(data,hashMapFemale);
				this.pFemaleCur = Double.parseDouble(data.get(3));
				this.T = Math.max(findT(this.pMaleMax,this.pFemaleCur), findT(this.pMaleCur,this.pFemaleMax));
				if(hashMapMale.containsKey(data.get(1).trim())){
					ArrayList<ArrayList<String>> matches = hashMapMale.get((data.get(1).trim()));
					for (ArrayList<String> match: matches){
						ArrayList<String> x = new ArrayList<>();
						x.add(String.valueOf(Double.parseDouble(data.get(3).trim()) + Double.parseDouble(match.get(3).trim())));
						x.addAll(data);
						x.addAll(match);
						joins.add(x);
					}
				}
			}
			for(ArrayList<String> join :joins){
				priorityQueue.offer(join);
			}
		}
		else{
			System.out.println("No that many couples available.");
			System.exit(0);
		}
	}

	public void updateHashmaps(ArrayList<String> information,HashMap<String, ArrayList<ArrayList<String>>> hashMap){

		if (information.get(4).equals("Male") && Double.parseDouble(information.get(3))>this.pMaleMax){
			this.pMaleMax = Double.parseDouble(information.get(3));
		}

		if (information.get(4).equals("Female") && Double.parseDouble(information.get(3))>this.pFemaleMax){
			this.pFemaleMax = Double.parseDouble(information.get(3));
		}

		String age = information.get(1).trim();
		
		if(hashMap.get(age)!= null){
			ArrayList<ArrayList<String>> infoDone = hashMap.get(age);
			infoDone.add(information);
			hashMap.put(age,infoDone);
		}
		else{
			ArrayList<ArrayList<String>> infoDone = new ArrayList<>();
			infoDone.add(information);
			hashMap.put(age,infoDone);
		}
	}

	public static void main(String[] args) throws IOException{
		Instant start = Instant.now();

		int k = Integer.parseInt(args[0]);
		BufferedReader brFemale = new BufferedReader(new FileReader("females_sorted"));
		BufferedReader brMale = new BufferedReader(new FileReader("males_sorted"));	
		exA exa = new exA();
		ArrayList<String> information = new ArrayList<>();
		HashMap<String, ArrayList<ArrayList<String>>> hashMapMale = new HashMap<>();
		HashMap<String, ArrayList<ArrayList<String>>> hashMapFemale = new HashMap<>();
		PriorityQueue<ArrayList<String>> priorityQueue = new PriorityQueue<>((a,b)->Double.compare(Double.parseDouble(b.get(0).trim()),Double.parseDouble(a.get(0).trim())));
        
		int counter = 0;
		while(counter < k){
			exa.findTopCouple(hashMapMale,hashMapFemale,brMale,brFemale,priorityQueue);
			while(!priorityQueue.isEmpty() && exa.findT(Double.parseDouble(priorityQueue.peek().get(4).trim()),Double.parseDouble(priorityQueue.peek().get(9).trim())) >= exa.T){
				System.out.println((counter+1)+". "+"pair: "+priorityQueue.peek().get(6)+","+priorityQueue.peek().get(1) + " score: "+priorityQueue.peek().get(0));
				priorityQueue.poll();
				counter++;
			}
		}
		System.out.println("For k = "+k+" the number of valid lines in males_sortes is: "+exa.counterMale + " and the number of valid lines in females_sortes is: "+exa.counterFemale);
		Instant end = Instant.now();
		Duration duration = Duration.between(start, end);
        long milliseconds = duration.toMillis();

        System.out.println("Execution time: " + milliseconds + " ms");
	}
}
