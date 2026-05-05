1. Luôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Truyền đầy đủ thông tin từ BookingScreen sang, bao gồm:
- Club: hiển thị cho UI
- Date và time cho book later, nếu now thì ẩn đi
- Room: hiển thị cho UI và id để request api
- Dancer đã chọn, bao gồm cả id để request api
- Number of songs và number of guests
- Tổng giá

3. Sau khi nhấn nút "Confirm booking" thì call api như sau, tạo api trong class BookingApi:
- Nếu book now:
{{baseUrl}}/api/v1/bookings/book-now

Request:
{
  "dancerIds": [
    "00000000-0000-4000-8002-000000000001"
  ],
  "roomId": "00000000-0000-4000-8001-000000000001",
  "numberOfSongs": 4,
  "numberOfGuests": 2
}

Response:
{
    "success": true,
    "statusCode": 201,
    "message": "Success",
    "data": [
        {
            "userId": "00000000-0000-4000-8000-000000000005",
            "dancerId": "00000000-0000-4000-8002-000000000001",
            "clubId": "00000000-0000-4000-8001-000000000002",
            "roomId": "00000000-0000-4000-8003-000000000007",
            "bookingDate": "2026-05-05T00:00:00.000Z",
            "startTime": "01:06",
            "endTime": null,
            "pricePerHour": "120.00",
            "bookingType": "immediate",
            "status": "confirmed",
            "totalAmount": "0.00",
            "numberOfSongs": 4,
            "numberOfGuests": 2,
            "notes": "Birthday party",
            "cancelReason": null,
            "cancelledAt": null,
            "id": "2f918683-a898-43af-bdbf-ca25594affee",
            "createdAt": "2026-05-05T06:06:51.835Z",
            "updatedAt": "2026-05-05T06:06:51.835Z",
            "deletedAt": null
        }
    ],
    "timestamp": "2026-05-05T06:06:51.850Z"
}

- Nếu book later:
{{baseUrl}}/api/v1/bookings/reserve

Request:
{
  "dancerIds": [
    "00000000-0000-4000-8002-000000000001"
  ],
  "roomId": "00000000-0000-4000-8001-000000000001",
  "bookingDate": "2026-03-15",
  "endTime": "22:00",
  "numberOfSongs": 4,
  "numberOfGuests": 2,
  "notes": "Anniversary dinner"
}

Response:
{
    "success": true,
    "statusCode": 201,
    "message": "Success",
    "data": [
        {
            "userId": "00000000-0000-4000-8000-000000000005",
            "dancerId": "00000000-0000-4000-8002-000000000001",
            "clubId": "00000000-0000-4000-8001-000000000002",
            "roomId": "00000000-0000-4000-8003-000000000007",
            "bookingDate": "2026-05-05T00:00:00.000Z",
            "startTime": "01:06",
            "endTime": null,
            "pricePerHour": "120.00",
            "bookingType": "immediate",
            "status": "confirmed",
            "totalAmount": "0.00",
            "numberOfSongs": 4,
            "numberOfGuests": 2,
            "notes": "Birthday party",
            "cancelReason": null,
            "cancelledAt": null,
            "id": "2f918683-a898-43af-bdbf-ca25594affee",
            "createdAt": "2026-05-05T06:06:51.835Z",
            "updatedAt": "2026-05-05T06:06:51.835Z",
            "deletedAt": null
        }
    ],
    "timestamp": "2026-05-05T06:06:51.850Z"
}

- Sửa BookingForm lại cho đúng với UI mới, bỏ những field cũ không cần thiết
- Sửa BookingDTO lại để máp với data mới, bỏ những field cũ không cần thiết
- Triển khai và tạo các class đúng theo kiến trúc hiện tại của dự án