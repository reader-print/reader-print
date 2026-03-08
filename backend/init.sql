-- ===============================================
-- 초기 디비 구성
-- ===============================================
SET NAMES utf8mb4; -- 인코딩 문제

-- 기존 테이블 삭제 (역순)
DROP TABLE IF EXISTS rating_score;
DROP TABLE IF EXISTS inquiry;
DROP TABLE IF EXISTS review;
DROP TABLE IF EXISTS user_rating_criteria;
DROP TABLE IF EXISTS user_tag;
DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS user_detail;
DROP TABLE IF EXISTS rating_criteria;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS user;

-- ===============================================
-- 테이블 생성
-- ===============================================

-- 사용자 기본 정보
CREATE TABLE user (
  seq BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id VARCHAR(20) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50) UNIQUE NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 일반 사용자 전용 정보
CREATE TABLE user_detail (
  user_seq BIGINT PRIMARY KEY,
  sns_linked BOOLEAN DEFAULT FALSE,
  block_count INT DEFAULT 0,
  blocked_until TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자 프로필
CREATE TABLE user_profile (
  user_seq BIGINT PRIMARY KEY,
  bio VARCHAR(300),
  profile_image VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 태그
CREATE TABLE tag (
  tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자-태그 관계
CREATE TABLE user_tag (
  user_seq BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_seq, tag_id),
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES tag(tag_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 별점 기준
CREATE TABLE rating_criteria (
  criteria_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  is_activate BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자-별점 기준 관계
CREATE TABLE user_rating_criteria (
  user_seq BIGINT NOT NULL,
  criteria_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_seq, criteria_id),
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE,
  FOREIGN KEY (criteria_id) REFERENCES rating_criteria(criteria_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 책
CREATE TABLE book (
  book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  isbn VARCHAR(20),
  title VARCHAR(255) NOT NULL,
  author VARCHAR(255),
  publisher VARCHAR(255),
  published DATE,
  genre VARCHAR(50),
  country VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 리뷰
CREATE TABLE review (
  review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_seq BIGINT NOT NULL,
  book_id BIGINT NOT NULL,
  content TEXT,
  is_blocked BOOLEAN DEFAULT FALSE,
  blocked_reason VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  reading_start_date DATE,
  reading_end_date DATE,
  UNIQUE KEY unique_user_book (user_seq, book_id),
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES book(book_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 별점
CREATE TABLE rating_score (
  review_id BIGINT NOT NULL,
  criteria_id BIGINT NOT NULL,
  score INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id, criteria_id),
  FOREIGN KEY (review_id) REFERENCES review(review_id) ON DELETE CASCADE,
  FOREIGN KEY (criteria_id) REFERENCES rating_criteria(criteria_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 문의
CREATE TABLE inquiry (
  inquiry_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_seq BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  type VARCHAR(50) NOT NULL,
  status VARCHAR(50) DEFAULT '대기',
  answer TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES user(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===============================================
-- 샘플 데이터 삽입
-- ===============================================

-- 사용자 샘플 데이터
-- 비밀번호 : password
INSERT INTO user (user_id, email, password, nickname, role) VALUES
('test1', 'test1@example.com', '$2a$10$KLPQoFX8fZMUJnKKlG3XQeRBEKTtF6yt.eDozCFxG0ybEkI5zjC4K', '독서왕김씨', 'ROLE_USER'),
('admin', 'admin@example.com', '$2a$10$KLPQoFX8fZMUJnKKlG3XQeRBEKTtF6yt.eDozCFxG0ybEkI5zjC4K', '관리자', 'ROLE_ADMIN');

-- 사용자 상세 정보
INSERT INTO user_detail (user_seq, sns_linked, block_count) VALUES
(1, FALSE, 0),
(2, FALSE, 0);

-- 사용자 프로필
INSERT INTO user_profile (user_seq, bio) VALUES
(1, '책 읽는 것을 좋아하는 개발자입니다.'),
(2, '관리자 계정입니다.');

-- 태그 샘플 데이터
INSERT INTO tag (name, description, is_active) VALUES
('잡식러', '모든 장르를 가리지 않고 봄.', TRUE),
('몰입형독자', '읽기 시작하면 시간 가는 줄 모름.', TRUE),
('완독주의자', '한 번 시작한 책은 반드시 끝까지 읽는다.', TRUE),
('문장수집가', '좋은 문장 보면 밑줄 긋고 기록하는 타입.', TRUE),
('활자중독자', '책 없이는 하루도 못 버팀.', TRUE),
('느긋한독서가', '하루 몇 장씩 천천히 곱씹으며 읽음.', TRUE),
('비평가기질', '읽으면서 항상 분석하고 평가함.', TRUE),
('감성러', '문장보다 감정의 여운을 더 중요하게 여김.', TRUE),
('공감러', '책 속 인물의 감정에 쉽게 이입하는 타입.', TRUE),
('지식수집가', '새로운 정보나 개념 배우는 걸 좋아함.', TRUE),
('삶탐구자', '책으로 인생의 의미를 찾으려 함.', TRUE),
('문체덕후', '스토리보다 문장의 맛을 즐김.', TRUE),
('속독마스터', '내용 파악 중심의 빠른 독서 선호.', TRUE),
('인용러', '책의 한 문장으로 생각을 표현함.', TRUE),
('실용파', '생활에 직접 도움이 되는 책 위주로 읽음.', TRUE),
('도전러', '어려운 책도 포기하지 않고 끝까지 읽음.', TRUE),
('문장수집가', '좋은 문장은 무조건 기록해야 함.', TRUE),
('소설파', '이야기에 몰입하는 걸 좋아함.', TRUE),
('논픽션러', '사실 기반의 책을 선호함.', TRUE),
('철학덕후', '철학적 사유와 개념을 즐김.', TRUE),
('과학러', '과학적 사고와 논리에 매료됨.', TRUE),
('자기계발러', '성장과 효율을 중시하는 독자.', TRUE),
('역사마니아', '과거를 통해 현재를 이해함.', TRUE),
('시애호가', '짧은 문장에 담긴 깊은 의미를 사랑함.', TRUE),
('책시작장인', '시작은 잘하지만 완독은 가끔...', TRUE),
('책탑건설자', '읽을 책은 쌓여가지만 읽진 않음.', TRUE),
('북유목민', '책을 여기저기 조금씩 읽다 놓음.', TRUE),
('책스포주의자', '결말을 먼저 보는 스타일.', TRUE),
('표지러버', '책 내용보다 표지 디자인에 끌림.', TRUE),
('도서관전사', '책은 안 사지만 도서관 출석은 꾸준함.', TRUE),
('카공러', '카페에서 책 읽는 게 인생의 낙.', TRUE),
('집콕리더', '집 안 독서가 제일 편안함.', TRUE),
('야행독자', '밤이 되면 비로소 책을 펼침.', TRUE),
('출퇴근리더', '이동 중 시간을 활용하는 타입.', TRUE),
('주말리더', '주말에 몰아서 읽는 스타일.', TRUE),
('기분파', '그날 기분에 따라 책을 고름.', TRUE);

-- 사용자-태그 관계
INSERT INTO user_tag (user_seq, tag_id) VALUES
(1, 1),
(1, 3),
(1, 5);

-- 별점 기준 샘플 데이터
INSERT INTO rating_criteria (name, description, is_activate) VALUES
('스토리', '이야기 전개와 구성', TRUE),
('문체', '글쓰기 스타일과 표현력', TRUE),
('몰입도', '책에 빠져드는 정도', TRUE),
('재독 의향', '다시 읽고 싶은 정도', TRUE),
('추천도', '다른 사람에게 권하고 싶은 정도', TRUE);

-- 사용자-별점 기준 관계
INSERT INTO user_rating_criteria (user_seq, criteria_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

-- 책 샘플 데이터
INSERT INTO book (isbn, title, author, publisher, published, genre, country) VALUES
('9788936433598', '1984', '조지 오웰', '민음사', '2003-01-25', '소설', '영국'),
('9788936434267', '데미안', '헤르만 헤세', '민음사', '2000-06-30', '소설', '독일');

-- 리뷰 샘플 데이터
INSERT INTO review (user_seq, book_id, content, is_blocked, reading_start_date, reading_end_date) VALUES
(1, 1, '전체주의 사회를 경고하는 디스토피아 소설의 걸작입니다. 현대 사회에도 많은 시사점을 던져줍니다.', FALSE, '2025-09-01', '2025-09-05'),
(1, 2, '성장과 자아 발견에 대한 깊은 통찰을 담은 작품입니다. 청소년기에 읽으면 더 좋을 것 같습니다.', FALSE, '2025-09-01', '2025-09-05');

-- 별점 샘플 데이터
INSERT INTO rating_score (review_id, criteria_id, score) VALUES
(1, 1, 5),
(1, 2, 4),
(1, 3, 5),
(1, 4, 4),
(1, 5, 5),
(2, 1, 4),
(2, 2, 5),
(2, 3, 4),
(2, 4, 3),
(2, 5, 4);

-- 문의 샘플 데이터
INSERT INTO inquiry (user_seq, title, content, type, status) VALUES
(1, '책 등록이 안됩니다', 'ISBN 검색이 되지 않는데 수동으로 등록할 수 있나요?', '기능문의', 'WAITING');

-- ===============================================
-- 초기화 완료 메시지
-- ===============================================
SELECT '데이터베이스 초기화가 완료되었습니다.' AS message;

