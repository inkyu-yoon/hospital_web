
# 전국 병·의원 데이터 웹 페이지 만들기

## ERD

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/erd.png" alt="erd" style="zoom:130%;" />
</p>

<br>

## 병·의원 데이터 정보 및 라이브러리

데이터 출처 : https://www.localdata.go.kr/devcenter/dataDown.do?menuNo=20001

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221109132651464.png" alt="image-20221109132651464" style="zoom:80%;" />
</p>

병원 데이터와 의원 데이터 결합, 총 121,005개의 데이터 (병원 6992개 + 의원 114013개)

데이터 형식 에러 튜플 2개 제외, 총 121003개 데이터

- Gradle 프로젝트 빌드

  ```groovy
  dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-mustache'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'com.mysql:mysql-connector-j:8.0.31'
	implementation 'com.github.gavlyukovskiy:p6spy-spring-boot-starter:1.8.1'
	implementation 'com.google.code.gson:gson:2.10'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

	//security 관련 라이브러리
	implementation 'org.springframework.security:spring-security-test'
	implementation group: 'io.jsonwebtoken', name: 'jjwt', version: '0.9.1'
	implementation group: 'org.springframework.boot', name: 'spring-boot-starter-security', version: '2.7.5'

  }
  ```

추가한 라이브러리는 위와 같다.

<br>


## 1. 전국 병 · 의원 데이터, MYSQL 데이터 베이스에 데이터 입력하기



### DDL 사용해서 데이터 테이블 생성 및 DB에 데이터 입력

1. application.yml 설정

```yaml
server:
  servlet:
    encoding:
      force-response: true
      
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/project-db #ec2 서버 사용시, 환경변수 사용
    username: root
    password: 12341234
    driver-class-name: com.mysql.cj.jdbc.Driver
    
  jpa:
    hibernate:
      ddl-auto: create  #테이블 생성 후 update 로 변경
    show-sql: true
    database-platform: org.hibernate.dialect.MySQL55Dialect
    database: mysql

logging:
  level:
    org.hibernate.sql: debug

jwt:
  token:
    secret: hello # Jwt 토큰 생성 시 사용, 실제 사용하는 값은 환경변수로 입력
```



2. 데이터베이스 연결 & Jwt 토큰 사용을 위한 환경변수 추가
```
SPRING_DATASOURCE_URL=jdbc:mysql://데이터베이스연결된ec2주소 :3306/db명 ;SPRING_DATASOURCE_PASSWORD=비밀번호 ; JWT_TOKEN_SECRET=비밀키
```



3. Hospital 클래스 생성

[Hospital 엔티티 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/domain/entity/Hospital.java)



4. 데이터 파일(txt) 한 줄 씩 읽어와서 객체 리스트를 생성하는 클래스 ReadData

[ReadData 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/parser/ReadData.java)



5. 읽어온 한 줄을 객체로 정의하고, List 컬렉션에 저장하기 위한 HospitalParser

[HospitalParser 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/parser/HospitalParser.java)


6. List 에 담긴 객체들을 데이터베이스에 입력하는 HospitalJpaRepository 소스 코드

데이터베이스에 저장하기 위해, 스프링 부트가 아닌 `resources.META-INF.persistence.xml` 을 생성하고, 순수 JPA의 persist로 입력하였다.

- [persistence.xml 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/resources/META-INF/persistence.xml)

- [HospitalJpaRepository 소스코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/repository/HospitalJpaRepository.java)

- [데이터 입력 실행 메인 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/InsertData.java)


<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221129011919246.png" alt="image-20221129011919246" style="zoom:80%;" />
</p>

SQL 쿼리문의 결과와 같이, 총 121003개의 데이터가 잘 입력되었다.

<br>

---

## 2. 회원 가입 기능과 암호화

`BcryptPasswordEncoder` 클래스의 `encode()` 메서드를 사용하여 암호화 한 뒤 DB에 저장하도록 구현하였고,

병원 리뷰 작성 & 게시판 게시글 작성 시 ID와 비밀번호를 함께 입력받도록 하여, `matches()` 메서드로 DB에 암호화 되어 있는 비밀번호와 같은지 확인한 뒤

일치하는 경우에만 입력할 수 있고록 구현하였다.

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212051256248.png" alt="image-20221212051256248" style="zoom: 67%;" />
</p>

회원 가입 시, 계정명, 비밀번호, 이름, 이메일, 전화번호를 입력받도록 하였고, 회원 계정명은 `unique` 속성을 부여하여, 중복될 수 없다.

form 에 입력 시, post 요청으로 DB에 입력되도록 하였다.

- [User Controller 소스 코드](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/UserController.java)

---

## 3. 병원 정보 표시 및 검색 기능

### 메인 화면 
<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212045103811.png" alt="image-20221212045103811" style="zoom: 60%;" />
</p>

화면 구성은 [Bootstrap](https://getbootstrap.com/docs/5.2/getting-started/introduction/) 을 사용하였고, template 라이브러리는  mustache를 사용하였다.

#### 1. 지역명 검색 기능
- [Hospital Controller 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/HospitalController.java)
- [Hospital repository 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/repository/HospitalRepository.java)
> JpaRepository 를 상속받는 Hospital Repository의 Jpa 메서드 명명규칙과 Pageable 클래스를 사용해서 약 12만건 데이터 페이징 구현

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212050448219.png" alt="image-20221212050448219" style="zoom:67%;" />
</p>

`울산` 검색 시 위와 같이 데이터가 필터링 되어 표시된다.

#### 2. 병원 상세 정보 및 리뷰 작성 기능

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212051906612.png" alt="image-20221212051906612" style="zoom:67%;" />
</p>

병원 이름 클릭 시, 위와 같은 상세 정보가 표시되고, 병원 도로명 주소 클릭 시, 네이버 맵 `search` api를 적용하여 검색되도록 구현하였다.

```
<td><a href="/hospitals/{{id}}/details">{{hospitalName}}</a></td> 
<td><a href="https://map.naver.com/v5/search/{{roadNameAddress}} "target="_blank">{{roadNameAddress}}</a></td>
```

<br>

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221212054416192.png" alt="image-20221212054416192" style="zoom:67%;" /></p>

회원 가입을 진행하여, 회원 계정이 DB에 있는 경우에만 리뷰를 위와 같이 등록할 수 있다.

또한, 회원가입을 했던 계정과 비밀번호가 일치해야 리뷰 등록이 되도록 구현하였다.

- [Review Controller 소스파일](https://github.com/inkyu-yoon/hospital_web/blob/main/src/main/java/hospital/web/controller/ReviewController.java)

