
&nbsp;&nbsp;클라이언트에서 서버로 보낸 요청은 때로 예기치 못한 상황이나 일시적인 네트워크 문제로 원하는 결과를 얻지 못할 수 있습니다. 만약 요청에 문제가 없다면 사용자는 다시 이 요청이 처리되길 원할 것이기 때문에 지난번 참여했던 프로젝트에서는 올바른 요청에 대해서는 재요청을 보내는 로직을 추가해 이슈를 처리했습니다. 

&nbsp;&nbsp;사실 이미 재요청 관련 기능을 구현한 라이브러리가 있긴 하지만 라이브러리에 의존하기 보단 직접 필요한 기능을 구현하고 라이브러리와 비교해보며 재요청 관련 로직에서 필요한 기술 혹은 기능들에 대해 인사이트를 가지고자 이번 포스트을 진행하게 되었습니다.

<br>

## 목차
1. [프로젝트 생성](##프로젝트%20생성)
2. [가상 응답 환경설정 : MSW](##가상%20응답%20환경설정)
3. [커스텀 재요청 라이브러리 구현](##커스텀%20재요청%20라이브러리%20구현)
4. [후기](##후기)

<br>

## 프로젝트 생성

&nbsp;&nbsp;API 재요청 라이브러리를 전 테스트 환경 구축을 위해 리액트 프로젝트를 생성해주었습니다. 이 프로젝트에서는 각종 응답 상황과 재요청 방식 등을 구별해 테스트를 진행하기 `ApiTester` 컴포넌트를 아래와 같이 만들었고, 각각의 API 요청을 `ApiTester`가 처리할 예정입니다.

```tsx
const ApiTester = () => {
  const { loading, data, fetch } = useFetch(); // fetch 요청을 처리할 커스텀 훅
  
  useEffect(() => {
    fetch('');
  }, [fetch]);
  
  const refreshHandler = () => {
    fetch('');
  };
  
  return (
    <StyledContainer>
      {loading ? <ApiResultSkeleton /> : <ApiResult data={data} />}
      <ApiRefresher onRefresh={refreshHandler} />
    </StyledContainer>
  );
};

export default ApiTester;
```
<br>

## 가상 응답 환경설정 : MSW

&nbsp;&nbsp;이제 API 요청을 처리할 `ApiTester`가 준비되었습니다. `ApiTester`는 `fetch` 함수를 통해 API 요청을 보낼 것이고, 요청이 완료되기 전까지는 로딩 화면을 요청이 완료된 뒤에는 

<br>

## 커스텀 재요청 라이브러리 구현

<br>

## 후기


<br>

**References**
- [자바스크립트를 이용한 재시도 로직 구현하기 - 번역](https://velog.io/@tap_kim/implement-retry-logic-using-javascript)
- [자바스크립트를 이용한 재시도 로직 구현하기 - 원문](https://anu95.medium.com/implement-retry-logic-using-javascript-e502693e0b5c)
- [Mock Service Worker](https://mswjs.io/docs/getting-started)
- [MSW](./MSW.md)