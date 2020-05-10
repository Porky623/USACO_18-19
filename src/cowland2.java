import java.util.*;
import java.io.*;
public class cowland2 {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("cowland.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("cowland.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int Q=Integer.parseInt(st.nextToken());
		st=new StringTokenizer(f.readLine());
		int[] excitement=new int[N+1];
		for(int i=0; i<N; i++) {
			excitement[i+1]=Integer.parseInt(st.nextToken());
		}
		HashMap<Integer,ArrayList<Integer>> roads=new HashMap<>();
		for(int i=1; i<=N; i++) {
			roads.put(i, new ArrayList<Integer>());
		}
		for(int i=0; i<N-1;i++) {
			st=new StringTokenizer(f.readLine());
			int a=Integer.parseInt(st.nextToken());
			int b=Integer.parseInt(st.nextToken());
			roads.get(a).add(b);
			roads.get(b).add(a);
		}
		int[] d=makeD(excitement,roads,N);
		HashMap<Integer,HashSet<Integer>> ref1=new HashMap<>();
		HashMap<Integer,Integer> ref2=new HashMap<>();
		HashSet<Integer> seen=new HashSet<>();
		Stack<Node> stack=new Stack<>();
		HashSet<Integer> temp2=new HashSet<Integer>();
		temp2.add(1);
		stack.push(new Node(1,0,temp2,0));
		while(!stack.isEmpty()) {
			Node x=stack.pop();
			if(seen.contains(x.ind))
				continue;
			seen.add(x.ind);
			ref1.put(x.ind, x.pathSet);
			ref2.put(x.ind, x.prev);
			for(int k:roads.get(x.ind)) {
				temp2=new HashSet<Integer>();
				temp2.addAll(x.pathSet);
				temp2.add(k);
				stack.push(new Node(k,0,temp2,x.ind));
			}
		}
		for(int i=0; i<Q; i++) {
			st=new StringTokenizer(f.readLine());
			int k=Integer.parseInt(st.nextToken());
			if(k==2) {
				int a=Integer.parseInt(st.nextToken());
				int b=Integer.parseInt(st.nextToken());
				int x;
				if(a==1)
					x=d[b];
				else if(b==1)
					x=d[a];
				else if(ref1.get(a).contains(b)) {
					x=d[a]^d[ref2.get(b)];
				}
				else if(ref1.get(b).contains(a)) {
					x=d[b]^d[ref2.get(a)];
				}
				else {
					x=d[a]^d[b]^excitement[LCA(a,b,roads,ref1,ref2)];
				}
				out.println(x);
			}
			else {
				int a=Integer.parseInt(st.nextToken());
				int b=Integer.parseInt(st.nextToken());
				updateD(excitement,ref1,d,a,b,ref2.get(a),roads);
			}
		}
		out.close();
	}
	public static int LCA(int a, int b,HashMap<Integer,ArrayList<Integer>> roads,HashMap<Integer,HashSet<Integer>> ref1,HashMap<Integer,Integer> ref2) {
		HashSet<Integer> curRef=ref1.get(b);
		int cur=a;
		while(!curRef.contains(cur))
			cur=ref2.get(cur);
		return cur;
	}
	public static int[] makeD(int[] excitement,HashMap<Integer,ArrayList<Integer>> roads,int N) {
		int[] d=new int[N+1];
		HashSet<Integer> seen=new HashSet<>();
		Stack<Node> stack=new Stack<>();
		stack.push(new Node(1,excitement[1],null,0));
		while(!stack.isEmpty()) {
			Node x=stack.pop();
			if(seen.contains(x.ind))
				continue;
			seen.add(x.ind);
			d[x.ind]=x.val;
			for(int k:roads.get(x.ind)) {
				stack.push(new Node(k,x.val^excitement[k],null,0));
			}
		}
		return d;
	}
	public static void updateD(int[] excitement, HashMap<Integer,HashSet<Integer>> ref2,int[] d,int ind, int val,int prev,HashMap<Integer,ArrayList<Integer>> roads) {
		int change=val^excitement[ind];
		d[ind]=d[ind]^change;
		Stack<Integer> stack=new Stack<Integer>();
		HashSet<Integer> seen=new HashSet<Integer>();
		for(int i:roads.get(ind)) {
			if(i!=prev)
				stack.push(i);
		}
		seen.add(ind);
		excitement[ind]=val;
		while(!stack.isEmpty()) {
			int x=stack.pop();
			if(seen.contains(x))
				continue;
			seen.add(x);
			d[x]=d[x]^change;
			for(int k:roads.get(x)) {
				stack.push(k);
			}
		}
	}
	private static class Node{
		int ind, val,prev;
		HashSet<Integer> pathSet;
		public Node(int ind, int val,HashSet<Integer> pathSet,int prev) {
			this.ind=ind;
			this.val=val;
			this.prev=prev;
			this.pathSet=pathSet;
		}
	}
}
