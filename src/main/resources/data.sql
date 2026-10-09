--categoriesテーブル
INSERT IGNORE INTO categories (id, name) VALUES (1, '和食');

--adminsテーブル（パスワードは平文"password"をBCryptでハッシュ化したもの）
INSERT IGNORE INTO admins (id, name, email, password) VALUES (1, '管理者', 'admin@example.com', '$2b$10$ai2xPxX53qTVR9ikfpm0K./2DUb9fyfc2EtjdjvSB/Bq/MlYjPfmO');


--shopsテーブル
INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (1, 1, 1, 'ひつまぶし 名古屋軒', '愛知県名古屋市中村区名駅1-1-1', 'shop01.jpg', '秘伝のタレで香ばしく焼き上げた絶品ひつまぶし専門店。','11:00', '21:30', 3000, 6000, 40);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity) 
VALUES (2, 1, 1, '味噌カツ 矢場亭', '愛知県名古屋市中区大須3-10-5', 'shop02.jpg', '濃厚な特製味噌ダレとサクサクのカツが相性抜群の人気店。', '11:30', '21:00', 1200, 2500, 35);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (3, 1, 1, '手羽先酒場 金シャチ屋', '愛知県名古屋市中区栄3-8-20', 'shop03.jpg', 'スパイスの効いた特製手羽先と地酒を楽しめる大衆居酒屋。', '17:00', '23:30', 2500, 4500, 50);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (4, 1, 1, '味噌煮込みうどん 尾張庵', '愛知県名古屋市中区錦3-15-12', 'shop04.jpg', 'コシのある自家製麺と八丁味噌スープが自慢の老舗うどん処。', '11:00', '20:30', 1000, 2000, 30);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (5, 1, 1, '台湾ラーメン 龍王閣', '愛知県名古屋市千種区今池1-4-8', 'shop05.jpg', '旨辛ミンチとニンニクが効いた刺激的な台湾ラーメンの名店。', '17:30', '23:59', 800, 1800, 25);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (6, 1, 1, 'あんかけスパゲティ パスタハウス栄', '愛知県名古屋市中区栄4-2-15', 'shop06.jpg', 'スパイシーなとろみソースと極太麺がクセになる専門店。', '11:00', '20:30', 900, 1600,28);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (7, 1, 1, 'きしめん本舗 なごや堂', '愛知県名古屋市中村区椿町6-9', 'shop07.jpg', '喉越しの良い平打ち麺と芳醇な出汁が香る伝統の一杯。', '10:30', '20:00', 700, 1500, 32);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (8, 1, 1, 'どて焼き処 なにわや名古屋店', '愛知県名古屋市中村区名駅南1-18-1', 'shop08.jpg', '赤味噌でじっくり煮込んだ牛すじ肉が絶品の大衆酒場。', '16:30', '23:00', 2000, 3800, 40);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (9, 1, 1, 'カレーうどん 鯱屋', '愛知県名古屋市東区東桜1-10-3', 'shop09.jpg', 'クリーミーでスパイシーな濃厚カレースープが絡む人気うどん店。', '11:30', '21:00', 900, 1800, 24);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (10, 1, 1, '名古屋コーチン料理 鳥よし', '愛知県名古屋市中区錦2-12-8', 'shop10.jpg', '弾力ある肉質と豊かな旨味の純系名古屋コーチン会席。', '17:00', '22:30', 5000, 10000, 36);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (11, 1, 1, '天むす処 千代田', '愛知県名古屋市中区千代田2-16-1', 'shop01.jpg', 'プリプリの小海老天と程よい塩気のひとくちおむすび。', '10:00', '19:00', 600, 1400, 16);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (12, 1, 1, '小倉トースト珈琲館 伏見茶房', '愛知県名古屋市中区錦1-20-11', 'shop02.jpg', 'バターの塩気と上品な粒あんが絶妙に調和する純喫茶。', '07:30', '18:00', 500, 1200, 26);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (13, 1, 1, 'ひつまぶし処 蓬莱亭', '愛知県名古屋市熱田区神宮2-5-1', 'shop03.jpg', '備長炭でじっくり焼き上げた極上の鰻を多彩な薬味とともに提供。', '11:00', '21:00', 3500, 7000, 48);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (14, 1, 1, '鉄板イタリアン 大須ダイナー', '愛知県名古屋市中区大須2-25-10', 'shop04.jpg', '熱々の鉄板に敷いた半熟卵とナポリタンが懐かしい洋食店。', '11:30', '21:30', 950, 1800, 22);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (15, 1, 1, '台湾まぜそば 麺屋名駅', '愛知県名古屋市中村区名駅4-22-8', 'shop05.jpg', '極太麺に魚粉と特製タレ、卵黄がよく絡むやみつきの一杯。', '11:00', '23:00', 900, 1500, 18);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (16, 1, 1, '味噌おでん処 錦や', '愛知県名古屋市中区錦3-20-4', 'shop06.jpg', '厳選素材に染み渡る八丁味噌のコクを味わうおでん処。', '17:30', '23:30', 2500, 4500, 28);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (17, 1, 1, '串カツ・土手煮の八右衛門', '愛知県名古屋市中区金山1-14-16', 'shop07.jpg', '揚げたての串カツを特製どて味噌に潜らせて味わう大衆立ち飲み処。', '16:00', '23:00', 1500, 3000, 30);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (18, 1, 1, 'あんかけスパ ボンジュール名駅', '愛知県名古屋市中村区名駅3-13-1', 'shop08.jpg', '粗挽きウインナーとたっぷり野菜が乗ったミラカンが看板メニュー。', '11:00', '21:00', 950, 1700, 32);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (19, 1, 1, '手羽先唐揚げ 尾張手羽先本舗', '愛知県名古屋市中区栄2-7-1', 'shop09.jpg', '外はカリッと中はジューシーに仕上げた甘辛手羽先唐揚げ専門店。', '16:30', '23:30', 2000, 4000, 45);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (20, 1, 1, 'きしめん・うどん 丸八庵', '愛知県名古屋市熱田区金山町1-3-2', 'shop10.jpg', '削りたてのかつお節がふわりと香る素朴で温かみのある麺処。', '11:00', '20:00', 750, 1600, 26);

INSERT IGNORE INTO shops (id, category_id, admin_id, name, address, image_name, description, opening_time, closing_time, lowest_price, highest_price, seating_capacity)
VALUES (21, 1, 1, '名古屋名物めし処 金鯱食堂', '愛知県名古屋市中区丸の内3-6-2', 'shop01.jpg', '味噌カツからひつまぶしまで名古屋めしを一度に楽しめる定食処。', '11:00', '21:30', 1100, 2800, 55);

--usersテーブル
INSERT IGNORE INTO users (id, name, furigana, email, password, enabled, subscriber)
VALUES (1, '侍太郎', 'サムライタロウ', 'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', true, true);