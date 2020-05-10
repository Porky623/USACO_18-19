import java.util.*;
import java.io.*;
public class dishes2 {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("dishes.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("dishes.out")));
		int N=Integer.parseInt(f.readLine().trim());
		int[] inp=new int[N];
		for(int i=0; i<N; i++) {
			inp[i]=Integer.parseInt(f.readLine().trim());
		}
		int start=N;
		for(int i=0; i<N; i++) {
			if(inp[i]!=i+1) {
				start=i;
				break;
			}
		}
		LinkedList<Stack<Integer>> soapy=new LinkedList<>();
		LinkedList<Integer> bottoms=new LinkedList<>();
		Stack<Integer> clean=new Stack<>();
		for(int i=start; i<N; i++) {
			if(!clean.isEmpty()&&clean.peek()>inp[i]) {
				out.println(i);
				out.close();
				System.exit(0);
			}
			if(!clean.isEmpty()&&clean.peek()==inp[i]-1) {
				clean.push(inp[i]);
				continue;
			}
			ListIterator<Stack<Integer>> it=soapy.listIterator();
			ListIterator<Integer> it2=bottoms.listIterator();
			boolean soaped=false;
			while(it.hasNext()&&!soaped) {
				Stack<Integer> cur=it.next();
				int curBot=it2.next();
				if(cur.peek()<inp[i]&&curBot>inp[i]) {
					while(cur.peek()<inp[i]) {
						clean.push(cur.pop());
					}
					cur.push(inp[i]);
					soaped=true;
					continue;
				}
				else if(cur.peek()<inp[i]) {
					continue;
				}
				if(cur.isEmpty()) {
					it.remove();
					it2.remove();
					continue;
				}
				cur.push(inp[i]);
				soaped=true;
			}
			if(!soaped) {
				soapy.add(new Stack<Integer>());
				soapy.getLast().add(inp[i]);
				bottoms.add(inp[i]);
			}
		}
		out.println(N);
		out.close();
	}
}
