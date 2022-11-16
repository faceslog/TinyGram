<template>

  <Header></Header>

  <div class="relative min-h-screen h-full border-2 border-gray-200 bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100">
    <div class="pt-6 h-full">
      <div class="max-w-4xl mx-auto">
        <!-- FEED HERE -->
        <div class="mx-auto w-4/5 mb-4">
          <PostCard :post="post"/>
        </div>
      </div>
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
  data() {
    return {
      post: {
        username: "",
        userpic: "",
        userkey: "",
        likesCount: 0,
        hasLiked: false,
        imgUrl: "",
        postId: "",
        description:""
      },
    }
  },
  async mounted(){

    let token = this.$store.getters.getToken;
    let postId = this.$route.params.post;

    this.$axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

    try {

      let res = await this.$axios.get(`/post/${postId}`);

      let user = await this.$axios.get(res.data._links.owner);

      this.post.username = user.data.result.name;
      this.post.userpic = user.data.result.image;
      this.post.userkey = user.data.result.key;

      this.post.likesCount = +res.data.result.likecount;
      this.post.hasLiked = res.data.result.liked;
      this.post.imgUrl = res.data.result.image;
      this.post.postId = res.data.result.key;
      this.post.description = res.data.result.description;

    } catch(err)  {
      this.$router.push("/not-found");
    }
  }
}
</script>