# 📄 **DFS**

## **DFS (Depth First Search)**

<p align="center" style="display: flex; justify-content: center;">
    <img style="width: 70%" src="../images/dfs.png" alt="dfs">
</p></br>

&nbsp;&nbsp;<u>**루트 노드에서 시작해 한 방향으로 들어갈 수 있는 depth까지 깊이 탐색**</u>하다가 더 이상 탐색할 수 없다면 가장 마지막으로 분기가 있었던 지점에서 다른 방향의 노드로 깊이 탐색을 하는 방식으로 최종적으로는 모든 노드를 탐색. 하나의 깊이에 대한 탐색이 종료되면 마지막 분기점으로 되돌아가야 하므로 <u>**Stack이나 재귀**</u>를 사용한 방법이 사용된다.
<br/>

&nbsp;&nbsp;만약 탐색하는 도중에 조건에 따라 탐색을 중단하고 이전 노드로 돌아가는 가지치기(Purning)가 실시되면 이는 <u>**백트래킹(Backtracking) 탐색**</u> 방식이다.
<br/><br/>

