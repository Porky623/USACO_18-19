import java.util.*;
import java.io.*;
public class cowpatibility {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("cowpatibility.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("cowpatibility.out")));
		HashMap<Integer,ArrayList<Integer>> flavors=new HashMap<Integer,ArrayList<Integer>>();
		int N=Integer.parseInt(f.readLine());
		long numCant=N*(N-1)/2;
		HashMap<Integer,HashSet<Integer>> isComp=new HashMap<Integer,HashSet<Integer>>();
		for(int i=0; i<N; i++) {
			StringTokenizer st=new StringTokenizer(f.readLine());
			HashSet<Integer> curComp=new HashSet<Integer>();
			for(int j=0; j<5; j++) {
				int flavor=Integer.parseInt(st.nextToken());
				if(!flavors.containsKey(flavor)) {
					flavors.put(flavor, new ArrayList<Integer>());
					flavors.get(flavor).add(i);
				}
//				else {
//					ArrayList<Integer> shareList=flavors.get(flavor);
//					for(int k=0; k<shareList.size(); k++) {
//						if(!curComp.contains(shareList.get(k))&&!isComp.get(shareList.get(k)).contains(i)) {
//							curComp.add(shareList.get(k));
//							numCant--;
//						}
//					}
//					flavors.get(flavor).add(i);
//				}
				else {
					curComp.addAll(flavors.get(flavor));
					flavors.get(flavor).add(i);
				}
			}
			numCant-=curComp.size();
			isComp.put(i, curComp);
		}
		out.println(numCant);
		out.close();
	}
}
