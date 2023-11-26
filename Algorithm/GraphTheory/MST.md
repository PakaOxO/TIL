## **MST(Minimum Spanning Tree), 최소신장트리**
&nbsp;&nbsp;신장트리는 그래프의 모든 정점과 간선의 부분 집합으로 구성되는 트리로 최소신장트리는 이러한 <u>**신장트리 중에서 사용된 간선들의 가중치 합이 최소가 되는 트리**</u>이다. 최소신장트리는 주로 도로, 통신, 유통망을 설치하기 위한 비용을 최소화 하기 위해 사용된다.
<br/><br/>

### 특징

- 무방향 가중치 그래프
- 그래프 가중치 합이 최소
- N개의 정점과 N-1개의 간선으로 이루어짐
- 사이클이 포함되어서는 안됨
- 대표적인 방법으로 Kruskal, Prim 등이 있음

<br>


### **Kruskal Algorithm**
- 간선 중심의 MST 알고리즘
- 간선을 하나씩 선택하여 MST를 찾는 알고리즘
- 그리디(greedy)한 방식으로 MST를 찾으며 이미 증명된 방식임

1. 모든 간선을 가중치에 따라 오름차순으로 정렬
2. 가중치가 가장 낮은 간선부터 선택하면서 트리를 증가 (만약 사이클이 존재하면 다음으로 가중치가 낮은 간선을 선택, 대표 노드가 같으면 사이클 존재)
3. n-1ㄴ의 간선이 선택되면 종료
<br/><br/>


### Kruskal 알고리즘 구현 (Java) (code/Kruskal.java)


```java
/* 대표자 노드를 담을 배열 */
static int[] p;

/* 대표자 노드를 찾는 메서드 */
static int findSet(int x) {
    if (p[x] != x) p[x] = findSet(p[x]);
    
    return p[x];
}

/* 두 집합의 합집합 */
static void union(int x, int y) {
//	p[findSet(y)] = findSet(x);
    p[y] = x; // 미리 findSet으로 대표 노드를 파라미터로 받은 경우
}

/**
 * main 함수
 */
/* MST를 생성하기 위한 간선 선택, V-1개 */
int selCnt = 0;
int total = 0;
for (int i=0; i<E; i++) {
    Edge e = edges[i];
    int px = findSet(e.from);
    int py = findSet(e.to);
    
    if (px != py) {
        union(px, py);
        selCnt++;
        total += edges[i].weight;
    }
    if (selCnt == V - 1) break;
}
		
```

<br>

### **Prim Algorithm**
- 정점 중심의 MST 알고리즘
- 하나의 정점에 연결된 간선들 중에 하나씩 선택하며 MST를 만들어가는 방식

1. 임의의 정점을 하나 선택
2. 선택한 정점과 인접한 정점 중 최소 비용으로 연결된 정점을 선택
3. 모든 정점이 선택될 때까지 위의 과정을 반복

<br>

### **Prim 알고리즘 구현, 인접 행렬 (Java)** (code/Prim_adjArr.java)
<div markdown="1">

```java
// 임의의 한점을 선택
dist[0] = 0;
p[0] = -1; // 루트가 없으므로 -1로 세팅

// Prim 알고리즘 (인접행렬 사용)
for (int i=0; i<V-1; i++) {
    int min = INF;
    int idx = -1;
    // 가중치가 가장 작으면서 이전에 선택되지 않은 정점 선택
    for (int j=0; j<V; j++) {
        if (isVisited[j] || dist[j] >= min) continue;
        min = dist[j];
        idx = j;
    }
    isVisited[idx] = true; // 선택된 정점 방문처리
    
    // 선택된 노드에서 갈 수 있는 노드의 dist값을 해당 간선의 가중치 값으로 초기화
    for (int j=0; j<V; j++) {
        if (isVisited[j] || adjArr[idx][j] == 0 || dist[j] <= adjArr[idx][j]) continue;
        dist[j] = adjArr[idx][j];
        p[j] = idx;
    }
}
		
```

<br>

### Prim 알고리즘 구현, 우선순위큐 (Java)(code/Prim_priorityQueue.java)

```java
boolean[] isVisited = new boolean[V]; // 방문했던 노드 체크 배열
// 임의의 노드 선택
isVisited[0] = true;

PriorityQueue<Edge> pq = new PriorityQueue<>();
pq.addAll(adjList[0]);

int cnt = 1; // 뽑은 노드 개수
int answer = 0;
while (cnt < V) {
    Edge edge = pq.poll();
    if (isVisited[edge.end]) continue; // 이미 뽑았던 노드는 패스
    
    isVisited[edge.end] = true;
    pq.addAll(adjList[edge.end]); // 다음 노드(edge.end)에서 다시 탐색
    
    answer += edge.weight;
    cnt++;
}
```
