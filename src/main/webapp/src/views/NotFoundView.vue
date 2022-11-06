<template>
    <div class="h-screen w-full flex flex-col justify-center items-center bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100">
        <h1 class="text-9xl font-extrabold text-black tracking-widest">404</h1>
        <div class="text-black text-xl font-extrabold">
            Oops, Page Not Found...
        </div>
        <div class="mt-5">
            <router-link to="/" class="relative inline-block text-sm font-medium text-black group focus:outline-none focus:ring">
                <span class="relative block px-8 py-3 bg-white bg-opacity-50 border border-current rounded-md">
                    Go Home
                </span>
            </router-link>
        </div>
    </div>
</template>