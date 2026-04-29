<!DOCTYPE html>

<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Manager Staff Login</title>
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
<style>
    body {
      min-height: max(884px, 100dvh);
    }
  </style>
  </head>
<body class="bg-background-light dark:bg-background-dark font-display text-slate-900 dark:text-slate-100 antialiased">
<div class="relative flex min-h-screen w-full flex-col overflow-x-hidden">
<!-- Top App Bar -->
<div class="flex items-center p-4 justify-between">
<div class="flex size-10 shrink-0 items-center justify-center rounded-full hover:bg-white/10 transition-colors cursor-pointer">
<span class="material-symbols-outlined text-slate-100">arrow_back_ios_new</span>
</div>
<div class="flex-1 text-center pr-10">
<span class="text-xs font-bold uppercase tracking-widest text-primary/80">Nightlife Portal</span>
</div>
</div>
<!-- Branding / Hero Section -->
<div class="px-6 pt-8 pb-10 flex flex-col items-center">
<div class="size-20 bg-gradient-to-tr from-primary to-purple-600 rounded-2xl flex items-center justify-center mb-6 shadow-[0_0_30px_rgba(244,37,244,0.3)]">
<span class="material-symbols-outlined text-4xl text-white">nightlight</span>
</div>
<h1 class="text-3xl font-bold tracking-tight text-white mb-2">Manager Access</h1>
<p class="text-slate-400 text-sm">Secure terminal for authorized staff only</p>
</div>
<!-- Login Form Container -->
<div class="flex flex-col gap-6 px-6">
<!-- Email Field -->
<div class="flex flex-col gap-2">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-400 ml-1">Work Email</label>
<div class="relative group">
<div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
<span class="material-symbols-outlined text-slate-500 group-focus-within:text-primary transition-colors">mail</span>
</div>
<input class="w-full pl-12 pr-4 py-4 bg-white/5 border border-white/10 rounded-xl focus:border-primary focus:ring-1 focus:ring-primary focus:bg-white/10 text-white placeholder:text-slate-600 outline-none transition-all duration-300" placeholder="manager@club.com" type="email"/>
</div>
</div>
<!-- Password Field -->
<div class="flex flex-col gap-2">
<label class="text-xs font-semibold uppercase tracking-wider text-slate-400 ml-1">Security Key</label>
<div class="relative group">
<div class="absolute inset-y-0 left-0 pl-4 flex items-center pointer-events-none">
<span class="material-symbols-outlined text-slate-500 group-focus-within:text-primary transition-colors">lock</span>
</div>
<input class="w-full pl-12 pr-12 py-4 bg-white/5 border border-white/10 rounded-xl focus:border-primary focus:ring-1 focus:ring-primary focus:bg-white/10 text-white placeholder:text-slate-600 outline-none transition-all duration-300" placeholder="••••••••" type="password"/>
<div class="absolute inset-y-0 right-0 pr-4 flex items-center cursor-pointer text-slate-500 hover:text-white">
<span class="material-symbols-outlined">visibility</span>
</div>
</div>
</div>
<!-- Login Button -->
<div class="mt-4">
<button class="w-full bg-primary hover:bg-primary/90 text-white font-bold py-4 rounded-xl shadow-[0_8px_20px_rgba(244,37,244,0.3)] active:scale-[0.98] transition-all flex items-center justify-center gap-2">
                    Login as Staff
                    <span class="material-symbols-outlined text-sm">login</span>
</button>
</div>
<!-- Quick Help / Recovery -->
<div class="flex flex-col items-center gap-4 mt-4">
<a class="text-sm font-medium text-slate-400 hover:text-primary transition-colors" href="#">Forgot credentials?</a>
<div class="h-px w-full bg-white/5 my-2"></div>
<div class="flex items-center gap-2 text-xs text-slate-500">
<span class="material-symbols-outlined text-xs">shield</span>
<span>Encrypted Connection Active</span>
</div>
</div>
</div>
<!-- Footer Visual -->
<div class="mt-auto pt-10 pb-8 px-6">
<div class="h-[200px] w-full rounded-xl overflow-hidden relative opacity-40 grayscale hover:grayscale-0 transition-all duration-700">
<div class="absolute inset-0 bg-gradient-to-t from-background-dark via-transparent to-transparent z-10"></div>
<div class="absolute inset-0 bg-primary/10 z-0"></div>
<img alt="Club environment" class="w-full h-full object-cover" data-alt="Blurred neon lit nightlife club atmosphere with crowds" src="https://lh3.googleusercontent.com/aida-public/AB6AXuCkBExaPe7ZKDCRQ5KEA3KL5OoVUXFOpilzUHTzAaDHUBtO0JIzvlRR_zTWdit-2TuGDf9fj2ILvyQW0XOhaqJp1smnUy0sJZbWV9iNBgdo64G7VKsBFZdlrQh8XGKLlRmjXfXZTyeyVGC5LTaLztnnCq7XnMhfwvZ-L8-NtJOutnlP1H1E2vEAvD0CeKmsDeNfVvQc3eHaLo5e1wO6IU4Mk0WFUzgkHJHeHR4MJ2Dcy3LCtT9vtkkRPHNKuku2MY2HKntQaP_I6QA"/>
</div>
</div>
</div>
</body></html>

Task:
Create manage signin screen

UI Requirements:
- Convert HTML to Jetpack Compose (Material3)
- Split into small reusable components
- Follow clean composable structure

Steps:
1. Analyze UI
2. Map to ViewModel + State
3. Generate skeleton
4. Generate Compose UI
5. Implement logic

Follow rules:
.cursor/rules/architecture.md
.cursor/rules/compose.md
.cursor/rules/naming.md
.cursor/rules/network.md
.cursor/rules/structure.md
.cursor/rules/i18n.md