import java.util.*;
import java.io.*;
public class dining2 {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("dining.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("dining.out")));
		String next=f.readLine();
		StringTokenizer st=new StringTokenizer(next);
		int N=Integer.parseInt(st.nextToken());
		int M=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		HashMap<Integer,ArrayList<int[]>> trailMap=new HashMap<Integer,ArrayList<int[]>>();
		for(int i=0; i<M; i++) {
			next=f.readLine();
			st=new StringTokenizer(next);
			int[] a=new int[] {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
			int c=Integer.parseInt(st.nextToken());
			for(int j=0; j<2; j++) {
				if(!trailMap.containsKey(a[j]-1))
					trailMap.put(a[j]-1, new ArrayList<int[]>());
				trailMap.get(a[j]-1).add(new int[] {a[1-j]-1,c});
			}
		}
		HashMap<Integer,Integer> balesMap=new HashMap<Integer,Integer>(); 
		for(int i=0; i<K; i++) {
			next=f.readLine();
			st=new StringTokenizer(next);
			int[] a=new int[] {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
			if(balesMap.containsKey(a[0]-1)) {
				balesMap.put(a[0]-1,Math.max(a[1],balesMap.get(a[0]-1)));
			}
			else {
				balesMap.put(a[0]-1, a[1]);
			}
		}
		int[] shortestPaths=djikstra(trailMap,balesMap,N);
		boolean[] shouldEat=new boolean[N];
		for(int bale:balesMap.keySet()) {
			for(int pasture:solve(trailMap,balesMap,bale,shortestPaths,N)) {
				shouldEat[pasture]=true;
			}
		}
		for(int i=0; i<N-1; i++) {
			if(shouldEat[i])
				out.println(1);
			else
				out.println(0);
		}
//		for(int i=0; i<N-1; i++) {
//			if(solve(trailMap,balesMap,i,shortestPaths,N))
//				out.println(1);
//			else
//				out.println(0);
//		}
		out.close();
	}
	public static int[] djikstra(HashMap<Integer,ArrayList<int[]>> trails,HashMap<Integer,Integer> bales,int N) {
		int[] temp=new int[N];
		Queue<Node> toCheck=new PriorityQueue<Node>();
		HashSet<Integer> done=new HashSet<Integer>();
		toCheck.add(new Node(N-1,0));
		while(toCheck.size()>0) {
			Node cur=toCheck.poll();
			if(done.contains(cur.node))
				continue;
			done.add(cur.node);
			for(int[] x:trails.get(cur.node)) {
				if(!done.contains(x[0]))
				toCheck.add(new Node(x[0],x[1]+cur.cost));
			}
			temp[cur.node]=cur.cost;
		}
		return temp;
	}
	public static ArrayList<Integer> solve(HashMap<Integer,ArrayList<int[]>> trails,HashMap<Integer,Integer> bales,int curBale,int[] shortestPaths,int N) {
		ArrayList<Integer> ret=new ArrayList<Integer>();
		Queue<Node> toCheck=new LinkedList<Node>();
		toCheck.add(new Node(curBale,0));
		HashSet<Integer> seen=new HashSet<Integer>();
		seen.add(curBale);
		while(toCheck.size()>0) {
			Node cur=toCheck.poll();
			if(shortestPaths[curBale]-shortestPaths[cur.node]+cur.cost<=bales.get(curBale)) {
				ret.add(cur.node);
				for(int[] end:trails.get(cur.node)) {
					if(!seen.contains(end[0])) {
						toCheck.add(new Node(end[0],end[1]+cur.cost));
					}
				}
			}
		}
		return ret;
	}
}
class Node implements Comparable<Node> { 
    public int node; 
    public int cost; 
  
    public Node() 
    { 
    } 
  
    public Node(int node, int cost) 
    { 
        this.node = node; 
        this.cost = cost; 
    } 
  
    @Override
    public int compareTo(Node node2) 
    { 
        if (this.cost < node2.cost) 
            return -1; 
        if (this.cost > node2.cost) 
            return 1; 
        return 0; 
    } 
} 
