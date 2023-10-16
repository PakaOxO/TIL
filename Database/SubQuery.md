# 📄 **SQL SubQuery**

<p align="center">
    <img style="width: 90%" src="images/sql_logo.png" alt="sql_logo">
</p></br>

## **SubQuery**

- <u>**하나의 SQL문 안에 포함되어 있는 SQL문**</u>
- 서브 쿼리를 포함하는 SQL을 외부 쿼리(Outer Query) 또는 메인 쿼리라고 부르며, 서브 쿼리는 내부 쿼리(Inner Query)라고 부른다
- **서브 쿼리 종류** : 중첩 서브쿼리, 인라인 뷰, 스칼라 서브 쿼리
  <br/><br/>

### **SubQuery 특징**

- 서브쿼리는 ()로 감싸서 사용
- 서브쿼리는 단일 행 또는 다중 행 비교 연산자와 함께 사용가능
- **서브쿼리를 포함할 수 있는 SQL** : SELECT, FROM, WHERE, HAVING, ORDER BY, VALUES, SET
  <br/><br/>

### **Nested Subquery**

- WHERE절에 작성하는 서브쿼리
- 서브쿼리 결과가 다중 행을 반환하면 IN, ANY, ALL 연산자와 함께 사용
  <br/>

```sql
-- 매니저가 KING인 직원
SELECT empno, ename, deptno, job
FROM emp
WHERE mgr = (SELECT empno
             FROM emp
             WHERE ename = "KING")
;

-- 20번 부서의 평균 급여보다 급여가 많은 사원 조회
SELECT empno, ename, deptno, sal
FROM emp
WHERE sal > (SELECT avg(sal)
             FROM emp
             WHERE deptno = 20
            )
;

-- SALESMAN인 직원들 중 최소 한명 이상보다 많은 급여를 받는 직원
SELECT empno, ename, deptno, sal
FROM emp
WHERE sal > ANY (SELECT sal
                 FROM emp
                 WHERE job = 'SALESMAN')
;

-- SALESMAN인 모든 직원들 보다 급여(커미션 포함)를 많이 받는 사원
SELECT empno, ename, deptno, sal
FROM emp
WHERE sal > ALL (SELECT sal + IFNULL(COMM, 0)
                 FROM emp
                 WHERE job = 'SALESMAN')
AND JOB <> 'SALESMAN'
;

-- 직원이 최소 한명 이상 근무하는 부서
SELECT deptno, dname, loc
FROM dept
WHERE deptno IN (SELECT DISTINCT deptno FROM emp)
;

-- 이름이 FORD인 사원과 매니저및 부서가 같은 사원
SELECT empno, ename, deptno, dname, mgr
FROM emp
WHERE (mgr, deptno) IN (SELECT mgr, deptno
                        FROM emp
                        WHERE ename = 'FORD')
AND ename <> 'FORD'
;

-- 소속 부서의 평균 급여보다 많은 급여를 받는 사원 (상호연관 서브쿼리, Corelated subquery)
SELECT empno, ename, dname, sal
FROM emp e1
WHERE sal > (SELECT avg(sal) FROM emp WHERE deptno = e1.deptno)
;
```

<br/>

### **Inline View**

- FROM절에 작성하는 서브쿼리
- 동적으로 생성된 테이블로 사용 가능
- SQL문이 실행될 때만 생성되는 테이블이므로 DB에 저장되지 않기 때문에 동적 뷰라고도 부름
- 뷰(View)와 같은 역할
  <br/>

```sql
-- 모든 사원에 대하여 사원 정보 및 사원이 소속된 부서의 평균 급여 조회(이름 오름차순)
SELECT e.ename, e.dname, e.sal, d.avgSal
FROM emp e, (SELECT deptno, AVG(sal) AS avgSal FROM emp GROUP BY deptno) AS d
WHERE e.deptno = d.deptno
ORDER BY e.ename
;
```

<br/>

### **Scalar Subquery**

- SELECT문에 작성하는 서브쿼리
  <br/>

```sql
-- 사원 정보 및 소속 부서의 평균 급여 조회
SELECT ename, deptno, dname, sal, (SELECT AVG(sal)
                                    FROM emp
                                    WHERE deptno = e.deptno)
FROM emp e
;
```

<br/><br/>

