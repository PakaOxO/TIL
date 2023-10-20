## 병합 정렬(Merge Sort)

&nbsp;&nbsp;`병합 정렬`은 정렬할 배열을 둘로 나눠가며 각 부분 배열을 차례로 정렬하면서 최종적인 정렬된 배열을 얻는 분할-정복의 방법론을 따릅니다. [안정정렬](./Stable&UnStableSort.md)이며 정렬의 시간 복잡도는 $O(NlogN)$입니다.

<br>

### Merge Sort Process

1. 정렬할 배열 입력

2. 입력 받은 배열을 반으로 나누어 가며 최소 단위의 배열이 될 때까지 나눈다(분할)

3. 나누어진 2개의 부분 배열을 선택하여 정렬을 진행하며 병합(정복)

4. 최종적으로 정렬된 배열 출력

<br>

> ❗ 두 부분 배열의 정렬은 포인터 두개를 활용하여 두 배열 중 더 작은 숫자를 임시배열에 먼저 삽입하는 방식으로 정렬합니다. 이 방법을 사용하려면 각 두 배열이 이미 정렬된 상태여야 하지만, Top-down 방식의 분할-정복 로직으로 호출 스택상 먼저 코드가 실행되는 하위 배열은 이미 정렬되어있다고 생각할 수 있습니다.

<br>

### 병합정렬 코드

```javascript
function sort(arr) {
  /* 메인 로직 */
  const temp = Array.from({ length: arr.length }, () => 0);
  mergeSort(0, arr.length - 1);
  return arr;

  /* 병합정렬  */
  function mergeSort(left, right) {
    if (left === right) return;

    // mid를 기점으로 좌우로 배열을 분해하여 정렬(분할)
    const mid = Math.floor((left + right) / 2);
    mergeSort(left, mid);
    mergeSort(mid + 1, right);
    // 분할하여 정렬된 배열을 합치기(정복)
    merge(left, mid, right);
  }

  /* 병합 */
  function merge(left, mid, right) {
    let [pointer, lPointer, rPointer] = [left, left, mid + 1];

    while (lPointer <= mid && rPointer <= right) {
      if (arr[lPointer] <= arr[rPointer]) {
        temp[pointer++] = arr[lPointer++];
      } else {
        temp[pointer++] = arr[rPointer++];
      }
    }

    while (lPointer <= mid) temp[pointer++] = arr[lPointer++];
    while (rPointer <= right) temp[pointer++] = arr[rPointer++];

    for (let i = left; i <= right; i++) arr[i] = temp[i];
  }
}
```

<br>

### 장단점

**장점**

- 퀵 정렬은 데이터에 따라 최악의 경우 $O(N^2)$의 시간복잡도를 가지지만 병합정렬은 $O(NlogN)$의 시간복잡도를 보장합니다.
- $O(N^2)$의 시간복잡도를 가지는 정렬들에 비해 효율적인 정렬방법입니다.
- 중복 값의 경우, 입력된 순서가 지켜지는 [안정정렬](./Stable&UnStableSort.md)입니다.

<br>

**단점**

- 부분적으로 정렬된 데이터를 담을 임시배열(temp)이 필요하므로 메모리상의 추가 공간이 필요합니다.
- 추가 메모리를 사용하여 정렬하므로 제자리 정렬이 아닙니다.
- 배열의 길이가 큰 경우 데이터의 이동횟수가 많아지므로 시간 낭비가 발생할 수 있습니다.
