## 삽입 정렬(InsertionSort)

<figure align="center">
    <img src="../images/Insertion_sort.png" alt="삽입정렬" />
</figure>

&nbsp;&nbsp;`삽입 정렬`은 시간 복잡도 $O(N^2)$를 가지는 정렬 방식으로 정렬 과정에서 배열의 앞 부분이 이미 정렬되었다는 가정하에 진행되는 정렬입니다. 정렬 과정이 손에 들고 있는 카드뭉치를 정렬하는 방식과 유사한데 먼저 삽입 정렬의 코드를 살펴보며 차근차근 과정을 살펴보겠습니다.

<br>

### 삽입정렬 코드

```javascript
function insertionSort(arr) {
  const N = arr.length;

  for (let i = 1; i < N; i++) {
    const temp = arr[i];
    let pointer = i;
    while (pointer > 0 && arr[pointer - 1] > temp) {
      arr[pointer] = arr[pointer - 1];
      pointer--;
    }
    arr[pointer] = temp;
  }

  return arr;
}
```

1. 먼저 삽입할 데이터(i번째 값)을 temp에 저장합니다.
2. 바로 데이터가 당겨질 위치(i)를 가리키는 pointer를 생성합니다.
3. pointer 뒤의 값과 temp의 값을 비교하여 temp보다 크다면 해당 값을 pointer로 이동시키고, pointer를 감소시킵니다.
4. 이를 반복하여 pointer 앞의 값이 temp보다 같거나 작아지는 순간, temp가 삽입될 위치는 pointer로 결정됩니다.

<br>

### 장단점

**장점**

- 구현이 단순하며, 이미 정렬되어 있는 배열에서 효율적일 수 있습니다.
- 입력 순서가 바뀌지 않는 [안정 정렬](./Stable&UnStableSort.md)입니다.
- 비교 횟수가 `버블 정렬`이나 `선택 정렬`에 비해 적을 수 있어 상대적으로 효율적입니다.

**단점**

- 일반적인 시간 복잡도는 $O(N^2)$으로 비효율적인 정렬입니다.
