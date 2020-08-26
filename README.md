# apsheron
Rest 
   
curl.md

curl samples (application deployed at application context restaurant-apsheron).
For windows use Git Bash
get All managers
curl -s http://localhost:8080/restaurant-apsheron/rest/admin/managers --manager admin@gmail.com:admin
get managers 100001
curl -s http://localhost:8080/restaurant-apsheron/rest/admin/managers/100001 --manager admin@gmail.com:admin
get All foods
curl -s http://localhost:8080/restaurant-apsheron/rest/profile/foods --manager manager@yandex.ru:password
get foods 100003
curl -s http://localhost:8080/restaurant-apsheron/rest/profile/foods/100003 --manager manager@yandex.ru:password
filter foods
curl -s "http://localhost:8080/restaurant-apsheron/rest/profile/foods/filter?startDate=2020-01-30&startTime=07:00:00&endDate=2020-01-31&endTime=11:00:00" --manager manager@yandex.ru:password
get foods not found
curl -s -v http://localhost:8080/restaurant-apsheron/rest/profile/foods/100008 --manager manager@yandex.ru:password
delete foods
curl -s -X DELETE http://localhost:8080/restaurant-apsheron/rest/profile/foods/100002 --manager manager@yandex.ru:password
create foods
curl -s -X POST -d '{"dateTime":"2020-02-01T12:00","description":"Created lunch","calories":300}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restaurant-apsheron/rest/profile/foods --manager manager@yandex.ru:password
update foods
curl -s -X PUT -d '{"dateTime":"2020-01-30T07:00", "description":"Updated breakfast", "calories":200}' -H 'Content-Type: application/json' http://localhost:8080/restaurant-apsheron/rest/profile/foods/100003 --manager manager@yandex.ru:password
