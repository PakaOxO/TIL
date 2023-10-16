import java.util.Arrays;

public class MergeSort {
	static int[] temp;

	static void mergeSort(int[] arr, int left, int right) {
		if (left == right) return;
		
		int mid = (left + right) / 2;
		mergeSort(arr, left, mid);
		mergeSort(arr, mid + 1, right);
		merge(arr, left, mid, right);
	}
	
	static void merge(int[] arr, int left, int mid, int right) {
		int L = left;
		int R = mid + 1;
		int pointer = left;
		
		while (L <= mid && R <= right) {
			if (arr[L] <= arr[R]) {
				temp[pointer++] = arr[L++];
			} else {
				temp[pointer++] = arr[R++];
			}
		}
		
		if (L <= mid) {
			while (L <= mid) temp[pointer++] = arr[L++];
		} else {
			while (R <= right) temp[pointer++] = arr[R++];
		}
		
		for (int i=left; i<=right; i++) {
			arr[i] = temp[i];
		}
	}
	
	public static void main(String[] args) {
		int[] arr = { 1, 5, 100, 3, 5, 29, 10, 29, 30, 45, 29, 54 };
		
		temp = new int[arr.length];
		mergeSort(arr, 0, arr.length - 1);
		
		System.out.println(Arrays.toString(arr));
	}

}
