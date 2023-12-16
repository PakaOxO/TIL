
![strongly connected components|600](../images/stronglyconnectedcomponents.png)

&nbsp;&nbsp;`SCC(Strongly Connected Component)`는 유향 그래프에서 서로 강하게 연결되어있는 부분집합을 가리킵니다. 여기서 강하게 연결 되었음은 사이클에 의해 서로 다른 두 정점이 서로에게 도달할 수 있는 경로가 직접적(혹은 간접적)으로 존재한다는 것을 의미합니다.

&nbsp;&nbsp;무향 그래프에서도 `SCC`를 정의하는 것은 가능합니다. 다만 모든 정점에 대해 양방향 이동이 가능하기 때문에 그래프 전체가 하나의 `SCC`로 묶이게 됩니다.

<br>

### Tarjan 알고리즘

&nbsp;&nbsp;`Tarjan's Algorithm`은 유향 그래프에서서 `SCC`를 그룹화하는 알고리즘 중 하나로 다음과 같은 과정을 거쳐 `SCC`를 그룹화합니다.

1. DFS 그래프 순회를 통해 각 정점을 방문하며, `이전 방문 여부`와 `SCC 생성 여부`를 확인합니다.
2. 만일 1의 