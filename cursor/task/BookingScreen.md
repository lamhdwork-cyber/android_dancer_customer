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
                        "background-dark": "#0a030a",
                        "card-dark": "#160816",
                    },
                    fontFamily: {
                        "display": ["Spline Sans", "sans-serif"]
                    },
                    borderRadius: {"DEFAULT": "1rem", "lg": "2rem", "xl": "3rem", "full": "9999px"},
                },
            },
        }
    </script>
<style type="text/tailwindcss">
        @layer components {
            .glass-card {
                @apply bg-white/5 border border-white/10 backdrop-blur-md;
            }
            .stepper-btn {
                @apply flex size-10 items-center justify-center rounded-full border border-primary/30 bg-primary/10 text-primary active:scale-95 transition-transform;
            }
            .scrollbar-hide::-webkit-scrollbar {
                display: none;
            }
            .scrollbar-hide {
                -ms-overflow-style: none;
                scrollbar-width: none;
            }
        }
        .text-shadow-glow {
            text-shadow: 0 0 15px rgba(244, 37, 244, 0.4);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-dark font-display text-slate-100 antialiased overflow-x-hidden">
<div class="relative flex min-h-screen w-full max-w-[430px] mx-auto flex-col bg-background-dark shadow-2xl pb-40">
<header class="sticky top-0 z-40 flex items-center justify-between p-6 bg-background-dark/90 backdrop-blur-xl border-b border-white/5">
<button class="flex size-10 items-center justify-center rounded-full bg-white/5 border border-white/10">
<span class="material-symbols-outlined text-xl">arrow_back_ios_new</span>
</button>
<h1 class="text-lg font-bold tracking-tight">Create Booking</h1>
<button class="flex size-10 items-center justify-center rounded-full bg-white/5 border border-white/10">
<span class="material-symbols-outlined text-xl">info</span>
</button>
</header>
<main class="flex-1 p-6 space-y-8">
<section>
<div class="flex justify-between items-end mb-4">
<h2 class="text-xs font-bold uppercase tracking-[0.2em] text-slate-500">Selected Performers</h2>
<p class="text-[10px] text-primary font-bold">3/5 TOTAL</p>
</div>
<div class="flex gap-4 overflow-x-auto pb-2 scrollbar-hide">
<div class="flex-shrink-0 flex flex-col items-center gap-2">
<div class="size-20 rounded-2xl border-2 border-primary p-1 bg-primary/5">
<div class="w-full h-full rounded-xl bg-cover bg-center" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuD-WJyzkUh8xggHLqmj_b_glmHUFUvFQzJWledjIZMrPFCIAssEkuuhP0GKlyrH0GdzAEGeBOtXzFXd-vHYqWMx_uQzs4wcXjJfszEWYxeTwzo09MLB2VxMaoJrssnY8sNBozqLbPl2DPeuJSxOqM_RTd1Izl51K4qDsjxGFgshsvY_g9sVvOW0W3KdWW6BojPSTLgZ5RsBJpaBNKyuIY30NLOzx-_eNqGfD-pAVMoflutVh1pf1gjMYP_UMgxnW4TJJUOEcFrGfCs");'></div>
</div>
<p class="text-[11px] font-bold text-primary">Luna</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center gap-2">
<div class="size-20 rounded-2xl border-2 border-primary/40 p-1 bg-white/5">
<div class="w-full h-full rounded-xl bg-cover bg-center" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuCXu0W6Sj78MblWyNUHZr1TNd-vIVFHiewkOeBizkHI67yNiv-WjMudnwq4i3_1F2wp2axVixj79ieKbq7cRZUo4Qt15JO0RuTdoPs2IF1meHN3342irEONonvcZjw6VFxnuPRgzFrFR8SSSExcRBWo4eqgHuExIapnTAyng5aMW8vWBXTnaCuaHM_93FmFxkFWmXBbxYTasQ9M_gSHx6v_p2tznJeVcG_chshADuo9foff0FG83-vo0BV8XWfxGiYrZkltTqA1rWY");'></div>
</div>
<p class="text-[11px] font-bold text-slate-400">Jade</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center gap-2">
<div class="size-20 rounded-2xl border-2 border-primary/40 p-1 bg-white/5">
<div class="w-full h-full rounded-xl bg-cover bg-center" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuD-WJyzkUh8xggHLqmj_b_glmHUFUvFQzJWledjIZMrPFCIAssEkuuhP0GKlyrH0GdzAEGeBOtXzFXd-vHYqWMx_uQzs4wcXjJfszEWYxeTwzo09MLB2VxMaoJrssnY8sNBozqLbPl2DPeuJSxOqM_RTd1Izl51K4qDsjxGFgshsvY_g9sVvOW0W3KdWW6BojPSTLgZ5RsBJpaBNKyuIY30NLOzx-_eNqGfD-pAVMoflutVh1pf1gjMYP_UMgxnW4TJJUOEcFrGfCs");'></div>
</div>
<p class="text-[11px] font-bold text-slate-400">Amber</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center gap-2">
<button class="size-20 rounded-2xl border-2 border-dashed border-white/20 flex items-center justify-center text-slate-500 hover:border-primary/50 hover:text-primary transition-colors">
<span class="material-symbols-outlined text-3xl">add</span>
</button>
<p class="text-[11px] font-bold text-slate-500">Add</p>
</div>
</div>
</section>
<section class="space-y-4">
<h2 class="text-xs font-bold uppercase tracking-[0.2em] text-slate-500">Schedule</h2>
<div class="flex gap-3 overflow-x-auto pb-2 scrollbar-hide">
<div class="flex-shrink-0 flex flex-col items-center justify-center w-16 h-20 rounded-2xl bg-primary text-white shadow-lg shadow-primary/20">
<p class="text-[10px] font-bold uppercase opacity-80">Fri</p>
<p class="text-xl font-bold">24</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center justify-center w-16 h-20 rounded-2xl glass-card border-white/5 text-slate-400">
<p class="text-[10px] font-bold uppercase opacity-60">Sat</p>
<p class="text-xl font-bold text-white">25</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center justify-center w-16 h-20 rounded-2xl glass-card border-white/5 text-slate-400">
<p class="text-[10px] font-bold uppercase opacity-60">Sun</p>
<p class="text-xl font-bold text-white">26</p>
</div>
<div class="flex-shrink-0 flex flex-col items-center justify-center w-16 h-20 rounded-2xl glass-card border-white/5 text-slate-400">
<p class="text-[10px] font-bold uppercase opacity-60">Mon</p>
<p class="text-xl font-bold text-white">27</p>
</div>
</div>
<div class="grid grid-cols-3 gap-2">
<button class="py-2.5 px-1 rounded-xl glass-card border-primary/40 bg-primary/10 text-primary text-xs font-bold text-center">10:00 PM</button>
<button class="py-2.5 px-1 rounded-xl glass-card border-white/10 text-slate-400 text-xs font-bold text-center">11:30 PM</button>
<button class="py-2.5 px-1 rounded-xl glass-card border-white/10 text-slate-400 text-xs font-bold text-center">01:00 AM</button>
</div>
</section>
<section>
<h2 class="text-xs font-bold uppercase tracking-[0.2em] text-slate-500 mb-4">Select Room</h2>
<div class="grid grid-cols-1 gap-4">
<div class="relative glass-card p-5 rounded-3xl border-primary/50 bg-gradient-to-br from-primary/10 to-transparent flex items-center justify-between">
<div class="flex items-center gap-4">
<div class="size-14 rounded-2xl bg-primary/20 flex items-center justify-center text-primary">
<span class="material-symbols-outlined text-3xl" style="font-variation-settings: 'FILL' 1">king_bed</span>
</div>
<div>
<h3 class="font-bold text-base">VIP Room</h3>
<p class="text-xs text-slate-500">Private area, premium service</p>
</div>
</div>
<div class="text-right">
<p class="text-primary font-black text-xl">$500</p>
<div class="size-6 rounded-full bg-primary flex items-center justify-center ml-auto mt-1">
<span class="material-symbols-outlined text-white text-sm font-bold">check</span>
</div>
</div>
</div>
<div class="glass-card p-5 rounded-3xl opacity-60 flex items-center justify-between">
<div class="flex items-center gap-4">
<div class="size-14 rounded-2xl bg-white/5 flex items-center justify-center text-slate-400">
<span class="material-symbols-outlined text-3xl">meeting_room</span>
</div>
<div>
<h3 class="font-bold text-base text-slate-300">Private Suite</h3>
<p class="text-xs text-slate-500">Full isolation, bar access</p>
</div>
</div>
<div class="text-right">
<p class="text-slate-300 font-black text-xl">$850</p>
<div class="size-6 rounded-full border-2 border-slate-700 ml-auto mt-1"></div>
</div>
</div>
</div>
</section>
<section class="grid grid-cols-1 gap-4">
<div class="flex items-center justify-between glass-card p-5 rounded-2xl">
<div class="flex items-center gap-3">
<div class="size-10 rounded-xl bg-primary/10 flex items-center justify-center">
<span class="material-symbols-outlined text-primary">music_note</span>
</div>
<div>
<p class="font-bold text-sm">Number of Songs</p>
<p class="text-[10px] text-slate-500 uppercase tracking-wider">Per Performance</p>
</div>
</div>
<div class="flex items-center gap-4">
<button class="stepper-btn"><span class="material-symbols-outlined">remove</span></button>
<span class="text-xl font-bold w-6 text-center">4</span>
<button class="stepper-btn"><span class="material-symbols-outlined">add</span></button>
</div>
</div>
<div class="flex items-center justify-between glass-card p-5 rounded-2xl">
<div class="flex items-center gap-3">
<div class="size-10 rounded-xl bg-primary/10 flex items-center justify-center">
<span class="material-symbols-outlined text-primary">person_add</span>
</div>
<div>
<p class="font-bold text-sm">Number of Guests</p>
<p class="text-[10px] text-slate-500 uppercase tracking-wider">Clients total</p>
</div>
</div>
<div class="flex items-center gap-4">
<button class="stepper-btn"><span class="material-symbols-outlined">remove</span></button>
<span class="text-xl font-bold w-6 text-center">2</span>
<button class="stepper-btn"><span class="material-symbols-outlined">add</span></button>
</div>
</div>
</section>
<section>
<div class="glass-card bg-gradient-to-br from-primary/15 via-primary/5 to-transparent p-6 rounded-[2.5rem] border-primary/30 relative overflow-hidden">
<div class="absolute -right-4 -top-4 size-24 bg-primary/10 rounded-full blur-2xl"></div>
<div class="flex justify-between items-start relative z-10">
<div>
<p class="text-[10px] font-bold uppercase tracking-[0.2em] text-primary/80 mb-1">Estimated Total Due</p>
<h2 class="text-5xl font-black text-white text-shadow-glow tracking-tight">Collect: $950</h2>
</div>
<div class="px-3 py-1 bg-green-500/20 border border-green-500/30 text-green-400 rounded-full flex items-center gap-1.5">
<span class="size-1.5 rounded-full bg-green-400 animate-pulse"></span>
<span class="text-[10px] font-black uppercase tracking-wider">Cash Only</span>
</div>
</div>
<div class="mt-4 pt-4 border-t border-white/5 flex justify-between text-[10px] text-slate-500 font-bold uppercase tracking-widest">
<span>Base Fee: $500</span>
<span>Add-ons: $450</span>
</div>
</div>
</section>
</main>
<div class="fixed bottom-0 left-0 right-0 max-w-[430px] mx-auto p-6 pb-10 bg-background-dark/95 backdrop-blur-2xl border-t border-white/5 z-50">
<button class="w-full bg-primary hover:bg-primary/90 text-white font-bold py-5 rounded-2xl shadow-[0_0_40px_rgba(244,37,244,0.4)] flex items-center justify-center gap-3 transition-all active:scale-[0.98]">
<span class="material-symbols-outlined text-2xl" style="font-variation-settings: 'FILL' 1">verified</span>
<span class="text-lg tracking-tight">Confirm Booking</span>
</button>
</div>
</div>

</body></html>

1. Luôn tuân theo rules bên dưới:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

2. Convert html sang BookingScreen, tạo ở package com.kantek.dancer.booking.presentation.screen.booking

3. Phần schedule: 
- Nếu book now thì ẩn, chỉ có khi book later
- Ngày lấy ngày hiện tại đến 1 tuần lễ
- Time 7:00 PM -> 1:00 AM, cách nhau 30 phút

4. Room mock tạm data đúng chuẩn kiến trúc
