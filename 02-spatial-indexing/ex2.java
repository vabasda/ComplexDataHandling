//EVANGELOS BASDAVANOS 4962

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ex2{

    private static double q_x;
    private static  double q_y;
	
    public static double minDistMBR(double q_x, double q_y, MBR mbr) {
        double dx = 0;
        double dy = 0;
        if (q_x > mbr.getMaxX()){
            dx = q_x - mbr.getMaxX();
        }
        if (q_x < mbr.getMinX()){
            dx = mbr.getMinX() - q_x;
        }
        if (q_y > mbr.getMaxY()){
            dy = q_y - mbr.getMaxY();
        }
        if (q_y < mbr.getMinY()){
            dy = mbr.getMinY() - q_y;
        }
        return Math.sqrt(dx * dx + dy * dy);
    }

        public static double minDistPoint(double q_x, double q_y, Point point) {
        double dx = 0;
        double dy = 0;
        if (q_x > point.getX()){
            dx = q_x - point.getX();
        }
        if (q_x < point.getX()){
            dx = point.getX() - q_x;
        }
        if (q_y > point.getY()){
            dy = q_y - point.getY();
        }
        if (q_y < point.getY()){
            dy = point.getY() - q_y;
        }
        return Math.sqrt(dx * dx + dy * dy);
    }


    public static void nNSearcher(Node[] tree, int nodeId, INNS inns){
        
        int isLeaf = tree[nodeId].getIsLeaf();
        if (isLeaf == 1){
            for(MBR mbr : tree[nodeId].getMBRS()){
                if(mbr != null ){
                    double minDist = minDistMBR(q_x,q_y,mbr);
                    inns.insert(new PqEntry(minDist,mbr.getNodeId(),isLeaf,0.0,0.0));
                }
            }
        }
        if (isLeaf == 0){
            for(Point point : tree[nodeId].getPoints()){
                if(point != null ){
                    double minDist = minDistPoint(q_x,q_y,point);
                    inns.insert(new PqEntry(minDist,point.getRecordId(),isLeaf,point.getX(),point.getY()));
                }
            }
        }
    }

    public static Object[] reader(String treeFile,Node[] tree){
        int rootId = 0;

        double minX = 0, minY = 0, maxX = 0, maxY = 0;
        int index = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(treeFile))) {
            rootId = Integer.parseInt(reader.readLine().trim());
            if(index == 0){
                tree = new Node[rootId + 1];
            }
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] values = line.split(",");

                int nodeId = Integer.parseInt(values[0]);
                int capacity = Integer.parseInt(values[1]);
                int isLeaf = Integer.parseInt(values[2]);

                if(isLeaf == 0){
                    Point[] points = new Point[51];
                    String[] coordinates = line.split("\\(");
                    int l = 0;
                    for (int i = 2; i < coordinates.length; i++) {

                        double x =0 , y = 0;
                        String coordinate = coordinates[i];
                        String[] parts = coordinate.split(",");
                        int recordId = Integer.parseInt(parts[0]);
                        x = Double.parseDouble(parts[1].substring(2, parts[1].length() ));
                        if (i == coordinates.length - 1){
                            y = Double.parseDouble(parts[2].substring(0, parts[2].length()-3));
                        }
                        else{
                            y = Double.parseDouble(parts[2].substring(0, parts[2].length()-2));
                        }
                        Point point = new Point(recordId,x,y);
                        points[l] = point;
                        l++;
                    }
                    Node node = new Node(nodeId,capacity,isLeaf,points);
                    tree[index] = node;
                    index ++;
                }
                if(isLeaf == 1){
                    MBR[] mbrs = new MBR[28];

                    String[] coordinates = line.split("\\(");
                    int l = 0;
                    for (int i = 2; i < coordinates.length; i++) {

                        double xmin = 0 , ymin = 0,xmax = 0,ymax = 0;
                        String coordinate = coordinates[i];
                        String[] parts = coordinate.split(",");
                        int nodeIdMbr = Integer.parseInt(parts[0]);
                        xmin = Double.parseDouble(parts[1].substring(2, parts[1].length() ));
                        ymin = Double.parseDouble(parts[2].substring(0, parts[2].length()));
                        xmax = Double.parseDouble(parts[3].substring(0, parts[3].length() ));
                        if (i == coordinates.length - 1){
                            ymax = Double.parseDouble(parts[4].substring(0, parts[4].length()-3));
                        }
                        else{
                            ymax = Double.parseDouble(parts[4].substring(0, parts[4].length()-2));
                            
                        }
                        
                        MBR mbr = new MBR(nodeIdMbr,xmin,ymin,xmax,ymax);
                        mbrs[l] = mbr;
                        l++;
                    
                    }
                    Node node = new Node(nodeId,capacity,isLeaf,mbrs);
                    tree[index] = node;
                    
                    index ++;
                }
            }

            reader.close();
           
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Object[]{rootId, tree};
    }

    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Give file.txt x_point y_point k");
            return;
        }

        String treeFile = args[0];
        q_x = Double.parseDouble(args[1]);
        q_y = Double.parseDouble(args[2]);
        int k = Integer.parseInt(args[3]);
        
        Node[] tree = null;
        INNS inns = new INNS();
        
        Object[] result = reader(treeFile, tree);
        int rootId = (int) result[0];
        tree = (Node[]) result[1];

        nNSearcher(tree,rootId, inns);
        
        int count = 0;
        PqEntry nearest = null;
        System.out.println("----------------------------");
        System.out.println("FIRST K NEIGHBOURS:");
        
        while(count < k){
            
            nearest = inns.deleteMin();

            while(nearest.getIsLeaf() == 0){
                if(count>0){
                    System.out.println("***** NEXT NEIGHBOUR *****");
                }
                inns.print(nearest);
                System.out.println("----------------------------------");
                System.out.println("Content in the heap after the retrieval of neighbour with ID: "+nearest.getNodeId());
                inns.printFull();
                System.out.println("----------------------------------");
                count++;
                
                if(count == k){
                    break;
                }
                nearest = inns.deleteMin();

            }

            if(nearest.getIsLeaf() == 1){
                nNSearcher(tree,nearest.getNodeId(),inns);
            }
            
        }
        count = 0;

        System.out.println("K+1 NEIGHBOUR:");

        while(count < 1){   
            nearest = inns.deleteMin();         
            while(nearest.getIsLeaf() == 0){
                System.out.println("----------------------------------");
                
                inns.print(nearest);
                System.out.println("----------------------------------");
                System.out.println("Content in the heap after the retrieval of neighbour with ID: "+nearest.getNodeId());
                inns.printFull();
                System.out.println("----------------------------------");
                count++;
                if(count == 1){
                    break;
                }
            }

            if(nearest.getIsLeaf() == 1){
                nNSearcher(tree,nearest.getNodeId(),inns);
            }
        }
        count = 0;
        
        System.out.println("K+2 NEIGHBOUR:");
        while(count<1){
            nearest = inns.deleteMin();
            while(nearest.getIsLeaf() == 0){
                System.out.println("----------------------------------");
                
                inns.print(nearest);
                System.out.println("----------------------------------");
                System.out.println("Content in the heap after the retrieval of neighbour with ID: "+nearest.getNodeId());
                inns.printFull();
                System.out.println("----------------------------------");
                count++;
                if(count == 1){
                    break;
                }
            }

            if(nearest.getIsLeaf() == 1){
                nNSearcher(tree,nearest.getNodeId(),inns);
            }
        }
    }

}
