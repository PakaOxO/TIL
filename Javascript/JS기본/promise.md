## Promise

&nbsp;&nbsp;이전에 [콜백함수](./콜백함수.md)와 비동기 로직에 대해 다루면서 콜백함수를 사용한 비동기 방식의 문제점을 살펴보았습니다. `Promise`는 비동기 작업의 단위로 ES6 이후에 본격적으로 비동기 작업들을 관리하기 위해 사용되고 있습니다.

<br>

### Promise

```javascript
const promise1 = new Promise((resolve, reject) => {
  /*
    비동기 작업
  */
});
```

&nbsp;&nbsp;`Promise`는 비동기 작업을 위해 사용되는 객체로 여러가지 생성방법이 있으나 가장 정석적인 방법은 `new` 리터럴을 사용한 생성방법입니다. `Promise` 생성자 내부에는 `Executor`라는 이름의 콜백함수가 위치하며 이 콜백함수는 `resolve`와 `reject`라는 매개변수를 가집니다.

&nbsp;&nbsp;`resolve`와 `reject`는 각각 비동기 코드가 실행한뒤 성공했을 경우와, 실패했을 경우를 나타내기 위해 Promise에서 제공하는 함수입니다. 이 둘은 각각 매개변수를 가지는데 비동기 코드의 성공/실패시 필요한 값을 받아서 Promise 객체의 `상태(status)`와 `결과(result)`에 바인딩합니다.

<br>

> 💡 **Promise 3가지 상태**
>
> &nbsp;&nbsp;`Promise`는 ` 대기(pending)`, `이행(fulfilled)`, `거부(rejected)`의 3가지 상태를 가집니다. 처음 `Promise` 객체가 생성되어 비동기 작업이 완료되기 전 상태가 `대기`, 성공적으로 비동기 작업이 완료된 이후의 상태가 `이행`, 모종의 이유로 작업이 실패한 상태가 `거부`이며 앞에서 '이행'상태일때 `.then`에서 등록된 동작이 '거부'상태일때 `.catch`에서 등록된 동작이 수행됩니다. 3가지 상태 중 `fulfilled`와 `rejected`는 하나로 묶어 `settled` 상태로 부릅니다.

<br>

```javascript
const promise1 = new Promise((resolve, reject) => {
  try {
    setTimeout(() => {
      const data = { name: "Lee" }; // 3초 뒤에 데이터를 가져왔다고 가정합니다.
      console.log(data); // 3초 뒤에 data를 출력합니다.
      resolve(data);
    }, 3000);
  } catch((err)) {
    reject(err);
  }
});

promise1
  .then((res) => {
    // 비동기 코드가 성공적으로 완료되었을 경우, resolve에 의해 넘겨진 데이터를 받습니다.
    // 예시 코드에서는 요청에 대한 '응답(response)'을 넘겨받았다는 약어로 res를 사용했습니다.
    console.log(res);
}).catch((err) => {
  // 비동기 코드가 예기치 못한 원인으로 실패했을 경우, reject에 의해 넘겨진 데이터를 받습니다.
  // 일반적으로 에러를 나타내기 위한 에러 코드를 넘겨받을 수 있습니다.
  console.error(err);
});
```

&nbsp;&nbsp;Promise 객체 내부의 함수에서 실행된 비동기 코드의 실행 성공 여부에 따라 `.then` 블록과 `.catch` 블록이 실행됩니다. 예시에서 `setTimeout`이 성공적으로 실행이 완료되면 3초 뒤에 '{ name: "Lee" }'라는 객체가 출력될 것입니다. `resolve`에 의해 전달된 data를 활용하여 `.then` 블록에서 요청한 데이터를 활용해 처리할 로직을 수행할 수 있습니다. 중요한 점은 `console.log(data)`가 출력되는 시점은 promise1을 선언한 선언문에서 `Promise` 객체가 생성된 후, 3초 뒤로 비동기 호출은 `Promise` 객체가 생성된 시점에 이루어집니다.

&nbsp;&nbsp;만약 모종의 이유로 오류가 발생하여 `catch` 블록이 실행되면 `reject`에 의해 발생한 `Error` 객체가 전달되고 `.catch` 블록에서 넘겨받은 오류 정보를 출력합니다.

<br>

**executor 세부 사항**

1. `executor` 내부에서 에러가 throw 된다면 `reject`를 호출, 정상적으로 코드가 실행되었다면 `resolve`를 호출합니다.

2. `executor` 내부에서 호출된 `resolve`와 `reject`는 첫 번째 호출된 것만 인정됩니다.

```javascript
const promise1 = new Promise((resolve, reject) => {
  resolve(); // resolve만 실행됩니다.
  reject();
});

const promise1 = new Promise((resolve, reject) => {
  reject(); // reject만 실행됩니다.
  resolve();
});
```

<br>

### Promise 재사용

&nbsp;&nbsp;이제까지 `new Promise()`가 받는 함수 내부에서 비동기 작업을 실행할 수 있음을 확인했습니다. 그렇다면 이 비동기 작업을 여러 번 재사용하기 위해 매번 `new Promise()` 호출해야 할까요? 결론부터 말하자면 함수의 `반환값`으로 `Promise` 객체를 사용하면 Promise를 재사용하여 원하는 비동기 로직을 여러 번 호출할 수 있습니다.

```javascript
const setTimeoutPromise = (delay) => {
  return new Promise((resolve, reject) => {
    setTimeout(() => {
      resolve(`${delay}ms 이후에 실행됩니다.`);
    }, delay);
  });
};

const promise1 = setTimeoutPromise(1500);
const promise2 = setTimeoutPromise(500);
```

<br>

### Promise 정적 메서드

`Promise` 역시 객체이므로 내부적으로 정적 메서드를 가집니다.

<br>

**1. Promise.resolve**

&nbsp;&nbsp;어떤 값을 래핑한 프로미스 객체를 만들기 위해 사용됩니다. 제 경우, 주로 클라이언트 타입과 다른 객체를 서버로 반환 받았을 때 타입스크립트의 사용자 타입에 맞춘 객체를 반환하기 위해 응답 데이터를 변환해 프로미스로 반환했습니다.

**2.Promise.reject**

&nbsp;&nbsp;`Promise.resolve`가 전달받은 값을 `resolve`하는 프로미스를 반환했다면, `Promise.reject`는 전달받은 값으로 `reject`하는 프로미스를 반환합니다.

**3. Promise.all**

&nbsp;&nbsp;`Promise.all`은 프로미스 배열을 인자로 전달받습니다. 비동기 로직을 처리할 프로미스를 만든 뒤 모든 비동기 로직이 모두 `fulfilled` 상태가 되었다면 각 결과를 `resolve`하는 프로미스들을 배열로 반환합니다. 반환되는 프로미스 배열의 순서는 완료 순서와 무관하게 초기에 인자로 전달한 프로미스 배열 순서와 동일합니다. 만약 전달받은 프로미스 중 하나라도 `rejected` 상태가 된다면 나머지 프로미스가 `fulfilled` 상태가 되는 것을 기다리지 않고 바로 종료됩니다.

&nbsp;&nbsp;`Promise.all`은 서로 무관한 비동기 로직을 병렬적으로 실행하기 위해 사용할 수 있습니다.

```javascript
const results = Promise.all([
  fetch("_getUserInfoURL"),
  fetch("_getUserPostURL"),
  fetch("_getUserFriendsURL")
]);
```

**4. Promise.race**

&nbsp;&nbsp;`Promise.race`는 `Promise.all`과 마찬가지로 프로미스 요소들을 갖는 배열 등의 이터러블을 인자로 받습니다. 이 메서드는 가장 먼저 `fulfilled` 상태로 전환되는 비동기 로직의 결과를 `resolve`한 결과 프로미스를 반환합니다. 만약 프로미스가 하나라도 `rejected` 상태가 되면 `Promise.all`과 동일하게 동작합니다.

**5. Promise.allSettled**

&nbsp;&nbsp;`Promise.all`은 전달된 프로미스 중 하나라도 `reject`가 되면 중간에 중단되었지만 `allSettled`는 모두 `settled`, 즉 `pending`이 아닌 `fulfilled`, `rejected` 상태가 될때까지 기다립니다. 반환되는 배열에는 각 프로미스의 결과에 대한 상태(`fulfilled/rejected`)와 `resolve`된 경우 그 결과를 `value`로 포함하고 있으며, `reject` 되었을 경우에는 원인을 `reason` 프로퍼티에 담은 객체를 반환합니다.

<br>

### Review

&nbsp;&nbsp;`Promise`객체를 활용하여 비동기 작업을 수행했을 때의 가장 큰 의의는 비동기 작업의 실행(`new Promise()`)과 실행이 완료되었을 때의 동작 지정 부분(`then`, `catch`)을 분리함으로써 보다 유연한 설계를 가능하게 하는 점이라고 생각합니다.

<br>

**Reference**

- Javascript Deep Dive
- [[Javascript] 비동기, Promise, async, await 확실하게 이해하기](https://velog.io/@coin46/%EB%B9%84%EB%8F%99%EA%B8%B0%EB%A5%BC-%EC%B2%98%EB%A6%AC%ED%95%98%EB%8A%94-%EC%BD%9C%EB%B0%B1-Promise-asyncawait)
