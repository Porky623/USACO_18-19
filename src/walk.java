import java.util.*;
import java.io.*;
public class walk {
	public static void main(String[] args) throws IOException{
		BufferedReader f=new BufferedReader(new FileReader("walk.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("walk.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		final int C=2019201997;
		out.println(C-84*(K-1)-48*N);
		out.close();
	}
}
