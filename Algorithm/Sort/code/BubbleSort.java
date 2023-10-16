package sort;

import java.util.Arrays;

class BubbleSort {
	private int[] arr;
	
	public void swap(int left, int right) {
		int temp = arr[left];
		arr[left] = arr[right];
		arr[right] = temp;
	}
	
	/* 0이면 오름차순, 1이면 내림차순*/
	public int[] sort(int[] arr, int type) {
		int N = arr.length;
		this.arr = arr;
		
		for (int i=N-1; i>0; i--) {
			if (type == 0) {
				for (int j=0; j<i; j++) {					
					if (this.arr[j] > this.arr[j+1]) swap(j, j+1);
				}
			} else if (type == 1) {
				for (int j=0; j<i; j++) {					
					if (this.arr[j] < this.arr[j+1]) swap(j, j+1);
				}
			}
		}
		return this.arr;
	}
}

public class BubbleSort_Test {

	public static void main(String[] args) {
		int[] arr = { 3, 2, 5, 4, 7, 34, 2, 5, 10, 0 };
		int[] arr2 = {1, 4, 2};
		BubbleSort b = new BubbleSort();
		System.out.println(Arrays.toString(arr));
		System.out.println(Arrays.toString(b.sort(arr, 0)));
		System.out.println(Arrays.toString(arr2));
		System.out.println(Arrays.toString(b.sort(arr2, 0)));
	}

}
