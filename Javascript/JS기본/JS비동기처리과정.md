## JS 비동기 처리 과정

&nbsp;&nbsp;비동기처리 과정을 이해하기에 앞서 가장 먼저 기억해야 할 것은 자바스크립트는 기본적으로 싱글스레드로 동기적 프로세스만을 지원한다는 것입니다. 그렇다면 실제로 자바스크립트가 어떻게 통신 요청과 같은 프로세스를 비동기적으로 처리하는지 살펴보겠습니다.

<br>

### Web API

<figure align="center" style="width: 100%">
  <img src="../images/js_engine.gif" style="width: 600px" />
</figure>

&nbsp;&nbsp;브라우저는 자바스크립트 런타임 환경으로 우리가 알고 있는 Javascript인 `ECMAScript`와 `이벤트 루프`, `콜백 큐`, `Web API` 등을 가지고 있습니다. 일반적인 자바스크립트 코드는 순차적으로 `호출 스택(Call Stack)`에 담아 처리됩니다.

&nbsp;&nbsp;`호출 스택`만으로는 비동기 처리가 불가능하기 때문에 브라우저 엔진은 `이벤트 루프(Event Loop)`와 `테스크 큐(Task Queue or Callbacl Queue)`라는 장치들을 통해 비동기 로직을 처리합니다. 비동기 요청이 처리되는 과정은 다음과 같은 순서로 처리됩니다.

<br>

**비동기 처리 프로세스**

1. 개발자가 작성한 호출 스택에 담긴비동기 함수는 Web API를 호출하고 호출 스택에서 제거됩니다.(자바스크립트 자체에는 비동기 처리를 위한 API가 존재하지 않습니다)

2. 비동기 함수 내부에 있던 콜백 함수는 테스크 큐에 삽입되어 대기하게 되고 호출 스택이 빌때까지 대기합니다.

3. 이벤트 루프는 호출 스택이 모두 비었을 때, 테스크 큐 내부에 있는 콜백 함수를 호출 스택으로 모두 이동시킵니다.

<br>

&nbsp;&nbsp;예시 코드를 살펴보며 비동기 처리가 진행되는 과정을 살펴보고 결과를 예측해보도록 합시다.

```javascript
console.log("Hi");

setTimeout(() => {
  console.log("Time out");
}, 0);

console.log("Bye");
```

&nbsp;&nbsp;위 코드를 실행한 뒤, 콘솔에 출력된 결과를 살펴보면 timeout이 0초임에도 불구하고 "Hi" -> "Bye" -> "Time out" 순서로 출력된 것을 확인할 수 있을 것입니다. 이는 비동기 코드가 먼저 콜 스택에 쌓였음에도 바로 Web API를 호출한 뒤 테스크 큐로 이동했기 때문입니다. 테스크 큐에 쌓인 콜백 함수들은 콜 스택이 빌 때까지 대기하다 이벤트 루프에 의해 다시 콜 스택으로 이동한 뒤 실행됩니다.

<br>

### Task Queue

&nbsp;&nbsp;먼저 아래 문제를 살펴보고 콘솔에 어떤 순서로 문자가 찍힐지 예상해보도록 합시다.

```javascript
console.log("Start!");

setTimeout(() => {
  console.log("Time out");
}, 0);

Promise.resolve("Promise").then((res) => {
  console.log(res);
});

console.log("End");
```

&nbsp;&nbsp;`setTimeout`이 0초 뒤에 실행되므로 "Time out"이 "Promise"보다 먼저 콘솔에 찍힐 것 같지만 실제로는 "Start" -> "End" -> "Promise" -> "Time out" 순서로 콘솔에 출력됩니다.

&nbsp;&nbsp;테스크 큐(Task Queue)는 자세히 살펴보면 `Microtask Queue`와 `Macrotask Queue`로 구분됩니다. 각 큐에 삽입되는 비동기 이벤트의 종류는 다음과 같습니다.

**Mircrotask**

- process.nextTick()
- Promise callback
- async function

**Macrotask**

- setTimeout()
- setInterval()
- setImmediate()

<br>

<figure align="center" style="width: 100%">
  <img src="../images/taskqueue.gif" style="width: 600px" />
</figure>

&nbsp;&nbsp;Task의 우선순위는 Microtask가 높게 가져갑니다. 호출 스택에 삽입된 setTimeout과 Promise는 각각 Macrotask, Microtask Queue에 삽입되는데 "End"가 출력되고 호출 스택이 비게 되면 이벤트 루프는 우선적으로 Microtask Queue 내부의 모든 콜백 함수들을 순차적으로 호출 스택으로 이동시킵니다.

<br>

**Reference**

- Javascript Deep Dive

- [Javascript Visualsized](https://dev.to/lydiahallie/javascript-visualized-promises-async-await-5gke#syntax)

