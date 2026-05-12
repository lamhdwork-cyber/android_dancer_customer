<!DOCTYPE html>
<html class="dark" lang="en"><head>
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
                        "background-dark": "#1a0b1a",
                        "status-ready": "#22c55e",
                        "status-floor": "#f97316",
                    },
                    fontFamily: {
                        "display": ["Spline Sans"]
                    },
                    borderRadius: {"DEFAULT": "1rem", "lg": "2rem", "xl": "3rem", "full": "9999px"},
                },
            },
        }
    </script>
<title>Manager Status Board</title>
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
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100 antialiased">
<div class="relative flex min-h-screen w-full flex-col overflow-x-hidden max-w-[430px] mx-auto border-x border-primary/10 shadow-2xl bg-background-light dark:bg-background-dark">
<header class="sticky top-0 z-10 flex items-center justify-between bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-md px-6 py-4 border-b border-primary/5">
<div class="flex items-center gap-3">
<span class="material-symbols-outlined text-primary text-2xl">menu</span>
<h1 class="text-xl font-bold tracking-tight">Status Board</h1>
</div>
<div class="flex items-center gap-4">
<button class="flex items-center justify-center p-2 rounded-full hover:bg-primary/10 transition-colors">
<span class="material-symbols-outlined text-slate-600 dark:text-slate-300">search</span>
</button>
<div class="h-8 w-8 rounded-full bg-primary/20 flex items-center justify-center border border-primary/30 overflow-hidden">
<img alt="Manager profile photo avatar" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDZDA21t-2ezJpWgfT4IqvcCnMQybC6WL7UbLegEHlKKa0i4jj3ZsukNFqlp-kKkTID8Fji5lTvHq0M8MlXWV-daaz0RO9bLHBCRoqLyexUc_PBuK4pXA5bSnfabkau9eSP36rQunVeqXxjvLVDax8htcsjliqNtHVXKCEIjc7-Sz6AZBzrKQGOLPQl_aeOnI7JE4JRnciDGUONeXLLuMsOjuhYr19rmmH3tGI05Ft25fl5WBFiWPr9Gry1pqEyzv9bvExFSMZCWFE"/>
</div>
</div>
</header>
<div class="px-6 py-4 grid grid-cols-2 gap-4">
<div class="bg-status-ready/10 border border-status-ready/20 rounded-2xl p-4 flex flex-col items-center justify-center">
<span class="text-status-ready text-xs font-bold uppercase tracking-widest mb-1">Ready</span>
<span class="text-3xl font-bold text-status-ready">16</span>
</div>
<div class="bg-status-floor/10 border border-status-floor/20 rounded-2xl p-4 flex flex-col items-center justify-center">
<span class="text-status-floor text-xs font-bold uppercase tracking-widest mb-1">Floor</span>
<span class="text-3xl font-bold text-status-floor">08</span>
</div>
</div>
<main class="flex-1 px-4 py-2">
<div class="flex items-center justify-between px-2 mb-4">
<h2 class="text-lg font-semibold">Active Roster</h2>
<span class="text-sm text-primary/70 font-medium">24 Dancers</span>
</div>
<div class="space-y-3 pb-24">
<div class="flex items-center justify-between bg-white/5 dark:bg-primary/5 border border-primary/10 rounded-2xl p-4">
<div class="flex items-center gap-4">
<div class="relative">
<div class="w-14 h-14 rounded-full overflow-hidden border-2 border-status-ready">
<img alt="Dancer profile portrait for Elena" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDD1_WigeB993Trvzg46AbHInRW-Xlf-vs4JFNgfsVmwiOPPcJqMjrG2LoaTmp8COmgkHTuYsroC7ww03XualIA9Q1NERaWncXFlX_WiYQvlgMsZ5k-AfuGV6rc-V2bYWQGb9VKyWDKJKFP0WjjnSRyzVBuw8em9PYmONYU8Kcl1f4amubhLVb3mAXkKLpkKIH0V1ZfmPcmOPrWw1oUDLnIgJuohIb8GuklMMZ4MPtCQxwGwoVh-8Tw9soZUIyNpzXPuddHBql2pE8"/>
</div>
<div class="absolute bottom-0.5 right-0.5 w-3.5 h-3.5 bg-status-ready rounded-full border-2 border-background-dark"></div>
</div>
<div>
<p class="font-bold text-lg">Elena</p>
<p class="text-status-ready text-[10px] font-bold uppercase tracking-widest">Ready</p>
</div>
</div>
<div class="shrink-0">
<label class="relative flex h-[32px] w-[56px] cursor-pointer items-center rounded-full border-none p-1 transition-all duration-300 justify-end bg-status-ready">
<div class="h-6 w-6 rounded-full bg-white shadow-lg"></div>
<input checked="" class="hidden" type="checkbox"/>
</label>
</div>
</div>
<div class="flex items-center justify-between bg-white/5 dark:bg-primary/5 border border-primary/10 rounded-2xl p-4">
<div class="flex items-center gap-4">
<div class="relative">
<div class="w-14 h-14 rounded-full overflow-hidden border-2 border-status-floor">
<img alt="Dancer profile portrait for Jade" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA2FyUFGS5Lo5qYAUv4C0uZd0U_L-So1MymYJW6L_6nnRpfgOWle_gcb8E3GfxDDfIgV5VIHycZJ6RFaOJn49F2jRIrOXJu3vAWAkzKy1nV6ibFiDu4TUu6JV-9i4_VfWo-Af2yAR8ZOLL1fzL8Mpa1PX_x1XW7SwDr_b0I-VMQ6rTHMBwBrvr817Nap2_rVdWcfMhAVS16W0ua-CCcOd61VEhVAEdSp2_duU0zyhNtoW_w6mPv1nn5WCp7y4HMr6qY-hHKM_A42_w"/>
</div>
<div class="absolute bottom-0.5 right-0.5 w-3.5 h-3.5 bg-status-floor rounded-full border-2 border-background-dark"></div>
</div>
<div>
<p class="font-bold text-lg">Jade</p>
<p class="text-status-floor text-[10px] font-bold uppercase tracking-widest">Floor</p>
</div>
</div>
<div class="shrink-0">
<label class="relative flex h-[32px] w-[56px] cursor-pointer items-center rounded-full border-none p-1 transition-all duration-300 justify-end bg-status-floor">
<div class="h-6 w-6 rounded-full bg-white shadow-lg"></div>
<input checked="" class="hidden" type="checkbox"/>
</label>
</div>
</div>
<div class="flex items-center justify-between bg-white/5 dark:bg-primary/5 border border-primary/10 rounded-2xl p-4">
<div class="flex items-center gap-4">
<div class="relative">
<div class="w-14 h-14 rounded-full overflow-hidden border-2 border-status-floor">
<img alt="Dancer profile portrait for Sasha" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuA-_g365PRV7ov0QUMTkVtRQpV-7ym4JacsTXLqgYWT9MyaAE0xvyYitio9A5yxwtfItSlASE_PTb9hEgoYYfH0X7NLTRvM1wLmEynOoZVyGEmV5C6yJULurUBVxZe0SmGadH6m9gTJlepQ2e6kG2yjVrRNyb7MivpHoy3fUQwdMJH1ix2iMn8lxaI99DhLz-_MY_YQb9om1u6PHS6zcGJcrcUB_c1P6dp8bMXEqVwR9AcAQXo_sSK7n99RGvt1MF9K2PODOQlc-Ys"/>
</div>
<div class="absolute bottom-0.5 right-0.5 w-3.5 h-3.5 bg-status-floor rounded-full border-2 border-background-dark"></div>
</div>
<div>
<p class="font-bold text-lg">Sasha</p>
<p class="text-status-floor text-[10px] font-bold uppercase tracking-widest">Floor</p>
</div>
</div>
<div class="shrink-0">
<label class="relative flex h-[32px] w-[56px] cursor-pointer items-center rounded-full border-none p-1 transition-all duration-300 justify-end bg-status-floor">
<div class="h-6 w-6 rounded-full bg-white shadow-lg"></div>
<input checked="" class="hidden" type="checkbox"/>
</label>
</div>
</div>
<div class="flex items-center justify-between bg-white/5 dark:bg-primary/5 border border-primary/10 rounded-2xl p-4">
<div class="flex items-center gap-4">
<div class="relative">
<div class="w-14 h-14 rounded-full overflow-hidden border-2 border-status-ready">
<img alt="Dancer profile portrait for Luna" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuDTWqWsbB1PWs5aW85yrcPBaE33hl47-KtPKw9ImyVP4DfTCpS6GS0Ga06b9_BaRudQZ9QdQ6UlVNikAehk8p0H9DJ6wDzFjT-G_KRlzkBHiP7lzI9XAyuNmVxbdq78E6MReMs9-73ofrDdLbv5lL98amVaOovUjbRTJIbr2GdcPUNuG_BqJ2RACFrzZqhOS85kD2HhaS7fRL4039EIZt9vvI0dnZLWl75NEkuqDimCjlNBg5re6ojLYTGoR_rQq8MH7rnsBOOLkeE"/>
</div>
<div class="absolute bottom-0.5 right-0.5 w-3.5 h-3.5 bg-status-ready rounded-full border-2 border-background-dark"></div>
</div>
<div>
<p class="font-bold text-lg">Luna</p>
<p class="text-status-ready text-[10px] font-bold uppercase tracking-widest">Ready</p>
</div>
</div>
<div class="shrink-0">
<label class="relative flex h-[32px] w-[56px] cursor-pointer items-center rounded-full border-none p-1 transition-all duration-300 justify-end bg-status-ready">
<div class="h-6 w-6 rounded-full bg-white shadow-lg"></div>
<input checked="" class="hidden" type="checkbox"/>
</label>
</div>
</div>
</div>
</main>
<nav class="fixed bottom-0 left-0 right-0 max-w-[430px] mx-auto z-20 flex gap-2 border-t border-primary/20 bg-background-light/95 dark:bg-background-dark/95 backdrop-blur-lg px-4 pb-8 pt-3">
<a class="flex flex-1 flex-col items-center justify-center gap-1 rounded-full text-primary" href="#">
<span class="material-symbols-outlined text-2xl font-fill">group</span>
<p class="text-[10px] font-bold uppercase tracking-wider">Dancers</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined text-2xl">calendar_month</span>
<p class="text-[10px] font-bold uppercase tracking-wider">Bookings</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined text-2xl">bar_chart</span>
<p class="text-[10px] font-bold uppercase tracking-wider">Stats</p>
</a>
<a class="flex flex-1 flex-col items-center justify-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined text-2xl">settings</span>
<p class="text-[10px] font-bold uppercase tracking-wider">Settings</p>
</a>
</nav>
</div>

</body></html>


1. Đọc lại và uôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Tạo màn hình mới với html ở trên
- Tên: DancerListOfAdminScreen
- Lưu ở domain.model.response.dancer
- Lưu ý phần action bar không cần giống html, chỉ dùng ActionBarBackAndTitleView với title là Dancers

3. Nếu login là rule club_manager thì menu đầu tiên thay vì Explore thì thay thế nó là Dancers

4. List dancer sẽ dùng api DancerApi -> fetchByPage, clubId lấy từ cache trong UserDTO
Lưu ý: Tổng dancer sẽ lấy từ totalItems trong ApiPagingMeta

- Sửa ActionBarMainView trong DancerListOfAdminScreen thành Status Board
- Màn hình MyBookingScreen: nếu user thì dùng top_bar_my_booking như hiện tại, còn admin thì dùng text mới Booking Queue