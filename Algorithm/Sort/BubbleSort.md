## 버블 정렬(Bubble Sort)

<p align="center">
    <img src="https://upload.wikimedia.org/wikipedia/commons/c/c8/Bubble-sort-example-300px.gif" alt="sequentialList">
</p>

&nbsp;&nbsp;`버블 정렬`은 시간 복잡도 $O(N^2)$의 정렬 방식으로 선택 정렬과 함께 가장 단순한 정렬 방식입니다. 정렬 과정은 '_앞에서부터 2개씩 서로를 비교_'해 나가며 오름차순 정렬일 경우 큰 숫자가 뒤로 오도록 Swap해 나아가는데 이 과정이 거품이 보글보글 이는 것 같다하여 버블 정렬이라고 표현합니다.

&nbsp;&nbsp;N개의 데이터에 대해 버블 정렬을 수행하면 한번의 프로세스마다 가장 큰 수가 맨 뒤(오름차순 정렬)에 위치하게 됩니다. 각 회차에 대해 N, N-1, N-2...개의 데이터를 비교해 맨 끝으로 보내기 때문에 버블 정렬의 시간 복잡도는 $O(N^2)입니다$.

<br>

**버블정렬 코드**

```java
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
```

</br>

> 💡 **최소 시간복잡도**
>
> &nbsp;&nbsp;이미 정렬되어 있는 데이터에서 정렬 과정에서 swap이 발생하지 않으므로 버블 정렬의 최소 시간복잡도는 $O(N)$이지만 대부분의 경우에서 $O(N^2)$의 시간복잡도를 가지기 때문에 자주 사용되는 정렬 방식은 아닙니다.

</br>

**장점**

- 단순한 로직으로 빠르게 구현할 수 있습니다.

**단점**

- 매 비교 마다 swap이 발생하기 때문에 타 정렬 방식 대비 정렬 효율이 좋지 않습니다.

</br>
