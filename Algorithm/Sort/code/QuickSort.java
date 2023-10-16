import java.util.Arrays;

public class QuickSort {
	static int[] sortedArr;
	
	static void swap(int i, int j) {
		int temp = sortedArr[i];
		sortedArr[i] = sortedArr[j];
		sortedArr[j] = temp;
	}
	/* Lomuto partition */
	static int partition(int left, int right) {
		int pivot = sortedArr[right];
		int pointer = left - 1;
		
		for (int i=left; i<right; i++) {
			if (sortedArr[i] <= pivot) {
				pointer++;
				swap(pointer, i);
			}
		}
		swap(pointer + 1, right);
		return pointer + 1;
	}
	
	static void quickSort(int left, int right) {
		if (left > right) return;
		int pivot = partition(left, right);
		quickSort(left, pivot - 1);
		quickSort(pivot + 1, right);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 5, 100, 3, 5, 29, 10, 29, 30, 45, 29, 54 };
		sortedArr = arr.clone();
		
		quickSort(0, arr.length-1);
		System.out.println(Arrays.toString(sortedArr));
	}

}
