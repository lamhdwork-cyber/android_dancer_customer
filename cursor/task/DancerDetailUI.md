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
                        "background-dark": "#160816",
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
        .glass-panel {
            background: rgba(34, 16, 34, 0.75);
            backdrop-filter: blur(16px);
            -webkit-backdrop-filter: blur(16px);
            border-top: 1px solid rgba(244, 37, 244, 0.2);
        }
        .text-shadow-glow {
            text-shadow: 0 0 10px rgba(244, 37, 244, 0.5);
        }
        .button-glow {
            box-shadow: 0 8px 30px rgba(244, 37, 244, 0.4);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100 antialiased overflow-x-hidden">
<div class="relative flex h-screen w-full max-w-[430px] mx-auto flex-col bg-background-light dark:bg-background-dark overflow-hidden shadow-2xl">
<div class="absolute top-0 left-0 right-0 z-30 flex items-center justify-between p-6 bg-gradient-to-b from-background-dark/90 to-transparent">
<button class="flex size-10 items-center justify-center rounded-full bg-background-dark/40 backdrop-blur-md text-slate-100 border border-white/10">
<span class="material-symbols-outlined">arrow_back_ios_new</span>
</button>
<div class="flex gap-2">
<button class="flex size-10 items-center justify-center rounded-full bg-background-dark/40 backdrop-blur-md text-slate-100 border border-white/10">
<span class="material-symbols-outlined">favorite</span>
</button>
<button class="flex size-10 items-center justify-center rounded-full bg-background-dark/40 backdrop-blur-md text-slate-100 border border-white/10">
<span class="material-symbols-outlined">share</span>
</button>
</div>
</div>
<div class="relative w-full h-[60%] flex-shrink-0">
<div class="h-full w-full bg-cover bg-center" data-alt="Luna performing contemporary pole art under neon lights" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuD-WJyzkUh8xggHLqmj_b_glmHUFUvFQzJWledjIZMrPFCIAssEkuuhP0GKlyrH0GdzAEGeBOtXzFXd-vHYqWMx_uQzs4wcXjJfszEWYxeTwzo09MLB2VxMaoJrssnY8sNBozqLbPl2DPeuJSxOqM_RTd1Izl51K4qDsjxGFgshsvY_g9sVvOW0W3KdWW6BojPSTLgZ5RsBJpaBNKyuIY30NLOzx-_eNqGfD-pAVMoflutVh1pf1gjMYP_UMgxnW4TJJUOEcFrGfCs");'></div>
<div class="absolute bottom-16 left-0 right-0 flex justify-center gap-2 z-10">
<div class="h-1.5 w-6 rounded-full bg-primary"></div>
<div class="h-1.5 w-1.5 rounded-full bg-white/40"></div>
<div class="h-1.5 w-1.5 rounded-full bg-white/40"></div>
<div class="h-1.5 w-1.5 rounded-full bg-white/40"></div>
</div>
</div>
<div class="glass-panel absolute bottom-0 left-0 right-0 top-[52%] rounded-t-[3rem] px-8 pt-8 pb-32 flex flex-col z-20 overflow-y-auto">
<div class="w-12 h-1 bg-white/20 rounded-full self-center mb-8 flex-shrink-0"></div>
<div class="flex justify-between items-start mb-6">
<div>
<h1 class="text-4xl font-bold tracking-tight dark:text-white text-shadow-glow">Luna</h1>
<div class="flex items-center gap-2 mt-1 text-primary">
<span class="material-symbols-outlined text-sm">auto_awesome</span>
<p class="text-sm font-medium uppercase tracking-widest">Premium Performer</p>
</div>
</div>
<div class="bg-primary/20 border border-primary/30 px-3 py-1 rounded-full">
<p class="text-xs font-bold text-primary">ID: 8824</p>
</div>
</div>
<div class="grid grid-cols-2 gap-4 mb-8">
<div class="bg-white/5 rounded-2xl p-4 border border-white/5">
<p class="text-slate-400 text-[10px] uppercase tracking-[0.15em] mb-1">Age</p>
<p class="font-semibold text-lg">24 Years</p>
</div>
<div class="bg-white/5 rounded-2xl p-4 border border-white/5">
<p class="text-slate-400 text-[10px] uppercase tracking-[0.15em] mb-1">Style</p>
<p class="font-semibold text-lg">Contemporary</p>
</div>
</div>
<div class="mb-10">
<p class="text-slate-400 text-[10px] uppercase tracking-[0.15em] mb-3">About</p>
<p class="text-slate-300 leading-relaxed text-sm">
                    Luna brings a unique blend of contemporary grace and pole artistry to the stage, creating an unforgettable atmosphere with every performance. Specialized in storytelling through fluid movement and artistic expression.
                </p>
</div>
</div>
<div class="absolute bottom-0 left-0 right-0 z-40 p-6 flex flex-col gap-3 bg-gradient-to-t from-background-dark/95 via-background-dark/50 to-transparent">
<button class="w-full bg-primary hover:bg-primary/90 text-white font-bold py-5 rounded-2xl flex items-center justify-center gap-3 transition-all active:scale-[0.98] button-glow">
<span class="material-symbols-outlined">event_available</span>
<span class="uppercase tracking-widest text-sm">Book Now</span>
</button>
<button class="w-full bg-transparent border-2 border-primary/60 text-primary font-bold py-5 rounded-2xl flex items-center justify-center gap-3 hover:bg-primary/10 transition-all active:scale-[0.98]">
<span class="material-symbols-outlined">schedule</span>
<span class="uppercase tracking-widest text-sm">Book Late</span>
</button>
<div class="h-4"></div>
</div>
</div>

</body></html>

Đọc ký rule sau:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md

1. Sửa DetailDancerScreen lại UI mới, theo html ở trên