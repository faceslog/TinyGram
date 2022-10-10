<template>
  <div class="h-screen w-screen flex flex-wrap items-center justify-center p-3">
    <div class="flex items-center">
      <div class="hidden md:block">
        <img src="/images/login-side-img.png" class="max-h-[500px]" alt="login"/>
      </div>

      <div class="flex flex-col flex-shrink-0 w-[350px]">
        <div class="flex flex-col items-center justify-center rounded w-full border-[1px] border-gray-300 bg-white p-6">
          <div class="w-full">
            <img src="/images/instagram-login.png" class="h-14 mt-4 mx-auto my-3" alt="instagram"/>
          </div>
          
          <div class="w-full px-5">
            <form>
              <div class="w-full">
                <div class="w-full">
                  <div class="w-full mb-3">
                    <input placeholder="Phone number, username, or email"  name="username"  type="text" class="text-xs p-2 border-[1px] rounded bg-gray-200/10 w-full border-gray-300"/>
                  </div>
                </div>
                  
                <div>
                  <div class="relative">
                    <input type="password" class="text-xs p-2 border-[1px] rounded bg-gray-200/10 w-full border-gray-300" placeholder="Password"/>
                  </div>
                </div>

                <div class="w-full mt-2">
                  <button class="w-full bg-blue-300 text-sm text-white font-semibold p-1 rounded-sm" type="submit" disabled>Log In</button>
                </div>

                <div class="flex gap-2 items-center my-3">
                  <div class="border-b-[1px] bg-transparent border-gray-400 h-0 w-full"></div>
                  <div class="uppercase text-gray-500 font-semibold text-base"></div>
                  <div class="border-b-[1px] bg-transparent border-gray-400 h-0 w-full"></div>
                </div>

                <div class="mt-4">
                  <button  class="text-[#4267B2] flex items-center justify-center w-full" type="button">
                    <svg xmlns="http://www.w3.org/2000/svg" width="18" height="18" viewBox="0 0 18 18" aria-hidden="true"><title>Google</title><g fill="none" fill-rule="evenodd"><path fill="#4285F4" d="M17.64 9.2045c0-.6381-.0573-1.2518-.1636-1.8409H9v3.4814h4.8436c-.2086 1.125-.8427 2.0782-1.7959 2.7164v2.2581h2.9087c1.7018-1.5668 2.6836-3.874 2.6836-6.615z"></path><path fill="#34A853" d="M9 18c2.43 0 4.4673-.806 5.9564-2.1805l-2.9087-2.2581c-.8059.54-1.8368.859-3.0477.859-2.344 0-4.3282-1.5831-5.036-3.7104H.9574v2.3318C2.4382 15.9832 5.4818 18 9 18z"></path><path fill="#FBBC05" d="M3.964 10.71c-.18-.54-.2822-1.1168-.2822-1.71s.1023-1.17.2823-1.71V4.9582H.9573A8.9965 8.9965 0 0 0 0 9c0 1.4523.3477 2.8268.9573 4.0418L3.964 10.71z"></path><path fill="#EA4335" d="M9 3.5795c1.3214 0 2.5077.4541 3.4405 1.346l2.5813-2.5814C13.4632.8918 11.426 0 9 0 5.4818 0 2.4382 2.0168.9573 4.9582L3.964 7.29C4.6718 5.1627 6.6559 3.5795 9 3.5795z"></path></g></svg>
                    <span class="text-xs font-semibold ml-1">
                      Log in with Google
                    </span>
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>

        <div class="flex flex-col items-center justify-center rounded w-full border-[1px] border-gray-300 mt-4 bg-white p-6">
          <div class="text-sm">
            Don't have an account ?
            <span class="text-blue-500 font-semibold">Sign up</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
