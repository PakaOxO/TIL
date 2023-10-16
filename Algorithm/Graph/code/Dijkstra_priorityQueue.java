
import java.io.*;
import java.util.*;


/* 우선 순위 큐로 구현한 다익스트라 */
public class Dijkstra_priorityQueue {
	static class Node implements Comparable<Node> {
		int v, weight;
		
		Node(int v, int weight) {
			this.v = v;
			this.weight = weight;
		}
		
		@Override
		public int compareTo(Node o) { // 우선 순위 큐를 사용해야 하기 때문에 CompareTo 구현
			// TODO Auto-generated method stub
			return this.weight - o.weight;
		}
	}
	
	static final int INF = Integer.MAX_VALUE;
	static int V, E;
	static List<Node>[] adjList;
	static int[] dist;
	
	static void dijkstra(int s) {
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
