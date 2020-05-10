import java.util.*;
import java.io.*;
public class poetry {
	public static long[] dp(int N,HashMap<Integer,Integer> fullNumSyll) {
		long[] ret=new long[N+1];
		ret[0]=1;
		Set<Integer> keySet=fullNumSyll.keySet();
		for(int i=1; i<=N; i++) {
			for(int syll:keySet) {
				if(i>=syll) {
					ret[i]+=ret[i-syll]*fullNumSyll.get(syll);
					ret[i]%=1000000007;
				}
			}
		}
		return ret;
	}
	public static long raise(long val, int exp) {
		long orig=val;
		for(int i=0; i<exp; i++) {
			val=(val*orig)%1000000007;
		}
		return val;
	}
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader f=new BufferedReader(new FileReader("poetry.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("poetry.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		int M=Integer.parseInt(st.nextToken());
		int K=Integer.parseInt(st.nextToken());
		HashMap<Integer,HashMap<Integer,Integer>> numSyll=new HashMap<>();
		HashMap<Integer,Integer> fullNumSyll=new HashMap<>();
		HashMap<Character,Integer> perClass=new HashMap<>();
		for(int i=0; i<N; i++) {
			st=new StringTokenizer(f.readLine());
			int si=Integer.parseInt(st.nextToken());
			int ci=Integer.parseInt(st.nextToken());
			if(!numSyll.containsKey(ci)) {
				numSyll.put(ci, new HashMap<Integer,Integer>());
			}
			if(!numSyll.get(ci).containsKey(si))
				numSyll.get(ci).put(si, 0);
			numSyll.get(ci).put(si, numSyll.get(ci).get(si)+1);
			if(!fullNumSyll.containsKey(si))
				fullNumSyll.put(si, 0);
			fullNumSyll.put(si,fullNumSyll.get(si)+1);
		}
		int[] sizes=new int[26];
		for(int i=0; i<M; i++) {
			st=new StringTokenizer(f.readLine());
			int x=(int)st.nextToken().charAt(0)-(int)'A';
			sizes[x]++;
		}
		Arrays.sort(sizes);
		long total=1;
		long[] numWaysPerLine=dp(K,fullNumSyll);
		int curExp=1;
		long[] origWays=new long[numSyll.size()];
		long[] curWays=new long[numSyll.size()];
		long curSum=0;
		Set<Integer> rhymeClasses=numSyll.keySet();
		int ind=0;
		for(int rhymeClass:rhymeClasses) {
			long curVal=0;
			Set<Integer> numSyllables=numSyll.get(rhymeClass).keySet();
			for(int numSyllable:numSyllables) {
				int a=numSyll.get(rhymeClass).get(numSyllable);
				curVal+=numWaysPerLine[K-numSyllable]*a;
				curVal%=1000000007;
			}
			origWays[ind]=curWays[ind]=curVal;
			ind++;
			curSum+=curVal;
			curSum%=1000000007;
		}
		for(int i=0; i<26; i++) {
			if(sizes[i]<curExp)
				continue;
			if(sizes[i]>curExp) {
				for(int k=curExp; k<sizes[i]; k++) {
					curSum=0;
					for(int j=0; j<curWays.length; j++) {
						curWays[j]*=origWays[j];
						curWays[j]%=1000000007;
						curSum+=curWays[j];
						curSum%=1000000007;
					}
					curExp++;
				}
			}
			total*=curSum;
			total%=1000000007;
		}
		out.println(total);
		out.close();
	}

}
