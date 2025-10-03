-- ===============================================
-- 초기 디비 구성
-- ===============================================

-- 기존 테이블 삭제 (역순)
DROP TABLE IF EXISTS RatingScore;
DROP TABLE IF EXISTS Inquiry;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS User_RatingCriteria;
DROP TABLE IF EXISTS UserTag;
DROP TABLE IF EXISTS UserProfile;
DROP TABLE IF EXISTS UserDetail;
DROP TABLE IF EXISTS RatingCriteria;
DROP TABLE IF EXISTS Tag;
DROP TABLE IF EXISTS Book;
DROP TABLE IF EXISTS User;

-- ===============================================
-- 테이블 생성
-- ===============================================

-- 사용자 기본 정보
CREATE TABLE User (
  seq BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id VARCHAR(20) UNIQUE NOT NULL,
  email VARCHAR(255) UNIQUE NOT NULL,
  password VARCHAR(255) NOT NULL,
  nickname VARCHAR(50) UNIQUE NOT NULL,
  role VARCHAR(20) NOT NULL DEFAULT 'USER',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 일반 사용자 전용 정보
CREATE TABLE UserDetail (
  user_seq BIGINT PRIMARY KEY,
  sns_linked BOOLEAN DEFAULT FALSE,
  block_count INT DEFAULT 0,
  blocked_until TIMESTAMP NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자 프로필
CREATE TABLE UserProfile (
  user_seq BIGINT PRIMARY KEY,
  bio VARCHAR(300),
  profile_image VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 태그
CREATE TABLE Tag (
  tag_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  is_active BOOLEAN DEFAULT TRUE,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자-태그 관계
CREATE TABLE UserTag (
  user_seq BIGINT NOT NULL,
  tag_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_seq, tag_id),
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE,
  FOREIGN KEY (tag_id) REFERENCES Tag(tag_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 별점 기준
CREATE TABLE RatingCriteria (
  criteria_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(50) NOT NULL,
  description VARCHAR(200),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 사용자-별점기준 관계
CREATE TABLE User_RatingCriteria (
  user_seq BIGINT NOT NULL,
  criteria_id BIGINT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (user_seq, criteria_id),
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE,
  FOREIGN KEY (criteria_id) REFERENCES RatingCriteria(criteria_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 책
CREATE TABLE Book (
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
CREATE TABLE Review (
  review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_seq BIGINT NOT NULL,
  book_id BIGINT NOT NULL,
  content TEXT,
  is_blocked BOOLEAN DEFAULT FALSE,
  blocked_reason VARCHAR(255),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY unique_user_book (user_seq, book_id),
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE,
  FOREIGN KEY (book_id) REFERENCES Book(book_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 별점
CREATE TABLE RatingScore (
  review_id BIGINT NOT NULL,
  criteria_id BIGINT NOT NULL,
  score INT NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (review_id, criteria_id),
  FOREIGN KEY (review_id) REFERENCES Review(review_id) ON DELETE CASCADE,
  FOREIGN KEY (criteria_id) REFERENCES RatingCriteria(criteria_id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 문의
CREATE TABLE Inquiry (
  inquiry_id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_seq BIGINT NOT NULL,
  title VARCHAR(255) NOT NULL,
  content TEXT NOT NULL,
  type VARCHAR(50) NOT NULL,
  status VARCHAR(50) DEFAULT '대기',
  answer TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_seq) REFERENCES User(seq) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ===============================================
-- 샘플 데이터 삽입
-- ===============================================

-- 사용자 샘플 데이터 -- 비밀번호 : password
INSERT INTO User (user_id, email, password, nickname, role) VALUES
('test1', 'test1@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '독서왕김씨', 'USER'),
('admin', 'admin@example.com', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', '관리자', 'ADMIN');

-- 사용자 상세 정보
INSERT INTO UserDetail (user_seq, sns_linked, block_count) VALUES
(1, FALSE, 0),
(2, FALSE, 0);

-- 사용자 프로필
INSERT INTO UserProfile (user_seq, bio) VALUES
(1, '책 읽는 것을 좋아하는 개발자입니다.'),
(2, '관리자 계정입니다.');

-- 태그 샘플 데이터
INSERT INTO Tag (name, description, is_active) VALUES
('소설', '소설 장르', TRUE),
('자기계발', '자기계발 도서', TRUE),
('IT/프로그래밍', 'IT 및 프로그래밍 관련 도서', TRUE),
('에세이', '에세이 장르', TRUE),
('판타지', '판타지 장르', TRUE);

-- 사용자-태그 관계
INSERT INTO UserTag (user_seq, tag_id) VALUES
(1, 1),
(1, 3),
(1, 5);

-- 별점 기준 샘플 데이터
INSERT INTO RatingCriteria (name, description) VALUES
('스토리', '이야기 전개와 구성'),
('문체', '글쓰기 스타일과 표현력'),
('몰입도', '책에 빠져드는 정도'),
('재독 의향', '다시 읽고 싶은 정도'),
('추천도', '다른 사람에게 권하고 싶은 정도');

-- 사용자-별점기준 관계
INSERT INTO User_RatingCriteria (user_seq, criteria_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 5);

-- 책 샘플 데이터
INSERT INTO Book (isbn, title, author, publisher, published, genre, country) VALUES
('9788936433598', '1984', '조지 오웰', '민음사', '2003-01-25', '소설', '영국'),
('9788936434267', '데미안', '헤르만 헤세', '민음사', '2000-06-30', '소설', '독일');

-- 리뷰 샘플 데이터
INSERT INTO Review (user_seq, book_id, content, is_blocked) VALUES
(1, 1, '전체주의 사회를 경고하는 디스토피아 소설의 걸작입니다. 현대 사회에도 많은 시사점을 던져줍니다.', FALSE),
(1, 2, '성장과 자아 발견에 대한 깊은 통찰을 담은 작품입니다. 청소년기에 읽으면 더 좋을 것 같습니다.', FALSE);

-- 별점 샘플 데이터
INSERT INTO RatingScore (review_id, criteria_id, score) VALUES
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
INSERT INTO Inquiry (user_seq, title, content, type, status) VALUES
(1, '책 등록이 안됩니다', 'ISBN 검색이 되지 않는데 수동으로 등록할 수 있나요?', '기능문의', '대기');

-- ===============================================
-- 초기화 완료 메시지
-- ===============================================
SELECT '데이터베이스 초기화가 완료되었습니다.' AS message;