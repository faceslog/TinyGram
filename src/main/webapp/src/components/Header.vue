<template>
  <nav class="border-b py-3 sticky top-0 bg-white z-50">
    <div v-if="user" class="container mx-auto flex justify-between">

      <div class="px-2 hidden md:block">
        <h1 class="lg:text-2xl text-base">Greetings, {{ user.name }} 😄</h1>
      </div>

      <div class="md:w-3/12 w-5/12 md:mx-0 mx-auto">
        <input type="text" placeholder="Search" class="px-2 w-full h-full border text-sm bg-gray-200 rounded-sm" disabled/>
      </div>

      <div class="icons flex justify-center p-2">

        <router-link to="/" class="home-icon mr-6 cursor-pointer">
          <svg class="lg:h-6 lg:w-6 h-4 w-4" aria-label="Home" fill="#262626" viewBox="0 0 48 48">
            <path d="M45.5 48H30.1c-.8 0-1.5-.7-1.5-1.5V34.2c0-2.6-2.1-4.6-4.6-4.6s-4.6 2.1-4.6 4.6v12.3c0 .8-.7 1.5-1.5 1.5H2.5c-.8 0-1.5-.7-1.5-1.5V23c0-.4.2-.8.4-1.1L22.9.4c.6-.6 1.6-.6 2.1 0l21.5 21.5c.3.3.4.7.4 1.1v23.5c.1.8-.6 1.5-1.4 1.5z"></path>
          </svg>
        </router-link>

        <router-link to="/addpost" class="mr-6">
          <svg class="lg:h-6 lg:w-6 h-4 w-4" x="0px" y="0px" viewBox="0 0 384.97 384.97">
            <g id="Upload">
              <path d="M372.939,264.641c-6.641,0-12.03,5.39-12.03,12.03v84.212H24.061v-84.212c0-6.641-5.39-12.03-12.03-12.03 S0,270.031,0,276.671v96.242c0,6.641,5.39,12.03,12.03,12.03h360.909c6.641,0,12.03-5.39,12.03-12.03v-96.242 C384.97,270.019,379.58,264.641,372.939,264.641z"/>
              <path d="M117.067,103.507l63.46-62.558v235.71c0,6.641,5.438,12.03,12.151,12.03c6.713,0,12.151-5.39,12.151-12.03V40.95 l63.46,62.558c4.74,4.704,12.439,4.704,17.179,0c4.74-4.704,4.752-12.319,0-17.011l-84.2-82.997 c-4.692-4.656-12.584-4.608-17.191,0L99.888,86.496c-4.752,4.704-4.74,12.319,0,17.011 C104.628,108.211,112.327,108.211,117.067,103.507z"/>
            </g>
          </svg>
        </router-link>

        <router-link :to="'/users/' + id" class="profile-icon cursor-pointer">
          <img alt="Profile picture" class="lg:h-6 lg:w-6 h-4 w-4 rounded-full" draggable="false" :src="user.image">
        </router-link>
      </div>
    </div>
  </nav>
</template>

<script>
export default {
  data()
  {
    return {
      user: null,
      id: null
    }
  },
  mounted()  {
    this.user = this.$store.getters.getUser;
    this.id = this.$store.getters.getUserId;
  }
}
</script>