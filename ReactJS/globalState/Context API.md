
&nbsp;&nbsp;`Recoil`, `Redux`와 함께 전역상태관리를 위해 `Context API`를 사용해왔는데, 필요한 라이브러리를 골라 사용했다기 보단 유명하니 한번 사용해보자는 느낌으로 사용했던 터라 이번 기회를 통해 한번 정리하고자 합니다. 올해 새롭게 진행하는 프로젝트에서는 각 라이브러리의 특성을 이해하고 프로젝트 특성에 맞는 라이브러리를 선택하는데 있어 이번 학습이 기준이 되었으면 합니다.

<br>

### Context API

>[!tip] context는 React 컴포넌트 트리 안에서 전역적(global)이라고 볼 수 있는 데이터를 공유할 수 있도록 고안된 방법입니다. 그러한 데이터로는 현재 로그인한 유저, 테마, 선호하는 언어 등이 있습니다.

&nbsp;&nbsp;`Context API`는 리액트에서 공식으로 제공하는 전역상태관리를 위한 도구입니다. 한때 비교적 설정이 복잡한 `Redux`에 비해 가벼운 `Context API`가 유행했던적이 있었던 것 같은데 최근에는 여러가지 이슈로 `Recoil`이나 `Zustand`를 선호하는 경향이 있는 것 같습니다.

<br>

### Context API 사용법

&nbsp;&nbsp;`Conext API`를 사용하기 위해서는 다음과 같은 작업이 필요합니다.

1. 

```javascript
/* CounterProvider.js */
import { useState, createContext, useContext } from 'react';

const defaultContext = {
	counter: 0,
}

const CounterContext = createContext(defaultContext);

const CounterProvider = () => {
	const [counter, setCounter] = useState(0);
	
	return (
		<CounterContext.Provider value={}>
			{ children }
		</CounterContext.Provider>
	);
}
```