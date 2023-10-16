📄 **버블 정렬**
===================
## **Bubble Sort**
&nbsp;&nbsp;버블 정렬은 시간 복잡도 $O(N^2)$의 정렬 방식으로 선택 정렬과 함께 가장 단순한 정렬 방식이다. 정렬은 <u>앞에서부터 2개씩 서로를 비교</u>해 나가며 오름차순 정렬일 경우 큰 숫자가 뒤로 오도록 Swap해 나아가는데 이 과정이 거품이 보글보글 이는 것 같다하여 버블 정렬이라 한다고 한다.
<br/><br/>

<p align="center">
    <img src="https://upload.wikimedia.org/wikipedia/commons/c/c8/Bubble-sort-example-300px.gif" alt="sequentialList">
</p></br>

&nbsp;&nbsp;이미 정렬되어 있는 데이터에서 정렬 과정에서 swap이 발생하지 않으므로 버블 정렬의 최소 시간복잡도는 $O(N)$이지만 대부분의 경우에서 $O(N^2)$의 시간복잡도를 가지기 때문에 자주 사용되는 정렬 방식은 아니다. 버블정렬의 장단점을 정리하면 다음과 같다.
<br/><br/>

#### **장점**
- 단순한 로직으로 빠르게 구현할 수 있다.

#### **단점**
- 매 비교 마다 swap이 발생해 효율이 매우 나쁘다.
<br/><br/>

### **버블정렬 구현 (Java)** &nbsp;[[전체코드]](code/BubbleSort.java)
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