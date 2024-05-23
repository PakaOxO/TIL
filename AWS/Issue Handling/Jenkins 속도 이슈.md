
&nbsp;&nbsp;빌드 중 성능 문제로 서버가 응답이 없어 EC2를 한번 중단 후 재시작 했더니 Jenkins 접속시 속도 이슈가 있었습니다. 이 문제는 EC2 중단으로 재시작시 주소가 바뀌면서 Jenkins 접속 요청시 다른 주소를 거쳐 돌아오게 되면서 발생하는 이슈로 아래와 같이 설정을 변경해 해결할 수 있었습니다.

<br>

&nbsp;&nbsp;'Jenkins 관리'의 'System'에서 Jenkins URL을 변경된 주소로 수정합니다.

![Jenkins URL 변경|](jenkins_url.png)

<br>

**References**
- [Jenkins 속도 이슈](https://jiholine10.tistory.com/414)