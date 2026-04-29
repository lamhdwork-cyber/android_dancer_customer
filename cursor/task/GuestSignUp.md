<!DOCTYPE html>

<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Guest Registration - Nightlife Premium</title>
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
                        "background-dark": "#1a0a1a",
                    },
                    fontFamily: {
                        "display": ["Spline Sans", "sans-serif"]
                    },
                    borderRadius: {
                        "DEFAULT": "1rem",
                        "lg": "1.5rem",
                        "xl": "2rem",
                        "full": "9999px"
                    },
                },
            },
        }
    </script>
<style>
        body {
            font-family: 'Spline Sans', sans-serif;
            -webkit-tap-highlight-color: transparent;
        }
        .neon-glow {
            box-shadow: 0 0 15px rgba(244, 37, 244, 0.4);
        }
        .glass-panel {
            background: rgba(255, 255, 255, 0.03);
            backdrop-filter: blur(10px);
            border: 1px solid rgba(244, 37, 244, 0.1);
        }
    </style>
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark text-slate-900 dark:text-slate-100 min-h-screen font-display">
<div class="relative flex h-full min-h-screen w-full flex-col overflow-x-hidden max-w-[430px] mx-auto bg-background-light dark:bg-background-dark shadow-2xl">
<!-- Header / Top Navigation -->
<div class="flex items-center p-6 justify-between">
<button class="text-slate-900 dark:text-slate-100 flex size-10 shrink-0 items-center justify-center rounded-full bg-primary/10 transition-colors">
<span class="material-symbols-outlined">arrow_back_ios_new</span>
</button>
<div class="flex flex-col items-center">
<span class="text-[10px] uppercase tracking-[0.2em] text-primary font-bold">Vantage Club</span>
<h2 class="text-slate-900 dark:text-slate-100 text-sm font-semibold tracking-wide">Guest Registration</h2>
</div>
<div class="size-10"></div> <!-- Spacer for symmetry -->
</div>
<div class="px-6 pt-8 pb-4">
<h1 class="text-slate-900 dark:text-slate-100 tracking-tight text-4xl font-bold leading-tight">
                Join the <span class="text-primary italic">Elite</span>
</h1>
<p class="text-slate-500 dark:text-slate-400 mt-2 text-sm">Experience the city's most exclusive nightlife. Register your guest profile below.</p>
</div>
<!-- Registration Form -->
<div class="flex flex-col gap-5 px-6 py-4">
<div class="flex flex-col gap-1.5">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-500 dark:text-slate-400 ml-1">Full Name</label>
<div class="relative">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">person</span>
<input class="form-input flex w-full rounded-2xl border-none bg-slate-200/50 dark:bg-primary/5 focus:ring-2 focus:ring-primary h-14 pl-12 pr-4 text-slate-900 dark:text-slate-100 placeholder:text-slate-400 dark:placeholder:text-slate-600 font-medium" placeholder="John Doe" type="text"/>
</div>
</div>
<div class="flex flex-col gap-1.5">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-500 dark:text-slate-400 ml-1">Phone Number</label>
<div class="relative">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">smartphone</span>
<input class="form-input flex w-full rounded-2xl border-none bg-slate-200/50 dark:bg-primary/5 focus:ring-2 focus:ring-primary h-14 pl-12 pr-4 text-slate-900 dark:text-slate-100 placeholder:text-slate-400 dark:placeholder:text-slate-600 font-medium" placeholder="+1 (555) 000-0000" type="tel"/>
</div>
</div>
<div class="flex flex-col gap-1.5">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-500 dark:text-slate-400 ml-1">Email Address</label>
<div class="relative">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">alternate_email</span>
<input class="form-input flex w-full rounded-2xl border-none bg-slate-200/50 dark:bg-primary/5 focus:ring-2 focus:ring-primary h-14 pl-12 pr-4 text-slate-900 dark:text-slate-100 placeholder:text-slate-400 dark:placeholder:text-slate-600 font-medium" placeholder="john@vantage.com" type="email"/>
</div>
</div>
<div class="flex flex-col gap-1.5">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-500 dark:text-slate-400 ml-1">Password</label>
<div class="relative">
<span class="material-symbols-outlined absolute left-4 top-1/2 -translate-y-1/2 text-slate-400 text-xl">lock</span>
<input class="form-input flex w-full rounded-2xl border-none bg-slate-200/50 dark:bg-primary/5 focus:ring-2 focus:ring-primary h-14 pl-12 pr-12 text-slate-900 dark:text-slate-100 placeholder:text-slate-400 dark:placeholder:text-slate-600 font-medium" placeholder="••••••••" type="password"/>
<button class="absolute right-4 top-1/2 -translate-y-1/2 text-slate-400">
<span class="material-symbols-outlined text-xl">visibility</span>
</button>
</div>
</div>
<!-- Terms and Conditions -->
<div class="flex items-start gap-3 mt-2 px-1">
<div class="flex items-center h-6">
<input class="h-5 w-5 rounded-lg border-primary/30 bg-primary/10 text-primary focus:ring-primary transition-all cursor-pointer" id="terms" type="checkbox"/>
</div>
<label class="text-sm text-slate-500 dark:text-slate-400 leading-tight" for="terms">
                    I agree to the <a class="text-primary font-semibold hover:underline" href="#">Terms &amp; Conditions</a> and <a class="text-primary font-semibold hover:underline" href="#">Privacy Policy</a>.
                </label>
</div>
</div>
<!-- Footer / Action Area -->
<div class="mt-auto p-6 pb-10 flex flex-col gap-6">
<div class="relative group">
<div class="absolute -inset-1 bg-primary rounded-2xl blur opacity-30 group-hover:opacity-50 transition duration-300"></div>
<button class="relative w-full h-16 bg-primary text-white text-lg font-bold rounded-2xl neon-glow flex items-center justify-center gap-2 hover:scale-[1.02] active:scale-95 transition-all">
                    Create Account
                    <span class="material-symbols-outlined">arrow_forward</span>
</button>
</div>
<p class="text-center text-sm text-slate-500">
                Already have an account? 
                <a class="text-primary font-bold ml-1 hover:underline" href="#">Sign In</a>
</p>
</div>
<!-- Abstract Background Decorative Element -->
<div class="fixed top-[-10%] right-[-20%] w-[300px] h-[300px] bg-primary/20 rounded-full blur-[100px] -z-10"></div>
<div class="fixed bottom-[5%] left-[-10%] w-[200px] h-[200px] bg-primary/10 rounded-full blur-[80px] -z-10"></div>
</div>
</body></html>

Task:
Refactor UI of GuestSignUpScreen ONLY.

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