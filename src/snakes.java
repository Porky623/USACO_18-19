import java.util.*;
import java.io.*;
public class snakes {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("snakes.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		st=new StringTokenizer(f.readLine());
		int[] groups=new int[N];
		int max=0;
		for(int i=0; i<N; i++) {
			groups[i]=Integer.parseInt(st.nextToken());
			max=Math.max(max,groups[i]);
		}
		int sum=0;
		for(int i=0; i<N; i++) {
			groups[i]=max-groups[i];
			sum+=groups[i];
		}
		Node[][] dp=new Node[N+1][K+2];
		dp[1][1]=new Node(groups[0],groups[0],1);
		for(int i=1; i<N; i++) {
			int newMin=Math.min(dp[i][1].min,groups[i]);
			dp[i+1][1]=new Node(newMin*(i+1),newMin,i+1);
		}
		for(int i=2; i<=K+1; i++) {
			dp[i][i]=new Node(dp[i-1][i-1].val+groups[i-1],groups[i-1],1);
		}
		for(int i=2; i<=K+1; i++) {
			for(int j=i; j<N; j++) {
				int a=groups[j]+dp[j][i-1].val;
				int b=dp[j][i].val+(groups[j]-dp[j][i].min)*dp[j][i].len+groups[j];
				int c=dp[j][i].val+dp[j][i].min;
				if(dp[j][i].min>groups[j]) {
					if(a<b)
						dp[j+1][i]=new Node(b,groups[j],dp[j][i].len+1);
					else
						dp[j+1][i]=new Node(a,groups[j],1);
				}
				else {
					if(a<c)
						dp[j+1][i]=new Node(c,dp[j][i].min,dp[j][i].len+1);
					else
						dp[j+1][i]=new Node(a,groups[j],1);
				}
			}
		}
		out.println(sum-dp[N][K+1].val);
		out.close();
	}
	static class Node{
		public int val,min,len;
		public Node(int val, int min, int len) {
			this.val=val;
			this.min=min;
			this.len=len;
		}
	}
}
