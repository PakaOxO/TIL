# 📄 **SQL**

<p align="center">
    <img style="width: 90%" src="images/sql_logo.png" alt="sql_logo">
</p></br>

## **SQL(Structured Query Language)**

&nbsp;&nbsp;관계형 데이터베이스에서 데이터 조작과 데이터 정의를 위해 사용하는 언어. 표준 SQL은 모든 DBMS에서 사용 가능.
<br/>

### **SQL 기능**

- 데이터 조회
- 데이터 삽입, 삭제, 수정
- DB Object 생성 및 변경, 삭제
- DB 사용자 생성 및 삭제, 권한 제어
  <br/>

### **SQL 특징**

- 대소문자를 구별하지 않음 (데이터의 대소문자는 구분)
- 절차적인 언어가 아닌 선언적 언어
- DBMS에 종속적이지 않음
  <br/><br/>

## **SQL 분류**

### **DDL (Data Definition Language)**

- 데이터 베이스 객체(table, view, user, index 등)의 구조를 정의
  <br/><br/>

### **DML (Data Manipulation Language)**

- 데이터 조작 언어
- 데이터베이스에서 데이터를 조작하거나 조회할 때 사용
- 테이블 레코드를 CRUD (Create, Read, Update, Delete)
  <br/><br/>

### **TCL (Transaction Control Language)**

- 트랜잭션 단위로 실행한 명령문을 적용 또는 취소
  <br/><br/>

### **DCL (Data Control Language)**

- Database, Table 접근이나 CRUD 권한 정의
- 특정 사용자에게 테이블의 검색권한 부여 또는 금지
  <br/><br/><br/>

## **DDL (Data Definition Language)**

### **데이터베이스 생성, 삭제, 사용**

```sql
CREATE DATABASE database_name;

DROP DATABASE[SCHEMA] IF EXISTS db_name;

USE db_name;
```

<br/>
- CREATE DATABASE 명령문으로 새 데이터 베이스 생성
- 데이터베이스는 여러 개의 테이블을 포함
- 데이터베이스 생성시 관리자 권한으로 생성이 필요
<br/><br/>

### **데이터베이스 문자 집합(CharacterSet) 설정**

- 데이터베이스 생성 시 설정 또는 생성 후 수정 가능
- 문자집합은 각 문자가 컴퓨터에 저장될 때 어떠한 코드로 저장되는지 규칙을 지정한 집합
- Collation은 특정 문자 집합에 의해 데이터베이스에 저장된 값들을 비교, 검색, 정렬 등의 작업을 수행할 때 사용하는 비교 규칙 집합
  <br/>

```sql
CREATE DATABASE database_name
    [[DEFAULT]] CHARACTERSET chaset_name]
    [[DEFAULT]] COLLATE collation_name]
    ;

ALTER DATABASE database_name
    [[DEFAULT]] CHARACTERSET charset_name]
    [[DEFAULT]] COLLATE collation_name]
    ;
```

<br/><br/>

### **테이블생성**

&nbsp;&nbsp;Table Schema는 <u>테이블에 저장될 데이터의 구조와 형식을 의미</u>. 컬럼명, 타입, 제약조건, 설명(description) 등. DESC 명령어를 사용해 생성된 테이블 스키마 확인 가능.
<br/><br/>

```sql
DESC[DESCRIBE] table_name;
```

```sql
CREATE TABLE table_name (
    column1 data_type [contraints][options],
    column2 data_type,
    ...
);
```

<br/>

#### **자주 사용하는 Option**

- **DETAULT** : 값이 전달되지 않았을 때 기본값 설정
- **AUTO INCREMENT** : 새 레코드가 추가될 때마다 필드 값을 자동으로 1씩 증가
- **UNSIGNED** : 숫자인 경우에만 해당. 숫자가 0 또는 양수로 제한
  <br/><br/>

#### **자주 사용하는 Contraints**

- **NOT NULL** : 각 행은 해당 열의 값 포함, null 허용 X
- **UNIQUE** : 컬럼에 중복된 값을 저장할 수 없음, null 중복은 허용
- **PRIMARY KEY** : 기본키, <u>컬럼에 중복된 값을 저장할 수 없으며, null값을 허용하지 않음</u>
- **FORIEGN KEY** : 외래키, <u>특정 테이블의 PK(Primary key) 컬럼에 저장되어 있는 값만 저장</u>. 참조키라고도 불리며, 어떤 컬럼의 어떤 데이터를 참조하는지 반드시 지정.
- **CHEC**K\*\* : 값의 범위나 종류를 지정. MySQL 8부터 지원
  <br/><br/><br/>

## **DML (Data Manipulation Language)**

### **INSERT**

- 생성시 작성한 모든 컬럼에 입력 값이 주어지면 컬럼 이름 생략 가능
- 컬럼 이름과 입력 값의 순서가 일치하도록 작성
  <br/><br/>

```sql
INSERT INTO table_name
    VALUES (col_val1, col_val2, col_val3, ...)
;

INSERT INTO table_name (col_name1, col_name2, col_name3, ...);
    VALUES (val1, val2, val3, ...)
;
```

<br/>

### **UPDATE**

- 테이블의 데이터를 수정
- WHERE 조건절로 원하는 데이터만 수정하거나 모든 데이터를 수정하는 것이 가능
  <br/><br/>

```sql
UPDATE table_name
    SET user_name = "anonymous"
;

UPDATE table_name
    SET user_name = "anonymous"
    [WHERE condition1 ...]
;
```

<br/>

### **INSERT**

- 기존의 레코드를 수정
- WHERE 절을 사용해 하나의 레코드 또는 다수의 레코드를 한번에 수정 가능
  <br/><br/>

```sql
UPDATE table_name
    SET col_name=val1, [col_name2=val2, ...]
    [WHERE condition1 ...]
;
```

<br/>

### **DELETE**

- 레코드를 삭제
- WHERE 절을 사용해 특정한 데이터만 삭제할 수 있음
  <br/><br/>

```sql
DELETE FROM table_name
    [WHERE condition1 ...]
;
```

<br/>

### **SELECT**

- 테이블에서 레코드를 조회하기 위해 사용
- WHERE 절을 사용하여 원하는 필드만 조회하는 것도 가능.
- 기존의 필드명 대신 별칭(alias)을 사용할 수 있음
- JOIN을 사용해 동일한 필드를 사용하는 여러 테이블로부터 데이터를 조인하여 가져올 수 있다.
  <br/><br/>

```sql
SELECT * FROM student;

SELECT [DISTINCT] {* | col_name | expr[alias]} FROM student
    [WHERE condition1 ...]
;
```

<br/>

- AND / OR 연산을 사용해 다양한 데이터를 조회할 수 있다.

```sql
SELECT ename, sal, deptno
    FROM emp
    WHERE deptno = 30
    AND sal >= 1500
    ;

SELECT ename, sal, deptno
    FROM emp
    WHERE deptno = 30
    OR deptNO = 20
    ;
```

<br/>

- IN, BETWEWEN 명령어를 사용해 조건에 포함되는 데이터를 가져올 수 있다.(반대는 NOT IN)

```sql
SELECT ename, sal, deptno
    FROM emp
    WHERE deptno IN ('ACCOUNTING', 'ANALYS', 'SALES')
    ;

SELECT ename, sal, deptno
    FROM emp
    WHERE sal BETWEEN 2000 AND 3000
    ;
```

<br/>

- LIKE를 사용해 컬럼의 값이 특정 패턴을 가지는지 확인 가능
- %를 사용한 와일드 카드로 다양한 조합의 값을 조회할 수 있음

```sql
SELECT ename, sal, deptno
    FROM emp
    WHERE ename LIKE 'M%'
    ;

SELECT ename, sal, deptno
    FROM emp
    WHERE ename LIKE '%E%'
    ;

SELECT ename, sal, deptno
    FROM emp
    WHERE ename LIKE '_E%'
    ;
```

<br/>

- ORDER BY를 사용해 특정 컬럼의 오름차순, 내림차순 정렬이 가능하다.

```sql
SELECT ename, sal, deptno
    FROM emp
    ORDER BY sal DESC
    ;

SELECT ename, sal, deptno
    FROM emp
    ORDER BY deptno, sal DESC
    ;
```

<br/><br/>

## **Transaction**

- 커밋(Commit) 하거나 롤백(Rollback) 할 수 있는 가장 작은 작업 단위
- START TRANSACTION : 트랜잭션 시작
- COMMIT : 트랜잭션을 종료하여 변경사항에 대해서 영구적으로 저장하는 SQL
- ROLLBACK : 트랜잭션에 의해 수행된 모든 변경사항을 실행 취소하는 SQL
- MySQL에서는 기본적으로 세션이 시작하면 autocommit 설정 상태이기 때문에 SQL 문장이 오류를 반환하지 않으면 자동으로 commit을 수행한다 (SET AUTOCOMMIT = 0 으로 끌 수 있음)
