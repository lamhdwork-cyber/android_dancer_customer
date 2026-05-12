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

App chia thành 2 role, sau khi login sẽ có role trong UserDTO
1. Hãy khai báo 2 role này trong AppConfig để so sánh và check role
2. Check role cho BookingItemView như sau:
- Nếu user thì giữ nguyên như hiện tại
- Nếu club_manager thì:
Tab pending: Sử dụng 2 nút accept và reject như html ở trên
Tab Accepted: Thay thế accept là complete, thay thế reject và cancel
Tab Completed: Không hiển thị nút gì

Lưu ý: check role trong factory, sau đó trả về 1 biến trong IBooking để config BookingItemView thôi
3. Tương tự check role cho DetailBookingScreen như sau:
- Nếu user thì giữ nguyên như hiện tại
- Nếu club_manager thì theo status của booking:
pending: Sử dụng 2 nút accept và reject như html ở trên
confirmed: Thay thế accept là complete, thay thế reject và cancel
completed: Không hiển thị nút gì

Lưu ý: trong IBooking đã có thêm 1 biến để chekc ở list rồi nên detail cứ lấy biến đó ra để setting UI cho các button

4. Xử lý các button như sau:
- Reject: xử lý như cancel
- Accept: Gọi api accept trong BookingApi
- Complete: Gọi api complete trong BookingApi