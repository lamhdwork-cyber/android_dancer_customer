<!DOCTYPE html>

<html class="dark" lang="en"><head>
<meta charset="utf-8"/>
<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<title>Guest Profile Settings</title>
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
<div class="relative flex h-screen w-full flex-col overflow-hidden max-w-md mx-auto border-x border-primary/10 shadow-2xl">
<!-- Top Navigation Bar -->
<div class="flex items-center bg-background-light/80 dark:bg-background-dark/80 backdrop-blur-md p-4 sticky top-0 z-10 border-b border-primary/10">
<div class="flex size-10 shrink-0 items-center justify-center rounded-full bg-primary/10">
<span class="material-symbols-outlined text-primary">arrow_back_ios_new</span>
</div>
<h2 class="text-slate-900 dark:text-slate-100 text-lg font-bold flex-1 text-center pr-10">Settings</h2>
</div>
<div class="flex-1 overflow-y-auto pb-24">
<!-- Profile Header -->
<div class="flex flex-col items-center py-8 px-4">
<div class="relative group">
<div class="absolute -inset-1 bg-gradient-to-tr from-primary to-purple-600 rounded-full blur opacity-40 group-hover:opacity-60 transition duration-500"></div>
<div class="relative bg-background-dark rounded-full p-1">
<div class="bg-center bg-no-repeat aspect-square bg-cover rounded-full h-32 w-32 border-2 border-primary/20" data-alt="Professional portrait of a young man with stylish hair" style='background-image: url("https://lh3.googleusercontent.com/aida-public/AB6AXuBuEJoFnxNmDR_xG5Xr5GDB6SgM3cY7YI3n8V65xa5_rhzQ6MdUIa0CuhIuEITP1GxJEhwpERd6jO4asVM1pa0VfI4GU5qMezMT8pCxYp2mN3OAlGhSiVY-tNImKfga_c7D9fmMHMXKFmrmPIE50Jsu3r8tcixU5vtuAB3jgTWHXFPJ11jBGpSXaCYjLAVqCRItKsARjEJ7gaxxAAhaYWeoHoi3vaEl_q2UY93zJvF1oq2Jg9uirYiO4oFCLHJDWDeDf8UX6reS9iw");'></div>
</div>
<button class="absolute bottom-1 right-1 bg-primary text-white rounded-full p-2 border-2 border-background-dark flex items-center justify-center shadow-lg">
<span class="material-symbols-outlined text-sm">photo_camera</span>
</button>
</div>
<div class="mt-4 text-center">
<h1 class="text-2xl font-bold tracking-tight text-slate-900 dark:text-slate-100">Alex Rivera</h1>
<p class="text-primary font-medium text-sm">Elite Member • New York</p>
</div>
</div>
<!-- Profile Actions -->
<div class="px-4 space-y-3">
<div class="bg-slate-100/50 dark:bg-primary/5 rounded-xl border border-primary/10 p-1">
<!-- Edit Profile -->
<div class="flex items-center gap-4 p-4 hover:bg-primary/10 rounded-lg transition-colors cursor-pointer">
<div class="flex items-center justify-center rounded-lg bg-primary/20 shrink-0 size-10">
<span class="material-symbols-outlined text-primary">person</span>
</div>
<div class="flex-1">
<p class="text-slate-900 dark:text-slate-100 font-medium">Edit Profile</p>
<p class="text-slate-500 dark:text-primary/60 text-xs">Update your personal details</p>
</div>
<span class="material-symbols-outlined text-slate-400 dark:text-primary/40">chevron_right</span>
</div>
<div class="h-px bg-primary/10 mx-4"></div>
<!-- Notifications -->
<div class="flex items-center gap-4 p-4 hover:bg-primary/10 rounded-lg transition-colors cursor-pointer">
<div class="flex items-center justify-center rounded-lg bg-primary/20 shrink-0 size-10">
<span class="material-symbols-outlined text-primary">notifications</span>
</div>
<div class="flex-1">
<p class="text-slate-900 dark:text-slate-100 font-medium">Notification Settings</p>
<p class="text-slate-500 dark:text-primary/60 text-xs">Manage alerts and booking updates</p>
</div>
<span class="material-symbols-outlined text-slate-400 dark:text-primary/40">chevron_right</span>
</div>
<div class="h-px bg-primary/10 mx-4"></div>
<!-- Language -->
<div class="flex items-center gap-4 p-4 hover:bg-primary/10 rounded-lg transition-colors cursor-pointer">
<div class="flex items-center justify-center rounded-lg bg-primary/20 shrink-0 size-10">
<span class="material-symbols-outlined text-primary">language</span>
</div>
<div class="flex-1">
<p class="text-slate-900 dark:text-slate-100 font-medium">Language</p>
<p class="text-slate-500 dark:text-primary/60 text-xs">English (US)</p>
</div>
<span class="material-symbols-outlined text-slate-400 dark:text-primary/40">chevron_right</span>
</div>
</div>
<!-- Danger Zone Section -->
<div class="pt-8 pb-4">
<h3 class="text-slate-500 dark:text-primary/60 text-xs font-bold uppercase tracking-widest px-4 mb-3">Danger Zone</h3>
<div class="bg-red-500/5 dark:bg-red-500/10 rounded-xl border border-red-500/20 p-2">
<button class="w-full flex items-center gap-4 p-3 rounded-lg hover:bg-red-500/20 transition-colors">
<div class="flex items-center justify-center rounded-lg bg-red-500/20 shrink-0 size-10">
<span class="material-symbols-outlined text-red-500">delete_forever</span>
</div>
<div class="text-left">
<p class="text-red-500 font-bold">Delete Account</p>
<p class="text-red-500/60 text-xs">Permanent deletion of your data</p>
</div>
</button>
</div>
</div>
</div>
</div>
<!-- Bottom Navigation Bar (Persistent Style) -->
<div class="fixed bottom-0 left-0 right-0 max-w-md mx-auto flex gap-2 border-t border-primary/10 bg-background-light/90 dark:bg-background-dark/90 backdrop-blur-xl px-4 pb-6 pt-2 z-20">
<a class="flex flex-1 flex-col items-center justify-end gap-1 text-slate-400 dark:text-primary/40" href="#">
<span class="material-symbols-outlined">home</span>
<p class="text-[10px] font-medium leading-normal tracking-[0.015em]">Home</p>
</a>
<a class="flex flex-1 flex-col items-center justify-end gap-1 text-slate-400 dark:text-primary/40" href="#">
<span class="material-symbols-outlined">calendar_month</span>
<p class="text-[10px] font-medium leading-normal tracking-[0.015em]">Bookings</p>
</a>
<a class="flex flex-1 flex-col items-center justify-end gap-1 text-slate-400 dark:text-primary/40" href="#">
<span class="material-symbols-outlined">explore</span>
<p class="text-[10px] font-medium leading-normal tracking-[0.015em]">Discover</p>
</a>
<a class="flex flex-1 flex-col items-center justify-end gap-1 text-primary" href="#">
<span class="material-symbols-outlined fill-1">person</span>
<p class="text-[10px] font-bold leading-normal tracking-[0.015em]">Profile</p>
</a>
</div>
</div>
</body></html>

1. Sửa lại UI màn hình AccountScreen theo html trên, làm mới luôn, bỏ ui cũ

2. Thêm nút Logout dưới Laguage

3. Vẫn giữ nguyên logic cũ như: load infor user, xử lý các button khi nhấn