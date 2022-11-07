<template>

  <Header></Header>

  <div class="relative h-full border-2 border-gray-200 bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100">    
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
        location: "",
        likesCount: 0,
        hasLiked: false,
        imgUrl: "",
        postId: "",
        description:""
      }
    }
  },
  mounted(){

    this.$axios.get(`/posts?post=${this.$route.params.post}`).then(res => {

      this.post.username = "Toto";
      this.post.userpic = "https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png";
      this.post.location = "Université de Nantes";
      this.post.hasLiked = false;
      this.post.description = "Salut c'est un test";

      this.post.likesCount = res.data.likecount;
      this.post.imgUrl = res.data.image;
      this.post.postId = res.data.key;
            
    }).catch(err => {
      this.$router.push("/not-found");
    }); 
  }
}
</script>