import java.io.*;
import java.util.*;

/* 인접행렬로 프림 알고리즘 구현 */
public class Prim_adjArr {
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		int[][] adjArr = new int[V][V]; // 간선의 연결정보를 넣을 배열 (인접행렬)
		
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			adjArr[start][end] = weight;
			adjArr[end][start] = weight;
		}
		
		boolean[] isVisited = new boolean[V]; // 방문했던 노드 체크 배열
		int[] dist = new int[V]; // key를 저장하기 위한 배열
		int[] p = new int[V]; // 부모 정보를 저장하기 위한 배열
		
		Arrays.fill(dist, INF);
		
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
		
		int answer = 0;
		for (int i=0; i<V; i++) {
			answer += dist[i]; // 모든 정점에 어떤 간선(가중치 작은 것)을 선택해서 이동했는지 체크 되었으므로 모두 합
		}
		System.out.println(answer);
	}

}
