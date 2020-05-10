import java.util.*;
import java.io.*;
public class teamwork2 {
	public static int DFS(int[] newSkills,int K) {
		int max=0;
		int N=newSkills.length;
		for(int i=1; i<N; i++) {
			if(newSkills[i]>max)
				max=i;
		}
		int val=0;
		for(int i=Math.max(0,max-K+1); i<Math.min(N, max+K); i++) {
			int temp=newSkills[max]*Math.min(K, N);
			temp+=DFS(Arrays.copyOfRange(newSkills, 0, i),K);
			if(i+K+1<=N)
				temp+=DFS(Arrays.copyOfRange(newSkills, i+K+1, N),K);
			val=Math.max(val, temp);
		}
		return val;
	}
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("teamwork.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		int[] newSkills=new int[N];
		for(int i=0; i<N; i++) {
			newSkills[i]=Integer.parseInt(f.readLine());
		}
		int max=0;
		for(int i=1; i<N; i++) {
			if(newSkills[i]>max)
				max=i;
		}
		int val=0;
		for(int i=Math.max(0,max-K+1); i<Math.min(N, max+K); i++) {
			int temp=newSkills[max]*Math.min(K, N);
			temp+=DFS(Arrays.copyOfRange(newSkills, 0, i),K);
			if(i+K+1<=N)
				temp+=DFS(Arrays.copyOfRange(newSkills, i+K+1, N),K);
			val=Math.max(val, temp);
//			val=Math.max(val, K*newSkills[max]+DFS(Arrays.copyOfRange(newSkills, 0, i),K)+DFS(Arrays.copyOfRange(newSkills, i+K+1, N),K));
		}
		out.println(val);
		out.close();
	}
}
