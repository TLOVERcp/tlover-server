# 필요없는 컬럼 삭제
alter table user drop column user_email;
alter table reply drop column is_deleted;