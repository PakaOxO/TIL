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

>[!question] **Dependencies**
>
>&nbsp;&nbsp;`useEffect`의 문법에서 `setup`은 `Side Effect`를 관리하기 위한 코드를 작성하기 위한 콜백함수입니다. 그렇다면, `dependencies` 배열은 무엇을 위한 파라미터일까요? 위의 예시에서 살펴본 것처럼 useEffect는 렌더링이 완료된 이후, `setup` 코드를 실행합니다. 만약 예시 코드에서 "serverURL, fetchBoardList"가 담긴 의존성 배열을 지우면 어떻게 될까요?
>
>&nbsp;&nbsp;결론은 "컴포넌트가 무한 호출된다"입니다. `dependencies` 배열은 `Side Effect`를 관리하기 위해 컴포넌트가 관리할 요소들을 가집니다. `dependencies` 배열이 없다면 컴포넌트는 모든 렌더링 시에 `setup` 코드를 실행하게되고, 게시글을 가져옴과 동시에 상태를 변경하여 컴포넌트의 재렌더링이 트리거됩니다. 이로 인해 다시 `setup` 코드가 실행되고 무한 루프가 발생합니다.
>
>&nbsp;&nbsp;만약 컴포넌트의 첫 렌더링 시에만 발생할 `Side Effect`를 원한다면, 2번째 파라미터에 빈 배열을 넣어주면 됩니다.

<br>

### 컴포넌트 생명주기: clean-up 함수

```javascript
useEffect(() => {
	// setup logic: componentDidMount

	return () => {
		// clean-up logic: componentWillUnmount
	}
}, [...dependencies]);
```

&nbsp;&nbsp;리액트에서는 effects를 크게 `clean-up` 함수를 갖는 hook과 그렇지 않은 hook 두 가지로 나눕니다. 만약 컴포넌트를 class로 작성해본 사람이라면 `componentDidMount`와 `componentDidUpdate`, `componentWillUnmount`라는 class형 컴포넌트의 생명주기 hook에 대해 들어본 적이 있을 것입니다.

&nbsp;&nbsp;`useEffect`는 위의 3가지 생명주기 함수의 의미를 갖는 hook으로 `setup` 함수는 `componentDidMount` 내부에 작성할 `Side effect` 로직을, `clean-up` 함수는 컴포넌트의 DOM에서 제거될 때 호출되는 `componentWillUnmount`에 내부의 로직을 작성하기 위해 사용됩니다. 즉, `clean-up` 함수는 컴포넌트가 언마운트 되기 전에 처리해야 할 로직이 구현됩니다.

<br>

**Debouncing(디바운싱)**

&nbsp;&nbsp;`clean-up` 함수를 활용할 수 있는 예시 중 하나는 `Debouncing` 기법입니다. 만약 사용자가 닉네임을 만들기 위해 키를 입력하고 있다고 가정했을 때, DB에 저장된 닉네임과 중복을 확인하기 위해 키가 입력될 때마다 요청을 보내는 것은 불필요한 트래픽을 야기할 수 있습니다. 때문에 모든 키 입력이 종료된 뒤에 한번만 요청을 보내고 싶다면 `디바운싱` 기법을 사용하면 됩니다.

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

// input의 변경이 감지되면 nickname 상태를 변경합니다.
const changeHandler = (e) => {
	setNickName(e.currentTarget.value);
}

return (
	<div>
		<input text="nickname" value={nickname} onChange={changeHandler} />
		<span>{ isNickValid ? "" : "중복된 아이디가 있습니다."}</span>
	</div>
)
```

1. 입력이 감지되어 `nickname`의 변경이 감지되면 `useEffect`가 호출되며 해당 닉네임이 유효한지에 대한 요청을 `timer`를 사용해 비동기적으로 요청합니다.
2. 만약 지속적인 입력이 감지된다면 상태가 변경되어 컴포넌트의 재렌더링을 위해 이전에 마운트된 컴포넌트가 `unMount`되고, `clean-up` 함수가 실행됩니다.
3. `clean-up` 함수 내부에서는 이전에 비동기적으로 호출한 `timer` 내부 코드를 `clear`합니다.
4. 결국 입력이 종료되지 않는 한, 보내진 모든 요청은 `clear`되고 입력을 마친 후의 요청만이 처리됩니다.

<br>

>[!tip] **Throttling**
>
>&nbsp;&nbsp;`디바운싱`이 맨 마지막 요청만을 처리하기 위한 기법이었다면, `쓰로틀링(throttling)`은 일정한 시간동안 1개의 이벤트를 처리하기 위한 기법입니다. 어떤 사용자가 닉네임으로 애국가 1절을 입력하고 있다고 가정했을 때 `디바운싱`은 애국가 1절을 모두 입력하고 난 뒤 유효성 검사를 위한 요청을 보낸다면, `쓰로틀링`은 3초(정하기 나름)마다 입력된 내용을 확인해 유효성 검사를 위해 서버에 요청을 보냅니다.

<br>

&nbsp;&nbsp;지금까지 `useEffect`를 설명하기 위해 서버에 데이터를 요청하는 API를 예시를 들었습니다만.. 사실 `useEffect` 내부에서 비동기 로직을 처리하는 것은 [공식문서](https://maxrozen.com/race-conditions-fetching-data-react-with-useeffect)에서는 추천하는 방법은 아닙니다. 비동기 로직은 실행 후 완료되는 시점이 불분명하기 때문에 예기치 못한 오류를 발생시킬 수 있기 때문이며, 요청이 여러 개라면 각 요청을 보낸 순서대로 처리되지 않아 `Race condition(경쟁 상태)`가 발생할 수 있는 여지가 있습니다. 이외에도 다음과 같은 이유로 `useEffect` 내에서 `fetch` 로직을 구현하는 것은 지양해야 한다고 합니다.

<br>

1. 서버에서 실행되지 않습니다.
	&rarr; 서버에서 렌더링된 HTML은 데이터를 포함하지 않습니다. 데이터 로드를 위해서 클라이언트에서 모든 JS파일이 다운로드 된 뒤, 렌더링을 마친 후에 fetch가 발생하기 때문에 비효율적입니다.

2. `Network Waterfall` 이 발생할 수 있습니다.
	&rarr; 컴포넌트 렌더링은 부모부터 이루어지기 때문에, 만약 부모 컴포넌트에서 `fetch`가 발생한다면 부모 컴포넌트가 완전히 렌더링된 이후 자식 컴포넌트의 `fetch`가 발생하기까지 시간이 오래 걸립니다.

3.  데이터의 `preload`와 `caching`이 이루어지지 않습니다.
	&rarr; 컴포넌트가 `unmount`되고, 다시 `mount`되면 이전에 `fetch`한 데이터가 있더라도 다시 `fetching`이 발생합니다.

4. 사람에게 친화적인 코드가 아닙니다.
	&rarr; `Race condition`으로 인한 버그를 방지하기 위한 코드가 필요하기 때문에 불필요한 [boilerplate](https://ko.wikipedia.org/wiki/%EC%83%81%EC%9A%A9%EA%B5%AC_%EC%BD%94%EB%93%9C)가 발생할 수 있습니다.
	
<br>

**References**
- [React 공식문서](https://react.dev/reference/react/useEffect)
- [React - useEffect와 비동기 처리](https://jisu-y.github.io/react/React-useEffect/)