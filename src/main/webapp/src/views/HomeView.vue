<template>

  <Header></Header>

  <div class="relative bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100 h-screen border border-gray-200">    
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
      posts: []
    }
  }
}
</script>