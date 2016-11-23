package net.mine.std.struct;

public class FastSort {

	public void robin(int s[],int l, int r){

		if(l < r){
			int i = l,j = r, t = s[l];
			
			while(i<j){
				
				while(i< j && s[j]>t){
					j--;
				}
				if(i<j){
					s[i] = s[j];
					i++;
				}
				
				while(i<j && s[i] <= t){
					i++;
				}
				if(i< j){
					s[j] = s[i];
					j--;
				}
			}
			s[i] = t;
			
			robin(s, l, i-1);
			robin(s, i+1, r);
		}
		
	}
	
	public static void main(String args[]){
		
		int arr[]= {9,8,3,2,1,6,10,5};
		
		FastSort s = new FastSort();
		s.robin2(arr, 0, arr.length-1);
		
		for(int i : arr){
			System.out.println(i);
		}
		
	}
	
	
	
	public void robin2(int a[],int l, int r){
		
		if(l>=r)
			return;
		
		int i=l,j=r,x=a[l];
		while(i<j){
			
			while(i<j && a[j]>x){
				j--;
			}
			if(i<j){
				a[i] = a[j];
				i++;
			}
			
			while(i<j && a[i]<=x){
				i++;
			}
			if(i<j){
				a[j] = a[i];
				j--;
			}
			a[i] = x;
			
			robin2(a, l, i-1);
			robin2(a, i+1, r);
			
		}
		
		
	}
	
	
}
