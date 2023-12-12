
### 기본 표현식
```javascript
arr.filter(callbackFn, thisArg);
```

<br>

&nbsp;&nbsp;`filter` 메서드는 원본 배열의 요소를 콜백 함수의 반환값(true/false)에 따라 새로운 배열을 반환합니다. 콜백함수는 원본 배열의 길이만큼 호출되며 최대 3가지 파라미터를 받을 수 있는데, 각각 현재 요소의 값, 현재 요소의 인덱스, 원본 배열 객체를 가리킵니다.

```javascript
const arr = [1, 2, 3];

arr.filter((el, idx, self) => {
	console.log(el); // 1, 2, 3
	console.log(idx); // 0, 1, 2
	console.log(self); // [1, 2, 3]

	return el % 2 > 0;
}); // return [1, 3]
```
