## **퀵 정렬(Quick Sort)**

&nbsp;&nbsp;정렬할 배열을 둘로 나누고 각각을 정렬한다는 점에서 분할정복 방식이며 병합정렬과 유사하나 병합정렬은 단순히 중간 지점(mid)을 기준으로 둘로 나누는 반면, 퀵 정렬은 분할할 때 `기준(pivot)` 요소를 중심으로 작은 요소는 왼쪽, 큰 요소는 오른쪽으로 위치시킵니다.

&nbsp;&nbsp;각 부분 배열에 대한 정렬이 끝난 뒤에 병합정렬은 후처리로 병합과정을 거쳐야하지만 퀵 정렬은 **후처리 과정이 없다**는 장점이 있습니다. 병합정렬과 달리 퀵 정렬은 [불안정 정렬](./Stable&UnStableSort.md)방식이며, 시간 복잡도는 $O(NlogN)$이고, 일반적인 상황에서 가장 속도가 빠른 정렬이지만 최악의 경우 시간 복잡도가 $N^2$까지 떨어질 수 있습니다.

<br>

### **Quick Sort Process(분할-정복 방식)**

1. 정렬할 배열 입력받습니다.
2. 분할: 임의의 한 점을 pivot으로 선정하여 배열을 둘로 나눕니다.
3. 정복: pivot을 기준으로 작은 값은 좌측으로 큰 값은 우측으로 이동시킵니다.

<br>

> 💡 **Pivot 선정 방식**
>
> &nbsp;&nbsp;`퀵 정렬`은 Pivot point를 정하는 여러가지 방식이 있습니다. 대표적으로 배열의 왼쪽 끝을 Pivot point로 하는 `Hoare Partition` 방식과 배열의 오른쪽 끝을 Pivot Point로 하는 `Lomuto Partion`이 있습니다.

<br/>

**퀵 정렬 구현 (Java)** &nbsp;[[전체코드]](code/QuickSort.java)

```java
static void quickSort(int left, int right) {
    if (left > right) return;
    int pivot = partition(left, right);
    quickSort(left, pivot - 1);
    quickSort(pivot + 1, right);
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
```
