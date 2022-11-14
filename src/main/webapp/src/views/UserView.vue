<template>

  <Header></Header>

  <div class="bg-gray-100 h-screen lg:px-48">
    
    <!-- Profile header -->
    <div class="flex">
      <div class="p-4 text-center ">
        <div class="relative text-center mt-8">
          <button class="rounded-full"  id="user-menu">
            <img class="lg:h-40 lg:w-40 h-20 w-20 rounded-full" :src="user.image" alt="profile pic"/>
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
          <div v-if="!isItself">
            <button v-if="user.isFollowed" class="bg-gray-200 hover:bg-blue-500 font-semibold hover:text-white py-1 px-6 border border-gray-300 hover:border-transparent rounded mr-2">Unfollow</button>
            <button v-else class="bg-gray-200 hover:bg-blue-500 font-semibold hover:text-white py-1 px-6 border border-gray-300 hover:border-transparent rounded mr-2">Follow</button>
            <button class="bg-gray-200 hover:bg-blue-500 font-semibold hover:text-white py-1 px-6 border border-gray-300 hover:border-transparent rounded">Message</button>
          </div>
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
  data()  {
    return {
      user: {
        username: "Toto",
        image: "",
        followingCount: 0,
        followersCount: 0,
        isFollowed: false,
        key: "azertyuiop",
        bio: "Welcome to my profile !"        
      },
      posts: [
        {
          imgUrl: "https://cdn0.matrimonio.com.co/usr/2/1/0/2/cfb_315540.jpg",
          postUrl: ""
        }
      ]
    }
  },
  async mounted() {
    let token = this.$store.getters.getToken;
    this.$axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    const userId = this.$route.params.user;

    try {
      await this.loadUserInfo(userId);
    } catch(err) {
      this.$router.push("/not-found");
    }
  },
  methods: {
    loadUserInfo: async function(userId) {

      let res = await this.$axios.get(`/user/${userId}`);

      this.user.username = res.data.result.name;
      this.user.followersCount = +res.data.result.followercount;
      this.user.followingCount = +res.data.result.followingcount;
      this.user.image = res.data.result.image;
      this.user.isFollowed = res.data.result.followed;
      this.user.key = res.data.result.key;
    },
    follow: async function() {

      this.user.followersCount++;
      this.user.isFollowed = true;

      await this.$axios.put(`/user/${this.user.key}`, { followed: true });      
    },
    unfollow: async function() {

      this.user.followersCount--;
      this.user.isFollowed = false;

      await this.$axios.put(`/user/${this.user.key}`, { followed: false });
    }
  },
  computed: {
    isItself: function() {
      return this.$store.getters.getUserId == this.user.key;
    }  
  }
}
</script>