<!DOCTYPE html>
<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Final Booking Detail Status</title>
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
                        "background-dark": "#0a050a",
                    },
                    fontFamily: {
                        "display": ["Spline Sans", "sans-serif"]
                    },
                    borderRadius: {
                        "DEFAULT": "1rem",
                        "lg": "2rem",
                        "xl": "3rem",
                        "full": "9999px"
                    },
                },
            },
        }
    </script>
<style type="text/tailwindcss">
        body {
            font-family: 'Spline Sans', sans-serif;
            -webkit-tap-highlight-color: transparent;
        }
        .glass-card {
            background: rgba(244, 37, 244, 0.05);
            backdrop-filter: blur(12px);
            border: 1px solid rgba(244, 37, 244, 0.15);
        }
        .ios-scroller::-webkit-scrollbar {
            display: none;
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-dark text-slate-100 antialiased">
<div class="relative flex h-screen w-full flex-col overflow-hidden max-w-[430px] mx-auto bg-background-dark shadow-2xl">
<div class="relative h-[280px] w-full shrink-0">
<img alt="Club Interior" class="w-full h-full object-cover brightness-75" src="https://lh3.googleusercontent.com/aida-public/AB6AXuBybO5u2dqdPCk5ImOMxGDagZVgY3s_36oW4EZ9Up9Vnq6iD2yt6LLJxgGoeQS-i5P4TTBLz1_siYx2QWB_6RuiESZVr_AsATlpdD9gwgJ_JHbvYc0PPmGizIKynyepQ3P0KlVcwzp3w5Gl3und6P4nhwcDzXX_bY3_gxVaKrI1_kIWjDRBc81oU9Jtqh7aUaXC_YVXhrN3MMRT_868_AnZ_ElhjvfQdLYT-7sCC_JT6DlIglX_8ZaTGIdrdMVKffj2dK9rrTkHzx4"/>
<div class="absolute inset-0 bg-gradient-to-b from-black/40 via-transparent to-background-dark"></div>
<div class="absolute top-0 left-0 right-0 flex items-center justify-between p-4 pt-12">
<button class="size-10 flex items-center justify-center rounded-full bg-black/30 backdrop-blur-md border border-white/10">
<span class="material-symbols-outlined text-white">arrow_back_ios_new</span>
</button>
<button class="size-10 flex items-center justify-center rounded-full bg-black/30 backdrop-blur-md border border-white/10">
<span class="material-symbols-outlined text-white text-xl">more_horiz</span>
</button>
</div>
<div class="absolute bottom-6 left-4 flex gap-2">
<div class="flex h-7 items-center justify-center gap-x-1.5 rounded-full bg-emerald-500 px-3 border border-emerald-400/50 shadow-[0_0_15px_rgba(16,185,129,0.4)]">
<span class="size-1.5 rounded-full bg-white animate-pulse"></span>
<p class="text-white text-[10px] font-black uppercase tracking-widest">Accepted</p>
</div>
<div class="flex h-7 items-center justify-center rounded-full bg-primary px-3 border border-primary/50 shadow-[0_0_15px_rgba(244,37,244,0.4)]">
<p class="text-white text-[10px] font-black uppercase tracking-widest">VIP Guest</p>
</div>
</div>
</div>
<div class="flex-1 overflow-y-auto ios-scroller px-4 pb-32 -mt-2 relative z-10">
<div class="mb-6">
<h1 class="text-3xl font-bold tracking-tight text-white mb-1">Club Obsidian</h1>
<p class="text-slate-400 text-sm font-medium flex items-center gap-1">
<span class="material-symbols-outlined text-primary text-sm">location_on</span>
                    1200 Obsidian Way, Las Vegas
                </p>
</div>
<div class="grid grid-cols-3 gap-3 mb-8">
<div class="glass-card rounded-2xl p-3 flex flex-col items-center text-center">
<span class="material-symbols-outlined text-primary mb-1 text-xl">calendar_today</span>
<span class="text-[10px] text-slate-400 uppercase font-bold tracking-tighter">Date</span>
<span class="text-xs font-bold text-white mt-0.5">Oct 27</span>
</div>
<div class="glass-card rounded-2xl p-3 flex flex-col items-center text-center">
<span class="material-symbols-outlined text-primary mb-1 text-xl">schedule</span>
<span class="text-[10px] text-slate-400 uppercase font-bold tracking-tighter">Time</span>
<span class="text-xs font-bold text-white mt-0.5">10:00 PM</span>
</div>
<div class="glass-card rounded-2xl p-3 flex flex-col items-center text-center">
<span class="material-symbols-outlined text-primary mb-1 text-xl">meeting_room</span>
<span class="text-[10px] text-slate-400 uppercase font-bold tracking-tighter">Room</span>
<span class="text-xs font-bold text-white mt-0.5">VIP Suite</span>
</div>
</div>
<div class="mb-8">
<div class="flex items-center justify-between mb-3 px-1">
<h3 class="text-xs font-bold uppercase tracking-[0.2em] text-primary">Selected Dancers</h3>
<span class="text-[10px] font-bold text-slate-500">2 RESERVED</span>
</div>
<div class="grid grid-cols-2 gap-3">
<div class="glass-card p-2.5 rounded-2xl flex items-center gap-3">
<div class="size-11 rounded-full overflow-hidden ring-2 ring-primary/20">
<img alt="Lexi" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuC0kZTVWagXQNy5Z5CsIVYla8Ij604JYiK6r0kl32xDNoyHDJ7qDTpRA7jRTQQMtzDWLQfZxURPxtiEAeEV7Q8TnuzmXfn-CwZrJw1Ujdm4rmbUhyZb5B1rXbDkdqnIA1bsOvvukGZFTyXTmyAwvwT7dphAsuChqIraot54XdlNA78_Xg-YRAK02mR9uuTAXCrEK0gnGSAWtueGETFgRfzIbeJ5FWTSxpkwtz-38oSdddxcWEKyx6wDO6UJTF7ZoNWQJbcjDFPGmhg"/>
</div>
<div class="flex flex-col">
<span class="text-sm font-bold text-white">Lexi</span>
<span class="text-[9px] text-slate-400 font-bold uppercase tracking-wider">Top Tier</span>
</div>
</div>
<div class="glass-card p-2.5 rounded-2xl flex items-center gap-3">
<div class="size-11 rounded-full overflow-hidden ring-2 ring-primary/20">
<img alt="Sasha" class="w-full h-full object-cover" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCdwXKNh4XZE_vW4aH6fjjStT1Nv6o7gj3lSKViT9bpNQMOAhBg1eBlnPTKOOlx76gzfgjpKs44U9g9WOlCKVYj6jgSB0pDozmFLr0AObbh1OUiC-J37D2JyLtC3aNLWTkJflwPHjswNVt003h5UBZ6_TQjGFJU_I0noy07P0Gbymfvxdu0_6P9n0YeboPGBgJJnOcF8grOk6NLts2Qk9dsdBMU-mkZLJGbLxaGEslGn3GF6z2cc-UCUn3AxlcHjtAP_N_ZhuWVrW4"/>
</div>
<div class="flex flex-col">
<span class="text-sm font-bold text-white">Sasha</span>
<span class="text-[9px] text-slate-400 font-bold uppercase tracking-wider">Top Tier</span>
</div>
</div>
</div>
</div>
<div class="glass-card rounded-3xl p-5 mb-8">
<div class="space-y-4">
<div class="flex justify-between items-center border-b border-white/5 pb-4">
<div class="flex items-center gap-3">
<span class="material-symbols-outlined text-slate-400 text-xl">groups</span>
<span class="text-sm font-semibold text-slate-300">Number of Guests</span>
</div>
<span class="text-sm font-bold text-white">4 People</span>
</div>
<div class="flex justify-between items-center border-b border-white/5 pb-4">
<div class="flex items-center gap-3">
<span class="material-symbols-outlined text-slate-400 text-xl">music_note</span>
<span class="text-sm font-semibold text-slate-300">Number of Songs</span>
</div>
<span class="text-sm font-bold text-white">6 Songs</span>
</div>
<div class="pt-2">
<div class="flex justify-between items-center">
<span class="text-xs font-bold text-slate-500 uppercase tracking-widest">Total Amount Due</span>
<div class="bg-amber-500/10 border border-amber-500/30 px-2 py-0.5 rounded text-[8px] font-black text-amber-500 uppercase tracking-tighter">Cash Payment Only</div>
</div>
<div class="flex items-baseline justify-between mt-1">
<span class="text-3xl font-black text-white">$1,200.00</span>
<span class="text-[10px] text-slate-500 font-medium">Incl. Tax &amp; Fees</span>
</div>
</div>
</div>
</div>
<div class="space-y-3 pb-8">
<button class="w-full bg-primary hover:brightness-110 active:scale-[0.98] text-white font-black py-4 rounded-2xl shadow-xl shadow-primary/30 transition-all flex items-center justify-center gap-3">
<span class="material-symbols-outlined text-2xl">qr_code_2</span>
                    VIEW CHECK-IN PASS
                </button>
<button class="w-full bg-transparent hover:bg-red-500/10 active:scale-[0.98] text-red-500/60 font-bold py-4 rounded-2xl border border-red-500/20 transition-all flex items-center justify-center gap-2">
<span class="material-symbols-outlined text-xl">cancel</span>
                    Cancel Booking
                </button>
</div>
</div>
<div class="absolute bottom-0 w-full bg-background-dark/80 backdrop-blur-2xl border-t border-white/5 px-8 py-5 flex justify-between items-center z-50">
<div class="flex flex-col items-center gap-1 text-primary">
<span class="material-symbols-outlined fill-1 text-2xl">confirmation_number</span>
<span class="text-[9px] font-black uppercase tracking-tighter">Booking</span>
</div>
<div class="flex flex-col items-center gap-1 text-slate-500">
<span class="material-symbols-outlined text-2xl">explore</span>
<span class="text-[9px] font-bold uppercase tracking-tighter">Explore</span>
</div>
<div class="flex flex-col items-center gap-1 text-slate-500 relative">
<span class="material-symbols-outlined text-2xl">chat_bubble</span>
<span class="text-[9px] font-bold uppercase tracking-tighter">Chat</span>
<span class="absolute -top-1 -right-1 size-2 bg-primary rounded-full border border-background-dark"></span>
</div>
<div class="flex flex-col items-center gap-1 text-slate-500">
<span class="material-symbols-outlined text-2xl">person</span>
<span class="text-[9px] font-bold uppercase tracking-tighter">Profile</span>
</div>
</div>
</div>

</body></html>

1. Đọc lại và uôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Chuyển đổi html trên cho UI DetailBookingScreen
- Ở hình ảnh map thay thế cho hình ảnh club

Chỉnh lại như sau:
- Color đưa vào presentation.theme.Colors, có sẵn thì lấy dùng, k có thì tạo mới
- Bỏ DetailBookingHeroMapUrl đi, lấy link image thực trong data trả về, nó là link của club
- Tên và icon room lấy đúng với dữ liệu thực, trong create booking đã có xử lý map icon