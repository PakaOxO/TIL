
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

<br>

### 특징
- 콜백함수의 반환값에 따라 새로운 배열을 반환합니다.
- 원본 배열은 수정되지 않습니다.
- 모든 요소에 대해 `boolean` 값을 반환하는 콜백함수를 사용하는 만큼 [오버헤드](https://betterprogramming.pub/performance-analysis-of-javascript-array-prototype-filter-616e3e3d316f)가 발생할 여지가 있습니다.

<br>

**References**
- [MDN Docs](https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Array/filter)
- [A Performance Analysis of JavaScript’s Array.prototype.filter](https://betterprogramming.pub/performance-analysis-of-javascript-array-prototype-filter-616e3e3d316f)