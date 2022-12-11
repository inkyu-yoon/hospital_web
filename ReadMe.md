
# 전국 병의원 데이터 웹 페이지 만들기

## ERD

<p align="center">
<img src="https://raw.githubusercontent.com/buinq/imageServer/main/img/image-20221211025814358.png" alt="image-20221211025814358" style="zoom:120%;" />
</p>

<br>

## 데이터 정보 및 라이브러리

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

