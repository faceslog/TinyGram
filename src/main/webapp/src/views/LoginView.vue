<template>
  <div class="h-screen w-screen flex flex-wrap items-center justify-center p-3">
    <div class="flex items-center">
      <div class="hidden md:block">
        <img src="/images/login-side-img.png" class="max-h-[500px]" alt="login"/>
      </div>

      <div class="flex flex-col flex-shrink-0 w-[350px]">
        <div class="flex flex-col items-center justify-center rounded w-full border-[1px] border-gray-300 bg-white p-6">
          <div class="w-full">
            <img src="/images/tinygram-login.png" class="h-14 mt-4 mx-auto mb-8" alt="tinygram"/>
          </div>

          <div class="w-full px-5">
            <form>
              <div class="w-full">
                <div class="w-full">
                  <div class="w-full mb-3">
                    <input placeholder="Phone number, username, or email"  name="username"  type="text" class="text-xs p-2 border-[1px] rounded bg-gray-200/10 w-full border-gray-300" disabled/>
                  </div>
                </div>

                <div>
                  <div class="relative">
                    <input type="password" class="text-xs p-2 border-[1px] rounded bg-gray-200/10 w-full border-gray-300" placeholder="Password" disabled/>
                  </div>
                </div>

                <div class="w-full mt-2">
                  <button class="w-full bg-blue-300 text-sm text-white font-semibold p-1 rounded-sm" type="submit" disabled>Log In</button>
                </div>

                <hr class="flex gap-2 items-center my-3 border-gray-400"/>

                <div class="mt-4">
                  <div class="text-[#4267B2] flex items-center justify-center w-full">
                    <GoogleLogin :callback="login"/>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>

        <div class="flex flex-col items-center justify-center rounded w-full border-[1px] border-gray-300 mt-4 bg-white p-6">
          <div class="text-sm">
            Don't have an account ?
            <span class="text-blue-500 font-semibold">Sign up</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  methods: {
    login: function(response) {

      this.$store.dispatch('setToken', response.credential);
      let decodedData = this.$store.getters.getDecodedToken;

      let data = {
        name: decodedData.name,
        image: decodedData.picture
      }

      this.$axios.defaults.headers.common['Authorization'] = `Bearer ${response.credential}`;

      this.$axios.post("/user", data).then(res => {

        this.$swal('Welcome !', 'Loading Data ...', 'success');
        this.$store.dispatch('setUser', res.data.result);
        this.$router.push("/");

      }).catch(err => {

        console.log(err);
        this.$swal('Login Error', 'Oops Login Failed ...', 'error');
      });
    }
  }
}
</script>