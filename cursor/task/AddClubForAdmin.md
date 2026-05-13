Bổ sung thông tin Club, bao gồm tên và address cho màn hình DancerListOfAdminScreen
- UI tên và address được hiển thị như phần DetailBookingClubBlock trong DetailBookingScreen,
có thể move DetailBookingClubBlock sang Widgets để dùng chung
- Dùng api detail trong ClubApi để lấy thông tin hiển thị, dùng clubId lưu trong UserDTO để request
Lưu ý: Mọi logic, xử lý đều trong DancerListOfAdminVM, DancerListOfAdminScreen chỉ nhận data để hiển thị

- Viết thêm 1 actionbar khác thay thế ActionBarMainView, để hiển thị name club + address. Lưu ý đảm bảo hiển thị full nên k set cứng height
- Bỏ view DetailBookingClubBlock 