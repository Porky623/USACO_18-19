import java.util.*;
import java.io.*;
public class teamwork {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("teamwork.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("teamwork.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		int[][] skills=new int[N][];
		int[] newSkills=new int[N];
		for(int i=0; i<N; i++) {
			skills[i]=new int[] {Integer.parseInt(f.readLine()),i};
			newSkills[i]=skills[i][0];
		}
		Arrays.sort(skills, new Comparator<int[]>(){
			public int compare(int[] a, int[] b) {
				if(a[0]!=b[0])
				return -1*(a[0]-b[0]);
				return a[1]-b[1];
			}
		});
		HashSet<Integer> done=new HashSet<Integer>();
		for(int i=0; i<N; i++) {
			if(!done.contains(skills[i][1])) {
				ArrayList<Integer> canUse=new ArrayList<Integer>();
				int leftMost=Math.max(1+skills[i][1]-K, 0);
				loop:
				for(int j=Math.max(1+skills[i][1]-K,0); j<Math.min(skills[i][1]+K, N); j++) {
					if(done.contains(j)&&j<skills[i][1]) {
						leftMost=j+1;
						canUse=new ArrayList<Integer>();
					}
					else if(done.contains(j)){
						break loop;
					}
					else {
						canUse.add(j);
					}
				}
				if(canUse.size()<K) {
					for(int j=leftMost; j<leftMost+canUse.size(); j++) {
						newSkills[j]=skills[i][0];
						done.add(j);
					}
				}
				else {
					int maxDiff=0;
					for(int j=leftMost; j<leftMost+K; j++) {
						maxDiff+=skills[i][0]-newSkills[j];
					}
					int curLeftMost=leftMost;
					for(int j=leftMost+1; j<=leftMost+canUse.size()-K; j++) {
						int diff=0;
						for(int k=j; k<j+K; k++) {
							diff+=skills[i][0]-newSkills[k];
						}
						if(diff>maxDiff) {
							maxDiff=diff;
							curLeftMost=j;
						}
					}
					for(int j=curLeftMost; j<curLeftMost+K; j++) {
						newSkills[j]=skills[i][0];
						done.add(j);
					}
				}
			}
		}
		int sum=0;
		for(int i=0; i<N; i++) {
			sum+=newSkills[i];
		}
		out.println(sum);
		out.close();
	}
}
