import java.util.*;
import java.io.*;
public class dining {
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
		int[][] bales=new int[K][];
		for(int i=0; i<K; i++) {
			next=f.readLine();
			st=new StringTokenizer(next);
			int[] a=new int[] {Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())};
			bales[i]=a;
//			if(balesMap.containsKey(a[0]-1)) {
//				balesMap.put(a[0]-1,Math.max(a[1],balesMap.get(a[0]-1)));
//			}
//			else {
//				balesMap.put(a[0]-1, a[1]);
//			}
		}
		Arrays.sort(bales, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return a[1]-a[0]-b[1]+b[0];
			}
		});
		int[][] shortestPaths=FloydWarshall(trailMap,N);
		for(int i=0; i<N-1; i++) {
			if(solveF(trailMap,balesMap,i,shortestPaths,N,bales))
				out.println(1);
			else
				out.println(0);
		}
//		int[] shortestPaths=djikstra(trailMap,balesMap,N);
//		for(int i=0; i<N-1; i++) {
//			if(solve(trailMap,balesMap,i,shortestPaths,N))
//				out.println(1);
//			else
//				out.println(0);
//		}
		out.close();
	}
	public static boolean solveF(HashMap<Integer,ArrayList<int[]>> trails,HashMap<Integer,Integer> bales,int pasture,int[][] shortestPaths,int N,int[][] bales2) {
//		Set<Integer> baleIndices=bales.keySet();
//		int curLength=shortestPaths[pasture][N-1];
//		for(int bale:baleIndices) {
//			int newLength=shortestPaths[pasture][bale]+shortestPaths[bale][N-1];
//			if(newLength-curLength<=bales.get(bale))
//				return true;
//		}
		for(int i=0; i<bales2.length; i++) {
			if(shortestPaths[pasture][bales2[i][0]]+shortestPaths[bales2[i][0]][N-1]-shortestPaths[pasture][N-1]<=bales2[i][1])
				return true;
		}
		return false;
	}
	public static int[][] FloydWarshall(HashMap<Integer,ArrayList<int[]>> trails,int N){
		int[][] fw=new int[N][N];
		for(int start:trails.keySet()) {
			for(int[] end:trails.get(start)) {
				fw[start][end[0]]=end[1];
			}
		}
		for(int k=0; k<N; k++) {
			for(int i=0; i<N; i++) {
				smallLoop:
				for(int j=0; j<N; j++) {
					int curDist=fw[i][j];
					if(fw[i][k]==0||fw[j][k]==0)
						continue smallLoop;
					if(curDist==0)
						fw[i][j]=fw[j][i]=fw[i][k]+fw[k][j];
					else
						fw[i][j]=fw[j][i]=Math.min(fw[i][j], fw[i][k]+fw[k][j]);
				}
			}
		}
		return fw;
	}
}
