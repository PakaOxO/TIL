
### 기본 표현식

```javascript
arr.sort(optional: [compareFunction]);
```

<br>

### 특징

- 배열의 요소를 정렬한 뒤 정렬된 배열을 반환 값으로 리턴합니다.
- <u>원본 배열 자체를 정렬</u>합니다.
- 안정정렬이 아닐 수 있습니다.
- [compareFunction]은 옵션값으로, 생략되면 배열의 요소에 대해 <u>유니코드 포인트 값으로 변환</u>하여 오름차순으로 정렬합니다.
- 때문에 일반적으로 사용자 지정 조건함수가 없다면 숫자 배열은 문자로 변환되어 정렬되기에 주의해야 합니다.
- 사용자 지정함수의 두 파라미터에 대해 양수라면 뒤의 값이 뒤에 배치됩니다.

<br>

```javascript
arr.sort((a, b) => {
  if (a < b) {
    return -1; // b가 뒤에 배치
  } else if (a > b) {
    return 1; // a가 뒤에 배치
  }
  return 0;
});
```

<br>

**References**
- [MDN Docs](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/sort)