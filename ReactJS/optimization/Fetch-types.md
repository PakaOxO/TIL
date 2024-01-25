
&nbsp;&nbsp;최근 React Canary 버전에서는 fetch 이벤트와 Suspense를 통한 비동기 렌더링을 효과적으로 처리하기 위해 `use`라는 새로운 hook을 제시하고 있습니다. 이번 포스트에서는 다음 포스트에서 `use`에 대해 살펴보기 전에 `use`가 등장하게 된 배경으로 기존의 React에서 `fetch` 이벤트를 처리하는 세 가지 방법을 살펴보겠습니다.

<br>

### Tranditional Fetch vs Fetch with Suspense

&nbsp;&nbsp;[React 공식문서](https://17.reactjs.org/docs/concurrent-mode-suspense.html#traditional-approaches-vs-suspense)에서는 React에서 컴포넌트의 렌더링 시에 비동기 작업을 처리하는 방법으로 다음 3가지를 소개하고 있습니다. 각 방법의 구현 방식과 특징에 대해 살펴보고 마지막으로 `Suspense`를 사용해 문제점을 보완한 방법을 끝으로 포스트를 마무리하겠습니다.

1. Fetch-on-render: Fetch in useEffect
2. Fetch-then-render: Relay without Suspense
3. Render-as-you-fetch: Relay with Suspense

<br>

### Fetch-on-render: Fetch in useEffect

&nbsp;&nbsp;`Fetch-on-render`는 일반적으로 `useEffect` 내부에서 비동기 요청을 처리하고, 응답 데이터를 받으면 새롭게 컴포넌트를 렌더링하는 방법입니다. 아래 예시 코드는 최근 진행하는 프로젝트의 캐릭터 정보 조회 컴포넌트의 일부입니다.

```javascript
// Character.tsx
const Character = ({ uid }: { uid: string }) => {
  const character = useRecoilValue<ICharacter>();

  // fetch-on-render
  useEffect(() => {
    fetchCharacter(uid);
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
  const stats = useRecoilValue<IStats>();

  // fetch-on-render
  useEffect(() => {
    fetchStatus(cuid);
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

&nbsp;&nbsp;위 예시 코드의 동작 과정을 살펴보면,

1. Character 컴포넌트 렌더링 + characterFetch
2. 조회된 character를 통해 Character 컴포넌트 재렌더링 with 
3. Fetch 완료


<br>

**References**
- [React Docs, Suspense](https://react.dev/blog/2022/03/29/react-v18#suspense-in-data-frameworks)
- [React Docs(v17), Suspense for Data Fetching](https://17.reactjs.org/docs/concurrent-mode-suspense.html#traditional-approaches-vs-suspense)
- [Suspense와 선언적으로 Data fetching처리(카카오 기술블로그)](https://fe-developers.kakaoent.com/2021/211127-211209-suspense/)