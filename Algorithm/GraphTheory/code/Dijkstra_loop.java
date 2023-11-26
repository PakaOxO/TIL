
import java.io.*;
import java.util.*;

/* 반복문으로 구현한 다익스트라 */
public class Dijkstra_loop {
	static class Node {
		int v, weight;
		
		Node(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}
	}
	
	static final int INF = Integer.MAX_VALUE;
	static int V, E;
	static List<Node>[] adjList;
	static int[] dist;
	
	static void dijkstra(int s) {
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
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		
		adjList = new ArrayList[V];
		for (int i=0; i<V; i++) adjList[i] = new ArrayList<>();
		
		dist = new int[V];
		Arrays.fill(dist, INF);
		
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int t = Integer.parseInt(st.nextToken());
			int w = Integer.parseInt(st.nextToken());
			
			adjList[s].add(new Node(t, w));
			adjList[t].add(new Node(s, w)); // 유향 그래프일 경우에는 생략
		}
		
		dijkstra(0);
		
		System.out.println(Arrays.toString(dist));
	}

}
