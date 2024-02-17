
&nbsp;&nbsp;`Next.js`의 컴포넌트(정확히 `app` 하위에 위치한) 컴포넌트들은 기본적으로 `Server Component`입니다. 하지만 때로 사용자와의 인터렉션이 필요하거나, 브라우저 API(document 등)을 사용해야 한다면 `Client`에 컴포넌트 렌더링을 위임해야 합니다.

&nbsp;&nbsp;이번 포스트에서는 `Client Component`를 사용하는 방법과, 장점, 그리고 어떤 상황에서 사용하는 것이 좋은지 정리해보도록 하겠습니다.

<br>

### Client Component

&nbsp;&nbsp;`Next.js`에서 컴포넌트는 기본적으로 `Server Component`로 동작하기 때문에 `Client Component`로 지정하기 위해서는 `"use client"`라는 특별한 지시문을 컴포넌트 파일 상단에 넣어주어야 합니다. `"use client"` 지시문이 들어간 컴포넌트는 `Next.js`에 의해 `client bundle`로 인식되어 사용자의 페이지 요청 시 다운로드 되는 번들에 포함되어 다운로드 됩니다.

>[!tip] 그렇다면 자식 컴포넌트는?
>
>&nbsp;&nbsp;한 컴포넌트가 `"use client"` 지시문을 통해 `Client Component`로서 번들로 취급된다면, 이 컴포넌트가 import하는 외부 모듈이나 컴포넌트 역시 `client bundle`로써 함께 다운로드 됩니다.

<br>

### Client Component 렌더링

&nbsp;&nbsp;`Next.js`에서 `Client Component`는 전체 페이지가 렌더링될 때와, 후속 `Navigation`에 의한 렌더링될 때의 렌더링 되는 과정이 상이합니다.

<br>

**Full page rendering**

&nbsp;&nbsp;전체 페이지가 렌더링될 때는 이전에 `Server Component`에서 다루었던 것처럼 다음과 같이 서버에서의 pre-rendering과 클라이언트에 다운로드된 이후 `js bundle`과의 `hydrating`과정을 거쳐 사용자와 상호작용이 가능한 페이지로 렌더링 됩니다.

<br>

1. `React`는 `Server Component`를 `Server Component Payload(RSC Payload)`라는 특별한 데이터 포맷으로 변환합니다.
2. `Next.js`는 `RSC Payload`와 `Client Component`의 Javascript 지시문을 통해 서버 상에서 HTML을 렌더링합니다.

이후 클라이언트에 전달된 HTML은 다음의 과정을 거칩니다.

1. Server에서 렌더링되어 전달받은 HTML를 통해 초기 페이지를 그립니다. 이 페이지는 인터렉티브한 상호작용이 불가능하지만 zero-bundle size(no javascript)로 빠른 렌더링이 가능합니다.
2. `RSC Payload`는 `Server Component Tree`와 `Client Component Tree`를 조정하며, 이를 통해 DOM을 새롭게 업데이트합니다.
3. 서버에서 렌더링된 `Client Component`의 HTML은  Javascript 코드와 결합되는 `Hydrating` 과정을 통해 어플리케이션이 인터렉티브하게 동작할 수 있게 됩니다.


<br>

**Subsequent rendering**

&nbsp;&nbsp;하지만 `Navigation`에 의해 화면이 렌더링 될 때에 `Client Component`의 렌더링은 전적으로 클라이언트 상에서 발생합니다. 기존에 `RSC Payload`에 담긴 정보를 통해 렌더링 되었던 `Client Component`는 `js bundle`로써 클라이언트에 다운로드되고, 파싱되어 모든 번들이 준비되었을 떄 `RSC Payload`의 `Reference`를 통해 `Server Component`와 `Client Component`로 이루어진 `DOM Tree`를 새롭게 업데이트합니다.

<br>

<br>

**References**
- [Client Component, Vercel Docs.](https://nextjs.org/docs/app/building-your-application/rendering/client-components)