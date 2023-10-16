# 📄 **SQL Join**

<p align="center">
    <img style="width: 90%" src="images/sql_logo.png" alt="sql_logo">
</p></br>

## **Join**

- 둘 이상의 테이블에서 데이터를 조회하기 위해 사용
- 일반적으로 조인조건은 PK(primary key) 및 FK(foreign key)로 구성
- PK 및 FK 관계가 없더라도 논리적인 연관만으로 Join 가능
- Join의 종류 : Inner Join, Outer Join, Self Join
  <br/><br/>

### **Inner Join**

- 조인 조건에 해당하는 컬럼 값이 양쪽 테이블에 모두 존재하는 경우에만 조회
- 동등 조인(Equi-Join)이라고도 부름
- N개의 테이블 조인시 N-1개의 조인 조건이 필요
  <br/>

```sql
SELECT ename, job, e.deptno, d.dname
FROM emp e, dept d
WHERE e.deptno = d.deptno
;

SELECT ename, job, e.deptno, d.dname
FROM emp e
INNER JOIN dept d
ON e.deptno == d.deptno
;

SELECT ename, job, deptno, dname
FROM emp
INNER JOIN dept
USING (deptno)
;
```

<br/>

### **Outer Join**

- 조인 조건에 해당하는 컬럼 값이 한쪽 테이블에만 존재하더라도 조회 기준 테이블에 따라 Left Outer Join, Right Outer Join으로 구분
  <br/>

```sql
SELECT e.name, e.deptno, d.dname
FROM emp e LEFT OUTER JOIN dept d
ON e.deptno = d.deptno
;

SELECT d.deptno, d.dname, e.ename
FROM emp e RIGHT OUTER JOIN dept d
ON d.deptno = e.deptno
;
```

<br/>

### **Self Join**

- 같은 테이블 2개를 조인
- 모든 사원의 이름, 매니저 번호, 매니저 이름 조회 시에 사원의 정보는 e1, 매니저의 정보는 e2에서 조회
  <br/>

```sql
SELECT e1.ename, e1.mgr, e2.ename
FROM emp e1, emp e2
WHERE e1.mgr = e2.empno
;

-- 매니저가 없는 경우에는 나오지 않음
SELECT e1.ename, e1.mgr, e2.ename
FROM emp e1 INNER JOIN emp e2
ON e1.mgr = e2.empno
;

SELECT e1.ename, e1.mgr, e2.ename
FROM emp e1 LEFT OUTER JOIN emp e2
ON e1.mgr = e2.empno
;
```

<br/><br/>

## **Non-equi Join (비동등 조인)**

- 조인 조건이 table의 PK, FK 등으로 정확히 일치하는 것이 아닐 때
- 모든 사원의 사번, 이름, 급여, 급여등급 조회
  <br/>

```sql
SELECT e.empno, e.ename, e.sal, sg.grade
FROM emp e, salgrade sg
WHERE e.sal  BETWEEN sg.losal AND sg.hisal
ORDER BY sg.grade DESC, e.sal DESC
;
```

<br/><br/>
