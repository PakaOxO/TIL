
&nbsp;&nbsp;프로그래머스에서 간만에 SQL 문제를 풀며 복습하던 중, `Null`인 값에 대해 특정 값으로 치환해 출력하는 문제에서 `NVL`을 사용하려다 오류가 발생했습니다. 찾아보니 `MySQL`에서는 `NVL`이 존재하지 않고 대신 `IFNULL`을 사용하면 된다고 합니다.

<br>

### IFNULL

&nbsp;&nbsp;`IFNULL` 문법은 `NVL`과 동일합니다. NVL 괄호 내부에 좌측엔 필드명을, 우측엔 해당 필드의 값이 `Null`일 경우 치환해줄 값을 넣어주면 됩니다.

```sql
SELECT animal_type,
       IFNLL(name, 'No name') as name,
       age
FROM animal_ins
ORDER BY animal_id
;
```

