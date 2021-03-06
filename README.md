# Компостер

Проект для HaskDay 43 (Пенза), 14 - 16 октября 2016

## OSP

Наша команда, состоящая из четырёх разработчиков (iOS, Android, backend, языки Objective C, Swift, Java, Kotlin, PHP и другие) со стажем от 3 до 10 лет и двух аналитиков, занимается разработкой частично-оффлайновой бесконтактной системы оплаты на базе криптографических алгоритмов, которая ставит своей целью дать возможность небольшим организациям и частным предпринимателям проводить безналичную оплату B2C услуг при помощи распространённых мобильных устройств без дополнительного оборудования.

## 3+3

*Зачем - какова ваша мотивация и решаемая проблема?*

Путешествуя по России, печально видеть отставание некоторых регионов от крупных городов в плане автоматизации типовых операций. Без сокращения этого разрыва нельзя позиционировать Россию как единое, технологически развитое государство. Например, безналичная оплата проезда в общественном транспорте и такси зачастую невозможна, а даже если и производится с помощью так называемых “транспортных карт”, имеет ряд явных проблем, например:
конечное число точек пополнения
невозможность пополнения с банковской карты
невозможность оплаты проезда за других пассажиров или передача средств со счёта на счёт
“бутылочные горла” и заторы рядом с терминалами оплаты

*Что вы делаете?*

Чтобы решить эти проблемы, мы разрабатываем частично-оффлайновую бесконтактную систему оплаты на базе криптографических алгоритмов. 
“Частично-оффлайновая” означает, что интернет-соединение требуется только на устройстве получателя платежа (водителя или кондуктора транспортного средства). Интернет-соединение на стороне плательщика (пассажир транспортного средства) не требуется. Это снижает технические требования к эксплуатации системы клиентом.
“Бесконтактная” - для оплаты не требуется физический контакт с терминалом (как в случае с транспортной картой или наличными платежами). Оплата возможна на дистанции 5-10 метров. Это позволит избежать заторов в общественных местах, например при посадке в общественный транспорт, и повысит комфорт оплаты - можно оплатить после уже после посадки, не вставая с места.
“На базе криптографических алгоритмов” - безопасность платежей обеспечивается алгоритмом RSA. Перевод денег с одного счёта на другой возможен только с согласия владельцев обоих счетов.

*Для кого?*

С точки зрения приёма платежей, в эксплуатации системы заинтересованы представители мелкого бизнеса и частные предприниматели, занимающиеся пассажирскими перевозками, желающие повысить точность расчётов и учёта финансов, а так же создать имидж современной и технологичной организации.
С точки зрения оплаты услуг, система может быть интересная жителям регионов, в которых по каким-то причинам нет таких крупных игроков, как Убер или Яндекс Такси.

*Почему именно сейчас?*

Бесконтактная оплата в регионах развита слабо, следовательно при должном позиционировании системы она может уже в ближайшее время занять свою нишу.

*Кто вы такие и почему именно вы можете продолжать развивать этот продукт?*

Мы - это команда IT-специалистов различного профиля с большим опытом разработки мобильных приложений. У нас есть все необходимые технические знания и навыки для развития системы.

*Что вы планируете сделать по проекту в ближайшие 48 дней?*

Первая функциональная версия системы должна включать в себя приложения под Андроид и Айос, бек-енд для проведения транзакций и информационный портал. Предполагается интеграция с существующими поставщиками бесконтактной оплаты - Подорожник, Золотая корона и другие.
Получение такой версии займет не менее четырех месяцев и потребует труд пяти специалистов, в т.ч. по электронным платежам, маркетингу и других.

## Демонстрация работы

https://www.youtube.com/watch?v=unD30DcIBYI

#hackday #hackday43
