## 선택 정렬(Selection Sort)

<figure align="center">
    <img src="../images/Selection-Sort-Animation.gif" alt="선택정렬 애니메이션" />
</figure>

&nbsp;&nbsp;`선택 정렬`은 버블 정렬과 마찬가지로 $O(N^2)$의 시간 복잡도를 가지는 정렬 방식입니다. 다만 매 비교마다 swap이 발생하는 버블 정렬과 달리 선택 정렬은 모든 비교를 마친 뒤 단 한번의 swap만 발생하기 때문에 상대적으로 버블 정렬보다 효율적인 방식입니다.

&nbsp;&nbsp;위 애니메이션을 살펴보면 가장 먼저 첫 번째 데이터를 포함한 N개의 데이터를 비교해 가장 작은 숫자를 첫 번째 위치로 swap합니다. N개 프로세스는 각각 N, N-1, N-2... 데이터를 비교하고 한 번의 swap을 거치며, $O(N^2)$의 시간 복잡도를 가집니다.

<br>

**선택정렬 코드**

```javascript
function selectionSort(arr) {
  for (let i = 0; i < N; i++) {
    let minIdx = i;
    for (let j = i + 1; j < N; j++) {
      if (arr[j] < arr[i]) minIdx = j; // 제일 작은 숫자의 위치 갱신
    }
    swap(i, j); // 가장 작은 수를 맨 앞으로 이동
  }

  return arr;

  function swap(idx1, idx2) {
    [arr[idx1], arr[idx2]] = [arr[idx2], arr[idx1]]; // JS Array Destructuring
  }
}
```

<br>

**장점**

- 선택 정렬 역시 버블 정렬과 마찬가지로 단순한 로직으로 빠르게 구현할 수 있습니다.
- 버블 정렬에 비해 swap 횟수가 적으므로 상대적으로 빠른 정렬 방식입니다.

**단점**

- $O(N^2)$의 시간 복잡도로 일반적으로 병합 정렬과 같은 $O(logN)$ 시간 복잡도를 가지는 정렬에 비해 느립니다.

</br>
