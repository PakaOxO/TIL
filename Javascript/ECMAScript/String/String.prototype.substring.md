
### 기본 표현식
```javascript
str.substring(startIndex, optional: [endIndex]);
```

<br>

### 특징
- 문자열을 시작 인덱스부터 끝 인덱스까지 자른 문자열을 반환합니다.
- 끝 인덱스는 생략 가능하며, 생략했다면 시작 인덱스부터 끝까지의 문자열을 반환합니다.
- 원본 문자열은 변경되지 않습니다.

<br>

>[!tip] **String.prototype.substr**
>
> `substring`과 유사하나 `substr`은 두 번째 파라미터로 시작 인덱스를 기준으로 다음으로 올 문자의 개수를 입력 받습니다. 또한, `substr`은 웹 표준을 지원하긴 하지만 웹 표준과 맞지 않는 부분이 있어 공식 측에서 `substring()`의 사용을 권장하고 있습니다.

<br>

**References**
- [MDN Docs](https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Global_Objects/String/substring)