-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Хост: localhost
-- Время создания: Окт 15 2016 г., 21:29
-- Версия сервера: 5.7.14-8-beget-log
-- Версия PHP: 5.6.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `elizar9l_compost`
--

-- --------------------------------------------------------

--
-- Структура таблицы `route`
--
-- Создание: Окт 15 2016 г., 10:44
-- Последнее обновление: Окт 15 2016 г., 11:31
--

DROP TABLE IF EXISTS `route`;
CREATE TABLE `route` (
  `id` varchar(100) NOT NULL,
  `user_id` varchar(100) NOT NULL,
  `route_number` varchar(10) NOT NULL,
  `vehicle_number` int(25) NOT NULL,
  `price` int(11) NOT NULL,
  `currency` varchar(10) NOT NULL,
  `vehicle_color` varchar(20) NOT NULL,
  `vehicle_type` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `route`
--

INSERT INTO `route` (`id`, `user_id`, `route_number`, `vehicle_number`, `price`, `currency`, `vehicle_color`, `vehicle_type`) VALUES
('580210a1d044a', '58014aa4e9e84', '47K', 0, 25, 'RUR', 'белый', 'газель'),
('580210bf7232d', '580149d1edf4d', '47K', 0, 25, 'RUR', 'белый', 'газель');

-- --------------------------------------------------------

--
-- Структура таблицы `transaction`
--
-- Создание: Окт 15 2016 г., 12:53
-- Последнее обновление: Окт 15 2016 г., 18:10
--

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` varchar(50) NOT NULL,
  `route_number` varchar(10) NOT NULL,
  `vehicle_number` varchar(20) NOT NULL,
  `price` int(11) NOT NULL,
  `currency` varchar(20) NOT NULL,
  `timestamp` int(11) NOT NULL,
  `payment_id` varchar(50) NOT NULL,
  `publican_id` varchar(50) NOT NULL,
  `publican_sign` varchar(5000) NOT NULL,
  `payer_id` varchar(50) NOT NULL,
  `payer_sign` varchar(5000) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `transaction`
--

INSERT INTO `transaction` (`id`, `route_number`, `vehicle_number`, `price`, `currency`, `timestamp`, `payment_id`, `publican_id`, `publican_sign`, `payer_id`, `payer_sign`) VALUES
('580245e694c76', '47к', '', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('580245fd92173', '47к', '', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('5802466abf3b5', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024c402dde6', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024cfbab0a9', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024d0c24f3f', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024e73d42fe', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024e74c547f', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024e7599847', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024e7615eff', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58024f77a27cd', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58025114d4f81', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('5802514a0a566', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('5802517571463', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('5802525324188', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b23', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('580252c4eb9e5', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b22', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026d99d2d90', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d2b12', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026da3c8e9d', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d1b12', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026db7e30a5', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d1b112', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026dc29d907', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d1b1123', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026dcee76d3', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d1b1113', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('58026dd8057dc', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, 'b1c5ad42-328c-4fda-bd0d-76b3495d1b13', '58014aa4e9e84', '...', '580149d1edf4d', '...'),
('5802710dab398', '47к', 'x001ep163rus', 25, 'RUR', 1234567890, '9b9c5e4c-71cb-4d45-afb4-6d70fff4c96c', '58014aa4e9e84', 'ddddd', '580149d1edf4d', 'ffffffff');

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--
-- Создание: Окт 14 2016 г., 20:48
-- Последнее обновление: Окт 15 2016 г., 18:10
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `name` varchar(250) NOT NULL,
  `public_key` varchar(5000) NOT NULL,
  `balance` int(11) NOT NULL,
  `role` int(11) NOT NULL,
  `hash` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `name`, `public_key`, `balance`, `role`, `hash`) VALUES
('580149d1edf4d', 'test1', 'public_ke', -375, 1, 'password'),
('58014aa4e9e84', 'test11', 'public_ke', 475, 1, 'password'),
('5801e8ee76b60', 'test12', 'public_ke', 100, 1, 'password');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `route`
--
ALTER TABLE `route`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
