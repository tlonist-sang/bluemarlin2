# bluemarlin-backend

bluemarlin2 project 

백엔드:
- 커스텀 JWT 필터를 통한 access, refresh token 관리.
- JPA 엔티티 개선
- ExceptionHandler 를 통한 exception 통합관리
- blocking queue를 이용한 유저별 알람 메일링 쓰레드 관리
- maven packaging 이 front/back 이 executable jar 를 생성

프론트엔드:
- React.js 로 라이브러리 변경 (기존 vue.js)
- Redux를 통한 팝업 등의 reusable component 구현
- axios interceptor를 통한 에러 상황 처리
- semantic-ui 로 css 소스 변경 (기존 bootstrap)
 
CI-CD:
- Github 을 이용한 코드 관리
- Travis-CI를 통한 자동 CI-CD 배포
- aws elastic beanstalk, s3, elasticsearch 사용