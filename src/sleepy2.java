import java.util.*;
import java.io.*;
public class sleepy2 {

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
				curInd++;
				prev.next=new LinkedNode(curInd,curInd,x,prev,null);
				prev=prev.next;
			}
			if(i==N-1)
				tail=prev;
		}
		curInd=0;
		StringBuilder steps=new StringBuilder();
		int count=0;
		while(head!=tail) {
			LinkedNode cur=head;
			head.prev=null;
			head=cur.next;
			LinkedNode curEnd=tail;
			while(curEnd.val>cur.end()&&(curEnd.prev.end()<cur.val)) {
				curEnd=curEnd.prev;
			}
			for(int i=cur.start; i<=cur.end; i++) {
				steps.append(curEnd.end-cur.start);
				steps.append(" ");
				count++;
			}
			int inc=curEnd.end-cur.start+1;
			cur.end+=inc;
			cur.start+=inc;
			LinkedNode temp=curEnd;
			while(temp!=null) {
				temp.start+=inc;
				temp.end+=inc;
				temp=temp.next;
			}
			if(curEnd.prev.end()+1==cur.val) {
				curEnd.prev.end+=cur.end-cur.start;
				cur=curEnd;
				if(cur.prev==null)
					head=cur;
			}
			else {
				curEnd.prev.next=cur;
				cur.prev=curEnd.prev;
			}
			if(curEnd.val-1==cur.end()) {
				cur.end+=(curEnd.end-curEnd.start);
				cur.next=curEnd.next;
				if(cur.next==null){
					tail=cur;
				}
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
		public int start,end,val;
		public LinkedNode prev, next;
		public LinkedNode(int start,int end,int val,LinkedNode prev, LinkedNode next) {
			this.val=val;
			this.start=start;
			this.end=end;
			this.prev=prev;
			this.next=next;
		}
		public int end() {
			return val+end-start;
		}
	}
}
