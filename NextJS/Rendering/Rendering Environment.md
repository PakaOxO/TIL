
&nbsp;&nbsp;`Next.js`는 서버 혹은 클라이언트에서 컴포넌트를 렌더링할 수 있는 `Hybrid Web App`을 만들 수 있도록 기능을 제공합니다. 이번 포스트는 `Next.js`에서의 컴포넌트 렌더링 환경에 관한 내용을 담고 있습니다.

<br>

### Rendering Environment

&nbsp;&nbsp;렌더링 환경이란 코드가 실행되는 위치를 의미하며, `Next.js`의 렌더링 환경은 크게 `Server`측과 `Client`측 환경으로 나뉩니다.

<br>

**Server Rendering Environment**

- `Next.js` 서버에서 코드가 실행되는 경우입니다.
- `Node runtime`이기 때문에 `Node.js` 코드를 사용할 수 있습니다.
- `Server` 환경인 만큼 `파일 시스템`에 접근하거나 `Database`에 쿼리를 보내는 것이 가능합니다.
- `Browser API`를 사용할 수 없습니다.

**Client Rendering Environment**

- `브라우저`에서 코드가 실행되는 경우입니다.
- `Browser API`를 사용할 수 있으므로, `window`나 `document` 객체에 접근할 수 있습니다.
- `fetch`를 사용해 HTTP 요청을 보낼 수 있습니다.
- `파일 시스템`에 접근하거나 `Database`에 쿼리를 보내는 것은 불가능합니다.

<br>

>[!tip] `process.browser`
>
>&nbsp;&nbsp;`Node.js`는  서버와 클라이언트 렌더링 환경을 구분하기 위해 `process.browser` 변수를 제공합니다. 이 변수는 서버 환경에서는 `false`, 클라이언트 환경에서는 `true` 값을 가집니다.

<br>

&nbsp;&nbsp;컴포넌트의 렌더링 환경에 따라 `Server Component`와 `Client Component`로 구분할 수 있습니다. `Next.js`의 컴포넌트는 별다른 설정없이 `Server Component`으로 동작하는데, 만약 어떤 컴포넌트를 `Client Component`로 지정하고 싶다면 `use client` 키워드를 사용할 수 있습니다.

```javascript
// 서버 컴포넌트
"use server" // 명시적으로 사용할 수 있지만 없어도 괜찮습니다.
function ServerComponent() {
  return (<>
    // JSX...
  </>);
}

// 클라이언트 컴포넌트
"use client"

function ClientComponent() {
  return (<>
    // JSX...
  </>);
}
```

<br>

&nbsp;&nbsp;지금까지 살펴보았듯 `Next.js`의 컴포넌트 렌더링 환경은 `Server`와 `Client`로 나뉘어 `Server Component`와 `Client Component`로 구분됩니다. 각각의 컴포넌트의 특징과 장점 및 단점은 다음 포스트에서 차차 다뤄보도록 하겠습니다.

<br>

**References**
- [Next.js Rendering, Vercel Docs](https://nextjs.org/docs/app/building-your-application/rendering)