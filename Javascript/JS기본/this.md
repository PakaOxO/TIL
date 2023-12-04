## this

&nbsp;&nbsp;Java에서 `this`는 인스턴스 자신을 가리키는 참조변수이지만, Javaecript에서는 `this`에 바인딩 되는 객체는 함수의 호출 방식에 따라 달라집니다.

&nbsp;&nbsp;자바스크립트의 `this`는 문맥에 따라 다양한 값을 가지게 되는데, 이 부분이 자주 헷갈려 글로 남겨 정리하려고 합니다. `this`의 값은 이를 포함하는 함수가 어떤 방식으로 실행되느냐에 따라 결정됩니다. 이제부터 각각의 케이스에 대해 알아보겠습니다.

<br>

### 1. 일반 함수 실행(Regular Function Call)

&nbsp;&nbsp;기본적으로 `this`는 전역객체(Global object)에 바인딩됩니다. 전역함수가 아닌 내부함수의 경우에도 `this`는 외부함수를 가리키는 것이 아닌 전역객체에 바인딩됩니다.

```javascript
function foo() {
  console.log(this); // window
  bar();

  function bar() {
    console.log(this); // window
  }
}

foo();
```

<br>

&nbsp;&nbsp;메소드의 내부함수의 경우에도 `this`는 전역객체에 바인딩됩니다.

```javascript
var value = 1; // var가 아니면 window 객체의 프로퍼티 X

const obj = {
  value: 100,
  foo: function () {
    console.log(this); // obj
    console.log(this.value); // 100

    function bar() {
      console.log(this); // window
      console.log(this.value); // 1
    }

    bar();
  },
};

obj.foo();
```

<br>

&nbsp;&nbsp;콜백함수 역시 `this`는 전역객체에 바인딩됩니다.

```javascript
var value = 1;

const obj = {
  value: 100,
  foo: function () {
    setTimeout(function () {
      console.log(this); // window
      console.log(this.value); // 100
    }, 1000);
  },
};

obj.foo();
console.log("hi");
```

<br>

### 2. 메소드 호출 (Method Call)

&nbsp;&nbsp;함수가 어떤 객체의 프로퍼티의 값이면 메소드로서 호출되게 되는데, 메소드 내부의 `this`는 메소드를 소유한 객체에 바인딩되어 메소드를 호출한 객체를 가리킵니다. 메소드는 객체와 이 객체가 가진 메소드 사이에 .(dot)를 붙여 호출되는데, 호출된 메소드의 this는 dot 앞의 객체를 가리킨다고 볼 수 있습니다.

```javascript
var name = "Kim";

const obj = {
  name: "Lee",
  sayName1: function () {
    console.log(this.name); // Lee
  },
  sayName2: () => {
    console.log(this.name); // Kim
  },
};

obj.sayName1();
obj.sayName2();
```

<br>

> ❗ **화살표 함수(Arrow Function)과 렉시컬 스코프**
>
> &nbsp;&nbsp;메소드가 화살표 함수(arrow function)으로 정의된 경우에 함수는 자신의 상위 환경의 this를 계승하는 Lexical this를 따르게 됩니다. 위 예시에서 sayName2의 렉시컬 스코프는 전역 스코프이므로 "kim"을 출력하게 됩니다.

<br>

### 3. 생성자 함수 호출

&nbsp;&nbsp;객체지향 언어의 생성자 함수(new)와 다르게 Javascript에서는 일반적인 함수에 new 연산자를 붙여서 호출하면 해당 함수는 생성자 함수로 객체(인스턴스)를 반환하게 됩니다. 특이하게 함수 내부의 this 역시 함수가 생성자로 호출되면 생성된 인스턴스 자신(self)을 가리킵니다.

```javascript
function func(name) {
  this.name = name;
  console.log(this); // { name: "Kim" }
}

const me = func("Kim");
console.log(me); // { name: "Kim" }
```

<br>

### 4. 명백한 바인딩 (Explicit Binding)

&nbsp;&nbsp;Javascript 엔진에 의해 암묵적으로 바인딩되는 `this`외에 `this`를 특정 객체에 명시적으로 바인딩하는 방법으로 다음과 같은 방법들이 있습니다.

<br>

**Function.prototype.apply**

&nbsp;&nbsp;apply 메소드는 함수를 특정 객체에 바인딩하기 위한 명시적 바인딩 방법으로 첫번째 인자로 바인딩할 객체를, 두번째 인자로 함수에 넘겨줄 매개변수들을 받습니다.

```javascript
const func = function (name, age) {
  this.name = name;
  this.age = age;
};

var foo = {};

func.apply(foo, ["Kim", 10]);
console.log(foo); // { name: "Kim", age: 10 }
```

<br>

**Function.prototype.call**

&nbsp;&nbsp;call 메소드는 apply 메소드와 유사하나 두번째 인자로 배열을 넘겨주었던 것과 달리 첫번째 인자 뒤에 오는 인자들을 차례로 함수의 매개변수로 넘겨줍니다.

```javascript
const func = function (name, age) {
  this.name = name;
  this.age = age;
};

var foo = {};

func.call(foo, "Kim", 10);
console.log(foo); // { name: "Kim", age: 10 }
```

<br>

**Function.prototype.bind**

&nbsp;&nbsp;bind 메소드는 ES6에서 추가된 메소드로 함수를 객체에 바인딩하기 위해 사용됩니다.

```javascript
const func = function () {
  console.log(this.firstName, this.age);
};

var person = { firstName: "Kim", age: 10 };

const func2 = func.bind(person);
func(); // undefined, undefined
func2(); // Kim, 10
```

<br>

**Reference**

- 모던 자바스크립트 Deep Dive

- [im-developer님 블로그](https://im-developer.tistory.com/96)
