
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

>[!tip] **React `fetch`**
>
>&nbsp;&nbsp;React extends `fetch` to automatically memoize data requests, and the `cache` function can be used when `fetch` is not available.

&nbsp;&nbsp;`React`의 `fetch`는 내부적으로 요청 이후 데이터가 캐싱되는 로직이 포함되어 있습니다. 따라서 여러 컴포넌트에서 중복된 데이터를 요청하더라도 이전 요청에 의해 캐싱된 데이터가 있다면 중복된 요청은 발생하지 않을 수 있습니다. `React fetch`가 데이터를 캐싱하는 방식에 대해서는 [다음](https://nextjs.org/docs/app/building-your-application/caching#request-memoization) 내용을 참고할 수 있습니다.

<br>

**2. Keeping Server-only code out of the Client Environment

&nbsp;&nbsp;어떤 코드는 서버 환경에서만 실행하기를 원할 수 있습니다. 다음 코드에서 `API KEY`는 클라이언트에 노출되지 않기 위해 서버 환경에서만 해당 코드를 호출하고 싶지만 원치 않게 `Client Component`에서 호출될 수 있습니다. 또한, 클라이언트 환경에서 `NEXT_PUBLIC`은 접근할 수 없으므로 원칙적으로 `''(빈 문자열)`을 반환하므로 실제로 요청이 정상적으로 처리되지 않습니다.

```javascript
export async function getData() {
  const res = await fetch('https://external-service.com/data', {
    headers: {
      authorization: process.env.API_KEY,
    },
  })
 
  return res.json()
}
```

<br>

위의 `getData`가 클라이언트에서 호출되는 것을 방지하기 위해 `server-only`를 사용할 수 있습니다. `server-only`를 사용하면 `getData`가 원치 않게 클라이언트 코드에서 실행되는 것을 빌드 타임에 오류를 통해 확인할 수 있습니다.

```bash
npm install server-only
```

```javascript
import 'server-only'
 
export async function getData() {
  const res = await fetch('https://external-service.com/data', {
    headers: {
      authorization: process.env.API_KEY,
    },
  })
 
  return res.json()
}
```

&nbsp;&nbsp;`server-only`와 비슷하게 어떤 코드는 클라이언트 환경에서만 실행되기를 원할 수 있습니다. `client-only` 패키지를 사용하면 클라이언트 코드가 서버 코드에서 실행되는 것을 빌드 타임 시에 오류를 통해 확인할 수 있습니다.

<br>
**3. Using Third-party Packages and Providers

**Third-party Packages**

&nbsp;&nbsp;`Server Component`가 등장한 이후, 새로운 서드 파티 패키지에는 `"use client"` 지시문이 추가되어 릴리즈되어 나오고 있습니다. 이 경우, 서버 환경에서는 해당 패키지를 사용할 수 없으므로 서버 컴포넌트가 패키지를 사용하려고 하면 빌드 타임에 오류를 통해 이를 검출할 수 있습니다.

&nbsp;&nbsp;하지만 여전히 많은 패키지는 `"use client"` 지시문이 포함되어 있지 않습니다. 때문에 서버 환경에서 패키지에 접근해도 별다른 오류를 띄우지 않아 문제를 미리 파악할 수 없습니다. 하나의 패키지를 예시로 들어보겠습니다.

<br>

```javascript
// app/page.tsx
import { Carousel } from 'acme-carousel'
 
export default function Page() {
  return (
    <div>
      <p>View pictures</p>
 
      {/* Error: `useState` can not be used within Server Components */}
      <Carousel />
    </div>
  )
}
```

&nbsp;&nbsp;`acme-carousel` 패키지에는 `Carousel` 컴포넌트가 포함되어 있으며, 이 컴포넌트는 `useState` 훅을 사용합니다. 하지만 `"use client"` 지시문이 없기 때문에 서버 컴포넌트에서 `Carousel` 컴포넌트를 사용해도 별다른 오류를 사전에 확인할 수 없습니다.

&nbsp;&nbsp;이 경우, 서드파티 컴포넌트를 새로운 컴포넌트로 만든 뒤, `"use client"` 지시문을 포함시키는 방법을 사용할 수 있습니다. 클라이언트 환경에서 동작하는 패키지는 대부분 이 방법으로 해결할 수 있습니다.

```javascript
// app/carousel.tsx
'use client'
import { Carousel } from 'acme-carousel'
 
export default Carousel

// app/pages.tsx
import Carousel from './carousel'
 
export default function Page() {
  return (
    <div>
      <p>View pictures</p>
 
      {/*  Works, since Carousel is a Client Component */}
      <Carousel />
    </div>
  )
}
```

<br>

**Context Providers**

&nbsp;&nbsp;전역 상태를 위한 `Context API`의 `Provider`는 많은 경우 `root`에 인접하게 렌더링됩니다. 하지만 `Server Component`는 `Provider`를 지원하지 않으므로 오류를 발생시킬 수 있습니다. 이 경우 `Context`를 설계할 때 `Provider`를 별도의 `Client Component` 내부에 렌더링되도록 할 수 있습니다.

```javascript
// app/theme-provider/tsx
'use client'
import { createContext } from 'react'
export const ThemeContext = createContext({})
 
export default function ThemeProvider({ children }: {
  children: React.ReactNode
}) {
  return <ThemeContext.Provider value="dark">{children}</ThemeContext.Provider>
}
```

  &nbsp;&nbsp;이렇게 하면 `Server Component`는 `Provider` 컴포넌트를 `Client Component`로써 렌더링합니다. 만약 `<ThemeProvider />`가 `root`에 렌더링 되었다면 `root` 하위의 모든 `Client Component`는 `<ThemeProvider />`의 전역 상태에 접근할 수 있습니다.

<br>

### Client Components

&nbsp;&nbsp;`Next.js`에서 컴포넌트는 기본적으로 `Server Component`로 동작하는 만큼 `Client Component`는 `Server Component` 자식 컴포넌트로 사용되는 경우가 많습니다. 때문에 `Client Component`를 사용하기 위해서는 다음과 같은 사항들에 대해 고려하는 것이 좋습니다.

<br>

**1. Moving Client Components Down the Tree**

&nbsp;&nbsp;`Client Component` 내부에서 사용되는 모듈들은 `Client bundle`로써 클라이언트에게 다운로드됩니다. 다운로드되는 JS 번들 사이즈를 줄이기 위해서는 `Client Component`는 컴포넌트 트리 상에서 아래에 위치시키는 것이 좋습니다.

&nbsp;&nbsp;아래 코드에서 `<SearchBar />`는 사용자와의 상호작용 로직이 포함된 `Client Component`입니다. 반면 `Logo`는 모든 사용자에게 동일하게 보여질 `static Component`입니다. 만약 `Layout`을 `Server Component`로써 사용하면 `Logo`는 불필요하게 클라이언트에 다운로드 되지 않고, 서버 상에서 렌더링 되며, 오직 `SearchBar`와 내부에서 사용되는 모듈만 번들로 다운로드 됩니다.

```javascript
// app.tsx
// SearchBar is a Client Component
import SearchBar from './searchbar'
// Logo is a Server Component
import Logo from './logo'
 
// Layout is a Server Component by default
export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <>
      <nav>
        <Logo />
        <SearchBar />
      </nav>
      <main>{children}</main>
    </>
  )
}
```

<br>

**Passing props from Server to Client Components (Serialization)**

&nbsp;&nbsp;이 부분이 저에겐 가장 인상깊은 내용이었습니다. `Server Component`와 `Client Component`가 어떻게 `props`를 주고 받는 지에 대해 의문이 있었는데 이 의문을 해결해주는 내용이 담겨 있었습니다.

&nbsp;&nbsp;`Server Component`와 `Client Component` 사이에 주고받는 `props`는 [Serializable](https://developer.mozilla.org/ko/docs/Glossary/Serialization) 해야 합니다. `직렬화`가 가능한 데이터 만이 `props`를 통해 전달될 수 있으며, 대표적으로 함수가 `실행 컨텍스트`를 포함하고 있기 때문에 `직렬화`가 불가능한 요소입니다.

&nbsp;&nbsp;`직렬화`가 불가능한 요소를 `Client Component` 에서 사용하고 싶다면, 서드파티 라이브러리를 통해 `Client Component`에서 직접 `fetch`해오거나 `Next.js`에서 제공하는  [Route Handler](https://nextjs.org/docs/app/building-your-application/routing/route-handlers)를 사용하는 방법이 있습니다. `Route Handler`에 대해서는 이후 포스트에서 다뤄보도록 하겠습니다.
 
<br>

### Interleaving Server and Client Components

&nbsp;&nbsp;`Interleaving`의 사전적인 의미는 `끼워넣기`입니다. `Server Component`와 `Client Component`의 복합적인 구조에서 일반적으로 `Next.js`의 렌더링은 `Server Component`인 `root layout`을 부모로 컴포넌트 트리를 만들며 중간에 등장하는 `Client Component`에 의해 `Subtree`가 만들어집니다. 

&nbsp;&nbsp;`Subtree` 내부에는 `Server Component`가 포함될 수 있으며, `Server Action`을 호출할 수 있지만 다음과 같은 사항을 유의해야합니다.

1. `Request-Response` 라이프사이클 중에 작성된 코드는 `Server`에서 `Client`로 흐릅니다: 데이터는 서버에서 클라이언트로만 이동할 수 있음을 의미합니다. 클라이언트에서 데이터가 필요하다면 `Server Action` 등을 통해 서버에서 데이터 요청을 발생시킨 뒤, 클라이언트는 서버로부터 결과를 받아 사용해야 합니다. 반대로 데이터가 `Client`에서 `Server`로 이동할 수는 없습니다.

2. 새로운 요청이 발생하면 가장 먼저 모든 `Server Component`가 렌더링 됩니다. 렌더링된 결과로 생성된 `RSC Payload`는 `Client Component reference`를 가지고 있으므로, 클라이언트의 `React`는 `RSC Payload`를 통해 `Server`와 `Client Component`를 단일 트리로 만듭니다.

3. `Client Component`는 `Server Component`의 렌더링이 끝난 뒤 렌더링되기 때문에 `Client Component` 내부에서 `Server Component`를 `import`해 사용하는 것은 불가능합니다. `Client Component` 내부에서 `Server Component`를 사용하려면 `children prop`으로 받아 사용할 수 있습니다.

```javascript
// Not Supported Pattern
'use client'
// You cannot import a Server Component into a Client Component.
import ServerComponent from './Server-Component'
 
export default function ClientComponent({
  children,
}: {
  children: React.ReactNode
}) {
  const [count, setCount] = useState(0)
 
  return (
    <>
      <button onClick={() => setCount(count + 1)}>{count}</button>
 
      <ServerComponent />
    </>
  )
}
```

```javascript
'use client'
import { useState } from 'react'
 
export default function ClientComponent({
  children,
}: {
  children: React.ReactNode
}) {
  const [count, setCount] = useState(0)
 
  return (
    <>
      <button onClick={() => setCount(count + 1)}>{count}</button>
      {children}
    </>
  )
}
```

<br>

**References**
- [Composite patterns, Vercel Docs](https://nextjs.org/docs/app/building-your-application/rendering/composition-patterns)
- [React Fetchs](https://nextjs.org/docs/app/building-your-application/caching#request-memoization)
- [Serialization](https://developer.mozilla.org/ko/docs/Glossary/Serialization)
- [Route Handlers](https://nextjs.org/docs/app/building-your-application/routing/route-handlers)
