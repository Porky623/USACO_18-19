import java.util.*;
import java.io.*;
public class sleepy {

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		BufferedReader f=new BufferedReader(new FileReader("sleepy.in"));
		PrintWriter out=new PrintWriter(new BufferedWriter(new FileWriter("sleepy.out")));
		StringTokenizer st=new StringTokenizer(f.readLine());
		int N=Integer.parseInt(st.nextToken());
		st=new StringTokenizer(f.readLine());
		int x=Integer.parseInt(st.nextToken());
		LinkedNode head=new LinkedNode(0,0,x,null,null);
		LinkedNode tail=null;
		int curInd=0;
		LinkedNode prev=head;
		for(int i=1; i<N; i++) {
			x=Integer.parseInt(st.nextToken());
			if(x==prev.val+prev.end-prev.start+1) {
				prev.end++;
			}
			else {
				prev.setRange();
				curInd++;
				prev.next=new LinkedNode(curInd,curInd,x,prev,null);
				prev=prev.next;
			}
			if(i==N-1)
				tail=prev;
		}
		tail.setRange();
		curInd=0;
		StringBuilder steps=new StringBuilder();
		int count=0;
		while(head!=tail) {
			LinkedNode cur=head;
			head=cur.next;
			LinkedNode curEnd=tail;
			int change=0;
			if(cur.val>curEnd.val) {
				for(int i=cur.start; i<=cur.end; i++) {
					steps.append(N-1);
					steps.append(" ");
					count++;
				}
				if(curEnd.end()+1==cur.val) {
					curEnd.end+=cur.range;
				}
				else {
					curEnd.next=cur;
					cur.next=null;
					cur.prev=cur;
					tail=cur;
				}
				continue;
			}
			else {
				while(curEnd.val>cur.end()&&(curEnd.prev.end()<cur.val)) {
					change+=curEnd.range+1;
					curEnd=curEnd.prev;
				}
				change+=curEnd.range+1;
			}
			for(int i=cur.start; i<=cur.end; i++) {
				steps.append(N-1-change);
				steps.append(" ");
				count++;
			}
			if(curEnd.prev.end()+1==cur.val) {
				curEnd.prev.end+=cur.range+1;
				cur=curEnd;
				curEnd.prev.setRange();
				if(cur.prev==null)
					head=cur;
			}
			else {
				curEnd.prev.next=cur;
				cur.prev=curEnd.prev;
			}
			if(curEnd.val-1==cur.end()) {
				cur.end+=(curEnd.range+1);
				cur.next=curEnd.next;
				if(cur.next==null){
					tail=cur;
				}
				cur.setRange();
			}
			else {
				curEnd.prev=cur;
				cur.next=curEnd;
			}
		}
		out.println(count);
		out.println(steps.toString().substring(0,steps.toString().length()-1));
		out.close();
	}
	static class LinkedNode{
		public int start,end,val,range;
		public LinkedNode prev, next;
		public LinkedNode(int start,int end,int val,LinkedNode prev, LinkedNode next) {
			this.val=val;
			this.start=start;
			this.end=end;
			this.prev=prev;
			this.next=next;
		}
		public void setRange() {
			range=end-start;
		}
		public int end() {
			return val+range;
		}
	}
}
