import java.io.*;
import java.util.*;

/* 테스트 케이스
7 11
0 1 32
0 2 31
0 5 60
0 6 51
1 2 21
2 4 46
2 6 25
3 4 34
3 5 18
4 5 40
4 6 51
 */

public class Kruskal {
	static class Edge {
		int from, to, weight;
		
		Edge(int from, int to, int weight) {
			this.from = from;
			this.to = to;
			this.weight = weight;
		}
		
		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}
	}

	/* 대표자 노드를 담을 배열 */
	static int[] p;
	
	/* 대표자 노드를 찾는 메서드 */
	static int findSet(int x) {
		if (p[x] != x) p[x] = findSet(p[x]);
		
		return p[x];
	}
	
	/* 두 집합의 합집합 */
	static void union(int x, int y) {
//		p[findSet(y)] = findSet(x);
		p[y] = x; // 미리 findSet으로 대표 노드를 파라미터로 받은 경우
	}
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		/* 대표 노드 선정 */
		p = new int[V];
		for (int i=0; i<V; i++) {
			p[i] = i;
		}
		
		Edge[] edges = new Edge[E];
		for (int i=0; i<E; i++) {
			st = new StringTokenizer(br.readLine());
			Edge edge = new Edge(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
			edges[i] = edge;
		}
		
		/* 간선의 가중치 오름차순 정렬 */
		Arrays.sort(edges, new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				return o1.weight - o2.weight;
			}
		});
		
		for (Edge e : edges) {
			System.out.println(e);
		}
		
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
		
		System.out.println(total);
	}

}

