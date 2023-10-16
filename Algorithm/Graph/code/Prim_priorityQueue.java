import java.io.*;
import java.util.*;

/* 우선 순위 큐로 프림 알고리즘 구현 */
public class Prim_priorityQueue {
	static class Edge implements Comparable<Edge> {
		int start, end, weight;
		
		Edge(int start, int end, int weight) {
			this.start = start;
			this.end = end;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge o) { // 우선 순위 큐를 사용해야 하기 때문에 CompareTo 구현
			// TODO Auto-generated method stub
			return this.weight - o.weight;
		}
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		List<Edge>[] adjList = new ArrayList[V]; // 간선의 연결정보를 넣을 배열 (인접리스트)
		for (int i=0; i<V; i++) adjList[i] = new ArrayList<>();
		
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			adjList[start].add(new Edge(start, end, weight));
			adjList[end].add(new Edge(end, start, weight));
		}
		
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
		
		System.out.println(answer);
	}

}
