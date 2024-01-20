
>[!tip] Recoil을 사용하면 _atoms_ (공유 상태)에서 _selectors_ (순수 함수)를 거쳐 React 컴포넌트로 내려가는 data-flow graph를 만들 수 있다. Atoms는 컴포넌트가 구독할 수 있는 상태의 단위다. Selectors는 atoms 상태 값을 동기 또는 비동기 방식을 통해 변환한다.

&nbsp;&nbsp; `Recoil`은 Facebook이 만든 React에서 전역상태관리를 사용되는 라이브러리로 `context API`를 기반으로 개발되었습니다. `Redux`에 비해 초기 설정이 간단하고, 지원하는 hook도 복잡하지 않아 개인적으로 전역상태관리 라이브러리 중에서는 사용하기 가장 편하다고 느끼고 있습니다. 이번 포스트에서는 `Recoil`의 특징과 사용할 수 있는 hook에 대해 가볍게 정리해보도록 하겠습니다.


<br>

### Recoil 구성요소

&nbsp;&nbsp;`Recoil`은 크게 전역상태인 `Atoms`과 `Atoms`나 다른 `Selectors`의 변경된 값을 상속받아 새로운 전역상태를 반환하는 순수함수인 `Selectors`로 구성되어있습니다.

<br>

**Atoms**

>[!tip] Atoms는 상태의 단위이며, 업데이트와 구독이 가능하다. atom이 업데이트되면 각각 구독된 컴포넌트는 새로운 값을 반영하여 다시 렌더링 된다.

&nbsp;&nbsp;React의 상태처럼 `Recoil`에서 제공하는 상태의 단위입니다. `Recoil`에서 제공하는 `atom`함수를 사용해 생성합니다. `atom`은 객체를 인자로 받는데 이 객체는 `atom`을 식별하기 위한 `key`와 이 `atom`의 기본 초기화 값인 `default`를 프로퍼티로 갖습니다.

&nbsp;&nbsp;`atoms`를 구독하기 위해서는 필요한 컴포넌트 내부에서 `useRecoilState` hook을 통해 사용하고자 하는 `atom`의 이름을 가지고 구독해 값을 참조하거나 업데이트할 수 있습니다.

```javascript
/* emailState.tsx */
export const emailState = atom<string>({
	key: "emailState",
	default: "",
});

/* myComponent */
const MyComponent = () => {
	const [email, setEmail] = useRecoilState<string>(emailState);

	return (
		<form>
			<label htmlFor="email">Email</label>
			<input id="email" type="text" value={text} />
		</form>
	);
}
```

<br>

**Selectors**

>[!tip] **Selector**는 atoms나 다른 selectors를 입력으로 받아들이는 순수 함수(pure function)다.

&nbsp;&nbsp;`selectors`는 순수 함수로 `atoms`나 또 다른 `selectors`의 변경이 이루어졌을 때 이를 기반으로 파생된 데이터(`derived state`)를 생성하기 위해 사용됩니다. `selectors` 역시 `atoms`와 마찬가지로 컴포넌트에 의해 구독될 수 있습니다. 함수형 개발 관점에서 `selectors`는 자신이 의존하는 상태를 추적하여 계산하고 이를 추상화 함으로써 코드의 관리를 용이하게 할 수 있습니다.

&nbsp;&nbsp;`selctor`는 `atom`과 유사하게 객체를 인자로 넘겨받으며, `selector`를 식별하기 위한 `key`와 `get`을 통해 의존할 상태값을 호출하고 함수 내부의 계산결과를 바탕으로 반환값을 결정합니다. 객체는 `set` 프로퍼티 또한 가질 수 있는데, 이 경우 `selector`를 통해 직접 상태를 변경할 수 있으며, `selector`를 import한 뒤 `selector.set(newState)` 형식으로 호출합니다.

```javascript
/* emailState.tsx */
import { selector } from "recoil";

export const emailValidationState = selector({
	key: "emailValidationState",
	get: ({ get }) => {
		const email = get(emailState);
		const regex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
		
		return regex.match(email);
	},
	set: ({ set }, newState) => {
		return true;
	}
});

/* myComponent */
import emailValidationState from "../atoms/emailValidationState.text";

const MyComponent = () => {
	const [email, setEmail] = useRecoilState<string>(emailState);
	const isEmailValid = useRecoilValue<boolean>(emailValidationState);

	return (
		<form>
			<label htmlFor="email">Email</label>
			<input id="email" type="text" value={text} />
			{ isEmailValid && <span>이메일 형식을 확인해주세요</span> }
			<MasterBtn onClick={emailValidationState.set(true)} /> /* 이게 무슨 버튼이지... */
		</form>
	);
}
```


<br>

### Recoil Hooks

**1. useRecoilState**

```javascript
const [state, setState] = useRecoildState<any>(myState);
```

&nbsp;&nbsp;React의 `useState`와 비슷하게 원하는 `atom`의 전역상태를 구독하고 변경할 수 있는 메서드를 반환합니다.

**2. useRecoilValue**

```javascript
const state = useRecoilValue<any>(myState);
```

&nbsp;&nbsp;전역상태 중 사용하고자 하는 `atom`을 구독하고 값을 반환합니다.  별도의 전역상태를 변경할 필요가 없을 경우 사용할 수 있습니다.

**3. useSetRecoilState**

```javascript
const setState = useSetRecoilState<any>(myState);
```

&nbsp;&nbsp;변경하고자 하는 `atom`이 가진 상태값을 변경하기 위한 메서드를 반환합니다. 구독없이 값을 변경하는 메서드만을 반환하기 때문에 전역상태의 변경으로 인한 재렌더링으로부터 안전합니다.

**4. useResetRecoilState**

```javascript
const resetState = useResetRecoilState<any>(myState);
```

&nbsp;&nbsp;초기화하고자 하는 `atom` 또는 `selector`의 값을 선언시에 넘겨준 `default` 프로퍼티의 값으로 되돌리기 위한 메서드입니다. `useSetRecoilState`와 마찬가지로 `atom`을 구독하지 않고 초기화가 가능하므로 재렌더링으로부터 안전합니다.

<br>

### Recoil 특징

**1. 사용이 간편하다**

&nbsp;&nbsp;앞서 다른 프로젝트에서 `Redux`와 `contextAPI`를 사용해본 입장에서 `Recoil`은 정말 상대적으로 사용하기가 편리한 라이브러리였습니다. 쓸데없이 `Provider`를 쪼개기 위해 `Context`를 잘게 분리하거나 복잡한 초기 설정을 잡아주기 위해 들이는 시간 또한 적어 개인적으로는 최근에 가장 자주 사용했던 라이브러리가 아닌가 싶습니다.

&nbsp;&nbsp;지난 포스팅에서는 props drilling을 방지하고, 변수를 전역에서 관리하기 위해 리액트에서 내장으로 제공하는 `Context API`에 대해 다루었습니다. `Context API`의 경우에는 `Provider`에게 전역상태를 받아 사용하는 컴포넌트의 경우, 다른 컴포넌트에 의해 `Context`가 변경되었더라도 재렌더링되는 이슈가 있었습니다. 

**2. 파생된 상태관리가 용이**

&nbsp;&nbsp;`Recoil`은 `selector`를 통해 의존하고 있는 상태를 추적해 파생된 데이터를 추상화하여 캐싱하고, 이를 여러 컴포넌트에서 사용해 재사용성이 좋습니다.

**3. 캐싱 지원**

&nbsp;&nbsp;`selector` 내부에서 비동기 로직을 처리한다고 가정했을 때 `selector`가 의존하고 있는 `atom`의 값이 같다면 캐싱되어있는 값을 반환합니다. `selector`의 `set`을 통해 수동으로 캐싱하는 것 역시 가능합니다.

&nbsp;&nbsp;`Selector`는 프로퍼티로 `cachePolicy_UNSTABLE`를 가집니다. `eviction`의 값에 따라 `lru`는 정의된 메모리의 `maxSize`를 초과하면 가장 오래전 사용된 캐시를 제거하고, `most-recent`는 가장 최근 값만을 캐싱합니다. 만약 별도로 값을 지정하지 않았다면 `keep-all`이 기본값으로 모든 캐시를 보관하고 제거하지 않습니다.

```javascript
const clockState = () => {
	key: "clockState",
	get: ({ get }) => {
		const hour = get(hourState);
		const min = get(minState);
		const sec = get(secState);
		
		return `{hour}:{min}:{sec}`;
	},
	cachePolicy_UNSTABLE: {
		eviction: "most-recent", // or "lru", "keep-all"
	}
}
```

**4. Concurrent Mode 지원**

>[!tip] **Concurrent Mode?**
>
> &nbsp;&nbsp;Concurrent모드는 React 앱이 빠른 반응속도를 유지하도록 하고 사용자의 장치 기능 및 네트워크 속도에 적절하게 맞추도록 돕는 새로운 기능들의 집합체입니다.

&nbsp;&nbsp;`Recoil`은 React에서 제공하는 `Concurrent Mode`를 지원합니다. 쉽게 말해 동시다발적으로 진행되는 컴포넌트 프로세스에서 렌더링과 관련해 우선순위를 두어 적절한 타이밍에 동작할 수 있도록 하는 것인데 `Concurrent Mode`와 관련해서는 이후에 기회가 된다면 자세히 다뤄보도록 하겠습니다.

<br>

**References**
- [Recoil Docs](https://recoiljs.org/ko/docs/introduction/core-concepts)
- [3분 Recoil](https://velog.io/@gomjellie/3%EB%B6%84-Recoil)
- [immagrationm Recoil 0.4 Patch Note](https://immigration9.github.io/react,recoil/2021/08/01/reading-patchnote-recoil04.html)