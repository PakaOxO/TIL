public class BinarySearch {
	static int[] arr = { 1, 3, 5, 7, 9, 11, 14, 15, 39, 40, 44, 48, 50, 52, 60, 69, 73, 84, 99, 100 };
	
	static int binarySearch(int target, int left, int right) {
		int mid = -1;
		while (left <= right) {
			mid = (left + right) / 2;
			if (target > arr[mid]) {
				left = mid + 1;
			} else {
				if (target == arr[mid]) break;
				right = mid - 1;
			}
		}
		
		return mid;
	}

	public static void main(String[] args) {
		int result = binarySearch(3, 0, arr.length);
		System.out.println(result);
	}

}
