//EVANGELOS BASDAVANOS 4962

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;


public class STR{
	
	private int leafNodeCapacity = 51;
	private int nonLeafNodeCapacity = 28;
	private int nodeId;
	private static String outputFile;
	ArrayList<ArrayList<String>> treeInfo = new ArrayList<>();

	public void buildRTree(ArrayList<Point> points){

		int numberOfPoints = points.size();
		int numberOfLeafNodes = (int) Math.ceil((double) numberOfPoints / 51);
		int numberOfStripes = (int) Math.ceil((double)Math.sqrt(numberOfLeafNodes));

		Collections.sort(points, Comparator.comparingDouble(Point::getX));
		ArrayList<ArrayList<Point>> stripesPoints = new ArrayList<>();
		ArrayList<Node> stripesNodes = new ArrayList<>();
		ArrayList<Node> stripesNodesUpdated = new ArrayList<>();

		int treeInfoIndex = 0;
        
        for (int k = 0; k < numberOfStripes; k++) {
            stripesPoints.add(new ArrayList<>());
        }

        int i = 0;
        int stripe_index = 0;
        while (i < numberOfPoints) {
            ArrayList<Point> stripe = stripesPoints.get(stripe_index);
            int j = 0;
            while (i < points.size() && j < numberOfStripes*51) {
                stripe.add(points.get(i));
                i++;
                j++;
            }
            stripe_index++;
        }

       	Node[] nodes = new Node[3000];
        int numberOfNodes = 0;
        int nodeId = 0;
        int bool = 0;
        int stripeNum = 0;
        for(ArrayList<Point> stripe : stripesPoints){
        	int numberOfPointsInStripe = stripe.size();
        	Collections.sort(stripe, Comparator.comparingDouble(Point::getY));
        	
      		int flag = 0;
      		int numberOfPointsLeft = numberOfPointsInStripe;
      		while(flag < numberOfPointsInStripe){
      			int k = 0 ;
      			int capacity = Math.min(leafNodeCapacity,numberOfPointsLeft);
       			Point[] leaves = new Point[capacity];
	        	while(k<capacity && flag < numberOfPointsInStripe){
	        		leaves[k] = stripe.get(flag);
	        		k++;
	        		flag++;
	        		numberOfPointsLeft--;
	        	}
	        	Node leafNode = new Node(nodeId, capacity, 0, leaves);
	        	nodeId++;
	        	nodes[numberOfNodes] = leafNode;
	        	stripesNodes.add(leafNode);
	        	numberOfNodes++;
	        }
	        stripeNum ++;
    	}

    	treeInfo.add(new ArrayList<>());
    	treeInfo.get(treeInfoIndex).add("0");
    	treeInfo.get(treeInfoIndex).add(String.valueOf(numberOfNodes));
    	treeInfo.get(treeInfoIndex).add("0");
    	treeInfoIndex++;

    	
    	
    	int mbrNodeId = 0;
    	int indexOfStripe = 0;
    	int flag2 = 0;
    	int numberOfNodesInLevel = 0;
    	double mbrArea = 0;
    	int numberOfMbrs = 0;
    	int nodesLeft = 0;
    	int flag = 0;
    	while(stripesNodes.size()>1){
    		int counter = 0;
	    	stripesNodesUpdated.clear();
	    	nodesLeft = stripesNodes.size();
	    
	    	while (counter < stripesNodes.size()){

				int capacity = Math.min(nonLeafNodeCapacity, nodesLeft);

			   	MBR[] mbrs = new MBR[capacity];
				int k = 0;
				
				while (k < capacity && counter < stripesNodes.size()){
					double minX = Double.MAX_VALUE;
					double minY = Double.MAX_VALUE;
				    double maxX = Double.MIN_VALUE;
				 	double maxY = Double.MIN_VALUE;

				 	if (flag == 1){
				 		
				    	if (stripesNodes.get(counter) != null && stripesNodes.get(counter).getMBRS() != null && stripesNodes.get(counter).getMBRS().length > 0) {
				    	
				            for (MBR mbr : stripesNodes.get(counter).getMBRS()) {
				                if (mbr.getMinX() < minX) {
				                    minX = mbr.getMinX();
				                }
				                if (mbr.getMinY() < minY) {
				                    minY = mbr.getMinY();
				                }
				                if (mbr.getMaxX() > maxX) {
				                    maxX = mbr.getMaxX();
				                }
				                if (mbr.getMaxY() > maxY) {
				                    maxY = mbr.getMaxY();
				                }
				            }
					        MBR mbr = new MBR(mbrNodeId, minX, minY, maxX, maxY);
					        mbrArea += ((maxX - minX) * (maxY - minY));
							numberOfMbrs ++;
					        mbrNodeId++;
					        mbrs[k] = mbr;
					        k++;
					        counter ++;
					        nodesLeft --;
					    }
					}
					if(flag == 0){
						
						if (stripesNodes.get(counter) != null && stripesNodes.get(counter).getPoints() != null && stripesNodes.get(counter).getPoints().length > 0) {
			
				            for (Point point : stripesNodes.get(counter).getPoints()) {
				                if (point.getX() < minX) {
				                    minX = point.getX();
				                }
				                if (point.getY() < minY) {
				                    minY = point.getY();
				                }
				                if (point.getX() > maxX) {
				                    maxX = point.getX();
				                }
				                if (point.getY() > maxY) {
				                    maxY = point.getY();
				                }
				            }
				            MBR mbr = new MBR(mbrNodeId, minX, minY, maxX, maxY);
					        mbrArea += ((maxX - minX) * (maxY - minY));
							numberOfMbrs ++;
					        mbrNodeId++;
					        mbrs[k] = mbr;
					        k++;
					        counter ++;
					        nodesLeft --;
				        }
					}
				}
			
			    Node internalNode = new Node(nodeId, capacity, 1, mbrs);
			    stripesNodesUpdated.add(internalNode);
			    nodeId++;
			    nodes[numberOfNodes] = internalNode;
			    numberOfNodesInLevel ++;
			    numberOfNodes++;
			}
			stripesNodes.clear();
			stripesNodes.addAll(stripesNodesUpdated);
			flag = 1;
			treeInfo.add(new ArrayList<>());
	    	treeInfo.get(treeInfoIndex).add(String.valueOf(treeInfoIndex));
	    	treeInfo.get(treeInfoIndex).add(String.valueOf(numberOfNodesInLevel));
	    	treeInfo.get(treeInfoIndex).add(String.valueOf((double)(mbrArea/numberOfMbrs)));
	    	treeInfoIndex++;
	    	numberOfNodesInLevel = 0;
    		mbrArea = 0;
    		numberOfMbrs = 0;
		}
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
        	writer.write(nodes[numberOfNodes-1].getNodeId() +"");
        	writer.write("\n\n");
        	int nodeCounter = 0;
            for(Node node:nodes){
            	if (node != null ){
            		if (node.getIsLeaf() == 0 && node.getPoints() != null && node.getPoints().length > 0){
			            writer.write(node.getNodeId()+","+node.getCapacity()+","+node.getIsLeaf()+","+node.getPointsString());
			            writer.write("\n\n");
			        }
			        if(node.getIsLeaf() == 1 && node.getMBRS() != null && node.getMBRS().length > 0){
	        			writer.write(node.getNodeId()+","+node.getCapacity()+","+node.getIsLeaf()+","+node.getMBRSString());
		            	writer.write("\n\n");
		            }
	        	}
	        	
	        }
	    } catch (IOException e) {
        	System.err.println("Error writing to file: " + e.getMessage());
	    }
	}

	public void printStats(){
		String height = treeInfo.get(treeInfo.size()-1).get(0);
		System.out.println("Height of tree: "+(Integer.parseInt(height)+1));
		System.out.println("-----------------------------");
		for(ArrayList<String> stat : treeInfo){
			System.out.println("Stats for level: "+stat.get(0));
			System.out.println("Number of nodes in this level: "+ stat.get(1));
			System.out.println("Area of MBRs in this level: "+stat.get(2));
			System.out.println("-----------------------------");
		}
	}


	public static void main(String[] args) {
		STR str = new STR();
		outputFile = args[0];
        String fileName = "Beijing_restaurants.txt";
        int numPoints;
        ArrayList<Point> points = new ArrayList<>();
        int i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            numPoints = Integer.parseInt(br.readLine().trim());
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split("\\s+");
                double x = Double.parseDouble(values[0]);
                double y = Double.parseDouble(values[1]);
                int id = i + 1;
                Point point = new Point(id,x, y);
                points.add(point);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        

        str.buildRTree(points);
        str.printStats();
    } 
}
