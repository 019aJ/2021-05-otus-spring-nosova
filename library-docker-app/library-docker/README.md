Домашнее задание
Обернуть приложение в docker-контейнер (Серверная часть, клиент см library-docker-react)

Цель: деплоить приложение в современном DevOps-стеке
Результат: обёртка приложения в Docker

Обернуть приложение в docker-контейнер. Dockerfile принято располагать в корне репозитория. В image должна попадать JAR-приложения. Сборка в контейнере рекомендуется, но не обязательна.
БД в собственный контейнер оборачивать не нужно (если только Вы не используете кастомные плагины)
Настроить связь между контейнерами, с помощью docker-compose
