
&nbsp;&nbsp;`getServerSideProps` 함수는 Next.js에서 제공하는 함수로 데이터를 패칭하거나 사용자의 `request time`에 페이지를 렌더링하기 위해 사용됩니다.

<br>

### Example

&nbsp;&nbsp;`getServerSideProps` 함수는 `Page` 컴포넌트 내에서 3rd party API를 호출한 결과를 `props`로 받아 렌더링하기 위해 호출할 수 있습니다.

```tsx
import type { InferGetServerSidePropsType, GetServerSideProps } from 'next'
 
type Repo = {
  name: string
  stargazers_count: number
}
 
export const getServerSideProps = (async () => {
  // Fetch data from external API
  const res = await fetch('https://api.github.com/repos/vercel/next.js')
  const repo: Repo = await res.json()
  // Pass data to the page via props
  return { props: { repo } }
}) satisfies GetServerSideProps<{ repo: Repo }>
 
export default function Page({
  repo,
}: InferGetServerSidePropsType<typeof getServerSideProps>) {
  return (
    <main>
      <p>{repo.stargazers_count}</p>
    </main>
  )
}
```

<br>

### When should I use `serverSideProps`?

1. 사용자 정보 등 개인화된 데이터를 포함한 페이지를 렌더링할 때 사용할 수 있습니다.
2. `authorization header` 나 지리 정보 등 요청 시간에만 알 수 있는 데이터가 포함된 페이지를 렌더링할 때 사용할 수 있습니다.
3. 만약 사용자 요청에 의존하지 않는 페이지를 렌더링한다면 [getStaticProps](./getStaticProps)를 사용하는 편이 좋습니다.

<br>

### `getServerSideProps` 동작

&nsbp;&nbsp;`getServerSideProps`는...

1. 서버에서 호출되어 실행됩니다.
2. 오직 `page` 컴포넌트에서만 사용될 수 있습니다.
3. 호출의 결과로 `json`을 반환합니다.
4. 사용자가 페이지에 접근하면 `getServerSideProps`는 사용자 요청 시에 내부에 작성된 패칭 함수를 실행하고 반환된 데이터는 `props`로 컴포넌트에 전달되어 초기 페이지를 렌더링하기 위해 사용됩니다.
5.  만약 사용자가 `next/link` 혹은 `next/router`를 통해 페이지에 접근했다면 Next.js는 서버에 요청을 보내 서버에서 `getServerSideProps`를 실행하고 서버는 `json`을 반환합니다.
6. `getServerSideProps`는 서버에서 실행되는 함수이므로 불필요하게 `api routes`를 통해 데이터를 패칭할 필요는 없으며, 대신 CMS, db, 3rd party API를 호출해 데이터를 가져오는 편이 좋습니다.

<br>

`getServerSideProps` 함수의 자세한 사용법은 [공식문서](https://nextjs.org/docs/pages/api-reference/functions/get-server-side-props)를 통해 확인할 수 있습니다.

<br>

### Edge Cases

**Caching with Server-Side Rendering(SSR)**

&nbsp;&nbsp;`getServerSideProps`는 내부에 `cache-control` 헤더를 통해 응답을 캐싱할 수 있습니다. 다만 Next 공식문서에서는 이 방법 대신 `ISR`을 적용한  [getStaticProps](https://nextjs.org/docs/pages/building-your-application/data-fetching/get-static-props)사용을 권장하고 있습니다.

```tsx
// This value is considered fresh for ten seconds (s-maxage=10).
// If a request is repeated within the next 10 seconds, the previously
// cached value will still be fresh. If the request is repeated before 59 seconds,
// the cached value will be stale but still render (stale-while-revalidate=59).
//
// In the background, a revalidation request will be made to populate the cache
// with a fresh value. If you refresh the page, you will see the new value.
export async function getServerSideProps({ req, res }) {
  res.setHeader(
    'Cache-Control',
    'public, s-maxage=10, stale-while-revalidate=59'
  )
 
  return {
    props: {},
  }
}
```

<br>

**References**
- [Next.js 공식문서, serverSideProps](https://nextjs.org/docs/pages/building-your-application/data-fetching/get-server-side-props)
- [Next.js 공식문서, serverSideProps 사용법](https://nextjs.org/docs/pages/api-reference/functions/get-server-side-props)