
&nbsp;&nbsp;본 포스팅은 `Next.js`를 공부하기 위해 Vercel의 [Next.js 가이드 문서](https://nextjs.org/docs/app/building-your-application/rendering/composition-patterns)를 번역한 내용을 담고 있습니다. `Next.js` 프로젝트는 `Server`와 `Client` 컴포넌트의 복합적인 구조로 이루어져 있는데, 본 포스팅은 다양한 상황에서 어떤 컴포넌트를 사용해야 하는지, 가이드라인을 제시해주고 있습니다.

<br>

### When to use Server and Client Components?

&nbsp;&nbsp;`Server Component`와 `Client Component`가 언제 필요할지 간단하게 표로 정리하면 아래와 같습니다. 주로 `Server component`는 데이터 요청과 같이 백엔드 서비스와 관련이 있는 액션이나 `API KEY`처럼 클라이언트에 노출하지 않도록 하는 자원에 접근할 때 사용하며, `Client Component`는 사용자 액션 혹은, `document`, `localstorage`처럼 브라우저 API를 사용해야할 때 사용됩니다.

<br>

![when_to_use_server_and_client_components|600](../images/when_to_use_server_and_client_components.png)

<br>

### Server Component Patterns

&nbsp;&nbsp;`Server Component`는 앞서 이야기한 것처럼 주로 백엔드 서비스나 `API KEY`처럼 클라이언트에 노출시키지 않는 자원에 접근할 때 사용됩니다. 지금부터 각 상황들에 대해 조금 더 자세히 살펴보도록 하겠습니다.

<br>

**1. Sharing data between components**

&nbsp;&nbsp;만약 여러 `Server Component`에서 동일한 서버 데이터에 접근해야 하는 상황이라면 어떨까요? 클라이언트에서와 달리 `Server Component`는 `Context API`와 같은 전역 상태관리 라이브러리를 사용할 수 없습니다. 그러면 각 컴포넌트는 각자 필요한 데이터를 `중복`해서 서버에 요청해야 할까요?

&nbsp;&nbsp;`React`의 `fetch`는 확ㅈ