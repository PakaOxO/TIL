
&nbsp;&nbsp;`Server Component`는 사전에 빌드된 후 그려진 UI(`pre-rendered`)를 서버에 선택적으로 `캐싱`하고 이후에 빠르게 불러올 수 있다는 장점이 있습니다. 이번 포스트에서는 `Next.js`의 `Server Component`의 특징과 장점에 대해 살펴보겠습니다.

<br>

>[!tip] **Pre-render & Hydrating**
>
>&nbsp;&nbsp;`Next.js`의 컴포넌트는 기본적으로 빌드될 때 미리 렌더링되고(`pre-rendered`), 사용자가 페이지를 요청했을 때 캐싱된 페이지를 빠르게 응답으로 줄 수 있다는 장점이 있습니다. 하지만 서버에서 반환하는 사전 렌더링된 HTML에는 동적인 Javascript 코드는 빠져있습니다. 서버는 클라이언트에게 렌더링된 HTML과 함께 번들링된 Javascript를 전송하는데, 클라이언트는 이를 받아 HTML과 JS 번들 코드를 합치는 `Hydrating` 과정을 통해 어플리케이션이 인터렉티브하게 동작할 수 있습니다.

<br>

### Server Component in Next.js

&nbsp;&nbsp;`Next.js`에서 제공하는 컴포넌트는 기본적으로 `Server Component`입니다. 별다른 설정이 없다면 서버 환경에서 동작하는 컴포넌트로 캐싱 전략을 통해 클라이언트에게 빠른 응답을 제공하는 것이 가능합니다. `Next.js`의  `캐싱` 동작방식은 다음 [섹션](../Caching/Caching) 에서 살펴볼 수 있습니다.

<br>

### 렌더링 방식

`Next.js`에서 `Server Component`의 렌더링은 

<br>

### Server Rendering의 장점

