# job4j_cars

## Описание проекта

Учебный проект для раздела Web курса Job4J: Middle

Проект представляет собой сайт по продаже машин.
На сайте должны быть объявления. В объявлении должно быть: описание, марка машины, тип кузова, фото.
Объявление имеет статус продано или нет.

## Стек технологий
- Java 17
- Spring Boot 2.7.3
- PostgreSQL 14.5
- Hibernate 5.6.11
- Thymeleaf 3.0.15
- Boostrap 4.4.1
- Liquibase 4.15.0

## Требования к окружению
- Java 17+
- Maven 3.8
- PostgreSQL 14
- 
### Создание схемы БД
```shell
CREATE DATABASE job4j_cars
```

### Инициализация БД
Сначала необходимо настроить подключение Liquibase к созданной базе данных.<br>
Для этого папке проекта /db создать файл liquibase.properties, в котором указать параметры подключения к базе данных.<br>
В качестве примера можно использовать файл liquibase.properties.example.<br>
Пример содержимого файла настроек liquibase.properties:<br>
```
changeLogFile: db/master.xml
url: jdbc:postgresql://hostname:port/db_name
username: db_username
password: db_password
```

Затем, командной строке перейти в папку проекта и запустить команду<br>
```shell
mvn liquibase:update
```

### Настройка подключения к БД
В папке проекта /src/main/resources создать файл hibernate.cfg.xml, в котором указать параметры подключения к базе данных.<br>
В качестве примера можно использовать файл hibernate.cfg.xml.example.<br>
Пример содержимого файла настроек hibernate.cfg.xml:<br>
```
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "http://hibernate.org/dtd/hibernate-configuration-3.0.dtd">
<hibernate-configuration>
    <session-factory>
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>
        <property name="hibernate.connection.url">jdbc:postgresql://hostname:port/db_name</property>
        <property name="hibernate.connection.username">db_username</property>
        <property name="hibernate.connection.password">db_password</property>
        <property name="hibernate.connection.pool_size">10</property>
        <property name="hibernate.current_session_context_class">thread</property>
        <property name="hibernate.show_sql">true</property>
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>
        <property name="hibernate.jdbc.time_zone">Europe/Moscow</property>

        <mapping class="ru.job4j.cars.model.Car" />
        <mapping class="ru.job4j.cars.model.PriceHistory" />
        <mapping class="ru.job4j.cars.model.Engine" />
        <mapping class="ru.job4j.cars.model.Driver" />
        <mapping class="ru.job4j.cars.model.Post" />
        <mapping class="ru.job4j.cars.model.User" />
        <mapping class="ru.job4j.cars.model.BodyStyle" />
        <mapping class="ru.job4j.cars.model.ExteriorColor" />
        <mapping class="ru.job4j.cars.model.TransmissionType" />
        <mapping class="ru.job4j.cars.model.Drivetrain" />
        <mapping class="ru.job4j.cars.model.EngineType" />
        <mapping class="ru.job4j.cars.model.Brand" />
        <mapping class="ru.job4j.cars.model.PostSearchResult" />
        <mapping class="ru.job4j.cars.model.SalesOrder" />
    </session-factory>
</hibernate-configuration>
```

## Запуск приложения
```shell
mvn spring-boot:run
```

## Взаимодействие с приложением
### Регистрация пользователя
![Регистрация пользователя](/img/001_registration.png)
На этой странице пользователь может создать новый аккаунт в системе, введя логин, пароль и свое имя (никнейм).

### Авторизация пользователя
![Авторизация пользователя](/img/002_authorization.png)
На этой странице пользователь, который уже имеет аккаунт, может авторизоваться и войти в систему.

### Главная страница
![Главная страница](/img/003_homepage.png)
На этой странице отображается полный перечень активных объявлений, зарегистрированных в системе.

### Поиск
![Поиск](/img/004_search.png)
С помощью фильтров поиска пользователь может искать объявления по различным критериям: цена, пробег, тип кузова, тип двигателя и т.п.

### Просмотр объявления
![Просмотр объявления](/img/005_post_view.png)
На данной странице отображается подробная информация, указанная в объявлении.
Нажав на кнопку "Начать сделку" пользователь инициирует сделку в рамках объявления.

### Мои объявления
![Мои объявления](/img/006_my_posts.png)
На данной странице отображаются все объявления, размещенные пользователем.
Для каждого активного объявления доступны три действия: "Редактировать", "Посмотреть отклики", "Деактивировать".
Объявления, по которым уже завершена сделка, остаются в архиве и недоступны для редактирования.

### Отклики на объявления
![Отклики на объявления](/img/007_post_participates.png)
На данной странице отображается перечень всех пользователей, откликнувшихся на объявление, т.е. потенциальных покупателей.
Продавец может подтвердить совершение сделки с покупателем, нажав на кнопку "Оформить сделку".
После этого объявление становится неактивным, а в БД создается соответствующая запись о совершенной сделке.

### Редактирование объявления
![Редактирование объявления](/img/008_edit_post.png)
На данной странице пользователь может отредактировать некоторые данные, содержащиеся в объявлении:
- Текстовое описание
- Фотография объявления
- Цена

Характеристики автомобиля в режиме редактирования объявления менять нельзя.

## Контакты
email: nikolai.gladkikh.biz22@gmail.com