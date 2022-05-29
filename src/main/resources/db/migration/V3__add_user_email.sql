# 에러 때문에 다시 user_email 추가
ALTER TABLE user ADD COLUMN user_email varchar(255) null;