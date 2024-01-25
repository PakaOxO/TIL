
&nbsp;&nbsp;최근 React Canary 버전에서는 fetch 이벤트와 Suspense를 통한 비동기 렌더링을 효과적으로 처리하기 위해 `use`라는 새로운 hook을 제시하고 있습니다. 이번 포스트에서는 다음 포스트에서 `use`에 대해 살펴보기 전에 `use`가 등장하게 된 배경으로 기존의 React에서 `fetch` 이벤트를 처리하는 세 가지 방법을 살펴보겠습니다.

<br>

### Fetch-on-render

&nbsp;&nbsp;