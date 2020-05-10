import java.util.*;
import java.io.*;
public class cowpatiblity2 {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("cowpatibility.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		int[] maxSize=new int[2];
		HashMap<Integer,ArrayList<Integer>> flavors=new HashMap<Integer,ArrayList<Integer>>();
		HashMap<Integer,ArrayList<Integer>> cows=new HashMap<Integer,ArrayList<Integer>>();
		int N=Integer.parseInt(f.readLine());
		long numCant=N*(N-1)/2;
		for(int i=0; i<N; i++) {
			StringTokenizer st=new StringTokenizer(f.readLine());
			ArrayList<Integer> cur=new ArrayList<Integer>();
			for(int j=0; j<5; j++) {
				int flavor=Integer.parseInt(st.nextToken());
				if(!flavors.containsKey(flavor)) {
					flavors.put(flavor, new ArrayList<Integer>());
				}
				flavors.get(flavor).add(i);
				if(flavors.get(flavor).size()>maxSize[0]) {
					maxSize[0]=flavors.get(flavor).size();
					maxSize[1]=flavor;
				}
				cur.add(flavor);
			}
			cows.put(i, cur);
		}
		numCant-=(maxSize[0]-1)*maxSize[0]/2;
		HashSet<Integer> groupA=new HashSet<Integer>(flavors.get(maxSize[1]));
		for(int i=N-1; i>=0; i--) {
			if(!groupA.contains(i)) {
				for(int j=i-1; j>=0; j--) {
					HashSet<Integer> cur=new HashSet<Integer>(cows.get(i));
					cur.addAll(cows.get(j));
					if(cur.size()<10)
						numCant--;
				}
			}
		}
		out.println(numCant);
		out.close();
	}
}
