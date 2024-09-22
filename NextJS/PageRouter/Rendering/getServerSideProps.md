
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

1. 사용자 정보 등 개인화된 데이터를 포함한 페이지를 렌더링할 떄 사용할 수 있습니다.
2. `authorization header` 나 지리 정보 등 요청 시간에만 알 수 있는 데이터가 포함된 페이지를 렌더링할 때 사용할 수 있습니다.
3. 만약 사용자 요청에 의존하지 않는 페이지를 렌더링한다면 [getStaticProps](./getStaticProps)를 사용하는 편이 좋습니다.

<br>

### `getServerSideProps` 동작



**References**
- [Next.js 공식문서, serverSideProps](https://nextjs.org/docs/pages/building-your-application/data-fetching/get-server-side-props)