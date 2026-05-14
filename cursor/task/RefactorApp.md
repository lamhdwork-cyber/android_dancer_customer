1. Ở AppLazyColumn:
- Cần thêm emptyHtmlRes để set data cho NoDataView trong class này luôn. Và khi nodata hiển thị thì vẫn pull refresh được, AppLazyColumn nó sẽ nhận trạng thái isEmpty từ viewmodel để xử lý show/hide nodata thay vì check riêng ở component
- Chỉnh sửa các class liên quan có sử dụng AppLazyColumn
- Chú ý đảm bảo không làm thay đổi logic đang chạy