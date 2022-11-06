<template>

  <Header></Header>

  <div class="bg-gray-100 h-auto lg:px-48">
    
    <!-- Profile header -->
    <div class="flex">
      <div class="p-4 text-center ">
        <div class="relative text-center mt-8">
          <button class="rounded-full"  id="user-menu">
            <img class="lg:h-40 lg:w-40 h-20 w-20 rounded-full" :src="user.userpic"   alt="profile pic"/>
          </button>
        </div>
      </div>
      
      <div class="w-auto md:px-4 px-1 py-6 text-center">
        <div class="text-left pl-4 pt-3">
          
          <span class="md:text-xl text-lg font-bold">{{ user.username }}</span>
        </div>

        <div class="md:text-base text-xs text-left pl-4 pt-3">
          <span class="font-semibold mr-2">
            <b>{{ posts.length }}</b> Posts
          </span>
          <span class="font-semibold mr-2">
            <b>{{ user.followersCount }}</b> Followers
          </span>
          <span class="font-semibold">
            <b>{{ user.followingCount }}</b> Following
          </span>
        </div>

        <div class="text-base text-left pl-4 pt-3">
          <p class="font-medium text-gray-700 mr-2">{{ user.bio }}</p>
        </div>

        <div class="flex pt-4 pl-4 text-black text-sm">
          <button class="bg-gray-200 hover:bg-blue-500 font-semibold hover:text-white py-1 px-6 border border-gray-300 hover:border-transparent rounded mr-2">Follow</button>
          <button class="bg-gray-200 hover:bg-blue-500 font-semibold hover:text-white py-1 px-6 border border-gray-300 hover:border-transparent rounded">Message</button>
        </div>
      </div>
    </div>

    <hr class="border-gray-500 mt-6" />

    <!-- Profile feed -->
    <div class="flex pt-4">
      <div class="mx-auto py-2">
        <div class="lg:grid-cols-3 md:grid-cols-2 grid-cols-1 grid gap-4 cursor-pointer">
          <div v-for="post in posts" :key="post">
            <img class="h-80 w-80 hover:opacity-80" :src="post.imgUrl"/>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>


<script>
import Header from '@/components/Header.vue'

export default {
  components: {
    Header
  },
  data()
  {
    return {
      user: {
        username: "Toto",
        userpic: "https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png",
        followingCount: 144,
        followersCount: 126,
        bio: "Coucou c'est une bio test"
      },
      posts: [
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        },
        {
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: ""
        }
      ]
    }
  }
}
</script>