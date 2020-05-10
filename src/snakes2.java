import java.util.*;
import java.io.*;
public class snakes2 {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("snakes.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("snakes.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		st=new StringTokenizer(f.readLine());
		int[] groups=new int[N];
		for(int i=0; i<N; i++) {
			groups[i]=Integer.parseInt(st.nextToken());
		}
		int max=0;
		int sum=0;
		for(int i=0; i<N; i++) {
			max=Math.max(groups[i],max);
			sum+=groups[i];
		}
		out.println(dp(groups,K+1,N-1,max*N)-sum);
		out.close();
	}
	public static int dp(int[] groups,int k,int end,int max) {
		int min=max;
		if(k==1) {
			int Max=0;
			for(int i=0; i<=end; i++) {
				Max=Math.max(Max, groups[i]);
			}
			return Max*(end+1);
		}
		int maxVal=groups[end];
		for(int i=end; i>=k-1; i--) {
			maxVal=Math.max(maxVal, groups[i]);
			if(maxVal==groups[i-1]&&i>=k)
				continue;
			min=Math.min(min, maxVal*(end-i+1)+dp(groups,k-1,i-1,max));
		}
		return min;
	}
}
