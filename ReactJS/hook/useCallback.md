
&nbsp;&nbsp;`useMemo` hook과 비슷하게 `useCallback` 또한 함수를 메모이제이션하여 컴포넌트의 성능을 최적화 시켜주기 위한 도구입니다. 컴포넌트에 표현식으로 정의된 함수는 컴포넌트가 렌더링될 때마다 중복해서 생성되는데, 불필요하게 함수가 생성되는 것을 방지하기 위해 `useCallback` hook을 사용할 수 있습니다.

```javascript
// scroll component
const [windowHeight, setWindowHeight] = useState(window.innerHeight);

const onResize = () => {
	setWindowHeight(window.innerHeight);
}

useEffect(() => {
	window.addEventListener("resize", onResize);

	return () => {
		window.removeEventListener("resize", onResize);
	}
}, [onResize]);
```

&nbsp;&nbsp;위 컴포넌트는 화면의 높이가 변경될 때마다 `windowHeight`를