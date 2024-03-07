
>[!tip] 서론
>
>&nbsp;&nbsp;개발자 인터뷰에서 자주 등장하는 CS 질문들을 정리하고, 복습하기 위한 노트입니다. 학습한 내용을 배경으로 면접을 준비하는 과정에서 작성된 노트인만큼 정확하지 않는 정보가 포함되어 있을 수 있습니다.

<br>

### 운영체제(OS)란?

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;운영체제는 실행할 프로그램에 필요한 자원을 할당하고, 프로그램이 올바르게 실행되도록 돕는 특별한 프로그램입니다. 운영체제는 컴퓨터 부팅 시에 메모리 내 `커널 영역(kernel space)`라는 별도의 공간에 따로 적재되어 실행됩니다.
</details>

<br>

### 커널(Kernel)영역이란?

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;메모리는 크게 커널 영역과 사용자 영역으로 구분됩니다. 그 중에서 커널 영역은 필요한 운영체제의 핵심 부분만을 메인 메모리에 적재하여 운영체제를 사용하기 위한 메모리 영역입니다. 메모리의 커널영역을 차지하는 커널은 운영체제의 핵심 서비스로 자원에 접근하고 조작하는 기능, 프로그램이 안전하게 실행되게 하는 기능 등이 포함되어 있습니다.
</details>

<br>

### 메모리의 구조

<details>
  <summary>펼쳐보기</summary>
  &nbsp;&nbsp;메모리는 크게 커널 영역과 사용자 영역으로 구분되어 있습니다. 그 중 사용자 영역은 다시 코드, 데이터, 힙, 스택 영역으로 나뉩니다.
<br>
  &nbsp;&nbsp;`코드 영역`은 실행할 수 있는 코드인 기계어로 이루어진 명령어가 저장되는 영역입니다. CPU가 실행할 명령어가 담겨 있기 때문에 쓰기가 금지되어 있는 `읽기 전용(read-only)` 공간입니다. 코드 영역은 텍스트 영역(text segment)라고도 부릅니다.
<br>
  &nbsp;&nbsp;`데이터 영역`은 프로그램이 실행되는 동안 유지할 데이터가 할당되는 공간입니다. 대표적으로 `전역 변수(global variable)` 등이 저장됩니다.
<br>
  &nbsp;&nbsp;`힙 영역`은 프로그래머가 직접 할당할 수 있는 저장 공간으로 객체 등이 동적으로 메모리에 할당, 해제됩니다. 메모리 공간에 힙 영역을 할당을 했다면 이를 운영체제에 반환하는 과정도 필요합니다. 만약 메모리 공간이 반환되지 않는다면 메모리의 낭비 현상인 `메모리 누수(memory leak)`가 발생할 수 있습니다. 힙 영역은 스택 영역과 겹치는 것을 방지하기 위해 메모리의 낮은 주소에서 높은 주소로 할당됩니다.
<br>
  &nbsp;&nbsp;`스택 영역`은 정적 할당 영역인 데이터 영역과 달리 데이터가 일시적으로 저장되는 공간입니다. 대표적으로 `매개 변수`, `지역 변수` 등이 저장됩니다. 힙 영역과 겹치는 것을 방지하기 위해 메모리 주소가 높은 주소에서 낮은 주소로 할당됩니다.
</details>