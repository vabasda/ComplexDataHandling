//EVANGELOS BASDAVANOS 4962


import java.util.PriorityQueue;

public class INNS {
    PriorityQueue<PqEntry> pq;

    public INNS() {
        pq = new PriorityQueue<>((a, b) -> Double.compare(a.getMinDist(), b.getMinDist()));
    }

    public void insert(PqEntry entry) {
        pq.offer(entry);
    }

    public PqEntry deleteMin() {
        return pq.poll();
    }

    public void print(PqEntry element){
        System.out.println("(ID:"+element.getNodeId()+", Min Distance: "+element.getMinDist()+", X coordinate: "+ element.getX()+", Y coordinate: "+element.getY()+")");
        
    }

    public void printFull(){
        System.out.println("----------------------------------");
        PriorityQueue<PqEntry> copyPq = new PriorityQueue<>(pq);
        while (!copyPq.isEmpty()) {
            PqEntry element = copyPq.poll();
            System.out.println(element.getMinDist() + "  " + element.getNodeId());
        }
    }
}