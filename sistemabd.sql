-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-07-2026 a las 02:26:04
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistemabd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `telas`
--

CREATE TABLE `telas` (
  `id` bigint(20) NOT NULL,
  `color` varchar(255) DEFAULT NULL,
  `imagen_url` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `precio` double DEFAULT NULL,
  `stock` int(11) DEFAULT NULL,
  `tipo_material` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `telas`
--

INSERT INTO `telas` (`id`, `color`, `imagen_url`, `nombre`, `precio`, `stock`, `tipo_material`) VALUES
(1, 'Gris Oscuro', '/catalogo/001.jpeg', 'Lana de Traje (Super 120s)', 45, 0, 'Lana'),
(2, 'Champagne', '/catalogo/002.jpeg', 'Seda Satinada', 60, 500, 'Seda'),
(3, 'Beige Natural', '/catalogo/003.jpeg', 'Lino Premium', 35, 800, 'Lino'),
(4, 'Azul Marino', '/catalogo/004.jpeg', 'Gabardina de Algodón', 25, 1200, 'Algodón'),
(5, 'Blanco', '/catalogo/005.jpeg', 'Encaje Bordado', 80, 300, 'Encaje'),
(6, 'Verde Esmeralda', '/catalogo/006.jpeg', 'Terciopelo (Velvet)', 55, 600, 'Terciopelo'),
(7, 'Blanco y Negro', '/catalogo/007.jpeg', 'Tweed estilo Chanel', 70, 400, 'Lana Mixta'),
(8, 'Lavanda', '/catalogo/008.jpeg', 'Crepé de China', 40, 700, 'Seda Mixta'),
(9, 'Blanco y Negro', '/catalogo/009.jpeg', 'Pied de Poule', 50, 900, 'Lana'),
(10, 'Vino/Borgoña', '/catalogo/010.jpeg', 'Chifón (Gasas)', 20, 1500, 'Poliéster/Seda');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` bigint(20) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rol` varchar(255) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `mfa_enabled` bit(1) DEFAULT NULL,
  `mfa_secret` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `password`, `rol`, `username`, `mfa_enabled`, `mfa_secret`) VALUES
(1, '$2a$10$JXjeSGCi1WwBdTiG6iDPae5W6IxAxQSIu6EZhGa7p0VrPMK/mmKya', 'ADMIN', 'admin', b'1', 'Y2MZI242KDZXJKDCOYY3BKQWF7EEY764'),
(3, '$2a$10$2T1Z9WkYq6o09T7o7P6SVuZtN5X4XyN7b9pCq6w8nO2D9s4t5nOqG', 'ADMIN', 'supervisor', b'0', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ventas`
--

CREATE TABLE `ventas` (
  `id` bigint(20) NOT NULL,
  `cantidad` int(11) NOT NULL,
  `fecha_pedido` datetime(6) NOT NULL,
  `ruc` varchar(255) NOT NULL,
  `total` double NOT NULL,
  `tela_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ventas`
--

INSERT INTO `ventas` (`id`, `cantidad`, `fecha_pedido`, `ruc`, `total`, `tela_id`) VALUES
(1, 100, '2026-04-23 22:29:08.000000', '1072503291', 4500, 1),
(2, 900, '2026-04-24 23:42:45.000000', '1234568', 40500, 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `telas`
--
ALTER TABLE `telas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKm2dvbwfge291euvmk6vkkocao` (`username`);

--
-- Indices de la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK6mdd2endrdn7org1boywsi1f3` (`tela_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `telas`
--
ALTER TABLE `telas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ventas`
--
ALTER TABLE `ventas`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ventas`
--
ALTER TABLE `ventas`
  ADD CONSTRAINT `FK6mdd2endrdn7org1boywsi1f3` FOREIGN KEY (`tela_id`) REFERENCES `telas` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
