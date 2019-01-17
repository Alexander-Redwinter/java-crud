ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
#ALTER USER 'root'@'localhost' IDENTIFIED WITH caching_sha2_password BY 'root';

#Исправление/обход/костыль для java.sql.SQLException Unable to load authentication plugin 'caching_sha2_password'
#После проверки пожалуйста запустите скрипт с закомментированной первой и раскомментированной второй строкой чтобы вернуть все как было