## String.prototype.substring() & slice()

```javascript
// substring()
str.substring(startIndex, optional: [endIndex]);

// slice()
str.slice(startIdx, optional: [endIndex]);
```

<br>

### 특징

- 문자열을 시작 인덱스부터 끝 인덱스까지 자른 문자열을 반환합니다.
- 끝 인덱스는 생략 가능하며, 생략했다면 시작 인덱스부터 끝까지의 문자열을 반환합니다.
- 원본 문자열은 변경되지 않습니다.

<br>

> ### ❓ substring() vs slice()
>
> `substring()`과 `slice()`의 차이점 중 하나는 `substring()`의 경우, 시작 인덱스가 끝 인덱스보다 작게 입력이 되었다면 두 파라미터의 위치를 뒤집어 문자열 자르기를 실행하는 반면, `slice()`는 빈 문자열("")을 반환한다는 점입니다.

<br>

> ### ❗ substr()
>
> `substr()` 메서드와 유사하나 `substr()`은 두 번째 파라미터로 시작 인덱스 기준 다른 문자의 개수를 입력 받습니다. 또한, `substr()`은 웹 표준을 지원하지만 웹 표준과 맞지 않는 부분이 있어 공식 측에서 `substring()`의 사용을 권장하고 있습니다.

<br>
