<html class="dark"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<link href="https://fonts.googleapis.com/css2?family=Spline+Sans:wght@300;400;500;600;700&amp;display=swap" rel="stylesheet"/>
<link href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:wght,FILL@100..700,0..1&amp;display=swap" rel="stylesheet"/>
<script src="https://cdn.tailwindcss.com?plugins=forms,container-queries"></script>
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
                        "display": ["Spline Sans"]
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
            min-height: max(884px, 100dvh);
        }
        .material-symbols-outlined {
            font-variation-settings: 'FILL' 0, 'wght' 400, 'GRAD' 0, 'opsz' 24;
        }
        .fill-1 {
            font-variation-settings: 'FILL' 1;
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100 antialiased">
<div class="relative flex min-h-screen w-full max-w-[430px] mx-auto flex-col bg-background-light dark:bg-background-dark shadow-2xl overflow-hidden">
<div class="flex items-center justify-between px-6 pt-12 pb-4 bg-background-light dark:bg-background-dark">
<div class="flex h-10 w-10 items-center justify-center rounded-full bg-slate-200 dark:bg-white/5 text-slate-900 dark:text-white">
<span class="material-symbols-outlined">arrow_back_ios_new</span>
</div>
<h1 class="text-xl font-bold tracking-tight">Select Club</h1>
<div class="w-10"></div>
</div>
<div class="px-6 py-4">
<div class="flex items-center gap-4 rounded-2xl bg-slate-100 dark:bg-white/5 p-4 border border-slate-200 dark:border-white/10">
<div class="flex h-12 w-12 shrink-0 items-center justify-center rounded-xl bg-primary shadow-[0_0_15px_rgba(244,37,244,0.4)] text-white">
<span class="material-symbols-outlined text-2xl">near_me</span>
</div>
<div class="flex flex-col flex-1">
<p class="text-xs font-semibold uppercase tracking-wider text-slate-500 dark:text-primary/70">Current Location</p>
<p class="text-base font-medium text-slate-900 dark:text-slate-100">123 Neon District, City</p>
</div>
<span class="material-symbols-outlined text-slate-400">chevron_right</span>
</div>
</div>
<div class="px-6 py-4 flex flex-col gap-6 flex-1 overflow-y-auto pb-32">
<div class="flex items-center justify-between">
<h3 class="text-lg font-bold">Nearby Clubs</h3>
<span class="text-sm font-medium text-primary cursor-pointer">View Map</span>
</div>
<div class="group flex flex-col overflow-hidden rounded-2xl bg-slate-100 dark:bg-white/5 border border-slate-200 dark:border-white/10">
<div class="relative h-56 w-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuDaR3b2Nnktc192A60tF5yAyWM1-ifSHj-QtGs3PyJK1dyR3CyOsyllYxU9Kzc9RRIv9ZJDG2_IYBYtuG7w9F8E8laC0XjEbwtaUkbL6WuKGYIvEvxB_GkaYywdo_EyVf2bXYlrNeXmlB0zr2tJmRH6RlzEZa0Lab_3Oy4jCj-6RylNmUecpaKqlNeJEjtzmpMDogOs44EzftFLatzlIpp9wUCMJP7lo8lqYsfGDHFBmkUAQF7kVHQgsP9GbKqVAQkbSdzCNW2xh_Q');">
<div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent"></div>
<div class="absolute top-4 left-4 rounded-full bg-black/60 backdrop-blur-md px-3 py-1 text-[10px] font-bold uppercase tracking-widest text-primary">
                        Open Now
                    </div>
</div>
<div class="flex flex-col p-5 gap-3">
<div class="flex items-start justify-between">
<div>
<h4 class="text-xl font-bold">Neon Velvet</h4>
<div class="flex flex-wrap items-center gap-2 mt-1">
<span class="text-sm text-slate-500 dark:text-slate-400">0.5 miles</span>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-sm text-yellow-500 fill-1">star</span>
<span class="text-sm font-semibold">4.8</span>
</div>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<span class="text-[11px] font-medium text-slate-500 dark:text-slate-400 uppercase tracking-tight">Open: 8:00 PM - 4:00 AM</span>
</div>
</div>
</div>
<button class="w-full h-12 rounded-full bg-primary text-white font-bold text-sm shadow-[0_4px_20px_rgba(244,37,244,0.3)] hover:scale-[1.02] active:scale-95 transition-all">
                        Select this club
                    </button>
</div>
</div>
<div class="group flex flex-col overflow-hidden rounded-2xl bg-slate-100 dark:bg-white/5 border border-slate-200 dark:border-white/10">
<div class="relative h-56 w-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuDl3T3RkPEAEtbYIGkfmeYpOtvp6_G1PgwtsV0VS-Zvn0of65EgKzvNXgX-mnb_A8h-FjM4dlevQTQ3MXEZkMJz6IB8raKU4psf14a-yDTEqALfcFJHRfAJXCRst7360v7IfRpvrkvUHiTF-pgzzlzODtmGt8hME15Fquib5vNsYVwFVk_7Ac0GZ6Z759Be_rP2gOtmb0cTMwuN1smsMwBJ-o0ftdBpSQ6PWwdEJuWxRfvgWzpHO-av7AQzyy-D7S88uB2vicLCSJw');">
<div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent"></div>
<div class="absolute top-4 left-4 rounded-full bg-black/60 backdrop-blur-md px-3 py-1 text-[10px] font-bold uppercase tracking-widest text-primary">
                        Popular
                    </div>
</div>
<div class="flex flex-col p-5 gap-3">
<div class="flex items-start justify-between">
<div>
<h4 class="text-xl font-bold">The Onyx Lounge</h4>
<div class="flex flex-wrap items-center gap-2 mt-1">
<span class="text-sm text-slate-500 dark:text-slate-400">1.2 miles</span>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-sm text-yellow-500 fill-1">star</span>
<span class="text-sm font-semibold">4.6</span>
</div>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<span class="text-[11px] font-medium text-slate-500 dark:text-slate-400 uppercase tracking-tight">Open: 9:00 PM - 5:00 AM</span>
</div>
</div>
</div>
<button class="w-full h-12 rounded-full bg-primary text-white font-bold text-sm shadow-[0_4px_20px_rgba(244,37,244,0.3)] hover:scale-[1.02] active:scale-95 transition-all">
                        Select this club
                    </button>
</div>
</div>
<div class="group flex flex-col overflow-hidden rounded-2xl bg-slate-100 dark:bg-white/5 border border-slate-200 dark:border-white/10">
<div class="relative h-56 w-full bg-cover bg-center" style="background-image: url('https://lh3.googleusercontent.com/aida-public/AB6AXuAZ_obEmFosZaTItBkZ-Qq2q0_iTeJpgmQw4FctUzNI4r7cLxllLmNJRNP1qnUj6w0uGupYlger7rc35HWvbeJkTMxidNuhoTCBvcbuKfCof0apIa3W8vSBhn2vxO_qNaiCnBqrKUR-R0zzqVFvPbQrqno-q7dWgJU0ADxyY6-Gcq7ROO5m58aPMuMs9-56jpac15AqOJa96neeAm-_C_MJZIO-zK156gDV_yy6jWa75Q71t5gEl5qazccC-mJDiKFkkl0_VDlSS0c');">
<div class="absolute inset-0 bg-gradient-to-t from-black/80 via-black/20 to-transparent"></div>
<div class="absolute top-4 left-4 rounded-full bg-black/60 backdrop-blur-md px-3 py-1 text-[10px] font-bold uppercase tracking-widest text-primary">
                        New
                    </div>
</div>
<div class="flex flex-col p-5 gap-3">
<div class="flex items-start justify-between">
<div>
<h4 class="text-xl font-bold">Starlight Atrium</h4>
<div class="flex flex-wrap items-center gap-2 mt-1">
<span class="text-sm text-slate-500 dark:text-slate-400">2.1 miles</span>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<div class="flex items-center gap-1">
<span class="material-symbols-outlined text-sm text-yellow-500 fill-1">star</span>
<span class="text-sm font-semibold">4.9</span>
</div>
<span class="h-1 w-1 rounded-full bg-slate-400/50"></span>
<span class="text-[11px] font-medium text-slate-500 dark:text-slate-400 uppercase tracking-tight">Open: 8:00 PM - 4:00 AM</span>
</div>
</div>
</div>
<button class="w-full h-12 rounded-full bg-primary text-white font-bold text-sm shadow-[0_4px_20px_rgba(244,37,244,0.3)] hover:scale-[1.02] active:scale-95 transition-all">
                        Select this club
                    </button>
</div>
</div>
</div>
<div class="absolute bottom-0 left-0 right-0 border-t border-slate-200 dark:border-white/10 bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-xl px-6 pb-8 pt-4">
<div class="flex items-center justify-between">
<a class="flex flex-col items-center gap-1 text-primary" href="#">
<span class="material-symbols-outlined fill-1">explore</span>
<span class="text-[10px] font-bold uppercase tracking-widest">Explore</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined">confirmation_number</span>
<span class="text-[10px] font-bold uppercase tracking-widest">Bookings</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined">stars</span>
<span class="text-[10px] font-bold uppercase tracking-widest">VIP</span>
</a>
<a class="flex flex-col items-center gap-1 text-slate-400 dark:text-slate-500" href="#">
<span class="material-symbols-outlined">person</span>
<span class="text-[10px] font-bold uppercase tracking-widest">Profile</span>
</a>
</div>
</div>
</div>

</body></html>


Task:
Refactor UI of FindDancerScreen and navigation bottom menu ONLY.

Strict rules:
- DO NOT modify any business logic, state, or event handling
- DO NOT rename existing functions/variables
- Only change UI layer

UI Requirements:
- Convert HTML to Jetpack Compose (Material3)
- Split into small reusable components
- Follow clean composable structure

Follow rules:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md