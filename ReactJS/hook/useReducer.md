## useReducer

&nbsp;&nbsp;state를 관리하기 위해 `useState` hook을 사용할 수 있지만, 때로는 복잡한 state나 연관된 state들의 묶음을 하나로 관리하고 싶을 때가 있습니다. 리액트에서는 복잡한 state를 관리할 수 있도록 `useReducer` hook을 제공합니다.

<br>

### useReducer hook 구조

```javascript
const [state, dispatchFn] = useReducer(reducerFn, initialState, initFn);
```

- **state** : 관리할 상태 값(또는 객체)이 담깁니다.

- **dispatchFn** : 상태 값을 변경시키기 위해 호출하는 함수입니다. dispatch 함수가 호출되면 사전에 정의한 reducer 함수가 호출됩니다. 파라미터로 action 객체를 받을 수 있으며 action 객체는 reducer 함수가 전달 받습니다.

- **reducerFn** : 사용자가 지정하는 reducer 함수입니다. dispatch 함수에 의해 실행되며 파라미터로 이전 state와 dispatchFn 호출 시에 삽입된 action 객체를 전달 받아 복잡한 상태관리 로직을 구현할 수 있습니다.

- **initialState** : 초기 state를 지정할 수 있습니다.

- **initFn** : 만약 초기 state를 서버 요청을 보내 받는 등 로직이 필요하다면 이 위치에 넘겨 받은 함수를 호출하여 상태 값을 초기화할 수 있습니다. 만약 사용하지 않는다면 비울 수 있습니다.

<br>

### 예시 코드 (아이디 & 패스워드 및 폼 유효성 상태관리)

```javascript
const formReducer = (state, action) => {
  if (action.type === "EMAIL_INPUT") {
    return {
      ...state,
      email: action.email,
      emailIsValid: action.email.trim().includes("@"),
    };
  }

  if (action.type === "PASSWORD_INPUT") {
    return {
      ...state,
      password: action.password,
      passwordIsValid: action.password.trim().length > 6,
    };
  }

  if (action.type === "EMAIL_VALIDATION") {
    return { ...state, emailIsValid: state.email.trim().includes("@") };
  }

  if (action.type === "PASSWORD_VALIDATION") {
    return { ...state, passwordIsValid: state.password.trim().length > 6 };
  }

  if (action.type === "FORM_VALIDATION") {
    return {
      ...state,
      isValid: state.email.trim().includes("@") && state.password.trim().length > 6,
    };
  }

  return {
    email: "",
    password: "",
    emailIsValid: null,
    passwordIsValid: null,
    isValid: null,
  };
};

/* 로그인 컴포넌트 */
const Login = (props) => {
  const [formState, dispatchForm] = useReducer(formReducer, {
    email: "",
    password: "",
    emailIsValid: null,
    passwordIsValid: null,
    isValid: null,
  });

  useEffect(() => {
    const timer = setTimeout(() => {
      dispatchForm({ type: "FORM_VALIDATION" });
    }, 500);

    return () => {
      clearTimeout(timer);
    };
  }, [formState]);

  const emailChangeHandler = (event) => {
    dispatchForm({ type: "EMAIL_INPUT", email: event.target.value });
  };

  const passwordChangeHandler = (event) => {
    dispatchForm({ type: "PASSWORD_INPUT", password: event.target.value });
  };

  const validateEmailHandler = () => {
    dispatchForm({ type: "EMAIL_VALIDATION" });
  };

  const validatePasswordHandler = () => {
    dispatchForm({ type: "PASSWORD_VALIDATION" });
  };

  const validateFormHandler = () => {
    dispatchForm({ type: "FORM_VALIDATION" });
  }

  return (/* JSX 생략 */)
};
```
