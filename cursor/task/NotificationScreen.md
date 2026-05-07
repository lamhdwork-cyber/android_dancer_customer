<!DOCTYPE html>

<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Manager Notifications</title>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
<link href="https://fonts.googleapis.com/css2?family=Spline+Sans:wght@300;400;500;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script id="tailwind-config">
        tailwind.config = {
            darkMode: "class",
            theme: {
                extend: {
                    colors: {
                        "primary": "#f425f4",
                        "background-light": "#f8f5f8",
                        "background-dark": "#221022",
                    },
                    fontFamily: {
                        "display": ["Spline Sans", "sans-serif"]
                    },
                    borderRadius: {"DEFAULT": "1rem", "lg": "2rem", "xl": "3rem", "full": "9999px"},
                },
            },
        }
    </script>
<style>
        body {
            -webkit-tap-highlight-color: transparent;
        }
        .neon-glow {
            box-shadow: 0 0 15px rgba(244, 37, 244, 0.4);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100 antialiased">
<div class="relative flex h-screen max-w-md mx-auto flex-col bg-background-light dark:bg-background-dark overflow-hidden border-x border-slate-200 dark:border-primary/20">
<!-- Header -->
<header class="flex items-center bg-background-light dark:bg-background-dark p-4 pb-2 justify-between sticky top-0 z-10">
<div class="text-slate-900 dark:text-slate-100 flex size-12 shrink-0 items-center justify-start">
<span class="material-symbols-outlined text-2xl cursor-pointer">arrow_back_ios</span>
</div>
<h2 class="text-slate-900 dark:text-slate-100 text-lg font-bold leading-tight tracking-tight flex-1 text-center">Notifications</h2>
<div class="flex w-12 items-center justify-end">
<button class="flex items-center justify-center rounded-full h-12 w-12 bg-transparent text-primary hover:bg-primary/10 transition-colors">
<span class="material-symbols-outlined text-2xl">done_all</span>
</button>
</div>
</header>
<!-- Tabs -->
<div class="px-4 mb-2">
<div class="flex border-b border-slate-200 dark:border-primary/20 gap-8">
<a class="flex flex-col items-center justify-center border-b-2 border-primary text-slate-900 dark:text-slate-100 pb-3 pt-4 px-2" href="#">
<p class="text-sm font-bold tracking-wide">All</p>
</a>
<a class="flex flex-col items-center justify-center border-b-2 border-transparent text-slate-500 dark:text-slate-400 pb-3 pt-4 px-2" href="#">
<p class="text-sm font-bold tracking-wide">Unread</p>
</a>
</div>
</div>
<!-- Notification List -->
<div class="flex-1 overflow-y-auto px-4 py-2 space-y-3">
<!-- Notification Item 1 -->
<div class="flex items-center gap-4 bg-white dark:bg-primary/5 p-4 rounded-xl border border-slate-100 dark:border-primary/10 hover:border-primary/30 transition-all cursor-pointer group active:scale-[0.98]">
<div class="flex items-center justify-center rounded-lg bg-primary/20 text-primary shrink-0 size-12 neon-glow">
<span class="material-symbols-outlined text-2xl">confirmation_number</span>
</div>
<div class="flex flex-col justify-center flex-1">
<div class="flex items-center justify-between mb-0.5">
<p class="text-slate-900 dark:text-slate-100 text-base font-semibold leading-tight">New Booking Request #1025</p>
<span class="text-primary text-[10px] font-bold uppercase tracking-wider bg-primary/10 px-1.5 py-0.5 rounded">New</span>
</div>
<p class="text-slate-500 dark:text-slate-400 text-sm font-normal line-clamp-1">VIP Table for 6 - Pending approval</p>
<p class="text-slate-400 dark:text-slate-500 text-[11px] mt-1 font-medium">Just now</p>
</div>
<span class="material-symbols-outlined text-slate-300 dark:text-primary/30 group-hover:text-primary transition-colors">chevron_right</span>
</div>
<!-- Notification Item 2 -->
<div class="flex items-center gap-4 bg-white dark:bg-primary/5 p-4 rounded-xl border border-slate-100 dark:border-primary/10 hover:border-primary/30 transition-all cursor-pointer group active:scale-[0.98]">
<div class="flex items-center justify-center rounded-lg bg-primary/20 text-primary shrink-0 size-12 neon-glow">
<span class="material-symbols-outlined text-2xl">schedule</span>
</div>
<div class="flex flex-col justify-center flex-1">
<div class="flex items-center justify-between mb-0.5">
<p class="text-slate-900 dark:text-slate-100 text-base font-semibold leading-tight">Upcoming Late Booking</p>
</div>
<p class="text-slate-500 dark:text-slate-400 text-sm font-normal line-clamp-1">Booking #1012 arriving in 15 mins</p>
<p class="text-slate-400 dark:text-slate-500 text-[11px] mt-1 font-medium">15m ago</p>
</div>
<span class="material-symbols-outlined text-slate-300 dark:text-primary/30 group-hover:text-primary transition-colors">chevron_right</span>
</div>
<!-- Earlier Notifications Label -->
<div class="pt-4 pb-2">
<p class="text-slate-400 dark:text-slate-500 text-xs font-bold uppercase tracking-widest px-1">Earlier today</p>
</div>
<!-- Notification Item 3 -->
<div class="flex items-center gap-4 bg-white dark:bg-primary/5 p-4 rounded-xl border border-slate-100 dark:border-primary/10 opacity-70">
<div class="flex items-center justify-center rounded-lg bg-slate-200 dark:bg-primary/10 text-slate-500 dark:text-slate-400 shrink-0 size-12">
<span class="material-symbols-outlined text-2xl">person_add</span>
</div>
<div class="flex flex-col justify-center flex-1">
<p class="text-slate-900 dark:text-slate-100 text-base font-semibold leading-tight">Guest List Update</p>
<p class="text-slate-500 dark:text-slate-400 text-sm font-normal line-clamp-1">5 new additions to Friday Night VIP</p>
<p class="text-slate-400 dark:text-slate-500 text-[11px] mt-1 font-medium">2h ago</p>
</div>
<span class="material-symbols-outlined text-slate-300 dark:text-primary/30">chevron_right</span>
</div>
<!-- Notification Item 4 -->
<div class="flex items-center gap-4 bg-white dark:bg-primary/5 p-4 rounded-xl border border-slate-100 dark:border-primary/10 opacity-70">
<div class="flex items-center justify-center rounded-lg bg-slate-200 dark:bg-primary/10 text-slate-500 dark:text-slate-400 shrink-0 size-12">
<span class="material-symbols-outlined text-2xl">payments</span>
</div>
<div class="flex flex-col justify-center flex-1">
<p class="text-slate-900 dark:text-slate-100 text-base font-semibold leading-tight">Payout Processed</p>
<p class="text-slate-500 dark:text-slate-400 text-sm font-normal line-clamp-1">Transfer of $4,250.00 initiated</p>
<p class="text-slate-400 dark:text-slate-500 text-[11px] mt-1 font-medium">5h ago</p>
</div>
<span class="material-symbols-outlined text-slate-300 dark:text-primary/30">chevron_right</span>
</div>
</div>
<!-- Bottom Navigation Bar -->
<nav class="flex border-t border-slate-200 dark:border-primary/20 bg-white/80 dark:bg-background-dark/80 backdrop-blur-md px-4 pb-8 pt-2">
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500 transition-colors" href="#">
<span class="material-symbols-outlined text-2xl">home</span>
<p class="text-[10px] font-medium leading-normal tracking-wide">Home</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500 transition-colors" href="#">
<span class="material-symbols-outlined text-2xl">calendar_month</span>
<p class="text-[10px] font-medium leading-normal tracking-wide">Bookings</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-primary transition-colors" href="#">
<span class="material-symbols-outlined text-2xl fill-[1]">notifications</span>
<p class="text-[10px] font-bold leading-normal tracking-wide">Alerts</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500 transition-colors" href="#">
<span class="material-symbols-outlined text-2xl">settings</span>
<p class="text-[10px] font-medium leading-normal tracking-wide">Settings</p>
</a>
</nav>
</div>
</body></html>

1. Luôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Dựa vào html để sửa item của list AppLazyColumn của NotificationScreen
Chỉ sửa UI của item, không có sửa các UI khác, cái icon của item lấy icon notification mặc định
Có thể tách và tạo NotificationItemView riêng

3. Sửa lại NotificationDTO theo response mới để map theo với UI mới

 {
                "id": "9754d8b4-68eb-40d0-8323-1f668eec7a14",
                "userId": "424c4f2a-140b-4a1e-8862-73478c7fb97c",
                "type": "booking_pending",
                "title": "Reservation Received",
                "message": "Your reservation for 2026-05-07 at 19:30 is pending confirmation.",
                "isRead": false,
                "data": {
                    "bookingId": "1df589d7-e44d-4339-9f13-a8e40ae1a23c"
                },
                "createdAt": "2026-05-06T09:36:28.074Z",
                "updatedAt": "2026-05-06T09:36:28.074Z"
            }

Lưu ý: Tuân thủ theo kiến trúc đang có, mọi logic mapping xử lý trong NotificationFactory và sử dụng các hàm có sẵn/tạo mới trong TextFormatter nếu cần