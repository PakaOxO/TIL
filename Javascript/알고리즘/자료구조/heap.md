## 힙(Heap)

### 특징

- 일종의 `완전이진트리` 구조입니다.
- `우선순위 큐`를 구현하기 위해 자주 사용됩니다.
- 트리구조 특성상 데이터의 추가, 삭제에 O(NlogN) 시간복잡도를 가집니다.
- 최대값 또는 최소값을 빠르게 찾는데 유리합니다.

<br>

> 많은 언어에서 라이브러리로 제공하지만 JS에서는 제공되지 않습니다...

<br>

### 구현 아이디어 1 : 부모, 자식 관계

&nbsp;&nbsp;자바스크립트에서 힙은 배열을 사용해 쉽게 구현할 수 있습니다. 이진트리의 구조상 부모-자식의 관계를 파악하는 것이 중요한데, 배열을 사용하면 부모와 자식인덱스는 현재 인덱스를 기준으로 다음과 같이 표현됩니다. (단, 0번 인덱스는 null로 채워둡니다)

```javascript
const parentIdx = Math.floor(currIdx / 2);
const left = currIdx * 2;
const right = currIdx * 2 + 1;
```

<br>

### 구현 아이디어 2 : 삽입

&nbsp;&nbsp;새로운 데이터의 삽입은 이진트리의 자식 요소를 채우는 순서를 기준으로 추가됩니다. 현재 구현하는 힙은 배열을 기반으로 구현하고 있으므로 배열의 끝에 새로운 요소를 추가하면 됩니다. 마지막 리프노드에 요소가 추가된 이후에는 힙의 종류(최대 or 최소)에 따라 부모노드와 크기를 비교하여 교체해가며 높은 상위 노드로 이동해나갑니다. 부모노드의 인덱스는 위에서 언급했던 것처럼 `(현재 인덱스) / 2`로 구할 수 있습니다.

```javascript
const offer = (val) => {
  let currIdx = tree.length + 1;
  let parentIdx = Math.floor(currIdx / 2);
  tree[currIdx] = val;

  // 트리 배열의 인덱스 0번은 null입니다
  while (parentIdx > 0 && tree[currIdx] > tree[parentIdx]) {
    swap(currIdx, parentIdx); // 두 인덱스 위치의 값을 스왑
    currIdx = parentIdx;
    parentIdx = Math.floor(parentIdx / 2);
  }
};
```

<br>

### 구현 아이디어 3 : 삭제(최대값 or 최소값 뽑기)

&nbsp;&nbsp;힙을 구현하는 이유는 `최대값` 또는 `최소값`을 빠르게 얻어내기 위해서입니다. 최대 힙의 경우 루트노드가 최대값이기 때문에 최대값을 뽑아내기 위해서는 루트노드의 값을 읽고 루트노드의 값을 하위 노드가 채워주어야 합니다. 일련이 과정은 아래와 같이 설명할 수 있습니다.

1. 루트노드와 마지막 리프노드의 값의 위치를 교체합니다.
2. 리프노드의 값을 출력합니다.(최대값 출력)
3. 루트노드는 리프노드의 값이 옮겨왔으므로 자식노드(left, right)들과 비교하며 다시 정렬을 진행합니다.

<br>

> 최대 힙에서는 상위노드의 값이 하위노드의 값보다 커야합니다. 때문에 left, right 노드 둘 중 하나와 스왑이 발생할 때, 작은 쪽으로 스왑을 시키게 될 경우 스왑되어 생긴 부모노드의 값이 한쪽 자식노드보다 작아질 수 있기 때문에 주의가 필요합니다.

<br>

```javascript
const pop = () => {
  const leafIdx = tree.length - 1;
  // 힙이 비어있으면 null 리턴
  if (leafIdx < 2) return null;

  // 마지막 노드가 이제 최대값이므로 pop하여 배열에서 제거
  const max = tree.pop();
  // 0번 인덱스는 null이므로 루트 노드는 1번 인덱스
  swap(leafIdx, 1);

  // 루트 노드로 이동한 리프 노드의 값을 힙의 조건에 맞게 자식 노드와 스왑 반복
  let currIdx = 1;
  while (currIdx < tree.length) {
    const [leftIdx, rightIdx] = [currIdx * 2, currIdx * 2 + 1];
    // left, right 둘 다 없으면 더 이상 진행 x
    if (!tree[leftIdx] && !tree[rightIdx]) break;
    // right가 없다면 left와 swap
    if (!tree[rightIdx]) {
      swap(currIdx, leftIdx);
      currIdx = leftIdx;
    } else {
      // left, right 둘 다 있다면 더 큰 값과 swap
      if (tree[leftIdx] >= tree[rightIdx]) {
        swap(currIdx, leftIdx);
        currIdx = leftIdx;
      } else {
        swap(currIdx, rightIdx);
        currIdx = rightIdx;
      }
    }
  }

  return max;
};
```
