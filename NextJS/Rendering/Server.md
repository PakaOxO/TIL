
&nbsp;&nbsp;`Server Component`는 사전에 빌드된 후 그려진 UI(`pre-rendered`)를 서버에 선택적으로 `캐싱`하고 이후에 빠르게 불러올 수 있다는 장점이 있습니다. 이번 포스트에서는 `Next.js`의 `Server Component`의 특징과 장점에 대해 살펴보겠습니다.

<br>

### Server Component in Next.js

&nbsp;&nbsp;`Next.js 13`에서 `app dir` 하위에 존재하는 컴포넌트는 기본적으로 `Server Component`입니다. 별다른 설정이 없다면 서버 환경에서 동작하는 컴포넌트로 캐싱 전략을 통해 클라이언트에게 빠른 응답을 제공하는 것이 가능합니다. `Next.js`의  `캐싱` 동작방식은 다음 [섹션](../Caching/Caching) 에서 살펴볼 수 있습니다.

<br>

### Next.js Component 렌더링 과정

&nbsp;&nbsp;`Next.js`에서 렌더링은 `File system`에 의한 `Routing` 구조 혹은, `Suspense boundaries`에 의해 쪼개진 `chunk` 단위로 이루어집니다.

각각의  `chunk`는 서버에서 다음의 2단계 과정을 거쳐 렌더링됩니다.

1. `React`는 `Server Component`를 `Server Component Payload(RSC Payload)`라는 특별한 데이터 포맷으로 변환합니다.
2. `Next.js`는 `RSC Payload`와 `Client Component`의 Javascript 지시문을 통해 서버 상에서 HTML을 렌더링합니다. 이때 `RCC(React Client Component)`는 렌더링되지 않습니다.

이후 클라이언트에 전달된 HTML은 다음의 과정을 거칩니다.

1. Server에서 렌더링되어 전달받은 HTML를 통해 초기 페이지를 그립니다. 이 페이지는 인터렉티브한 상호작용이 불가능하지만 zero-bundle size(no javascript)로 빠른 렌더링이 가능합니다.
2. `RSC Payload`는 `Server Component Tree`와 `Client Component Tree`를 조정하며, 이를 통해 DOM을 새롭게 업데이트합니다.
3. `Client Component`는 클라이언트 환경에서 렌더링된 후,  Javascript 지시문에 의한 `Hydrating` 과정을 통해 어플리케이션이 인터렉티브하게 동작할 수 있게 됩니다.

<br>

**RSC Payload**

&nbsp;&nbsp

<br>

### Server Rendering의 장점

