-- phpMyAdmin SQL Dump
-- version 4.6.6
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 07, 2017 at 08:17 AM
-- Server version: 10.1.20-MariaDB
-- PHP Version: 7.0.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `id1337028_dbfaskes`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'rofi', 'imron'),
(2, 'admin', 'admin');

-- --------------------------------------------------------

--
-- Table structure for table `faskes`
--

CREATE TABLE `faskes` (
  `id` int(11) NOT NULL,
  `tipe` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `jenis` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `nama` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `alamat` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `kecamatan` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `telepon` varchar(15) COLLATE utf8_unicode_ci NOT NULL,
  `latitude` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `longitude` varchar(20) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `faskes`
--

INSERT INTO `faskes` (`id`, `tipe`, `jenis`, `nama`, `alamat`, `kecamatan`, `telepon`, `latitude`, `longitude`) VALUES
(1, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Arjuno', 'Jl. Arjuno No.17', 'Klojen', '0341356339', '-7.9781387', '112.6266307'),
(2, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Bareng', 'Jl. Bareng Tebes IV A / 639', 'Klojen', '0341322280', '-7.9788353', '112.6232203'),
(3, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Celaket', 'Jl. Simpang Kasembon No.5', 'Klojen', '0341356380', '-7.9815062', '112.6267827'),
(4, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Cisadea', 'Jl. Cisadea No.19, Kec. Blimbing\r\n', 'Blimbing', '0341489540', '-7.95544', '112.643587'),
(5, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Kendal Kerep', 'Jl. Sulfat No.100', 'Blimbing', '0341477838', '-7.9612031', '112.6508529'),
(6, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Pandan Wangi', 'Jl. LA. Sucipto No.315', 'Blimbing', '0341484472', '-7.9472411', '112.6552292'),
(7, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Kedungkandang', 'Jl. Ki Ageng Gribig No.242', 'Kedungkandang', '0341710112', '-7.9931129', '112.648131'),
(8, 'FaskesTingkat1', 'Puskesmas', 'Puskemas Gribig', 'Jl. Ki Ageng Gribig, Malang', 'Kedungkandang', '0341718165', '-7.9807003', '112.6653153'),
(9, 'FaskesTingkat1', 'Puskesmas', 'Puskemas Arjowinangun', 'Jl. Raya Arjowinangun no.2', 'Kedungkandang', '0341751398', '-8.038622', '112.641816'),
(10, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Janti', 'Jl. Janti Barat No.88', 'Sukun', '0341352203', '-8.001', '112.620549'),
(11, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Cipto Mulyo', 'Jl. Kol. Sugiyono VIII No.54', 'Sukun', '0341329918', '-8.002043', '112.6299579'),
(12, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Dinoyo', 'Jl. Keramik No.2', 'Lowokwaru', '0341581442', '-7.943378', '112.611279'),
(13, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Mojolangu', 'Jl. Sudimoro No.17A', 'Lowokwaru', '0341482905', '-7.93529', '112.626973'),
(14, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Mulyorejo', 'Jl. Budi Utomo No.11 A', 'Sukun', '0341580955', '-7.982074', '112.626938'),
(15, 'FaskesTingkat1', 'Puskesmas', 'Puskesmas Kendalsari', 'Jl. Cengger Ayam No.8', 'Lowokwaru', '0341478215', '-7.9462596', '112.6308858'),
(16, 'FaskesTingkat1', 'Puskesmas', 'RB Pemkot', 'Jl. Panji Suroso No.9', 'Blimbing', '0341491320', '-7.9422168', '112.6489084'),
(35, 'FaskesTingkat1', 'Optik', 'Optik Vista', 'Jl. S. Wiryo Pranoto no.31 A', 'Klojen', '0341351466', '-7.9849539', '112.6311039'),
(36, 'FaskesTingkat1', 'Optik', 'Optik Internasional', 'Jl. S. Wiryo Pranoto no.25', 'Klojen', '0341361651', '-7.984637', '112.631215'),
(37, 'FaskesTingkat1', 'Optik', 'Optik Intercontinental', 'Jl. S. Wiryo Pranoto no.2F Malang\r\n', 'Klojen', '0341361920', '-7.984718', '112.6308313'),
(38, 'FaskesTingkat1', 'Optik', 'Optik Focus', 'Jl Kahuripan No 16B Malang', 'Klojen', '0341347109', '-7.976424', '112.630321'),
(39, 'FaskesTingkat1', 'Optik', 'Optik Internasional Kepuh', 'Jl. S. Supriadi No. 45 A Malang\r\n', 'Sukun', '0341832958', '-8.0122059', '112.620164'),
(40, 'FaskesTingkat1', 'Optik', 'Optik Internasional Dinoyo', 'Jl. MT Haryono Nomor 167-A Malang\r\n', 'Lowokwaru', '0341551830', '-7.939103', '112.607752'),
(41, 'FaskesTingkat1', 'Optik', 'Optik internasional blimbing', 'jl. Ahmad yani No 14- B malang\r\n', 'Blimbing', '0341417899', '-7.943631', '112.641671'),
(42, 'FaskesTingkat1', 'Optik', 'Optik Star', 'jl. Letjen Sutoyo No 56 Malang', 'Klojen', '0341491873', '-7.960937', '112.636843'),
(43, 'FaskesTingkat1', 'Optik', 'optik Indo', 'Jl. Arif Rahman Hakim No.26F', 'Klojen', '0341329466', '-7.9807588', '112.6276946'),
(44, 'FaskesTingkat1', 'RumahSakit', 'RSUD Kota Malang', 'Jl. Rajasa- Bumiayu no.27', 'Kedungkandang', '0341754338', '-8.0263478', '112.6393954'),
(45, 'FaskesTingkat1', 'RumahSakit', 'Rumah Sakit Permata bunda', 'Jl. Soekarno Hatta No.75 Mojolangu', 'Lowokwaru', '0341487487', '-7.938896', '112.6248248'),
(46, 'FaskesTingkat1', 'RumahSakit', 'RUMKITBAN', 'Jl. Panglima Sudirman Malang\r\n', 'Blimbing', '-', '-7.9740891', '112.6386934'),
(47, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit dr. Soepraoen', 'Jl.  S Supriadi No 22 Malang', 'Sukun', '0341325113', '-7.989928', '112.6214189'),
(48, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit Islam Unisma', 'Jl. Mayjend Haryono No.139 Malang\r\n', 'Lowokwaru', '0341551356', '-7.9401441', '112.6090101'),
(49, 'FaskesTingkat2', 'RumahSakit', 'RSIA Puri Bunda', 'Jl. Simpang Sulfat Utara 60 A Malang', 'Blimbing', '0341480047', '-7.9586933', '112.6554416'),
(50, 'FaskesTingkat2', 'RumahSakit', 'RSIA Melati Husada', 'Jl.  Kawi No 32 Malang', 'Klojen', '0341341357', '-7.9760335', '112.6213975'),
(51, 'FaskesTingkat2', 'RumahSakit', 'RSI Aisyiyah', 'Jl. Sulawesi no 19 Malang', 'Klojen', '0341326773', '-7.9886734', '112.6252909'),
(52, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit Umum Lavelette', 'Jl. W.R. Supratman No. 10 Malang', 'Klojen', '0341470805', '-7.965773', '112.637877'),
(53, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit Panti Nirmala', 'Jl. Kebalen Wetan No. 2-8 Malang', 'Klojen', '0341362459', '-7.994361', '112.633917'),
(54, 'FaskesTingkat2', 'RumahSakit', 'RSIA Muhammadiyah', 'Jl. Wahid Hasyim No. 30 Malang', 'Klojen', '0341326222', '-7.9841278', '112.6290835'),
(55, 'FaskesTingkat2', 'RumahSakit', 'RSIA Galeri Candra', 'Jl Andong No. 3 Malang', 'Lowokwaru', '0341478571', '-7.947945', '112.619574'),
(56, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit Persada Hospital', 'Jl. Panji Suroso ,Araya Bussines Centre Kav. II-IV', 'Blimbing', '03412996333', '-7.9349396', '112.6499545'),
(57, 'FaskesTingkat2', 'RumahSakit', 'Rumah Sakit Panti Waluya Sawahan', 'Jl. Nusakambangan No. 56', 'Klojen', '0341366033', '-7.9863695', '112.6250535'),
(58, 'FaskesTingkat3', 'RumahSakit', 'Rumah Sakit dr. Saiful Anwar', 'Jl. JA Suprapto No 2 Malang', 'Klojen', '0341362101', '-7.9724887', '112.6315624'),
(59, 'FaskesTingkat1', 'Klinik', 'Klinik Rawat Jalan Ontoseno', 'Jl. Ontoseno I No. 2 Malang', 'Blimbing', '0341335154', '-7.983812', '112.644408'),
(60, 'FaskesTingkat1', 'Klinik', 'Klinik Hamid Rusdi', 'Jl. Hamid Rusdi No.45 Malang', 'Blimbing', '0341362954', '-7.967988', '112.641404'),
(61, 'FaskesTingkat1', 'Klinik', 'G and G Health Clinic', 'Jl. Terusan Kawi Kav 9 No 9\r\n', 'Klojen', '0341568527', '-7.974097', '112.616038'),
(62, 'FaskesTingkat1', 'Klinik', 'Klinik Melati', 'Jl. Jaksa Agung Suprapto No 23 Malang\r\n', 'Klojen', '0341336271', '-7.97103', '112.63127'),
(63, 'FaskesTingkat1', 'Klinik', 'klinik 24 jam griya bromo', 'Jl. Bromo No. 7 Malang\r\n', 'Klojen', '0341335957', '-7.978175', '112.625476'),
(64, 'FaskesTingkat1', 'Klinik', 'Klinik Melati Panjaitan', 'Jl. Mayjend Panjaitan 247 Kav 1', 'Klojen', '0341550121', '-7.951537', '112.617086'),
(65, 'FaskesTingkat1', 'Klinik', 'klinik Kimia Farma Bromo', 'Jl. Bromo No 1 Malang\r\n', 'Klojen', '0341360301', '-7.9788646', '112.6250992'),
(66, 'FaskesTingkat1', 'Klinik', 'Klinik bahrul Magfiroh', 'Jl. Batu Permata no 1\r\n', 'Lowokwaru', '085101533389', '-7.933713', '112.603226'),
(67, 'FaskesTingkat1', 'Klinik', 'Klinik Panti Rahayu', 'Jl. Simpang Borobudur no. 1\r\n', 'Lowokwaru', '085103067005', '-7.939823', '112.638683'),
(68, 'FaskesTingkat1', 'Klinik', 'Poliklinik Widya Husada', 'Jl. Sudimoro 16 Malang\r\n', 'Lowokwaru', '0341407530', '-7.9384024', '112.6319623'),
(69, 'FaskesTingkat1', 'Klinik', 'Klinik Elisa', 'Jl.  Danau Toba E5/22 Malang\r\n', 'Kedungkandang', '0341718377', '-7.979889', '112.658277'),
(70, 'FaskesTingkat1', 'Klinik', 'Pr. Adi Bungsu', 'Jl. Ki Ageng Gribig No.1 Malang\r\n', 'Kedungkandang', '0341718885', '-7.983254', '112.660959'),
(71, 'FaskesTingkat1', 'Klinik', 'Klinik Telemedika Healt Center', 'Jl. Raya Danau Sentani Blok IV STO Telkom Sawojaja', 'Kedungkandang', '0341720655', '-7.9795591', '112.6630636'),
(72, 'FaskesTingkat1', 'Klinik', 'Klinik Dinamika Sehat', 'Jl. Danau Maninjau Barat B1 A47\r\n', 'Kedungkandang', '03413021071', '-7.9747938', '112.6557391'),
(73, 'FaskesTingkat1', 'Klinik', 'Klinik Rawat Jalan Delta', 'Jl. Raya Kepuh No.47 Malang\r\n', 'Sukun', '081233396666', '-8.012545', '112.620252'),
(74, 'FaskesTingkat1', 'Klinik', 'Klinik Bentoel Medical Center', 'Jl. Niaga No. 25 Malang\r\n', 'Sukun', '0341298883 ', '-7.998205', '112.627385'),
(75, 'FaskesTingkat1', 'Klinik', 'Klinik Daqu Sehat', 'Jl. Bendungan Sigura-Gura Barat Raya 15 A\r\n', 'Sukun', '03412991199', '-7.957014', '112.605837'),
(76, 'FaskesTingkat1', 'KlinikTniPolri', 'Klinik Rawat Jalan Rampal (TNI AD)', 'Jl. Panglima Sudirman No D-9A Malang\r\n', 'Blimbing', '0341363165', '-7.9724717', '112.6385239'),
(77, 'FaskesTingkat1', 'KlinikTniPolri', 'Poliklinik POLRES Malang Kota', 'Jl. Pahlawan Trip No. 1\r\n', 'Klojen', '0341568974', '-7.9687009', '112.6226655'),
(78, 'FaskesTingkat1', 'KlinikTniPolri', 'Balai Kesehatan LANAL Malang (TNI-AL)', 'Jl. Yos Sudarso No.14\r\n', 'Klojen', '0341326881', '-7.9913836', '112.6252941'),
(79, 'FaskesTingkat1', 'KlinikTniPolri', 'Klinik Wira Husada (TNI-AD)', 'Jl. Sudanco Supriadi No.23\r\n', 'Kedungkandang', '0341363455', '-7.974165', '112.6641197'),
(80, 'FaskesTingkat1', 'Apotek', ' Instalasi Farmasi RSSA ', 'Jalan Jaksa Agung Suprapto No. 2', 'Klojen', '0341362101', '-7.9724887', '112.6315624'),
(81, 'FaskesTingkat1', 'Apotek', ' IFRS Dr. Soepraoen ', 'JL. S. Supriyadi, No. 22', 'Sukun', '0341325113', '-7.9899277', '112.6214186'),
(82, 'FaskesTingkat1', 'Apotek', ' IFRS Melati Husada  ', 'Jl. Kawi No.32', 'Klojen', '0341341357', '-7.9760335', '112.6213975'),
(83, 'FaskesTingkat1', 'Apotek', ' IFRSI Aisyiyah ', 'Jl. Sulawesi No.16', 'Klojen', '0341326773', '-7.9886734', '112.6252909'),
(84, 'FaskesTingkat1', 'Apotek', ' IFRS Lavalette ', 'Gg. III No.10, Rampal Celaket', 'Klojen', '0341470805', '-7.965773', '112.637877'),
(85, 'FaskesTingkat1', 'Apotek', ' IFRS Permata Bunda ', 'Jl. Soekarno Hatta No.75', 'Lowokwaru', '0341487487', '-7.938896', '112.6248248'),
(86, 'FaskesTingkat1', 'Apotek', ' IFRS Puri Bunda ', 'Jalan Simpang Sulfat Utara No. 60 A', 'Blimbing', '0341480047', '-7.9586933', '112.6554416'),
(87, 'FaskesTingkat1', 'Apotek', ' IFRS. Islam Unisma ', 'Jl. M.T. Haryono', 'Lowokwaru', '0341551356', '-7.9401441', '112.6090101'),
(88, 'FaskesTingkat1', 'Apotek', ' IFRS. Ganesha Medika ', 'Jl. Mayjend. Panjaitan', 'Klojen', '0341552955', '-7.9529006', '112.6191426'),
(89, 'FaskesTingkat1', 'Apotek', ' IFRS Panti Nirmala ', 'Jl. Kebalen Wetan 2-8, Kotalama', 'Kedungkandang', '0341362459', '-7.994361', '112.633917'),
(90, 'FaskesTingkat1', 'Apotek', ' IFRS Muhammadiyah Tongan ', 'Jl. Wahid Hasyim No. 30', 'Klojen', '0341326222', '-7.9845635', '112.628742'),
(91, 'FaskesTingkat1', 'Apotek', ' IFRS Rumkitban Kota Malang ', 'Jl. Panglima Sudirman', 'Blimbing', '-', '-7.9740891', '112.6386934'),
(92, 'FaskesTingkat1', 'Apotek', ' IFRSIA Galeri Candra ', 'Jl. Bunga Andong No.3', 'Lowokwaru', '0341478571', '-7.947945', '112.619574'),
(93, 'FaskesTingkat1', 'Apotek', 'IFRS PERSADA HOSPITAL ', 'Kompleks Araya Business Centre Kav. 2-4, Jalan Pan', 'Blimbing', '03412996333', '-7.9349396', '112.6499545'),
(94, 'FaskesTingkat1', 'Apotek', ' IFRS PANTI WALUYA SAWAHAN  ', 'Jl. Nusakambangan No.56', 'Klojen', '0341366033', '-7.9863695', '112.6250535');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `faskes`
--
ALTER TABLE `faskes`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `faskes`
--
ALTER TABLE `faskes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=131;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
