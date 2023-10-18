## 병합 정렬(Merge Sort)

&nbsp;&nbsp;`병합 정렬`은 정렬할 배열을 둘로 나눠가며 각 부분 배열을 차례로 정렬하면서 최종적인 정렬된 배열을 얻는 분할-정복의 방법론을 따릅니다. [안정정렬](./Stable&UnStableSort.md)이며 정렬의 시간 복잡도는 $O(NlogN)$입니다.

<br>

### **Merge Sort Process**

1. 정렬할 배열 입력
2. 입력 받은 배열을 반으로 나누어 가며 최소 단위의 배열이 될 때까지 나눈다(분할)
3. 나누어진 2개의 부분 배열을 선택하여 정렬을 진행하며 병합(정복)
4. 최종적으로 정렬된 배열 출력

<br>

### **병합정렬 구현 (Java)** &nbsp;[[전체코드]](code/MergeSort.java)

```javascript
/* 병합정렬 */
function mergeSort(arr, left, right) {
  if (left === right) return arr;
  const mid = Math.floor((left + right) / 2);
  const sortedLeft = mergeSort(arr, left, mid);
  const sortedRight = mergeSort(arr, mid + 1, right);

  return merge([...sortedLeft, ...sortedRight], left, mid, right);
}

/* 병합 */
function merge(arr, left, mid, right) {
  const merged = [];
  let [lPointer, rPointer] = [left, mid + 1];
  while (lPointer <= mid && rPointer <= right) {
    if (arr[lPointer] <= arr[rPointer]) {
      merged.push(arr[lPointer++]);
    } else {
      merged.push(arr[rPointer++]);
    }
  }

  while (lPointer <= mid) merged.push(arr[lPointer++]);
  while (rPointer <= mid) merged.push(arr[rPointer++]);

  return merged;
}
```

```java

/* 병합정렬 */
static void mergeSort(int[] arr, int left, int right) {
    if (left == right) return;

    int mid = (left + right) / 2;
    mergeSort(arr, left, mid);
    mergeSort(arr, mid + 1, right);
    merge(arr, left, mid, right);
}

/* 병합 */
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
```

