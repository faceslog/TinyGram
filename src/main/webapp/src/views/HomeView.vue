<template>

  <Header></Header>

  <div class="relative bg-gray-200 h-full border-2 border-gray-200">    
    <div class="pt-6 h-full">

      <div class="max-w-4xl mx-auto">
        <!-- FEED HERE -->
        <div v-for="post in posts" :key="post" class="mx-auto w-4/5 mb-4">
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
  data()
  {
    return {
      posts: [
        {
          username: "Toto",
          userpic: "https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png",
          location: "Université de Nantes",
          likesCount: 1456,
          hasLiked: false,
          timeStamp: Date.now(),
          imgUrl: "https://images.unsplash.com/photo-1667144842815-1c740881a905?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1587&q=80",
          postUrl: "",
          description:"Salut c'est un test"
        },
        {
          username: "Gabriel",
          userpic: "https://i.pinimg.com/originals/c2/4a/af/c24aaf49f7dc286dd0f7020a5bb820ac.png",
          location: "Université de Nantes",
          likesCount: 156,
          hasLiked: true,
          timeStamp: Date.now(),
          imgUrl: "https://images.unsplash.com/photo-1667206742636-2f7ae349a3f4?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=387&q=80",
          postUrl: "",
          description:"Salut c'est un test 2"
        }
      ]
    }
  }
}
</script>