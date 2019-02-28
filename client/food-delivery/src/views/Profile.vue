<template>
    <div class="profile-container">
        <h1 class="text-center font-weight-bold m-t-20">Your profile</h1>
        <div class="m-t-30">
            <div class="row">

                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="first_name" class="col-sm-2 col-form-label text-right">Name:</label>
                        <div class="col-sm-5">
                            <input type="text" v-model="newUser.firstName" class="form-control" id="first_name" placeholder="First Name">
                        </div>
                        <div class="col-sm-5">
                            <input type="text" v-model="newUser.lastName" class="form-control" id="last_name" placeholder="Last Name">
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label text-right">Address:</label>
                        <div class="col-sm-10">
                            <input type="text" v-model="newUser.address" class="form-control" id="address">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="phone" class="col-sm-2 col-form-label text-right">Phone:</label>
                        <div class="col-sm-10">
                            <input type="tel" v-model="newUser.phone" class="form-control" id="phone">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 offset-md-5">
                    <button @click="updateUserDetails" class="btn btn-primary btn-block m-t-20">Update Details</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Profile",
        data: function () {
            return {
                newUser: {
                    firstName: "",
                    lastName: "",
                    username: "",
                    address: "",
                    phone: ""
                }
            }
        },
        computed: {
            user: function () {
                return this.$store.state.currentUser;
            }
        },
        methods: {
            updateUserDetails: function () {
                let self = this;
                this.$http.post("/user/update", this.newUser, {emulateJSON: true})
                    .then(function (res) {
                        self.$notify({
                            group: 'notifications',
                            type: 'success',
                            title: 'Success',
                            text: 'User details updated'
                        });
                        this.$store.commit('setCurrentUser', res.body);
                    })
            }
        },
        mounted: function () {
            this.newUser.firstName = this.user.firstName;
            this.newUser.lastName = this.user.lastName;
            this.newUser.username = this.user.username;
            this.newUser.address = this.user.address;
            this.newUser.phone = this.user.phone;
        }
    }
</script>

<style scoped>
    .profile-container {
        background: #fff;
        padding: 10px 30px 10px 30px;
        border-radius: 10px;
    }
</style>
