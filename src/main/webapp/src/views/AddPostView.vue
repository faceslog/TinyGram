<template>
  
  <Header></Header>
    
  <div class="w-full overflow-scroll h-screen bg-gradient-to-r from-blue-100 via-purple-100 to-pink-100 p-4 flex items-center justify-center" >
    <div class="bg-white py-6 px-10 sm:max-w-md w-full ">
      <div class="sm:text-3xl text-2xl font-semibold text-center text-sky-600  mb-12">Post your Picture (Dev)</div>

      <div class="">
        <div>
          <input type="text" class="focus:outline-none border-b w-full pb-2 border-sky-400 placeholder-gray-500"  placeholder="Url"/>
        </div>

        <div>
          <input type="text" class="focus:outline-none border-b w-full pb-2 border-sky-400 placeholder-gray-500 my-8"  placeholder="Location (Optional)" maxlength="80"/>
        </div>

        <div>
          <input type="text" class="focus:outline-none border-b w-full pb-2 border-sky-400 placeholder-gray-500 mb-8"  placeholder="Description (Optional)" maxlength="80"/>
        </div>

        <div class="flex">
          <input type="checkbox" class="border-sky-400" value=""/>
          <div class="px-3 text-gray-500">I accept terms & conditions</div>
        </div>

        <div class="flex justify-center my-6">
          <button class="rounded-full p-3 w-full sm:w-56   bg-gradient-to-r from-sky-600  to-teal-300 text-white text-lg font-semibold">Publish</button>
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
  }
}
</script>