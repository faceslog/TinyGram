<template>

  <Header></Header>

  <div class="w-full min-h-screen h-full bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100 p-4 flex items-center justify-center" >
    <div class="bg-white py-6 px-10 sm:max-w-md w-full ">
      <div class="sm:text-3xl text-2xl font-semibold text-center text-sky-600  mb-12">Post your Picture (Dev)</div>

      <div class="">
        <div>
          <input v-model="url" type="text" class="focus:outline-none border-b w-full pb-2 border-sky-400 placeholder-gray-500 mb-4"  placeholder="Url"/>
        </div>

        <div>
          <input v-model="description" type="text" class="focus:outline-none border-b w-full pb-2 border-sky-400 placeholder-gray-500 mb-8"  placeholder="Description (Optional)" maxlength="80"/>
        </div>

        <div class="flex">
          <input type="checkbox" class="border-sky-400" v-model="hasAgreed"/>
          <div class="px-3 text-gray-500">I accept terms & conditions</div>
        </div>

        <div class="flex justify-center my-6">
          <button @click="post" class="rounded-full p-3 w-full sm:w-56 bg-gradient-to-r from-sky-600  to-teal-300 text-white text-lg font-semibold">Publish</button>
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
      url: '',
      description: '',
      hasAgreed: false,
      isLoading: false
    }
  },
  methods: {
    verifyUrl: function() {

      const regExp = /(ftp|http|https):\/\/(\w+:{0,1}\w*@)?(\S+)(:[0-9]+)?(\/|\/([\w#!:.?+=&%@!\-\/]))?\.(png|jpg|jpeg)/;

      return regExp.test(this.url);
    },
    post: function() {

      if(this.isLoading) return;

      if(!this.verifyUrl()) {
        this.$swal('Invalid Image', 'Must point to a .png .jpg .jpeg', 'error');
        return;
      }

      if(!this.hasAgreed) {
        this.$swal('Oops...', 'You must agree to the terms', 'error');
        return;
      }

      // Toggle loading to true to prevent spamming the post button
      this.isLoading = true;

      let data = {
        image: this.url,
        description: this.description
      }

      let token = this.$store.getters.getToken;
      this.$axios.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      this.$axios.post("/post", data).then(res => {

        let postId = res.data.result.key;
        this.$router.push(`/posts/${postId}`);

      }).catch(err => {

        console.log(err);
        this.isLoading = false;
        this.$swal('Failed to upload', 'Oops something went wrong', 'error');
      });
    }
  }
}
</script>