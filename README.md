<div align="center">
<h1> 🛒 Neo-Market </h1>
</div>

>  ⭐️ 경매와 중고거래가 모두 가능한 웹사이트 개발 및 ELK 시각화 프로젝트 ⭐️
<br/>

## 🧑‍💻 팀원 👩‍💻
| <img src="https://avatars.githubusercontent.com/u/139302518?v=4" width="150" height="150"/> | <img src="https://avatars.githubusercontent.com/u/83341978?v=4" width="150" height="150"/>|   <img src="https://avatars.githubusercontent.com/u/129728196?v=4" width="150" height="150"/>| <img src="https://avatars.githubusercontent.com/u/86272865?v=4" width="150" height="150"/> |
|:-------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------:|
|                       곽병찬 <br/>[@gato-46](https://github.com/gato-46)                       |                       박지원<br/>[@jiione](https://github.com/jiione)                    |                          최나영<br/>[@na-rong](https://github.com/na-rong)                      |                   최수연 (팀장) <br/>[@lotuxsoo](https://github.com/lotuxsoo)                   |                         |
<br>

## ⚙️ Infra
![image](https://github.com/user-attachments/assets/70906600-ee54-4240-bb39-4952bb6bbe4c)
<br>

## 🦴 ERD
![image](https://github.com/user-attachments/assets/882fb753-119e-4767-a460-4f610c7e6781)
<br>

## 📕 약어사전

|     도메인 |           물리명(DB) |   변수명(Java) |    데이터 타입 |           설명 |
| --- | --- | --- | --- | --- |
| User | user_id | userId | BIGINT | 유저 아이디 |
| User | nickname | nickname | varchar(20) | 유저 닉네임 |
| User | username | username | varchar(20) | 유저 이름 |
| User | email | email | varchar(225) | 유저 이메일 |
| User | profile | profile | varchar(225) | 유저 프로필 사진 |
| User | role | role | varchar(20) | 유저 역할 |
| User | address | address | varchar(225) | 유저 주소 |
| User | account_number | accountNum | varchar(225) | 유저 계좌번호 |
| User | bank_name | bankName | varchar(20) | 유저 은행 이름 |
| User | inactive_date | inactiveDate | datetime(6) | 유저 비활성 날짜 |
| User | point | point | long | 유저 네오페이 |
|          - |  |  |  |  |
| Auction | auction_id | aId | BIGINT | 경매  아이디 |
| Auction | auction_title | aTitle | varchar(20) | 경매 제목 |
| Auction | auction_content | aContent | text | 경매 본문 |
| Auction | auction_category | aCategory | varchar(20) | 경매 카테고리 |
| Auction | auction_status | aStatus | varchar(2) | 경매 거래 상태 |
| Auction | start_price | startPrice | BIGINT | 경매 시작 가격 |
| Auction | current_price | currentPrice | BIGINT | 경매 현재 가격 |
| Auction | auction_views | aViews | bigint | 경매 조회수 |
| Auction | deadline | deadline | varchar(20) | 경매 마감 기한 |
| Auction | auction_deleted | aDeleted | boolean | 경매 삭제 여부 |
| Auction | auction_created_at | createdAt | datetime(6) | 경매 생선 시간 |
| Auction | auction_updated_at | updatedAt | datetime(6) | 경매 수정 시간 |
|          - |  |  |  |  |
| Used | used_id | uId | BIGINT | 중고 게시글 아이디 |
| Used | used_title | uTitle | varchar(20) | 중고 제목 |
| Used | used_content | uContent | text | 중고 본문 |
| Used | used_category | uCategory | varchar(20) | 중고 카테고리 |
| Used | used_status | uStatus | varchar(2) | 중고 거래 상태 |
| Used | price | price | BIGINT | 중고 가격 |
| Used | used_views | uViews | BIGINT | 중고 조회수 |
| Used | used_deleted | uDeleted | boolean | 중고 삭제 여부 |
| Used | used_created_at | createdAt | datetime(6) | 중고 생성 시간 |
| Used | used_updated_at | updatedAt | datetime(6) | 중고 수정 시간 |
|          -  |  |  |  |  |
| Wish | wish_id | wishId | BIGINT | 위시리스트 아이디 |
|          - |  |  |  |  |
| Picture | picture_id | pictureId | BIGINT | 사진 아이디 |
| Picture | url | url | varchar(20) | 사진 url |
<br/>
