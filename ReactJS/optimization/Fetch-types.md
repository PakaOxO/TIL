
&nbsp;&nbsp;최근 React Canary 버전에서는 fetch 이벤트와 Suspense를 통한 비동기 렌더링을 효과적으로 처리하기 위해 `use`라는 새로운 hook을 제시하고 있습니다. 이번 포스트에서는 다음 포스트에서 `use`에 대해 살펴보기 전에 `use`가 등장하게 된 배경으로 기존의 React에서 `fetch` 이벤트를 처리하는 세 가지 방법을 살펴보겠습니다.

<br>

### Tranditional Fetch vs Fetch with Suspense

&nbsp;&nbsp;[React 17 공식문서](https://17.reactjs.org/docs/concurrent-mode-suspense.html#traditional-approaches-vs-suspense)에서는 React에서 컴포넌트의 렌더링 시에 비동기 작업을 처리하는 방법으로 다음 3가지를 소개하고 있습니다. 본 포스팅을 작성하는 시점은 곧 `React 19`가 업데이트 될 예정인 시기이고, 위 참조 문서는 `outdated`되었지만 리액트에서 데이터를 `fetching`하고 렌더링하는 과정은 위 문서에서 설명하는 바와 크게 다르지 않기 때문에 각 방법의 구현 방식과 특징에 대해 살펴보고 마지막으로 `Suspense`를 사용해 문제점을 보완한 방법을 끝으로 포스트를 마무리하겠습니다.

1. Fetch-on-render: Fetch in useEffect
2. Fetch-then-render: Relay without Suspense
3. Render-as-you-fetch: Relay with Suspense

<br>

### Fetch-on-render: Fetch in useEffect

&nbsp;&nbsp;`Fetch-on-render`는 일반적으로 `useEffect` 내부에서 비동기 요청을 처리하고, 응답 데이터를 받으면 새롭게 컴포넌트를 렌더링하는 방법입니다. 아래 예시 코드는 최근 진행하는 프로젝트의 캐릭터 정보 조회 컴포넌트의 일부입니다.

```javascript
// Character.tsx
const Character = ({ uid }: { uid: string }) => {
  const [character, setCharacter] = useState<ICharacter>();

  // fetch-on-render
  useEffect(() => {
    fetchCharacter(uid).then(setCharacter);
  }, [uid]);

  // 동기화 작업
  if (isEmpty(character)) {
    return <p>Loading...</p>
  }

  return (
    <>
      <h2>character?.name</h2>
      <CharacterStatus cuid={uid} />
    </>
  );
}

// CharacterStatus.tsx
const CharacterStatus = ({ cuid }: { cuid: string }) => {
  const [stats, setStats] = useState<IStat>();

  // fetch-on-render
  useEffect(() => {
    fetchStatus(cuid).then(setStats);
  });

  // 동기화 작업
  if (isEmpty(stats)) {
    return <p>Loading</p>
  }

  return (
    <ul>
      {stats?.map((stat) => 
        <li key={stat.id}>{ stat.name }: { stat.value }</li>
      )}
    </ul>
  );
}
```

<br>

**Race Condition(경쟁 상태)**

&nbsp;&nbsp;위 코드를 살펴보면 데이터를 `fetch`하는 함수와 컴포넌트 렌더링은 각자 독자적인 `생명주기(Lifecycle)`가지고 동작하기 때문에 서로 `경쟁 상태`에 놓이게 됩니다. 때문에 서버로부터 데이터를 받기 전에 빈 데이터가 렌더링 되는 것을 방지하기 위해 두 작업을 동기화 시키는 코드를 추가했죠. 하지만 이러한 방식은 컴포넌트 복잡성 증가와 `Waterfall` 이슈를 야기할 수 있습니다.

<br>

**Waterfall problem**

다음은 위 예시 코드의 동작 과정입니다.

1. Character 렌더링 + characterFetch
2. 조회된 character로 Character 컴포넌트 재렌더링
3. CharacterStatus 렌더링 + characterStatFetch
4. 조회된 stats로 CharacterStatus 재렌더링

<br>

&nbsp;&nbsp;비동기 작업의 장점은 동시성을 통한 효율성이지만, 위 코드는 동시성을 포기했기 때문에 `Waterfall` 이슈가 발생하게 됩니다. CharacterStatus 컴포넌트는 Character 컴포넌트에서 호출한 fetch 함수의 결과로 응답을 받았을 때 비로소 렌더링될 수 있으며, CharacterStatus에서 fetch 요청을 보낼 수 있습니다. 결과적으로 부모 컴포넌트부터 순차적으로 렌더링 & fetch가 이루어지기 때문에 낮은 효율성을 가집니다.

<br>

### Fetch-then-render: Relay without Suspense

&nbsp;&nbsp;그렇다면 이번에는 fetch 이벤트의 동시성을 보장하기 위해 부모 컴포넌트에서 한번에 처리해보도록 하겠습니다. 모든 비동기 작업이 완료되면 부모 컴포넌트는 자식 컴포넌트에게 데이터를 전달합니다.

```javascript
// Character.tsx
const Character = ({ uid }: { uid: string }) => {
  const [character, setCharacter] = useState<ICharacter>();
  const [stats, setStats] = useState<IStat>();

  // fetch-then-render
  useEffect(() => {
    Promise.all([fetchCharacter(uid), fetchStatus(uid)])
      .then((character, stats) => {
        setCharacter(character);
        setStats(stats);
      });
  }, [uid]);

  return (
    <>
      <h2>character?.name</h2>
      <CharacterStatus stats={stats} />
    </>
  );
}

// CharacterStatus.tsx
const CharacterStatus = ({ stats }: { stats: IStat }) => {
  return (
    <ul>
      {stats?.map((stat) => 
        <li key={stat.id}>{ stat.name }: { stat.value }</li>
      )}
    </ul>
  );
}
```

<br>

**높은 결합도**

&nbsp;&nbsp;이제는 모든 비동기 작업이 동시에 이루어집니다. 하지만 Character 컴포넌트의 역할이 커지게 되었다는 측면에서 문제가 드러나게 됩니다. 실제로 Character는 자식 컴포넌트로 장착 장비(Weapons), 장착 펫(Pets) 등을 가지고 있습니다. 모든 자식 컴포넌트의 fetch 요청을 Character가 들고 있게 되면 하위 컴포넌트는 Character에 높은 결합도가 생기고, 각 컴포넌트의 역할이 불분명해집니다. 컴포넌트의 역할을 분명하게 하기 위해서는 각 컴포넌트에서 자신이 필요로 하는 작업을 처리하는 편이 더 좋아보입니다.

<br>

### Render-as-you-fetch: Relay with Suspense

&nbsp;&nbsp;`Render-as-you-fetch`를 살펴보기 전에 `Suspense`의 동작 방식을 먼저 알아야 합니다. React에서 제공하는 `Suspense` 컴포넌트와 비동기 작업을 가진 자식 컴포넌트 간의 동작은 다음과 같이 이루어집니다.

1. `children` 컴포넌트가 `Promise`를 `throw`하면 `Suspense` 컴포넌트는 `fallback`으로 넘겨받은 컴포넌트를 렌더링합니다.
2. `fullfill(이행)`된 `Promise`가 반환되면 `children` 컴포넌트를 렌더링합니다.

<br>

**Custom Hook 작성**

&nbsp;&nbsp;아래 `useFetch` custom hook은 기존에 제가 작성하던 코드보다 깔끔하게 작성되어 있어 [카카오 기술블로그](https://fe-developers.kakaoent.com/2021/211127-211209-suspense/)로부터 참조했습니다. `useFetch`에서 넘겨 받은 `fetch` 함수의 반환 `Promise`의 `status`값이 `fullfilled`가 되기 전엔 `throw`를 통해 `Suspense`의 `fallback`이 렌더링되며, `fullfilled`가 되었을 때 비로소 `children`으로 주어진 컴포넌트(Character)가 렌더링됩니다.

```javascript
// useFetch.tsx
const useFetch<T, I> = (fetch: (arg: I) => Promise<T>, arg: I) => {
  const [ _promise, _setPromise ] = useState<Promise<void>>();
  const [ _status, _setStatus ] = useState<"pending" | "fullfilled" | "error">("pending");
  const [ _result, _setResult ] = useState<T>();
  const [ _error, _setError ] = useState<Error>();

  const _resolvePromise = (result: T) => {
    _setStatus("fullfilled");
    _setResult(result);
  }

  const _rejectPromise = (error: Error) => {
    _setStatus("error");
    _setError(error);
  }

  useEffect(() => {
    _setStatus("pending");
    _setPromise(fetch(arg).then(_resolvePromise, _rejectPromise));
  }, [arg]);

  if (_status === "pending" && _promise) {
    throw _promise;
  }

  if (_status === "error") {
    throw _error;
  }

  return _result;
}

export default useFetch;

// Character.tsx
const Character = ({ uid }: { uid: string }) => {
  const character = useFetch<ICharacter, string>(getCharacter, uid);
  return (
    <>
      <h2>character?.name</h2>
    </>
  );
}

// App.tsx
const LazyCharacter = React.lazy(() => import("./Character"));
const App = () => {
  return (
    <div className="app">
      <Suspense fallback={<div>Loading...</div>}>
        <LazyCharacter uid={"u012320240125"} />
      </Suspense>
    </div>
  );
}
```

<br>

>[!tip] &nbsp;&nbsp;React Canary 버전에서는 새로운 hook인 [use](https://ko.react.dev/reference/react/use)를 제시하고 있습니다. `use`는 인자로 `Promise`, 혹은 `Context`을 넘겨 받으며 `Promise`의 상태(status)가 `fullfilled`가 아니라면 `Suspense`의 `fallback`을 렌더링하도록 합니다. `use` hook에 대해서는 이후 포스트에서 자세하게 다뤄보도록 하겠습니다.

<br>

**Suspense의 장점**

1. 이제 자식 컴포넌트는 각자 필요한 데이터를 동시에 직접 호출하며, `Waterfall` 이슈가 발생하지 않습니다.
2. `Suspense`와 `fallback` 프로퍼티를 통한 렌더링으로 컴포넌트들은 `경쟁상태`를 신경쓰지 않아도 됩니다.
3. 컴포넌트들의 역할이 분명해졌습니다. 또한 컴포넌트 결합도가 낮아집니다.
4. 동기화를 위해 사용했던 코드가 사라졌습니다. 각 컴포넌트는 `fetch`와 렌더링 로직만 가지고 있어 컴포넌트의 복잡성이 낮아졌습니다.

<br>

### 후기

&nbsp;&nbsp;현재 진행하는 프로젝트에서는 명확한 규칙이 없어 `Fetch-on-Render`와  `Fetch-then-Render` 방식이 혼재해 있어 데이터 `fetching`과 렌더링에 `Waterfall` 이슈가 있으며, 컴포넌트의 복잡성이 매우 높은 편입니다. 마감 기한으로 현재는 바로 코드 개선이 어려운 상황이지만 릴리즈 이후에 본격적으로 `Suspense`, `react-query` 등을 도입해 리팩토링과 성능 개선을 위한 시간을 가져보려고 합니다. 이후 리팩토링 기간에 직접 코드를 작성하며 `Suspense`를 사용해 컴포넌트 렌더링을 함으로써 개선한 사항들을 다룰 기회가 있다면 별도의 포스트를 남겨보도록 하겠습니다.

<br>

**References**
- [React Docs, Suspense](https://react.dev/blog/2022/03/29/react-v18#suspense-in-data-frameworks)
- [React Docs(v17), Suspense for Data Fetching](https://17.reactjs.org/docs/concurrent-mode-suspense.html#traditional-approaches-vs-suspense)
- [Suspense와 선언적으로 Data fetching처리(카카오 기술블로그)](https://fe-developers.kakaoent.com/2021/211127-211209-suspense/)
- [React.use](https://ko.react.dev/reference/react/use)