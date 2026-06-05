//EVANGELOS BASDAVANOS 4962

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.*;
import java.time.Instant;
import java.time.Duration;

public class exB{
	
	public HashMap<String, ArrayList<ArrayList<String>>> malesReadFileAndCreateHashmap() throws IOException {
        ArrayList<ArrayList<String>> fulfillCriteria = new ArrayList<>();
        try (BufferedReader brMale = new BufferedReader(new FileReader("males_sorted"))) {
            String line = null;
            while ((line = brMale.readLine()) != null) {
                String[] elements = line.split(",");
                if (elements[8].startsWith(" Married") || Integer.parseInt(elements[1].trim()) < 18) {
                    continue;
                }else{
                    ArrayList<String> valuesForHashtables = new ArrayList<>();
                    valuesForHashtables.add(elements[0]);
                    valuesForHashtables.add(elements[1]);
                    valuesForHashtables.add(elements[8]);
                    valuesForHashtables.add(elements[25]);
                    fulfillCriteria.add(valuesForHashtables);
                }
            }
        }

        HashMap<String, ArrayList<ArrayList<String>>> hashMapMale = new HashMap<>();
        for(ArrayList<String> male : fulfillCriteria){
            String age = male.get(1);

            if(hashMapMale.containsKey(age)){
                hashMapMale.get(age).add(male);
            }
            else{
                ArrayList<ArrayList<String>> infoDone = new ArrayList<>();
                infoDone.add(male);
                hashMapMale.put(age,infoDone);
            }
        }
        return hashMapMale;
    }


    public PriorityQueue<ArrayList<String>> findTopCouple(HashMap<String, ArrayList<ArrayList<String>>> hashMapMale, int k) throws IOException{
        PriorityQueue<ArrayList<String>> priorityQueue = new PriorityQueue<>((a,b)->Double.compare(Double.parseDouble(a.get(0).trim()),Double.parseDouble(b.get(0).trim())));
        BufferedReader brFemale = new BufferedReader(new FileReader("females_sorted"));
        String line = null;
        while ((line = brFemale.readLine()) != null) {
            String[] elements = line.split(",");
            if (elements[8].startsWith(" Married") || Integer.parseInt(elements[1].trim()) < 18) {
                continue;
            }else{
                ArrayList<String> valuesForHashtables = new ArrayList<>();
                valuesForHashtables.add(elements[0]);
                valuesForHashtables.add(elements[1]);
                valuesForHashtables.add(elements[8]);
                valuesForHashtables.add(elements[25]);

                if(hashMapMale.containsKey(valuesForHashtables.get(1))){
                    ArrayList<ArrayList<String>> matches = hashMapMale.get((valuesForHashtables.get(1)));
                    ArrayList<ArrayList<String>> joins = new ArrayList<>();
                    for(ArrayList<String> match : matches){
                        ArrayList<String> x = new ArrayList<>();
                        x.add(String.valueOf(Double.parseDouble(valuesForHashtables.get(3)) + Double.parseDouble(match.get(3))));
                        x.addAll(valuesForHashtables);
                        x.addAll(match);
                        joins.add(x);
                    }

                    for(ArrayList<String> join : joins){
                        if (priorityQueue.size() < k){
                            priorityQueue.offer(join);
                        }
                        if (!priorityQueue.isEmpty() && Double.parseDouble(join.get(0)) > Double.parseDouble(priorityQueue.peek().get(0))){
                            priorityQueue.poll();
                            priorityQueue.offer(join);
                        }
                    }
                }
            }
        }
        return priorityQueue;
    }

   

	public static void main(String[] args) throws IOException{
        Instant start = Instant.now();

        int k = Integer.parseInt(args[0]);
		exB exB = new exB();
        
        HashMap<String, ArrayList<ArrayList<String>>> hashMapMale = exB.malesReadFileAndCreateHashmap();
        PriorityQueue<ArrayList<String>> priorityQueue = exB.findTopCouple(hashMapMale,k);

        Instant end = Instant.now();

        int counter = 0;
        int flag = k;
        ArrayList<String> messages = new ArrayList<>();
        while(counter < k){
            if(!priorityQueue.isEmpty()){
                String message = ((flag)+". "+"pair: "+priorityQueue.peek().get(5)+","+priorityQueue.peek().get(1) + " score: "+priorityQueue.peek().get(0));
                messages.add(message);
                priorityQueue.poll();
                counter++;
                flag--;
            }
        }
        for (int i = messages.size() - 1; i >= 0; i--) {
            System.out.println(messages.get(i));
        }

        
        Duration duration = Duration.between(start, end);
        long milliseconds = duration.toMillis();

        System.out.println("Execution time: " + milliseconds + " ms");
	}
}
