# Шаблон для практической работы по теме JDBC

### Задание №1
Составить инфологическую и даталогическую модель БД, исходя из выбранного варианта финальной работы. Имплементировать
схему в реальную БД MySQL, сделать дамп базы. Поместить файлы дампа в директорию `src/main/resources`

### Задание №2
Написать приложение на java, реализующее запросы к базе данных БД«Интернет-аукцион», указанные в предыдущей задаче. 
Каждый запрос реализовать отдельным методом класса.

### Задание №3
Реализовать спринг-бут приложение, которое через JDBC будет подключаться к БД и, используя JdbcTemplate, будет выполнять
запросы, реализованные в задании №2. DataSource и JdbcTemplate должны быть созданы в конфигурации приложения. 
Перед стартом всех запросов необходимо наполнить БД тестовыми данными.
