
&nbsp;&nbsp;`Next.js`의 컴포넌트(정확히 `app` 하위에 위치한) 컴포넌트들은 기본적으로 `Server Component`입니다. 하지만 때로 사용자와의 인터렉션이 필요하거나, 브라우저 API(document 등)을 사용해야 한다면 `Client`에 컴포넌트 렌더링을 위임해야 합니다.

&nbsp;&nbsp;이번 포스트에서는 `Client Component`를 사용하는 방법과, 장점, 그리고 어떤 상황에서 사용하는 것이 좋은지 정리해보도록 하겠습니다.

<br>

### Client Component

&nbsp;&nbsp;`Next.js`에서 컴포넌트는 기본적으로 `Server Component`로 동작하기 때문에 `Client Component`로 지정하기 위해서는 `"use client"`라는 특별한 지시문을 컴포넌트 파일 상단에 넣어주어야 합니다. `"use client"` 지시문이 들어간 컴포넌트는 `Next.js`에 의해 `client bundle`로 인식되어 사용자의 페이지 요청 시 다운로드 되는 번들에 포함되어 다운로드 됩니다.