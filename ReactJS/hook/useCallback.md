
&nbsp;&nbsp;`useMemo` hook과 비슷하게 `useCallback` 또한 함수를 메모이제이션하여 컴포넌트의 성능을 최적화 시켜주기 위한 도구입니다. 컴포넌트에 표현식으로 정의된 함수는 컴포넌트가 렌더링될 때마다 중복해서 생성되는데, 불필요하게 함수가 생성되는 것을 방지하기 위해 `useCallback` hook을 사용할 수 있습니다.

<br>

```javascript
// scroll component
const [windowHeight, setWindowHeight] = useState(window.innerHeight);
const [counter, setCounter] = useState(0);

const onResize = () => {
	setWindowHeight(window.innerHeight);
}

const onCounterIncrease = () => {
	setCounter((count) => count + 1);
}

useEffect(() => {
	window.addEventListener("resize", onResize);
	console.log("onResize 함수가 수정되었습니다."); // 카운터가 변경될 때마다 호출됩니다.

	return () => {
		window.removeEventListener("resize", onResize);
	}
}, [onResize]);

return (
	<div>
		<input value={counter} readOnly />
		<button type="button" onClick={onCounterIncrease}>증가</button>
	</div>
)
```

&nbsp;&nbsp;위에 작성된 컴포넌트는 화면의 높이가 변경될 때마다 `windowHeight` 상태를 변경하고, 변경된 상태값을 통해 스크롤 이벤트 등을 수행하기 위해 작성되었습니다. 실제로 브라우저의 높이를 변경하면 `windowHeight` 상태에 변경이 올바르게 일어나는 것을 확인할 수 있는데, 문제는 브라우저의 높이와 무관하게 `button` 요소를 클릭해 `counter`의 상태가 변경되면 컴포넌트가 렌더링되면서 `onResize` 함수 역시 새롭게 만들어지는 것을 콘솔을 통해 확인할 수 있습니다. 이는 원치 않는 현상입니다. `onResize`는 `counter`의 변경과 무관하게 동작하며 

<br>

&nbsp;&nbsp;이와 같이 상태의 변경에 따라 불필요하게 함수가 생성되는 것을 방지하고 이전에 메모리에 있는(캐싱되어 있는) 함수를 가져와 사용하여 컴포넌트를 최적하기 위해 `useCallback`을 사용해 위의 코드를 다시 작성할 수 있습니다.

```javascript
// scroll component
const [windowHeight, setWindowHeight] = useState(window.innerHeight);

const onResize = useCallback(() => {
	setWindowHeight(window.innerHeight);
}, []);

useEffect(() => {
	window.addEventListener("resize", onResize);
	console.log("onResize 함수가 수정되었습니다."); // 높이가 변경되어도 호출되지 않습니다.

	return () => {
		window.removeEventListener("resize", onResize);
	}
}, [onResize]);
```

&nbsp;&nbsp;이제 이 컴포넌트는 높이가 변경되어도 `onResize` 함수를 새로 생성하지 않습니다. `useCallback`을 잘 살펴보면 2가지 인자를 받고 있는 것을 확인할 수 있는데, 첫 번째 인자는 캐싱할 함수를 `콜백함수` 형태로 입력하며 두 번째 인자는 캐싱된 함수를 수정하기 위해 필요한 `의존성을 주입하기 위한 배열`을 입력합니다.

&nbsp;&nbsp;예시에서 `onResize` 함수는 처음 렌더링 이후에 변경될 이유가 없으므로 의존성 배열로 빈 배열을 받았습니다. 만약 어떠한 값의 변경에 따라 새롭게 함수를 정의해야 한다면 해당 값들을 의존성 배열에 추가해주어야 합니다.

<br>

**References**
- [React Docs](https://ko.legacy.reactjs.org/docs/hooks-reference.html#usecallback)