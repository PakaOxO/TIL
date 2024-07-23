
&nbsp;&nbsp;`memo`는 리액트 컴포넌트를 `메모이제이션` 하기 위해 리액트에서 제공하는 함수입니다. 컴포넌트는 `props`의 변경이 이루어지기 전까지 리렌더링이 발생하지 않습니다.

<br>

### 기본 사용법

&nbsp;&nbsp;기본적인 `memo` 사용법은 다음과 같습니다. 메모이제이션할 컴포넌트를 `memo()` 로 감싸고 만약 `props`에 대한 구체적인 비교를 제시하고 싶다면 두 번째 인자로 `props`를 비교해 같으면 `true` 다르면 `false`를 반환하는 `arePropsEqual` 함수를 콜백으로 넣어줍니다.

&nbsp;&nbsp;`memo`는 입력으로 주어진 컴포넌트를 수정하지 않고 메모이제이션된 새로운 컴포넌트를 반환합니다. 입력 컴포넌트로는 `forwardRef`를 포함해 대부분의 유효한 리액트 컴포넌트가 올 수 있습니다.

```javascript
const memoizedComponent = memo(<MyComponent />, arePropsEqual?);
```

<br>

&nbsp;&nbsp;`arePropsEqual`은 이전 `props` 배열과 새로운 `props` 배열을 인자로 가지고 바뀌기 전/후 `props`를 비교해 일치하는 지 여부를 구현합니다. 만약 `memo`에 `arePropsEqual` 콜백을 추가하지 않았다면 리액트는 기본적으로 `Object.is`로 `props`에 대한 얕은 비교를 실시합니다.

```javascript
const arePropsEqual = (prevProps, nextProps) => { /* 함수 내용 */ }
```

<br>

### 리렌더링

&nbsp;&nbsp;`memo`로 감싸진 컴포넌트는 다음과 같은 상황에서 리렌더링이 발생합니다.

<br>

**1. `props` 변경**

&nbsp;&nbsp;일반적인 리액트 컴포넌트는 부모 컴포넌트의 상태가 변경되었다면 모든 자식 컴포넌트에도 리렌더링이 발생합니다. 하지만 `memo`를 사용하면 주어진 `props`의 변경되지 않았을 경우, 부모 컴포넌트가 리렌더링 되는 상황에도 메모이제이션된 컴포넌트의 리렌더링이 발생하지 않습니다.

```ts
const App = () => {
  const [name, setName] = useState<boolean>("");
  const [age, setAge] = useState<number>(0);

  return (
    <div>
      <input type="text" onChange={(e) => setName(e.currentTarget.value)} />
      <input type="number" onChange={(e) => setAge(e.currentTarget.value)} minValue="1" />
      <MemoizedGreeting name={name} />
    </div>
  );
};

const Greeting = ({ name }) => {
  console.log("rerendering");
  return `Hello, ${name}!`;
};

const MemoizedGreeting = memo(<Child />);
```

<br>

**2. 자신 상태값의 변경**

&nbsp;&nbsp;다른 일반적인 컴포넌트와 마찬가지로 관리하고 있는 상태값에 변경이 발생하면 리렌더링이 이루어집니다. [전체 코드](https://codesandbox.io/p/devbox/react-memo-kltjp5?file=%2Fsrc%2FApp.tsx%3A1%2C1-71%2C1)

```ts
const App = () => {
  const [name, setName] = useState<string>("");
  const [address, setAddress] = useState<string>("");
    
  return (
    <div>
      <label>
      name:
        <input
        type="text"
        value={name}
        onChange={(e) => setName(e.currentTarget.value)}
        />
      </label>
      <br />
      <label>
      address:
        <input
        type="text"
        value={address}
        onChange={(e) => setAddress(e.currentTarget.value)}
        />
      </label>
      <Greeting name={name} />
    </div>
  );
};

export default App;
```


<br>

**3. Context 변경**

&nbsp;&nbsp;`memo` 컴포넌트는 구독하고 있는 전역 상태가 변경되면 리렌더링이 발생합니다. 일반적으로 `Context.Provider` 하위 컴포넌트는 루트 컴포넌트가 가지고 있는 전역 상태에 변경이 발생하면 하위 컴포넌트에 리렌더링이 발생하지만 메모이제이션된 컴포넌트는 전역 상태를 구독하고 있지 않다면 리렌더링의 대상에서 제외됩니다. [전체 코드](https://codesandbox.io/p/devbox/react-memo-context-d9qf6s?file=%2Fsrc%2FApp.tsx%3A25%2C3)

```ts
const ThemeContext = createContext<"dark" | "light">("light");

const App = () => {
  const [theme, setTheme] = useState<"dark" | "light">("light");
  
  const themeToggle = () => {
    setTheme((prev) => (prev === "dark" ? "light" : "dark"));
  };
  
  return (
    <ThemeContext.Provider value={theme}>
      <button onClick={themeToggle}>{theme}</button>
      <div
        style={{
        width: "30px",
        height: "30px",
        background: theme === "dark" ? "black" : "white",
        }}
>      </div>
      <Greeting />
    </ThemeContext.Provider>
  );
};
```

<br>

### 렌더링 최적화

<br>

**References**
- [memo, React Docs](https://ko.react.dev/reference/react/memo)
- [React.Memo와 useMemo](https://velog.io/@integer/React.memo%EC%99%80-useMemo)