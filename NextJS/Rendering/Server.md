
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

### RSC Payload

&nbsp;&nbsp;[공식문서](https://nextjs.org/docs/app/building-your-application/rendering/server-components)를 읽으면서 가장 이해하기 어려웠던 개념은 `RSC Payload`였습니다. 문서에는 `RSC Payload`를 다음과 같이 설명하고 있습니다.

>[!tip] **RSC Payload**
>
>&nbsp;&nbsp;The RSC Payload is a compact binary representation of the rendered React Server Components tree. It's used by React on the client to update the browser's DOM. The RSC Payload contains:

<br>

&nbsp;&nbsp;꽤나 난해한 개념이었지만 어떤 분의 [블로그](https://velog.io/@2ast/React-%EC%84%9C%EB%B2%84-%EC%BB%B4%ED%8F%AC%EB%84%8C%ED%8A%B8React-Server-Component%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B3%A0%EC%B0%B0)를 보고 어느정도 이해할 수 있게 되었는데 제가 이해한 내용을 바탕으로 정리해보았습니다.

&nbsp;&nbsp;우선 공식문서에 이야기한 것처럼 `RSC`는 바이너리 데이터의 일종으로 `RSC` 컴포넌트 트리 정보를 담고 있는 데이터의 형태입니다. 그렇다면 이렇게 독특한 데이터의 형태를 왜 사용하는 것일까요? 이에 대한 해답은 우선 `Next.js`가 단순히 `SSR(Server Side Rendering)`을 위한 도구가 아님을 먼저 알아야 합니다.

<br>

**Next.js에서의 SSR**

&nbsp;&nbsp;전통적인 `SSR`은 사실 사전에 서버에서 렌더링된 HTML과 JS 번들을 클라이언트에 전달하고, 클라이언트는 브라우저 환경에서 이 둘을 결합하는 `Hydrating`의 과정을 거치는 단순한 구조입니다. 하지만 실제 `Next.js` 프로젝트는 `RSC(React Server Component)` 뿐만 아니라 `RCC(React Client Component)`가 섞인 복합적인 구조로 이루어져 있죠. `RSC`와 `RCC`의 관계를 정의하고, 이를 통해 DOM Tree를 작성하기 위한 데이터가 `RSC Payload`입니다.

&nbsp;&nbsp;아래는 `RSC Payload`를 설명하기 위한 예시로, 만약 `RSC`와 `RCC`가 뒤섞여 있는 페이지를 사용자가 요청했다고 가정하고 `RSC Payload`가 어떻게 이용되는지 설명하고 있습니다.

<br>

1. 사용자의 요청에 따라 서버는 `Root`부터 컴포넌트 트리를 작성하기 시작합니다.
2. 컴포넌트 트리는 브라우저에게 전달하기 위해 `직렬화(serialization)` 과정을 거쳐 `json`의 형태로 변환됩니다. `RCC`는 함수로 `직렬화`가 불가능하기 때문에 `placeholder`의 형태로 해당 위치에 `RCC`가 렌더링되는 위치임을 나타내는 `Reference`로서 담깁니다. 이렇게 `직렬화` 과정을 거쳐 만들어진 `json` 데이터가 `RSC Payload` 입니다.
4. 클라이언트는 `RSC Payload`와 JS 번들을 내려받아 렌더링하고,`RCC Reference`가 등장할 때마다 `RCC`를 렌더링해 빈 공간을 채웁니다.

<br>

>[!tip] **직렬화(Serialization)**
>
>&nbsp;&nbsp;`직렬화(Serialization)`은 

<br>

### Server Rendering의 장점


<br>

**References**
- []()
- [S](https://ko.wikipedia.org/wiki/%EC%A7%81%EB%A0%AC%ED%99%94)