Trên device màn hình nhỏ có bugs như sau:
1. Màn hình DetailDancerScreen nếu tên dancer dài thì rating bị vỡ, vì vậy ưu tiên show rating, tên có thể max 2 hàng và nhiều thì ... cuối
2. Màn hình DetailBookingScreen, 3 view data, time room nếu tên room dài thì item room bị dài hơn so với 2 item còn lại. Vì vậy, cần tăng chiều dài 2 item còn lại cho = item room
3. Màn hình DetailBookingScreen, item dancer đã select, nên hiển thị chiều width theo tên dancer, có thể item ngắn, item dài nhưng đảm bảo hiển thị đủ tên
4. Màn hình DetailBookingScreen, kiểm tra tại sao một số item mất đi number song, có phải do màn hình nhỏ làm mất đi?

Tôi cần chia như sau:
- Tên và datetime thành 1 hàng 2 cột, ưu tiên datetime hiển thị full, nếu tên dài thì có thể 2 dòng
- Tiếp theo: Room + guest + song và price 1 hàng 2 cột, ưu tiên price, nếu room + guest + song k đủ thì cho guest + song xuống dòng thứ 2