## **Updates 종류**

---

### **Update**

```c#
void Update() { /* code */}
```

- Script가 enabled 상태일 때, 매 프레임마다 호출.
- 물리 효과를 적용하지 않은 물체의 움직임이나 타이머, 키 입력 등을 받기 위해 사용.

<br>

### **FixedUpdate**

```c#
void FixedUpdate() { /* code */}
```

- 매 프레임마다 호출되는 Update와 달리 Fixed Timestop에 설정된 값에 따라 일정한 간격으로 호출.(기본 값은 50회)
- Rigidbody에 의해 물리 시스템의 영향을 받는 오브젝트의 움직임을 위해 조정하기 위해 사용.

<br>

### **LateUpdate**

```c#
void LateUpdate() { /* code */}
```

- 모든 Update 메서드가 호출된 뒤 마지막에 호출.
- 오브젝트를 따라 이동하는 카메라의 경우 Update 메서드 내부에서 오브젝트의 위치가 변경될 수 있기 때문에 주로 LateUpdate 내부에서
  움직이도록 코드를 작성.
