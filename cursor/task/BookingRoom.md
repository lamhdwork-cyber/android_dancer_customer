
1. Luôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Sửa phần RoomSection trong BookingScreen được lấy data từ api dưới:

/api/v1/rooms/by-club/
 @Query("clubId") clubId: String,
        @Query("limit") perPage: Int = -1

response: {
    "success": true,
    "statusCode": 200,
    "message": "Success",
    "data": {
        "items": [
            {
                "id": "00000000-0000-4000-8003-000000000003",
                "name": "Deluxe Lounge",
                "description": "Spacious lounge with comfortable seating and excellent DJ booth access",
                "type": "deluxe_lounge",
                "image": "https://dancer.kendemo.com/uploads/room-images/deluxe-lounge.jpg",
                "capacity": 30,
                "availabilityStatus": "available",
                "hourlyRate": "1200.00",
                "services": [
                    "Spacious",
                    "DJ booth included"
                ],
                "clubId": "00000000-0000-4000-8001-000000000001",
                "createdAt": "2026-04-29T07:31:03.327Z",
                "updatedAt": "2026-04-29T07:31:03.327Z",
                "deletedAt": null
            },
            {
                "id": "00000000-0000-4000-8003-000000000004",
                "name": "Executive Suite",
                "description": "Top-tier executive suite with full amenities and premium beverages",
                "type": "executive_suite",
                "image": "https://dancer.kendemo.com/uploads/room-images/executive-suite.jpg",
                "capacity": 25,
                "availabilityStatus": "available",
                "hourlyRate": "2500.00",
                "services": [
                    "Premium luxury",
                    "full amenities"
                ],
                "clubId": "00000000-0000-4000-8001-000000000001",
                "createdAt": "2026-04-29T07:31:03.327Z",
                "updatedAt": "2026-04-29T07:31:03.327Z",
                "deletedAt": null
            },
            {
                "id": "00000000-0000-4000-8003-000000000002",
                "name": "Private Suite",
                "description": "Intimate private suite perfect for group celebrations and private parties",
                "type": "private_suite",
                "image": "https://dancer.kendemo.com/uploads/room-images/private-suite.jpg",
                "capacity": 15,
                "availabilityStatus": "available",
                "hourlyRate": "1500.00",
                "services": [
                    "Full isolation",
                    "bar access"
                ],
                "clubId": "00000000-0000-4000-8001-000000000001",
                "createdAt": "2026-04-29T07:31:03.327Z",
                "updatedAt": "2026-04-29T07:31:03.327Z",
                "deletedAt": null
            },
            {
                "id": "00000000-0000-4000-8003-000000000001",
                "name": "VIP Room",
                "description": "Exclusive VIP lounge with premium sound system and prime dance floor views",
                "type": "vip_room",
                "image": "https://dancer.kendemo.com/uploads/room-images/vip-room.jpg",
                "capacity": 20,
                "availabilityStatus": "available",
                "hourlyRate": "2000.00",
                "services": [
                    "Private area",
                    "premium service"
                ],
                "clubId": "00000000-0000-4000-8001-000000000001",
                "createdAt": "2026-04-29T07:31:03.327Z",
                "updatedAt": "2026-04-29T07:31:03.327Z",
                "deletedAt": null
            }
        ],
        "meta": {
            "totalItems": 4,
            "itemCount": 4,
            "itemsPerPage": 10,
            "totalPages": 1,
            "currentPage": 1
        }
    },
    "timestamp": "2026-04-29T08:09:26.696Z"
}

Lưu ý: api này đang lấy all nên k cần paging hay loadmore
