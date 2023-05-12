# diary

## description 
- 자신의 일기를 기록하는 웹 어플리케이션입니다.
원하는 것을 기록하고 저장하는 공간입니다. 

- [데모 사이트 링크](https://black-diary.netlify.app)
  - AWS 비용 문제로 개선 중에 있습니다. 

- 상세내용 노션 링크
  - https://sleet-termite-41e.notion.site/bd7a5743da1845aa8a6dda490d44bc44

### 기술 스택
***
#### 백엔드
- Java (Language)
- Spring (API Server)
- JPA (ORM)
- Junit (test)
#### 프론트 엔드
- JavaScript 
- Vue

### 데브옵스
- AWS(EC2, S3, CodeDeploy)
- MySQL (RDB)
- GitHub action

*** 
### UI (예정)

### 폴더 구조 
- domain: 데이터 별로 그룹핑
  - api: controller 관리
  - dto: request/ response DTO 관리
  - entity: domain entity 관리
  - exception: 해당 domain 발생하는 Exception 관리
  - repository: domain에 DB 접근 관리
- global: 프로젝트 전방위적으로 사용되는 객체 관리
  - common(예정)
  - config(예정)
  - error(예외 핸들링)
  - util(유틸성 클래스)
  
### 주요 버전 
- gradle 7.5.1
- springframework - version 2.7.6
- Java 11


### 기능 내용
- 어드민 회원
  - 회원가입, 로그인
  
- 다이어리(diary) 기능
  - 등록, 수정, 삭제 기능
  - 유저는는 여러 다이어리 소유(N) 가능
  - 검색
