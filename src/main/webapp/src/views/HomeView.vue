<template>

  <div class="bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100 min-h-screen h-full">

    <div v-on:scroll="onScroll" class="h-screen overflow-x-auto">

      <Header></Header>

      <div class="mt-6 max-w-4xl mx-auto">
        <!-- FEED HERE -->
        <div v-for="post in postsList" :key="post" class="mx-auto w-4/5 mb-4">
          <PostCard :post="post"/>
        </div>
      </div>

      <div v-if="nextFeedFollowedUrl || nextFeedGlobalUrl" class="text-lg text-center text-gray-800 font-bold mb-3">Loading ...</div>

    </div>
  </div>
</template>

<script>
import Header from '@/components/Header.vue'
import PostCard from '@/components/PostCard.vue';

export default {
  components: {
    Header,
    PostCard
  },
  data()  {
    return {
      postsList: [],
      nextFeedGlobalUrl: "",
      nextFeedFollowedUrl: "",
      isLoadingFeed: false
    }
  },
  async mounted()  {

    let token = this.$store.getters.getToken;
    this.$axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    this.nextFeedFollowedUrl = "/feed/followed";
    this.nextFeedGlobalUrl = "/feed/global";

    await this.loadFeed();
  },
  methods: {
    loadFeed: async function() {

      if(this.isLoadingFeed)
        return;

      if(!this.nextFeedFollowedUrl && !this.nextFeedGlobalUrl)
        return;

      this.isLoadingFeed = true;

      try {

        let [x, y] = await Promise.all([ this.loadFeedFollowed(), this.loadFeedGlobal()]);

        let promises = [...x, ...y].map(post => this.loadPost(post._links.self));

        this.postsList = [...this.postsList, ...await Promise.all(promises)];
        this.postsList = this.postsList.filter((s => a => !s.has(a.postId) && s.add(a.postId))(new Set));

      } catch(err) {
        console.log(err);
       // this.$router.push("/not-found");
      }

      this.isLoadingFeed = false;
    },
    loadFeedFollowed: async function() {

      if(!this.nextFeedFollowedUrl) return [];

      let res = await this.$axios.get(this.nextFeedFollowedUrl);

      this.nextFeedFollowedUrl = res.data._links?.next;

      return res.data.result;
    },
    loadFeedGlobal: async function() {

      if(!this.nextFeedGlobalUrl) return [];

      let res = await this.$axios.get(this.nextFeedGlobalUrl);

      this.nextFeedGlobalUrl = res.data._links?.next;

      return res.data.result;
    },
    loadPost: async function(postUrl) {

      let res = await this.$axios.get(postUrl);
      let user = await this.$axios.get(res.data._links.owner);

      let post = {};

      post.username = user.data.result.name;
      post.userpic = user.data.result.image;
      post.userkey = user.data.result.key;

      post.likesCount = +res.data.result.likecount;
      post.hasLiked = res.data.result.liked;
      post.imgUrl = res.data.result.image;
      post.postId = res.data.result.key;
      post.description = res.data.result.description;

      return post;
    },
    onScroll: function({ target: { scrollTop, clientHeight, scrollHeight }}) {

      if (scrollTop + clientHeight >= scrollHeight)
        this.loadFeed();
    }
  }
}
</script>