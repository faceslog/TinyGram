<template>

  <Header></Header>

  <div class="relative bg-gray-200 h-full border-2 border-gray-200">    
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
        username: "Toto",
        userpic: "https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png",
        location: "Université de Nantes",
        likesCount: 1456,
        hasLiked: false,
        timeStamp: Date.now(),
        imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
        postUrl: "",
        description:"Salut c'est un test",
        comments: [
          {
            username: "Didier",
            content: "Wesh tu es trop bo"
          },
          {
            username: "Rafa",
            content: "Tu as quand des vacances"
          }
        ]
      }
    }
  },
  mounted(){
    console.log("Ceci est le param: " + this.$route.params.post);

    this.$axios.get(`/posts?post=${this.$route.params.post}`).then(res => {

      console.log(res);
      
    }).catch(err => {
        console.log(err);
    }); 

  }
}
</script>