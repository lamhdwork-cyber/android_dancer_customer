<html class="dark"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Spline+Sans:wght@300;400;500;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script id="tailwind-config">
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    colors: {
                        "primary": "#f425f4",
                        "background-light": "#f8f5f8",
                        "background-dark": "#0f050f",
                        "card-dark": "rgba(255, 255, 255, 0.05)",
                    },
                    fontFamily: {
                        "display": ["Spline Sans"]
                    },
                    borderRadius: {
                        "DEFAULT": "0.75rem",
                        "lg": "1.5rem",
                        "xl": "2.25rem",
                        "full": "9999px"
                    },
                },
            },
        }
    </script>
<style type="text/tailwindcss">
        body {
            font-family: "Spline Sans", sans-serif;
            background: radial-gradient(circle at 50% 0%, #2a102a 0%, #0f050f 100%);
        }
        .glass-card {
            background: rgba(255, 255, 255, 0.03);
            backdrop-filter: blur(10px);
            -webkit-backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.08);
        }
        .neo-blur {
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
</style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-dark text-slate-100 min-h-screen flex flex-col">
<header class="sticky top-0 z-50 bg-background-dark/60 neo-blur border-b border-white/5">
<div class="flex items-center justify-between px-5 py-4">
<div class="flex items-center gap-3">
<span class="material-symbols-outlined text-primary">menu</span>
<h1 class="text-lg font-bold tracking-tight">Booking Queue</h1>
</div>
<div class="flex items-center gap-4">
<div class="relative">
<span class="material-symbols-outlined text-slate-400">notifications</span>
<span class="absolute -top-1 -right-1 flex h-2 w-2 rounded-full bg-primary"></span>
</div>
</div>
</div>
<nav class="flex px-2">
<a class="flex-1 flex flex-col items-center py-2 border-b-2 border-primary text-primary" href="#">
<span class="text-xs font-bold uppercase tracking-wider">Pending</span>
</a>
<a class="flex-1 flex flex-col items-center py-2 border-b-2 border-transparent text-slate-500" href="#">
<span class="text-xs font-bold uppercase tracking-wider">Accepted</span>
</a>
<a class="flex-1 flex flex-col items-center py-2 border-b-2 border-transparent text-slate-500" href="#">
<span class="text-xs font-bold uppercase tracking-wider">Completed</span>
</a>
</nav>
</header>
<main class="flex-1 overflow-y-auto px-4 py-4 space-y-3">
<div class="glass-card rounded-xl p-3.5 shadow-2xl relative overflow-hidden">
<div class="flex justify-between items-start mb-3">
<div>
<div class="flex items-center gap-2">
<span class="text-[10px] font-black text-primary/80 tracking-tighter">#1024</span>
<h3 class="text-sm font-bold text-white uppercase tracking-tight">John D.</h3>
</div>
<div class="flex items-center gap-3 mt-1.5">
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-slate-500">meeting_room</span>
<span class="text-[11px] text-slate-400">VIP Suite 2</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">groups</span>
<span class="text-[11px] text-slate-400">4</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">music_note</span>
<span class="text-[11px] text-slate-400">5</span>
</div>
</div>
</div>
<div class="text-right">
<div class="text-[11px] font-bold text-primary flex items-center justify-end gap-1">
<span class="w-1 h-1 rounded-full bg-primary animate-pulse"></span>
                        NOW
                    </div>
<div class="text-sm font-bold text-emerald-400 mt-0.5">$500.00</div>
</div>
</div>
<div class="flex items-center gap-2 mb-4 bg-white/5 p-2 rounded-lg">
<div class="flex -space-x-2">
<img alt="Mila" class="w-8 h-8 rounded-full border-2 border-[#1a0a1a] object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDRs7po0kPBZJenTXKMzM9QB_mMeBXK7rC9zmhMaqncwRexMHirSxe2uWHqf7pJ1pHqWX2D6MVcGv-xxhjOUqg3Ema2glA1o2kVIUDOrAG-yBkr6EkSmVZiaWyQ5PoHpx-fEZuuPNNuUxvNT6WZ7lKJ8RqRZmGORkjOQDy-9C0v_wmAmLdhHdWRtdkypChh0gpvjEZIGB3pY5694kYUu3EYy4K-G7WG5OUnJFFWBreaYzt1CcGN_ljOoIr7R78GLCz0B95UPNXLrZg"/>
<img alt="Coco" class="w-8 h-8 rounded-full border-2 border-[#1a0a1a] object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuD60xgZSlPFK-nxqrzXt1VZH-ncc7iUgw1muSZb6D2hEhnCD_2bqtNYX0iGIII1rQ076L_u_ag6rlnVhiwkt2rGrusuyFektNaNsz_nuzR5p0IataE7Pv6P2y3_YkjicIheyjAB38nT2CloqCCp4rl4cJkTxLa8N-qYfnz5gRuexgKh1PNPLVHOs0CZLG4nlSQNhvrF_9IYIQosRWDPuJbbOdr-GPQqhcc2QaYoXTeRMkBmyby7DyTzk8_01F2-meXBo4r4Lc_uQCM"/>
</div>
<div class="flex flex-col">
<span class="text-[10px] text-slate-500 font-bold uppercase tracking-widest">Selected Talent</span>
<span class="text-[11px] text-slate-200 font-medium">Mila, Coco</span>
</div>
</div>
<div class="flex gap-2">
<button class="flex-[2] h-10 rounded-lg bg-primary text-white font-bold text-xs tracking-widest shadow-[0_0_15px_rgba(244,37,244,0.3)] active:scale-95 transition-all">
                    ACCEPT
                </button>
<button class="flex-1 h-10 rounded-lg border border-white/10 text-slate-400 font-bold text-xs tracking-widest active:bg-white/5 transition-all">
                    REJECT
                </button>
</div>
</div>
<div class="glass-card rounded-xl p-3.5 shadow-2xl relative overflow-hidden">
<div class="flex justify-between items-start mb-3">
<div>
<div class="flex items-center gap-2">
<span class="text-[10px] font-black text-primary/80 tracking-tighter">#1025</span>
<h3 class="text-sm font-bold text-white uppercase tracking-tight">Sarah K.</h3>
</div>
<div class="flex items-center gap-3 mt-1.5">
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-slate-500">meeting_room</span>
<span class="text-[11px] text-slate-400">Table 14</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">groups</span>
<span class="text-[11px] text-slate-400">2</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">music_note</span>
<span class="text-[11px] text-slate-400">3</span>
</div>
</div>
</div>
<div class="text-right">
<div class="text-[11px] font-bold text-slate-500 flex items-center justify-end">11:30 PM</div>
<div class="text-sm font-bold text-emerald-400 mt-0.5">$350.00</div>
</div>
</div>
<div class="flex items-center gap-2 mb-4 bg-white/5 p-2 rounded-lg">
<div class="flex -space-x-2">
<img alt="Jade" class="w-8 h-8 rounded-full border-2 border-[#1a0a1a] object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDu7DOxhtQndfCoVEs-nN3Jil9ELJkd68ziLVXw7XlXkfnJX2qbkAnOMkfHrYFpJFpj0eFtNQ-eKeax2wU-R70sU4sx0sKX2myYhI3dVpAeuWCKo4f4S-0BGgs5FRjJeU1SoqHmbS2STqwFj-5_llIl89KBOE2nEcEJZeHXlTNw6-Pw7MAndOPEshIiXuXBT46oBJGnLXXJ6a4vAID8BIlZaRJPgZZcdfYFl-FBliJOJy5zXCBUJbMpFknyy5IcJ2UTZNTa8F2UOXc"/>
</div>
<div class="flex flex-col">
<span class="text-[10px] text-slate-500 font-bold uppercase tracking-widest">Selected Talent</span>
<span class="text-[11px] text-slate-200 font-medium">Jade</span>
</div>
</div>
<div class="flex gap-2">
<button class="flex-[2] h-10 rounded-lg bg-primary text-white font-bold text-xs tracking-widest shadow-[0_0_15px_rgba(244,37,244,0.3)] active:scale-95 transition-all">
                    ACCEPT
                </button>
<button class="flex-1 h-10 rounded-lg border border-white/10 text-slate-400 font-bold text-xs tracking-widest active:bg-white/5 transition-all">
                    REJECT
                </button>
</div>
</div>
<div class="glass-card rounded-xl p-3.5 shadow-2xl relative overflow-hidden opacity-80">
<div class="flex justify-between items-start mb-3">
<div>
<div class="flex items-center gap-2">
<span class="text-[10px] font-black text-primary/80 tracking-tighter">#1026</span>
<h3 class="text-sm font-bold text-white uppercase tracking-tight">Mike R.</h3>
</div>
<div class="flex items-center gap-3 mt-1.5">
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-slate-500">meeting_room</span>
<span class="text-[11px] text-slate-400">VIP Suite 1</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">groups</span>
<span class="text-[11px] text-slate-400">8</span>
</div>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-[14px] text-primary/70">music_note</span>
<span class="text-[11px] text-slate-400">12</span>
</div>
</div>
</div>
<div class="text-right">
<div class="text-[11px] font-bold text-slate-500 flex items-center justify-end">12:00 AM</div>
<div class="text-sm font-bold text-emerald-400 mt-0.5">$1,200.00</div>
</div>
</div>
<div class="flex items-center gap-2 mb-4 bg-white/5 p-2 rounded-lg">
<div class="flex -space-x-2">
<img alt="Bella" class="w-8 h-8 rounded-full border-2 border-[#1a0a1a] object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuARkL3B9iIrIhvTzmUk2PrS1nXuWuO99-BrK1-eLtSjX-Wzo6rYiHz8xJIuNqRxAgRvWX7Hah2RHMnqnIxKpaPecFmjmsXDqxdqHUxUXNgp_PWEu0BEO5HmonbYyfyazwhqIGWehhzgjhx4411kH8_DxkiEz8qqupMJCIa0OcT18Jqy4_O0xNgE6hnMSh5Sr3R0soBdKLGWwPN-ebBfQI2guaLZv3PU3vmtR96IiHyTUrVWtchc--SN3ApjH6w_SfXCd0y5y5bibBE"/>
<img alt="Skye" class="w-8 h-8 rounded-full border-2 border-[#1a0a1a] object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDZvhG3b1KrTyHoakim1vF1nUMHjeXpLLuE6GEH3CdllK_rLRB__n9yI4FeFiTYEeGzg_lygYc220-i6WlrPdG0dWis1HBzLXtN95oHJwzCXzdzDaYycdPGkargWzOh5KHmkL_8CXhsIQ13ta_QvuFCbGNho4KmtjdCRKqORYC1wX7RgqB73PpCDeQeIcJyriQ8La5thDeXqt2LlakQv6uK9rBTcy8tCDqgeEhiU-4KsVVCVApwcYKcfQSS9UD3VNKHvVojwEsnKoc"/>
</div>
<div class="flex flex-col">
<span class="text-[10px] text-slate-500 font-bold uppercase tracking-widest">Selected Talent</span>
<span class="text-[11px] text-slate-200 font-medium">Bella, Skye</span>
</div>
</div>
<div class="flex gap-2">
<button class="flex-[2] h-10 rounded-lg bg-primary text-white font-bold text-xs tracking-widest shadow-[0_0_15px_rgba(244,37,244,0.3)] active:scale-95 transition-all">
                    ACCEPT
                </button>
<button class="flex-1 h-10 rounded-lg border border-white/10 text-slate-400 font-bold text-xs tracking-widest active:bg-white/5 transition-all">
                    REJECT
                </button>
</div>
</div>
</main>
<footer class="bg-background-dark/80 neo-blur border-t border-white/5 px-4 pb-8 pt-3">
<div class="flex justify-between items-center max-w-md mx-auto">
<a class="flex flex-col items-center gap-1 text-primary" href="#">
<span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1">list_alt</span>
<span class="text-[10px] font-bold uppercase tracking-widest text-center leading-none">Queue</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-500 hover:text-primary transition-colors" href="#">
<span class="material-symbols-outlined">grid_view</span>
<span class="text-[10px] font-bold uppercase tracking-widest text-center leading-none">Tables</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-500 hover:text-primary transition-colors" href="#">
<span class="material-symbols-outlined">group</span>
<span class="text-[10px] font-bold uppercase tracking-widest text-center leading-none">Staff</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-500 hover:text-primary transition-colors" href="#">
<span class="material-symbols-outlined">settings</span>
<span class="text-[10px] font-bold uppercase tracking-widest text-center leading-none">Settings</span>
</a>
</div>
</footer>

</body></html>


1. Luôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Sửa BookingItemView lại giống với UI html, thay nút "Accept" thành "Cancel", xoá nút "Reject"

3. Sửa lại data cho BookingDTO map với json bên dưới, bỏ cũ đi, sau đó sửa lại data map với UI mới,
lưu ý những model DTO nào có sẵn thì lấy xài, không tạo mới nữa
 {
            "id": "1ad2d30c-4575-402e-a5f9-2cec3cd0e9e1",
            "userId": "d714aa1a-a94d-42d1-a068-384b4d2ede6d",
            "dancerId": "00000000-0000-4000-8002-000000000005",
            "clubId": "00000000-0000-4000-8001-000000000002",
            "roomId": "00000000-0000-4000-8003-000000000008",
            "bookingDate": "2026-05-06",
            "startTime": "19:00:00",
            "endTime": "19:20:00",
            "pricePerHour": "150.00",
            "bookingType": "scheduled",
            "status": "pending",
            "totalAmount": "50.00",
            "numberOfSongs": 4,
            "numberOfGuests": 2,
            "notes": null,
            "cancelReason": null,
            "cancelledAt": null,
            "createdAt": "2026-05-05T17:05:44.825Z",
            "updatedAt": "2026-05-05T17:05:44.825Z",
            "deletedAt": null,
            "user": {
                "id": "d714aa1a-a94d-42d1-a068-384b4d2ede6d",
                "email": "luffy9@ladify.io",
                "firstName": "Luffy 9",
                "lastName": "Monkey D.",
                "avatar": "https://dancer.kendemo.com/uploads/avatars/1778042756502-476552459.jpg",
                "role": "user",
                "status": "active",
                "phone": null,
                "fcmToken": "f2ydMbYlRo6tgNBiJ5M2wi:APA91bGvKt6uBgo6aj_uK6NeB4TXnyoZxi8iYSOQslcV3mRR2YkCZWNChc1Okbu-gG0ny4rIZ2pQMgtKTz3mGZFaKWZDhcHhHY61q30EEPOHpDbJ_tRnSYQ",
                "clubId": null,
                "createdAt": "2026-04-29T07:33:30.328Z",
                "updatedAt": "2026-05-06T06:56:35.639Z",
                "deletedAt": null
            },
            "dancer": {
                "id": "00000000-0000-4000-8002-000000000005",
                "name": "Priya Nair",
                "dateOfBirth": "1996-09-14",
                "avatar": "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-5-1.jpg",
                "clubId": "00000000-0000-4000-8001-000000000002",
                "danceStyles": [
                    "Tango",
                    "Salsa",
                    "Cha-cha"
                ],
                "bio": "Contemporary and Latin fusion artist. Brings a unique style blending classical and street.",
                "experience": 7,
                "hourlyRate": "150.00",
                "rating": "4.80",
                "totalReviews": 37,
                "tos": 0,
                "status": "ready",
                "isAvailableNow": true,
                "gallery": [
                    "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-5-1.jpg",
                    "https://dancer.kendemo.com/uploads/dancer-gallery/dancer-5-2.jpg"
                ],
                "createdAt": "2026-04-29T07:31:03.411Z",
                "updatedAt": "2026-04-29T07:31:03.411Z",
                "deletedAt": null,
                "age": 29
            },
            "room": {
                "id": "00000000-0000-4000-8003-000000000008",
                "name": "Executive Suite",
                "description": "Top-tier executive suite with full amenities and premium beverages",
                "type": "executive_suite",
                "image": "https://dancer.kendemo.com/uploads/room-images/executive-suite.jpg",
                "capacity": 28,
                "availabilityStatus": "available",
                "hourlyRate": "2800.00",
                "services": [
                    "Premium luxury",
                    "full amenities"
                ],
                "clubId": "00000000-0000-4000-8001-000000000002",
                "createdAt": "2026-04-29T07:31:03.337Z",
                "updatedAt": "2026-04-29T07:31:03.337Z",
                "deletedAt": null,
                "club": {
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
                    "createdAt": "2026-04-29T07:31:03.289Z",
                    "updatedAt": "2026-04-29T07:31:03.289Z",
                    "deletedAt": null
                }
            },
            "review": null
        }

        Lưu ý: Chỉ là những phần yêu cầu, còn lại không tự ý chỉnh sửa gì thêm!