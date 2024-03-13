
  &nbsp;&nbsp;`DATE_FORMAT`은 날짜 형식의 데이터를 특정한 포맷으로 변환하기 위한 문법입니다. 첫 번째 인자로 변환할 날짜를, 두 번째 인자로 포맷을 지정해줍니다.

<br>

### DATE_FORMAT

```mysql
SELECT animal_id,
       name,
       DATE_FORMAT(datetime, '%Y-%m-%d, %HH-%MM-%ss') as datetime
FROM animal_ins
ORDER BY animal_id
```

<br>

**포맷**

| No. | Type  | Format | Result       |
| --- | ----- | ------ | ------------ |
| 1   | Year  | %Y     | 2023         |
| 2   | Year  | %y     | 23           |
| 3   | Month | %M     | 01           |
| 4   | Month | %m     | Jan          |
| 5   | Day   | %D     | 01           |
| 6   | Day   | %d     | 1st          |
| 7   | Hour  | %H     | 13(24시간 표기법) |
| 8   |       |        |              |