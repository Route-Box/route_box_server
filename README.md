# RouteBox

## 서버 주소
- 개발: https://api-dev.myroutebox.com/
- 운영: https://api.myroutebox.com/

## 기술 스택
### Backend
- **Language & Framework**
  - Java 17
  - Spring Boot
  - Spring Security
  - Spring Data JPA
  - QueryDSL

- **Database**
  - MySQL (AWS RDS)
  - Redis (AWS ElastiCache)

- **Infrastructure**
  - AWS ECS (Container Orchestration)
  - AWS ECR (Container Registry)
  - AWS S3 (File Storage)
  - AWS ElastiCache (Redis)
  - AWS RDS (MySQL)

- **DevOps**
  - Docker
  - GitHub Actions (CI/CD)
  - Gradle

- **Documentation**
  - Swagger (OpenAPI 3.0)

- **Testing**
  - JUnit 5
  - Mockito
  - Jacoco (Test Coverage)

## 프로젝트 설정

### 필수 요구사항
- JDK 17
- Gradle
- Docker (배포 시 필요)

### 환경 설정
1. `src/main/resources/env.properties` 파일을 생성하고 다음 형식으로 환경변수를 설정하세요:
```
# DB
DB_URL=your_db_url
DB_USERNAME=your_username
DB_PASSWORD=your_password

# JWT
JWT_SECRET_KEY=your_jwt_secret

# Redis
REDIS_HOST=your_redis_host
REDIS_PORT=your_redis_port

# Swagger
SWAGGER_SERVER_URL=http://localhost:8080

# AWS
AWS_S3_BUCKET_NAME=your_bucket_name
AWS_S3_ACCESS_KEY=your_access_key
AWS_S3_SECRET_KEY=your_secret_key
```

## 빌드 및 실행

### 로컬 개발 환경

```
# 프로젝트 빌드
./gradlew build
```

```
# 테스트 실행
./gradlew test
```

```
# 애플리케이션 실행
./gradlew bootRun
```

### 테스트 커버리지 확인
- 테스트 실행 후 Jacoco 리포트는 `build/reports/jacoco/test/html/index.html`에서 확인할 수 있습니다.

## 배포 환경

### 개발(Development)
- Branch: `develop`
- 자동 배포: GitHub Actions를 통해 develop 브랜치에 push 시 자동으로 AWS ECS에 배포
- 환경: AWS ECS, ECR, RDS, ElastiCache(Redis)

### 운영(Production)
- Branch: `main`
- 배포 환경: AWS ECS
- 인프라: 
  - Database: AWS RDS (MySQL)
  - Cache: AWS ElastiCache (Redis)
  - Storage: AWS S3
  - Container Registry: AWS ECR

## CI/CD

### CI (Continuous Integration)
- Pull Request 생성 시 자동으로 다음 작업 수행:
  - 코드 빌드
  - 테스트 실행
  - Jacoco 테스트 커버리지 리포트 생성

### CD (Continuous Deployment)
- develop 브랜치에 push 시:
  1. Docker 이미지 빌드
  2. AWS ECR에 이미지 푸시
  3. AWS ECS 서비스 업데이트

## API 문서
- Swagger UI
    - `http://localhost:8080/swagger-ui.html` (로컬 환경)
    - `https://api-dev.myroutebox.com/swagger-ui.html` (개발 환경)
    - `https://api.myroutebox.com/swagger-ui.html` (운영 환경)
