📄 **Dijkstra Algorithm**
===================
## **Dijkstra, 다익스트라**
&nbsp;&nbsp;최단 경로란 간선의 가중치가 있는 그래프에서 두 정점 사이의 경로들 중에 <u>**간선 가중치 합이 최소가 되는 경로**</u>를 의미한다. 이러한 최단 경로를 구하는 알고리즘에는 다익스트라와 벨만-포드 알고리즘이 있는데 벨만-포드(Bellman-Ford)는 음의 가중치를 허용하는 반면 <u>다익스트라에서는 음의 가중치를 허용하지 않는다</u>. 이 둘과 별개로 모든 정점들에 대한 최단 경로를 구하는 플로이드-워셜(Floyd-Warshall) 알고리즘도 있다.
<br/><br/>

### **다익스트라 특징**
- 시작정점에서 거리가 최소인 정점을 선택해 나가면서 최단 경로를 구하는 알고리즘
- 탐욕 기법을 사용한 알고리즘으로 MST의 프림 알고리즘과 유사
- 현재의 최적해를 구하는 탐욕 기법의 특성 상 한계가 존재하는데 음의 가중치가 있는 경우에는 최적해를 구할 수 없음
- 시간 정점(s)에서 끝 정점(t)까지의 최단 경로에 정점 x가 존재하며, 전체 최단 경로는 s에서 x까지의 최단 경로와 x에서 t까지의 최단 경로로 구성된다는 아이디어에서 출발
<br/><br/>

### **동작 과정**
1. 시작 정점을 입력
2. 거리를 저장할 배열(dist)을 큰 값으로 초기화 한 뒤 시작점에서 갈 수 있는 곳의 값을 바꿔놓음
3. 아직 방문하지 않은 정점에서 가지고 있는 거리의 값과 현재 정점에서 방문하지 않은 정점까지의 가중치 합이 작다면 변경
4. 모든 정점을 방문할 때까지 반복
<br/><br/>

### **Dijkstra 알고리즘 구현, 반복문 (Java)** &nbsp;[[전체코드]](code/Dijkstra_loop.java)
<details>
<summary>코드보기</summary>
<div markdown="1">

```java
boolean[] isVisited = new boolean[V];
dist[s] = 0; // 시작 노드 s의 경로 누적값을 0으로

for (int i=0; i<V-1; i++) {
    int min = INF;
    int idx = -1;
    
    // 가장 가중치가 작은 노드 선택
    for (int j=0; j<V; j++) {
        if (isVisited[j] || min <= dist[j]) continue;
        min = dist[j];
        idx = j;
    }
    if (idx == -1) return; // 첫 노드에서 더 이상 갈 곳이 없을 때
    isVisited[idx] = true; // 선택 노드 방문처리
    
    for (int j=0; j<adjList[idx].size(); j++) {
        Node curr = adjList[idx].get(j);
        if (isVisited[curr.v] || dist[curr.v] <= dist[idx] + curr.weight) continue;
        
        dist[curr.v] = dist[idx] + curr.weight; 
    }
}
```

</div>
</details>
</br>


### **Dijkstra 알고리즘 구현, 우선순위큐 (Java)** &nbsp;[[전체코드]](code/Dijkstra_priorityQueue.java)
<details>
<summary>코드보기</summary>
<div markdown="1">

```java
PriorityQueue<Node> pq = new PriorityQueue<>();
boolean[] isVisited = new boolean[V];

dist[s] = 0; // 시작 노드 s의 경로 누적값을 0으로
pq.add(new Node(s, 0));

while (pq.size() > 0) {
    Node curr = pq.poll();
    if (isVisited[curr.v]) continue; 
    
    isVisited[curr.v] = true;
    for (Node n : adjList[curr.v]) {
        if (isVisited[n.v] || dist[n.v] <= dist[curr.v] + n.weight) continue;
        
        dist[n.v] = dist[curr.v] + n.weight;
        pq.offer(new Node(n.v, dist[n.v]));
    }
}
```

</div>
</details>
</br>