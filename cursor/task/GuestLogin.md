<!DOCTYPE html>
<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Guest Login Screen</title>
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
                        "background-dark": "#120812",
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
            min-height: max(884px, 100dvh);
        }
        .bg-mesh {
            background-color: #120812;
            background-image: 
                radial-gradient(at 0% 0%, rgba(244, 37, 244, 0.15) 0px, transparent 50%),
                radial-gradient(at 100% 100%, rgba(244, 37, 244, 0.1) 0px, transparent 50%);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark text-slate-900 dark:text-slate-100 min-h-screen flex flex-col font-display">
<div class="relative flex h-full min-h-screen w-full flex-col bg-mesh overflow-x-hidden">
<div class="flex items-center p-4 pb-2 justify-between">
<div class="text-slate-100 flex size-12 shrink-0 items-center justify-start cursor-pointer">
<span class="material-symbols-outlined">close</span>
</div>
<h2 class="text-slate-100 text-lg font-bold leading-tight tracking-tight flex-1 text-center pr-12">Guest Login</h2>
</div>
<div class="flex flex-col items-center pt-8 pb-12 px-6">
<div class="w-24 h-24 bg-primary/20 rounded-xl flex items-center justify-center mb-6 border border-primary/30 shadow-[0_0_20px_rgba(244,37,244,0.3)]">
<span class="material-symbols-outlined text-primary text-5xl">nightlife</span>
</div>
<h1 class="text-slate-100 tracking-tight text-4xl font-bold leading-tight text-center">VELVET</h1>
<p class="text-slate-400 text-sm mt-2 tracking-widest uppercase">Premium Nightlife Access</p>
</div>
<div class="relative px-6 flex-grow">
<div class="absolute inset-0 bg-primary/5 blur-3xl rounded-full"></div>
<div class="relative space-y-5">
<div class="flex flex-col gap-2">
<label class="text-slate-300 text-sm font-medium px-1">Email Address</label>
<div class="relative group">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 group-focus-within:text-primary transition-colors">mail</span>
<input class="w-full h-14 pl-12 pr-4 bg-slate-900/40 border border-slate-800 rounded-xl text-slate-100 placeholder:text-slate-600 focus:outline-none focus:ring-2 focus:ring-primary/50 focus:border-primary transition-all" placeholder="your@email.com" type="email"/>
</div>
</div>
<div class="flex flex-col gap-2">
<label class="text-slate-300 text-sm font-medium px-1">Password</label>
<div class="relative group">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-500 group-focus-within:text-primary transition-colors">lock</span>
<input class="w-full h-14 pl-12 pr-12 bg-slate-900/40 border border-slate-800 rounded-xl text-slate-100 placeholder:text-slate-600 focus:outline-none focus:ring-2 focus:ring-primary/50 focus:border-primary transition-all" placeholder="••••••••" type="password"/>
<span class="material-symbols-outlined absolute right-4 top-1/2 -translate-y-1/2 text-slate-500 cursor-pointer hover:text-slate-300 transition-colors">visibility</span>
</div>
</div>
<div class="pt-4 space-y-6">
<button class="w-full h-14 bg-primary text-white font-bold text-lg rounded-xl shadow-[0_0_25px_rgba(244,37,244,0.4)] hover:shadow-[0_0_35px_rgba(244,37,244,0.6)] active:scale-[0.98] transition-all">
                        Login
                    </button>
<div class="text-center">
<a class="text-slate-500 text-sm hover:text-primary transition-colors inline-block" href="#">Forgot Password?</a>
</div>
</div>
</div>
</div>
<div class="mt-auto pb-10 px-6 text-center">
<p class="text-slate-400 text-sm">
                Don't have an account? 
                <a class="text-primary font-bold ml-1 hover:underline" href="#">Sign Up</a>
</p>
</div>
<div class="absolute inset-0 -z-10 overflow-hidden pointer-events-none opacity-20">
<div class="w-full h-full bg-center bg-no-repeat bg-cover" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuDjjwGcjxCHGleMJk1W3xBzrVEfH9j6F3DQoPgf6bakVT_Cw8cs9Ly3i3mAn9m_aYjADsXpBUfvxs0Gwt6SO8JJD36hTrHcnA4vf69SFHSOVJRG2wA1JekmNjhtZbb880aGRIcAQdAJigD9XeWmctyzKiejHeeFzmAYAFp2-o2prZIm3BwJJJF8HZlVRdG3TjAMB7XI1W6GTr33BAawLSBSWf3ATQz5NoleVirpkFfqCK-fR0B26_36TR6J2lHoIOinYUG7RdClpcg");'>
</div>
<div class="absolute inset-0 bg-gradient-to-b from-background-dark via-background-dark/80 to-background-dark"></div>
</div>
</div>

</body></html>

Task:
Refactor UI of GuestSignInScreen ONLY.

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