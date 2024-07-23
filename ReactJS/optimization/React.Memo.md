
&nbsp;&nbsp;`memo`는 리액트 컴포넌트를 `메모이제이션` 하기 위해 리액트에서 제공하는 함수입니다. 컴포넌트는 `props`의 변경이 이루어지기 전까지 리렌더링이 발생하지 않습니다.

<br>

&nbsp;&nbsp;기본적인 `memo` 사용법은 아래와 같습니다. `메모이제이션`할 컴포넌트를 `memo()` 로 감싸고 만약 `props`에 대한 구체적인 비교를 제시하고 싶다면 두 번째 인자로 `props`를 비교해 같으면 `true` 다르면 `false`를 반환하는 `arePropsEqual` 함수를 콜백으로 넣어줍니다.

```javascript
const memoizedComponent = memo(<MyComponent />, arePropsEqual?);
```

<br>

&nbsp;&nbsp;`arePropsEqual`은

```javascript
const arePropsEqual = (prevProps, nextProps) => { /* 함수 내용 */ }
```


**References**
- [memo, React Docs](https://ko.react.dev/reference/react/memo)
- [React.Memo와 useMemo](https://velog.io/@integer/React.memo%EC%99%80-useMemo)