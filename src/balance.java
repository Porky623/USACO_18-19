import java.util.*;
import java.io.*;
public class balance {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("balance.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("balance.out")));
		int N=Integer.parseInt(f.readLine().trim());
		StringTokenizer st=new StringTokenizer(f.readLine());
		int[] board=new int[2*N];
		for(int i=0; i<2*N; i++) {
			board[i]=Integer.parseInt(st.nextToken());
		}
		int[] numOne1=new int[N];
		int[] numOne2=new int[N];
		int[] numZero1=new int[N];
		int[] numZero2=new int[N];
		LinkedList<Integer> nearZero1=new LinkedList<>();
		LinkedList<Integer> nearZero2=new LinkedList<>();
		LinkedList<Integer> nearOne1=new LinkedList<>();
		LinkedList<Integer> nearOne2=new LinkedList<>();
		if (board[0]==1) {
			numOne1[0]=1;
			nearOne1.add(0);
		}
		else {
			numZero1[0]=1;
			nearZero1.add(0);
		}
		if(board[N]==1) {
			numOne2[0]=1;
			nearOne2.add(0);
		}
		else {
			numZero2[0]=1;
			nearZero2.add(0);
		}
		long inv1=0,inv2=0;
		for(int i=1; i<N; i++) {
			numOne1[i]=numOne1[i-1];
			numOne2[i]=numOne2[i-1];
			numZero1[i]=numZero1[i-1];
			numZero2[i]=numZero2[i-1];
			if(board[i]==1) {
				numOne1[i]++;
				nearOne1.addFirst(i);
			}
			else {
				inv1+=numOne1[i];
				numZero1[i]++;
				nearZero1.addFirst(i);
			}
			if(board[N+i]==1) {
				numOne2[i]++;
				nearOne2.addFirst(i);
			}
			else {
				inv2+=numOne2[i];
				numZero2[i]++;
				nearZero2.addFirst(i);
			}
		}
		long diff=Math.abs(inv1-inv2);
		if(inv1<inv2) {
			long cur=0;
			long min=diff;
			long x=diff;
			if(numOne1[N-1]>numZero2[N-1]) {
				while(cur<diff) {
					if(board[N-1]==1&&board[N]==0) {
						x+=numZero2[N-1]-numOne1[N-1];
						board[N-1]=0;
						board[N]=1;
						cur++;
						long newDiff=Math.abs(x)+cur;
						if(newDiff<min)
							min=newDiff;
					}
					else if(board[N-1]==1) {
						board[N]=0;
						int b=nearZero2.removeLast();
						x-=b;
						cur+=b;
					}
					else if(board[N]==0) {
						board[N-1]=1;
						int a=nearOne1.removeFirst();
						x+=N-a-1;
						cur+=N-a-1;
					}
					else {
						board[N-1]=1;
						board[N]=0;
						int a=nearOne1.removeFirst();
						int b=nearZero2.removeLast();
						x+=N-a-1-b;
						cur+=(N-a-1+b);
					}
				}
			}
			else {
				while(cur<diff) {
					if(board[N-1]==0&&board[N]==1) {
						x+=numOne1[N-1]-numZero2[N-1];
						cur++;
						long newDiff=Math.abs(x)+cur;
						if(newDiff<min)
							min=newDiff;
						board[N-1]=1;
						board[N]=0;
					}
					else if(board[N-1]==0) {
						board[N]=1;
						int b=nearOne2.removeLast();
						x+=b;
						cur+=b;
					}
					else if(board[N]==1) {
						board[N-1]=0;
						int a=nearZero1.removeFirst();
						x-=N-a-1;
						cur+=N-a-1;
					}
					else {
						int a=nearZero1.removeFirst();
						int b=nearOne2.removeLast();
						board[N-1]=0;
						board[N]=1;
						cur+=(N-a-1+b);
						x+=b-N+a+1;
					}
				}
			}
			out.println(min);
		}
		else if(inv1==inv2) {
			out.println(0);
		}
		else {
			long cur=0;
			long min=diff;
			long x=diff;
			if(numOne1[N-1]<numZero2[N-1]) {
				while(cur<diff) {
					if(board[N-1]==0&&board[N]==1) {
						x+=numZero2[N-1]-numOne1[N-1];
						cur++;
						long newDiff=Math.abs(x)+cur;
						if(newDiff<min)
							min=newDiff;
						board[N-1]=1;
						board[N]=0;
					}
					else if(board[N-1]==0) {
						board[N]=1;
						int b=nearOne2.removeLast();
						x+=b;
						cur+=b;
					}
					else if(board[N]==1) {
						board[N-1]=0;
						int a=nearZero1.removeFirst();
						x-=N-a-1;
						cur+=N-a-1;
					}
					else {
						int a=nearZero1.removeFirst();
						int b=nearOne2.removeLast();
						board[N-1]=0;
						board[N]=1;
						cur+=(N-a-1+b);
						x+=N-a-1-b;
					}
				}
			}
			else {
				while(cur<diff) {
					if(board[N-1]==1&&board[N]==0) {
						inv1+=numOne1[N-1];
						inv2+=numZero2[N-1];
						board[N-1]=0;
						board[N]=1;
						cur++;
						long newDiff=Math.abs(inv1-inv2)+cur;
						if(newDiff<min)
							min=newDiff;
					}
					else if(board[N-1]==1) {
						board[N]=0;
						int b=nearZero2.removeLast();
						x-=b;
						cur+=b;
					}
					else if(board[N]==0) {
						board[N-1]=1;
						int a=nearOne1.removeFirst();
						x+=N-a-1;
						cur+=N-a-1;
					}
					else {
						board[N-1]=1;
						board[N]=0;
						int a=nearOne1.removeFirst();
						int b=nearZero2.removeLast();
						inv1-=(N-a-1);
						inv2-=b;
						cur+=(N-a-1+b);
					}
				}
			}
			out.println(min);
		}
		out.close();
	}
}
