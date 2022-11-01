<template>
    
    <nav class="border-b py-3 sticky top-0 bg-white z-50">
      <div class="container mx-auto flex justify-between">

        <div class="px-2 hidden md:block">
          <h1 class="font-bold lg:text-2xl text-lg">TinyGram</h1>
        </div>

        <div class="md:w-3/12 w-5/12 md:mx-0 mx-auto">
          <input type="text" placeholder="Search" class="px-2 w-full h-full border text-sm bg-gray-200 rounded-sm" />
        </div>

        <div class="icons flex justify-center p-2">

          <router-link to="/" class="home-icon mr-6 cursor-pointer">
            <svg class="lg:h-6 lg:w-6 h-4 w-4" aria-label="Home" fill="#262626" viewBox="0 0 48 48">
              <path d="M45.5 48H30.1c-.8 0-1.5-.7-1.5-1.5V34.2c0-2.6-2.1-4.6-4.6-4.6s-4.6 2.1-4.6 4.6v12.3c0 .8-.7 1.5-1.5 1.5H2.5c-.8 0-1.5-.7-1.5-1.5V23c0-.4.2-.8.4-1.1L22.9.4c.6-.6 1.6-.6 2.1 0l21.5 21.5c.3.3.4.7.4 1.1v23.5c.1.8-.6 1.5-1.4 1.5z"></path>
            </svg>
          </router-link>

          <div class="mr-6">
            <svg class="lg:h-6 lg:w-6 h-4 w-4" aria-label="Direct" fill="#262626" viewBox="0 0 48 48">
              <path d="M47.8 3.8c-.3-.5-.8-.8-1.3-.8h-45C.9 3.1.3 3.5.1 4S0 5.2.4 5.7l15.9 15.6 5.5 22.6c.1.6.6 1 1.2 1.1h.2c.5 0 1-.3 1.3-.7l23.2-39c.4-.4.4-1 .1-1.5zM5.2 6.1h35.5L18 18.7 5.2 6.1zm18.7 33.6l-4.4-18.4L42.4 8.6 23.9 39.7z"></path>
            </svg>
          </div>

          <div class="mr-6">
            <svg class="lg:h-6 lg:w-6 h-4 w-4" aria-label="Activity Feed" fill="#262626" viewBox="0 0 48 48">
              <path d="M34.6 6.1c5.7 0 10.4 5.2 10.4 11.5 0 6.8-5.9 11-11.5 16S25 41.3 24 41.9c-1.1-.7-4.7-4-9.5-8.3-5.7-5-11.5-9.2-11.5-16C3 11.3 7.7 6.1 13.4 6.1c4.2 0 6.5 2 8.1 4.3 1.9 2.6 2.2 3.9 2.5 3.9.3 0 .6-1.3 2.5-3.9 1.6-2.3 3.9-4.3 8.1-4.3m0-3c-4.5 0-7.9 1.8-10.6 5.6-2.7-3.7-6.1-5.5-10.6-5.5C6 3.1 0 9.6 0 17.6c0 7.3 5.4 12 10.6 16.5.6.5 1.3 1.1 1.9 1.7l2.3 2c4.4 3.9 6.6 5.9 7.6 6.5.5.3 1.1.5 1.6.5.6 0 1.1-.2 1.6-.5 1-.6 2.8-2.2 7.8-6.8l2-1.8c.7-.6 1.3-1.2 2-1.7C42.7 29.6 48 25 48 17.6c0-8-6-14.5-13.4-14.5z"></path>
            </svg>
          </div>

          <div class="profile-icon cursor-pointer">
            <img alt="Profile picture" class="lg:h-6 lg:w-6 h-4 w-4" draggable="false" src="https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png">
          </div>
        </div>
      </div>
    </nav>
</template>


<script>
export default {
  data()
  {
    return {
      userId: null
    }
  }
}
</script>