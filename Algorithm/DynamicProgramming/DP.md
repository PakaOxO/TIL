📄 **Dynamic Programming**
===================
## **DP (Dynamic Programming)**
&nbsp;&nbsp;동적계획법은 그리디 알고리즘과 같이 최적화 문제를 해결하는 알고리즘이다. <u>먼저 작은 부분에 대한 문제를 해결하고 이를 사용해 보다 상위의 문제를 해결하여 최종적으로 원래 해결하고자 하는 문제를 해결</u>하는 방식이다. 만약 부분 문제에 대한 해가 전체에 대한 최적해가 아니라면 이는 DP로 접근할 수 없는 문제이다. (ex. 최장 경로 구하기)
<br/><br/>

### **부분최적문제 구조**
&nbsp;&nbsp;동적계획법을 사용하기 위해서는 상위 문제에 대한 최적해는 부분 문제의 최적해에 의해 정의될 수 있어야 한다. 만약 부분 문제의 최적해로 상위 문제의 최적해를 구할 수 없다면 해당 문제에서는 동적계획법을 사용할 수 없다.
<br/><br/>

### **Memoization (메모이제이션)**
&nbsp;&nbsp;컴퓨터 프로그램을 실행할 때 이전에 계산한 값을 메모리에 저장해 이후에 다시 계산되지 않도록 함으로 실행속도를 빠르게 하는 방법으로 동적계획법의 메인이 되는 방법이다.
<br/>

- 값을 저장할 추가적인 메모리 공간이 필요
- 재귀함수 호출로 인해 시스템 호출스택을 사용(Top-down)하게 되고, 실행속도 저하 또는 오버플로우가 발생할 가능성이 있음
- Bottom-up 방식으로 접근할 수 있는 문제라면 반복문을 통해 부분 문제를 해결하면서 전체 문제의 해를 구하는 것도 가능
<br/><br/>


## **동적계획법(DP) 접근 방법**
### **방법 1 : Top-down**
1. 완전탐색을 먼저 고려하여 상태공간 트리를 구상
2. 완전탐색에서 중복 호출(부분 문제)에 대해 고려
3. 해당 중복 호출을 memoization하는 방법에 대해 생각
<br/><br/>

### **방법 2 : Bottom-up**
1. 몇 개의 작은 문제들을 보며 규칙성을 확인
2. 점화식을 찾아내 점화식을 활요해 최적해 GET
<br/><br/>

### **메모이제이션 예시 1 - 피보나치 수열** &nbsp;[[전체코드]](code/Fibonachi_dp01.java)
<details>
<summary>코드보기 (Top-down)</summary>
<div markdown="1">

```java
/* 이전에 구한 값이 있다면 재귀 호출이 아닌 직접 가져다 쓰는 방식(메모이제이션) */
static int fibonachi(int n) {
    cntF1++; // n = 10 기준 17회 호출
    if (n == 1 || n == 2) return 1;
    if (f[n] == 0) f[n] = fibonachi(n - 1) + fibonachi(n - 2);
    
    return f[n];
}

/* 메모이제이션 없이 피보나치 계산에 재귀함수 호출 */
static int fibonachi2(int n) {
    cntF2++; // n = 10 기준 109회 호출
    if (n == 1 || n == 2) return 1;
    
    return fibonachi2(n - 1) + fibonachi2(n - 2);
}
```

</div>
</details>

<details>
<summary>코드보기 (Bottom-up)</summary>
<div markdown="1">

```java
/* main 함수 */
int N = Integer.parseInt(br.readLine());

f = new int[N + 1];
f[1] = 1;
f[2] = 1;

/* 이전에 만들어진 메모값들을 가져와 계산에 사용 */
for (int i=3; i<=N; i++) {
    f[i] = f[i - 1] + f[i - 2];
}
System.out.println(f[N]);
```
</div>
</details>
</br>

<br/>