import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 반복문을 사용한 Bottom-up 방식 */
public class Fibonachi_dp02 {
	static int[] f;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		f = new int[N + 1];
		f[1] = 1;
		f[2] = 1;
		
		/* 이전에 만들어진 메모값들을 가져와 계산에 사용 */
		for (int i=3; i<=N; i++) {
			f[i] = f[i - 1] + f[i - 2];
		}
		System.out.println(f[N]);
	}

}
