import java.io.BufferedReader;
import java.io.InputStreamReader;

/* 재귀 함수를 사용한 Top-down 방식 */
public class Fibonachi_dp01 {
	static int[] f;
	static int cntF1, cntF2;
	
	/* 이전에 구한 값이 있다면 재귀 호출이 아닌 직접 가져다 쓰는 방식(메모이제이션) */
	static int fibonachi(int n) {
		cntF1++;
		if (n == 1 || n == 2) return 1;
		if (f[n] == 0) f[n] = fibonachi(n - 1) + fibonachi(n - 2);
		
		return f[n];
	}
	
	/* 메모이제이션 없이 피보나치 계산에 재귀함수 호출 */
	static int fibonachi2(int n) {
		cntF2++;
		if (n == 1 || n == 2) return 1;
		
		return fibonachi2(n - 1) + fibonachi2(n - 2);
	}

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		f = new int[N + 1];
		f[1] = 1;
		f[2] = 1;
		
		System.out.println(fibonachi2(N) + ", 재귀함수 호출 회수 : " + cntF2);
		System.out.println(fibonachi(N) + ", 재귀함수(메모이제이션 사용) 호출 회수 : " + cntF1);
	}

}
