<!DOCTYPE html>
<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Final Booking Summary Static Dancers</title>
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
                        "accent-amber": "#ffaa00",
                        "background-light": "#f8f5f8",
                        "background-dark": "#0a020a",
                    },
                    fontFamily: {
                        "display": ["Spline Sans", "sans-serif"]
                    },
                    borderRadius: { "DEFAULT": "1rem", "lg": "2rem", "xl": "3rem", "full": "9999px" },
                },
            },
        }
    </script>
<style type="text/tailwindcss">
        body {
            min-height: 100dvh;
        }
        .scrollbar-hide::-webkit-scrollbar {
            display: none;
        }
        .scrollbar-hide {
            -ms-overflow-style: none;
            scrollbar-width: none;
        }
        .neon-amber-glow {
            text-shadow: 0 0 10px rgba(255, 170, 0, 0.5);
            box-shadow: 0 0 15px rgba(255, 170, 0, 0.2);
        }
        .glass-glow {
            background: rgba(244, 37, 244, 0.05);
            backdrop-filter: blur(12px);
            border: 1px solid rgba(244, 37, 244, 0.3);
            box-shadow: 0 0 25px rgba(244, 37, 244, 0.15);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100">
<div class="relative flex h-full min-h-screen w-full flex-col bg-background-light dark:bg-background-dark overflow-x-hidden pb-32">
<div class="flex items-center p-4 justify-between sticky top-0 bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-md z-10">
<div class="text-slate-900 dark:text-slate-100 flex size-10 shrink-0 items-center justify-center rounded-full bg-primary/10">
<span class="material-symbols-outlined font-bold">arrow_back</span>
</div>
<h2 class="text-slate-900 dark:text-slate-100 text-lg font-bold leading-tight tracking-tight flex-1 text-center">Final Booking Summary</h2>
<div class="size-10"></div>
</div>
<div class="p-4 @container">
<div class="flex flex-col items-stretch justify-start rounded-xl bg-primary/5 border border-primary/10 overflow-hidden shadow-2xl shadow-primary/5">
<div class="w-full bg-center bg-no-repeat aspect-video bg-cover" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuCcfcRJq6n4BvLZhM_V0Ms3WP_srw_4fAvB5Rk0LZcOpLspN6eqduLZZAfDXq_7tJYaDZeop28lB9WMYFp_rMaobS8dOlfODJBa4O6e3i2XXSZ185Wg0HQ72liF67vAC8GfC8wvjGw-FnCedhxaF1FGOEQ2IBxSiYoIvYThKORVbRarlhRJKIflYeqQgMPdFKipGzLk9SzexMoojSwlx0jVmVmeZmOEMLWAh1br4I20idoVmqFz7ucG57pKDYPfa-jZmNU6IJPBHjc");'></div>
<div class="flex w-full min-w-72 grow flex-col items-stretch justify-center gap-2 p-5">
<div class="flex justify-between items-start">
<div>
<p class="text-slate-900 dark:text-slate-100 text-xl font-bold tracking-tight uppercase">Club Neon</p>
<p class="text-primary text-sm font-medium">Downtown District</p>
</div>
<div class="bg-primary/20 px-3 py-1 rounded-full border border-primary/30">
<span class="text-primary text-xs font-bold uppercase tracking-wider">Premium Venue</span>
</div>
</div>
</div>
</div>
</div>
<div class="px-4 mb-3">
<div class="flex gap-3">
<div class="flex-1 bg-white/5 border border-white/10 rounded-2xl p-4 flex items-center gap-3">
<div class="bg-primary/20 p-2 rounded-lg">
<span class="material-symbols-outlined text-primary text-xl">calendar_today</span>
</div>
<div>
<p class="text-[10px] uppercase tracking-widest text-slate-500 font-bold">Date</p>
<p class="text-slate-100 font-bold">Friday, Nov 24</p>
</div>
</div>
<div class="flex-1 bg-white/5 border border-white/10 rounded-2xl p-4 flex items-center gap-3">
<div class="bg-primary/20 p-2 rounded-lg">
<span class="material-symbols-outlined text-primary text-xl">schedule</span>
</div>
<div>
<p class="text-[10px] uppercase tracking-widest text-slate-500 font-bold">Time</p>
<p class="text-slate-100 font-bold">11:30 PM</p>
</div>
</div>
</div>
</div>
<div class="px-4 mb-2">
<div class="w-full bg-white/5 border border-white/10 rounded-2xl p-4 flex items-center gap-4">
<div class="bg-primary/20 p-3 rounded-xl border border-primary/30">
<span class="material-symbols-outlined text-primary text-2xl" style="font-variation-settings: 'FILL' 1">bedroom_parent</span>
</div>
<div class="flex-1">
<p class="text-[10px] uppercase tracking-widest text-slate-500 font-bold">Selected Room Type</p>
<div class="flex items-center gap-2">
<p class="text-slate-100 text-lg font-bold">VIP Suite</p>
<span class="bg-primary/10 text-primary text-[10px] px-2 py-0.5 rounded border border-primary/20 font-bold">PRIVATE</span>
</div>
</div>
</div>
</div>
<div class="px-4 py-6">
<div class="flex items-center justify-between mb-4 px-2">
<h3 class="text-slate-900 dark:text-slate-100 text-lg font-bold">Selected Dancers</h3>
</div>
<div class="glass-glow rounded-3xl p-6">
<div class="flex gap-8 overflow-x-auto pb-1 scrollbar-hide justify-center">
<div class="flex flex-col items-center gap-3 shrink-0">
<div class="size-20 rounded-full border-2 border-primary p-0.5 shadow-[0_0_20px_rgba(244,37,244,0.5)]">
<div class="size-full rounded-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuARBPmPCw_tfd349S-aTCsRvkERUu_GLLhmVDMNuikhMHkye9LnyPIMjkd3kB1HK2-zv4-t_6RIeQco0V_ZS5-CP1dH40ufZ2roLAUOd3PqOTRv2kT-Z7X1INvF0bRKZTKQBLe-xTLpnZELVgXogf1KqJyUv2rvqWLlMeuj9E9r5oquANaPNNM6yaM8J9qmiIhVqQ19IvX_t3R7cq0qG1OGXtgse32EY4bbJYAKeahtjmOC8zgtjTOkkYDCTqrViRZF9FrW0OFB_BA')"></div>
</div>
<p class="text-xs font-bold dark:text-slate-200">Elena</p>
</div>
<div class="flex flex-col items-center gap-3 shrink-0">
<div class="size-20 rounded-full border-2 border-primary p-0.5 shadow-[0_0_20px_rgba(244,37,244,0.5)]">
<div class="size-full rounded-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuC8nWGYNEOvVmhDhTg3X33yTz4y1iBZb_dGavFwFyJDmHAnd6lGBdTCxWSkK8WidK-LRLeGBkn7k9hvT9Vzzc5Qt_gIeHBSoFiEz570ix414VTLrfmWRUkM6l5UPiATUhNCOLENsGBQC5p8wocCjvOiUSU8Szq07743O4jCzMZU-doaGNJ3btNcqRi626UjKDzCjKZQ3ePngvT3Kamv6IhA4muYB-5UQ4SzDgn0VnGdqyv6FPsA7V9JFPs41lpYN4SL2_IsJ9Ylbfw')"></div>
</div>
<p class="text-xs font-bold dark:text-slate-200">Jade</p>
</div>
<div class="flex flex-col items-center gap-3 shrink-0">
<div class="size-20 rounded-full border-2 border-primary p-0.5 shadow-[0_0_20px_rgba(244,37,244,0.5)]">
<div class="size-full rounded-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuBBeiJ1LG3uJHfIkDSquUD7dRTJu0MEd4B_87lWY85Q4ibsSuTYU9JJm1Pq6CbdL5aUcIKkFGSgb74ypyzrtdUuQ4aZVoqecDypu1h6V4AaU8rhy0ELJE8ZVpUtA3vi3gAqCA6HeJ-rJjAtb8jCMDxMFHhCgUmhog55dd3fsbkkAdZydt87GO3JYiuOVYTdhx_lbIfAiWz85Q2LhLgb852j2YdpC9o_LOfdPvB0veva1wRlkUmYB4v-1ts-gHPYrQo3WhuFoZJ2p08')"></div>
</div>
<p class="text-xs font-bold dark:text-slate-200">Sasha</p>
</div>
</div>
</div>
</div>
<div class="m-4 p-5 rounded-xl bg-slate-100 dark:bg-white/5 border border-slate-200 dark:border-white/10">
<div class="flex justify-between items-center mb-4">
<span class="text-slate-600 dark:text-slate-400 font-medium">Number of Songs</span>
<span class="text-slate-900 dark:text-slate-100 font-bold">12</span>
</div>
<div class="flex justify-between items-center mb-6">
<span class="text-slate-600 dark:text-slate-400 font-medium">Number of Guests</span>
<span class="text-slate-900 dark:text-slate-100 font-bold">4</span>
</div>
<div class="h-px bg-slate-200 dark:bg-white/10 my-4"></div>
<div class="flex flex-col gap-4">
<div class="flex justify-between items-end">
<div class="flex flex-col">
<span class="text-slate-900 dark:text-slate-100 text-lg font-bold leading-tight uppercase tracking-tight">Total Amount Due</span>
<span class="text-slate-500 text-xs font-medium">(Collected at Venue)</span>
</div>
<div class="text-right">
<div class="inline-block px-2 py-0.5 rounded bg-accent-amber/10 border border-accent-amber/30 neon-amber-glow mb-1">
<span class="text-accent-amber text-[10px] font-black uppercase tracking-[0.1em]">CASH PAYMENT ONLY</span>
</div>
<div class="block">
<span class="text-primary text-4xl font-black tracking-tighter leading-none">$295.00</span>
</div>
</div>
</div>
</div>
</div>
<div class="fixed bottom-0 left-0 right-0 bg-background-light/95 dark:bg-background-dark/95 backdrop-blur-md border-t border-slate-200 dark:border-white/10 p-6 z-20">
<button class="w-full bg-primary hover:bg-primary/90 text-white font-bold py-5 rounded-full shadow-[0_0_30px_rgba(244,37,244,0.4)] flex items-center justify-center gap-3 transition-all active:scale-95">
<span class="material-symbols-outlined" style="font-variation-settings: 'FILL' 1">check_circle</span>
<span class="tracking-widest">CONFIRM BOOKING</span>
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

2. Tạo BookingConfirmScreen trong package com.kantek.dancer.booking.presentation.screen.booking bằng html trên
