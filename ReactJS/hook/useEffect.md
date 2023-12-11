### React 컴포넌트 내부 로직

1. UI Rendering
2. Event Handler

&nbsp;&nbsp;리액트 컴포넌트는 기본적으로 함수로 작성되며, 컴포넌트의 첫 번째 목적은 화면에 UI를 그리기 위함입니다. 함수 형태의 컴포넌트는 JSX를 반환하며, JSX는 Parsing되어 브라우저 상에 그려집니다.

&nbsp;&nbsp;만약 렌더링에 의해 게시글을 작성하기 위한 UI가 그려졌다면, 사용자는 input을 사용해 게시글을 작성하고, button을 클릭해 게시글을 등록하기 위한 요청을 서버에 보낼 것 입니다. 이와 같이 사용자의 액션에 의해 처리되는 이벤트를 실행하기 위한 로직은 Event Handler에 의해 처리됩니다.

<br>

### Side Effect

&nbsp;&nbsp;앞서 리액트 컴포넌트에서의 로직은 크게 화면을 그리고, 그려진 화면에서 사용자의 액션에 의한 이벤트를 처리하는 2가지로 구성된다고 이야기 했습니다. 그렇다면 렌더링이 이루어지기 전에 SNS에서 관심 피드 목록을 가져온다던가, 유튜브에서 추천 영상 목록을 가져오는 로직은 어디에 작성되어야 할까요?

&nbsp;&nbsp;컴포넌트 내에서 http request를 통해 필요한 데이터를 서버에서 가져오거나, 수동으로 컴포넌트의 DOM을 수정하는 등 컴포넌트의 기본적인 2가지 로직 이외의 기능들은 `Side Effect`라고 표현됩니다. 그리고 이러한 `Side Effect`를 처리하기 위해 사용되는 것이 `useEffect` hook이죠.

<br>

### useEffect 문법

```javascript
useEffect(setup, [...dependencies]);
```

&nbsp;&nbsp;`useEffect`는 다음과 같은 형태로 `setup`과 `dependencies` 배열, 2가지 파라미터를 가질 수 있는 React hook입니다. 간단하게 서버로부터 게시글 목록을 가져오기 위한 컴포넌트를 예시로 살펴보겠습니다.


```javascript
import { useEffect, useState } from 'react';
import Board from "./Board.js";

const BoardList = () => {
	const [serverURL, setServerURL] = useState("https://webServer.com/");
	const [boardList, setBoardList] = useState([]);
	const { fetchBoardList } = useBoard(); // Custom hook

	console.log("나 렌더링한다?"); // 2번 실행됩니다.

	useEffect(() => {
		(async () => {
			try {
				const list = await fetchBoardList(serverURL);
				setBoardList([...list]);
			} catch(err) {
				console.error(err);
			}
		}());
	}, [serverURL, fetchBoardList]);

	return (
		<ul>
			{boardList.map((data) => {
				<Board data={data} />
			})}
		</ul>
	);
}
```

1. `BoardList` 컴포넌트는 게시글을 가져오기 위한 서버의 주소를 `serverURL`이라는 `state`로 관리하고 있습니다.
2. 게시글을 가져오기 위한 API는 `useBoard`라는 커스텀 훅의 `fetchBoardList`를 통해 구현되어 있습니다.
3. `useEffect`는 컴포넌트가 렌더링된 직후, 실행됩니다. `fetchBoardList`에 의해 가져온 게시글 목록을 list에 담은 뒤 `setBoardList`를 통해 `boardList`의 상태를 변경합니다.
4. 상태값이 변경되면 리액트 컴포넌트는 변경을 감지하여 다시 렌더링을 실시합니다. 실제로 위 컴포넌트에서 console이 2번 실행되는 것을 확인할 수 있습니다.
5. 2번째 렌더링에서는 boardList에 담긴 가져온 게시글 목록들을 `Board` 컴포넌트에 `props`로 내려 렌더링합니다.

<br>

>[!tip] **Dependencies**
>
>&nbsp;&nbsp;`useEffect`의 문법에서 `setup`은 `Side`

**기타 특징**

- 만약 의존성 배열에 빈 배열이 있다면 useEffect의 콜백 함수는 처음 컴포넌트가 실행될 때 1번만 호출됩니다.

- 아예 2번째 인자를 비워져 있다면 콜백 함수는 렌더링이 발생할 때마다 호출되기 때문에 콜백 함수로 state를 수정하는 등의 작업을 한다면 렌더링이 계속해서 발생하여 컴포넌트가 무한 렌더링되는 문제가 생길 수 있습니다.

<br>

### clean-up 함수

&nbsp;&nbsp;리액트에서는 effects를 크게 `clean-up` 함수를 갖는 hook과 그렇지 않은 hook 두 가지로 나눕니다. 만약 컴포넌트를 class로 작성해본 사람이라면 `componentDidMount`와 `componentDidUpdate`, `componentWillUnmount`라는 class형 컴포넌트의 생명주기 hook에 대해 들어본 적이 있을 것입니다.

&nbsp;&nbsp;useEffect는 위의 3가지 생명주기 함수의 의미를 갖는 hook으로 특히 `clean-up` 함수는 컴포넌트의 DOM에서 제거될 때 호출되는 `componentWillUnmount`에 대응됩니다.

<br>

**Debouncing(디바운싱)**

&nbsp;&nbsp;clean-up 함수를 활용할 수 있는 예시 중 하나는 `디바운싱` 기법입니다. 만약 사용자가 닉네임을 만들기 위해 키를 입력하고 있다고 가정했을 때, DB에 저장된 닉네임과 중복을 확인하기 위해 키가 입력될 때마다 요청을 보내는 것은 트래픽 낭비를 야기할 수 있습니다. 때문에 모든 키 입력이 종료된 뒤에 한번만 요청을 보내도록 하는 것이 `디바운싱`입니다.

<br>

```javascript
/* 디바운싱 예시 */
const [nickname, setNickname] = useState("");
const [isNickValid, setIsNickValid] = useState(false);

useEffect(() => {
  const timer = setTimeout(() => {
    /*
      Data Request
    */
    if (alreadyHas) {
      setIsNickValid(false);
    } else {
      setIsNickValid(true);
    }
  }, 500);

  // 컴포넌트가 unmount 되기 전에 호출됩니다.
  // 키 입력에 의해 state가 변경되어 컴포넌트 함수가 새로 호출되면 이전에 호출된 컴포넌트는 unmount됩니다.
  return () => {
    // timer를 제거하여 request가 발생하지 않도록 합니다.
    clearTimeout(timer);
  };
}, [nickname]);
```

<br>

**References**
- [React 공식문서](https://react.dev/reference/react/useEffect)