1. Tạo class ClubApi vào:
com.kantek.dancer.booking.data.remote.api
2. Với api request vá response như sau:
{{baseUrl}}/api/v1/clubs

  @Query("page") page: Int,
        @Query("limit") perPage: Int = AppConfig.PER_PAGE

{
    "success": true,
    "statusCode": 200,
    "message": "Success",
    "data": {
        "items": [
            {
                "id": "00000000-0000-4000-8001-000000000002",
                "name": "Rhythm & Motion NYC",
                "description": "Contemporary dance studio in Manhattan specialising in ballroom, street styles and Latin fusion.",
                "address": "350 West 42nd Street",
                "city": "New York",
                "district": "Manhattan",
                "latitude": "40.7580000",
                "longitude": "-74.0060000",
                "coverImage": "https://dancer.kendemo.com/uploads/club-covers/club-2.jpg",
                "gallery": null,
                "phone": "+1-212-555-0187",
                "email": "rhythmstudio@dancer.local",
                "openTime": "19:00",
                "closeTime": "01:00",
                "status": "active",
                "createdAt": "2026-04-18T12:43:50.430Z",
                "updatedAt": "2026-04-18T12:43:50.430Z",
                "deletedAt": null
            },
            {
                "id": "00000000-0000-4000-8001-000000000001",
                "name": "Salsa Nights Miami",
                "description": "Premier Latin dance club in the heart of Miami offering salsa, bachata and social dancing every night.",
                "address": "1200 Ocean Drive",
                "city": "Miami",
                "district": "Miami-Dade County",
                "latitude": "25.7825000",
                "longitude": "-80.1300000",
                "coverImage": "https://dancer.kendemo.com/uploads/club-covers/club-1.jpg",
                "gallery": null,
                "phone": "+1-305-555-0101",
                "email": "salsa@dancer.local",
                "openTime": "18:00",
                "closeTime": "02:00",
                "status": "active",
                "createdAt": "2026-04-18T12:43:50.430Z",
                "updatedAt": "2026-04-18T12:43:50.430Z",
                "deletedAt": null
            },
            {
                "id": "00000000-0000-4000-8001-000000000003",
                "name": "The Beat Factory LA",
                "description": "Upscale dance club in Los Angeles featuring bachata, kizomba and social dancing on the West Coast.",
                "address": "8600 Sunset Boulevard",
                "city": "Los Angeles",
                "district": "West Hollywood",
                "latitude": "34.0900000",
                "longitude": "-118.3817000",
                "coverImage": "https://dancer.kendemo.com/uploads/club-covers/club-3.jpg",
                "gallery": null,
                "phone": "+1-310-555-0234",
                "email": "beatfactory@dancer.local",
                "openTime": "20:00",
                "closeTime": "03:00",
                "status": "active",
                "createdAt": "2026-04-18T12:43:50.430Z",
                "updatedAt": "2026-04-18T12:43:50.430Z",
                "deletedAt": null
            }
        ],
        "meta": {
            "totalItems": 3,
            "itemCount": 3,
            "itemsPerPage": 10,
            "totalPages": 1,
            "currentPage": 1
        }
    },
    "timestamp": "2026-04-22T15:55:01.718Z"
}

3. Chỉnh sửa UI chỉ scroll list, dùng view và code theo kiến trúc như NotificationScreen

Đừng quên làm theo rule:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md