import java.io.*;
import java.util.*;
public class shortcut {
	public static HashMap<Integer,HashSet<Integer>> inPathOf=new HashMap<>();
	public static HashMap<Integer,HashSet<Integer>> inPath=new HashMap<>();
	public static HashMap<Integer,Integer> numHitch=new HashMap<>();
	public static int[] Dijkstra(HashMap<Integer,Integer> numHitch, HashMap<Integer,HashSet<Integer>> inPath, HashMap<Integer,HashSet<Integer>> inPathOf, int T, int N, HashMap<Integer,HashSet<ArrayList<Integer>>> trails,int[] cows) {
		int[] ret=new int[N];
		Queue<eNode> pq=new PriorityQueue<eNode>();
		HashSet<Integer> done=new HashSet<Integer>();
		pq.add(new eNode(0,0,0,cows[0]));
		while(pq.size()>0) {
			eNode cur=pq.poll();
			if(done.contains(cur.dest))
				continue;
			done.add(cur.dest);
			inPath.put(cur.dest, new HashSet<Integer>());
			for(int x:inPath.get(cur.from)) {
				inPath.get(cur.dest).add(x);
//				inPathOf.get(cur.from).add(cur.dest);
				numHitch.put(cur.from, numHitch.get(cur.from)+cows[cur.dest]);
			}
			inPath.get(cur.dest).add(cur.dest);
//			inPathOf.get(cur.dest).add(cur.dest);
			numHitch.put(cur.dest, numHitch.get(cur.dest)+cows[cur.dest]);
//			int newNum=cur.numCows+cows[cur.dest];
			int newNum=0;
			for(ArrayList<Integer> trail:trails.get(cur.dest)){
				pq.add(new eNode(cur.dest,trail.get(0),trail.get(1)+cur.time,newNum));
			}
			if(cur.time>T)
				ret[cur.dest]=(cur.time-T);
		}
		return ret;
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader f=new BufferedReader(new FileReader("shortcut.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("shortcut.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int M=Integer.parseInt(st.nextToken());
		int T=Integer.parseInt(st.nextToken());
		int[] cows=new int[N];
		st=new StringTokenizer(f.readLine());
		for(int i=0; i<N; i++) {
			cows[i]=Integer.parseInt(st.nextToken());
//			inPathOf.put(i, new HashSet<Integer>());
			numHitch.put(i, 0);
		}
		HashMap<Integer,HashSet<ArrayList<Integer>>> trails=new HashMap<>();
//		HashMap<Integer,HashSet<eNode>> trailMap=new HashMap<>();
		for(int i=0; i<M; i++) {
			st=new StringTokenizer(f.readLine());
			int a=Integer.parseInt(st.nextToken())-1;
			int b=Integer.parseInt(st.nextToken())-1;
			int t=Integer.parseInt(st.nextToken());
//			if(!trailMap.containsKey(a))
//				trailMap.put(a, new HashSet<eNode>());
//			if(!trailMap.containsKey(b))
//				trailMap.put(a,new HashSet<eNode>());
//			trailMap.get(a).add(new eNode(b,t));
			if(!trails.containsKey(a)) {
				trails.put(a, new HashSet<ArrayList<Integer>>());
			}
			if(!trails.containsKey(b)) {
				trails.put(b,new HashSet<ArrayList<Integer>>());
			}
			ArrayList<Integer> temp=new ArrayList<Integer>();
			temp.add(b);
			temp.add(t);
			trails.get(a).add(temp);
			temp=new ArrayList<Integer>();
			temp.add(a);
			temp.add(t);
			trails.get(b).add(temp);
		}
		int[] shortestDist=Dijkstra(numHitch, inPath, inPathOf, T, N,trails,cows);
		int max=shortestDist[0];
		for(int i=1; i<N; i++) {
			max=Math.max(max, shortestDist[i]*numHitch.get(i));
//			if(min==0&&shortestDist[i]>0||shortestDist[i]<min)
//				min=shortestDist[i];
		}
		out.println(max);
		out.close();
	}
	static class eNode implements Comparable<eNode>{
		public int dest, time,from,numCows;
		public eNode(int from, int dest, int time, int numCows) {
			this.dest=dest;
			this.time=time;
			this.from=from;
		}
		@Override
		public int compareTo(eNode en) {
			if(this.time<en.time)
				return -1;
			if(this.time>en.time)
				return 1;
			if(this.from<en.from)
				return -1;
			if(this.from>en.from)
				return 1;
			return 0;
		}
	}
}
