
&nbsp;&nbsp; Unity에서 Raycast는 씬에 투영된 직선의 광선이 특정 오브젝트에 적중되면 true를 리턴하는 물리 메서드이다.

<br>

### **유니티 제공 Raycast Methods (2020.3v)**

```c#
bool Raycast(Vector3 origin, Vector3 direction, float maxDistance = Mathf.Infinity, int layerMask = DefaultRaycastLayers, QueryTriggerInteraction queryTriggerInteraction = QueryTriggerInteraction.UseGlobal);

bool Raycast(Vector3 origin, Vector3 direction, out RaycastHit hitInfo, float maxDistance, int layerMask, QueryTriggerInteraction queryTriggerInteraction);

bool Raycast(Ray ray, float maxDistance = Mathf.Infinity, int layerMask = DefaultRaycastLayers, QueryTriggerInteraction queryTriggerInteraction = QueryTriggerInteraction.UseGlobal);

bool Raycast(Ray ray, out RaycastHit hitInfo, float maxDistance = Mathf.Infinity, int layerMask = DefaultRaycastLayers, QueryTriggerInteraction queryTriggerInteraction = QueryTriggerInteraction.UseGlobal);
```

<br>

&nbsp;&nbsp; 여러 파라미터 중에서 우리는 Ray, RaycastHit, Raycast에 대해 알아보자.

<br>

#### **Ray**

- Ray는 직선 광선의 시작점(origin)과 방향(direction)을 가지는 단순한 구조체.
- origin과 direction은 Vector3 타입.
- Ray를 생성하는 방법

```c#
// new 생성자를 사용해 직접 생성
Ray ray = new Ray(transform.position, tranform.forward);

// 카메라 Viewport 중앙에서 시작하는 Ray는 Helper 함수를 사용해 자동으로 Ray를 생성
Ray ray = Camera.main.ViewportPointToRay(new Vector3(0.5f, 0.5f, 0));

// 스크린 마우스 위치를 기준으로 Ray 생성
Ray ray = Camera.main.ScrenenPointToRay(Input.mousePosition);

// Helper 함수를 사용해 월드의 특정 지점에서 Ray 생성
// 단, update 시마다 ray의 시작점과 방향이 달라질 수 있으므로 주의
Ray ray;

void Update()
{
  ray = transfrom.position, transform.farward;
}
```

<br>

#### **RaycastHit**

- 위에서 생성한 Ray가 오브젝트와 충돌했을 때, 해당 오브젝트와의 충돌 결과 데이터를 저장하는 구조체.
- RaycastHit는 아래에서 다룰 Raycast 함수에서 리턴 받을 수 있다.

```c#
RaycastHit hitData;

// Ray로 감지한 오브젝트 위치
Vector3 hitPos = hitData.point;

// origin부터 대상 오브젝트 사이의 거리
float hitDistance = hitData.distance;

// 대상 오브젝트의 Collider 정보
String tag = hitData.collider.tag;

// 대상 오브젝트의 Transform 참조
GameObject hitObject = hitData.transform.gameObject;
```

<br>

#### **Raycast**

- Ray로부터 얻을 수 있는, 충돌여부 및 충돌정보(RaycastHit)와 같은 정보를 리턴하는 함수.
- 아래 예시는 가장 많이 사용되는 Raycast 함수 작성법으로 오브젝트와의 히트 여부(bool)를 리턴하고 out
  파라미터로 RaycastHit을 되돌려 줌.(다른 함수의 오버로딩 함수는 위에서 코드로 언급)

```c#
void shotRay()
{
  Ray ray = new Ray(transform.position, transform.forward);
  RaycastHit hitData;

  Physics.Raycast(ray, out hitData);
}
```

<br><br>

### **Raycast의 다양한 기능**

#### **Raycast의 최대거리 지정**

&nbsp;&nbsp; Raycast 내부에 최대거리를 파라미터로 넣음으로써 Raycast의 최대거리를 제한할 수 있다.

```c#
Ray ray = new Ray(transform.position, transform.forward);

if (Physics.Raycast(ray, 10))
{
  // Logic After Ray Hit
}
```

<br>

#### **Layer Mask**

&nbsp;&nbsp; Raycast로 특정 오브젝트 그룹만을 확인하고 싶다면 Layer Mask를 지정하면 된다.
Layer Mask를 지정하여 지정된 Layer외에는 Ray의 충돌판정이 발생하지 않는다.

```c#
public LayerMask interactable;

void ShootRay()
{
  Ray ray = Camera.main.ViewportPointToRay(new Vector3(0.5f, 0.5f, 0));
  if (Physics.Raycast(ray, 10, interactable)) {
    // 상호작용 가능한 오브젝트와 충돌 시 실행될 로직
  }
}
```

<br><br>
