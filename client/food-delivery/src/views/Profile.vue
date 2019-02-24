<template>
    <div class="profile-container">
        <h1 class="text-center font-weight-bold m-t-20">Your profile</h1>
        <div class="m-t-30">
            <div class="row">

                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="first_name" class="col-sm-2 col-form-label text-right">Name:</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="first_name" placeholder="First Name">
                        </div>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="last_name" placeholder="Last Name">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="email" class="col-sm-2 col-form-label text-right">E-mail:</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="email">
                        </div>
                    </div>
                </div>

                <div class="col-md-6">
                    <div class="form-group row">
                        <label for="address" class="col-sm-2 col-form-label text-right">Address:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="address">
                        </div>
                    </div>

                    <div class="form-group row">
                        <label for="phone" class="col-sm-2 col-form-label text-right">Phone:</label>
                        <div class="col-sm-10">
                            <input type="tel" class="form-control" id="phone">
                        </div>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col-md-2 offset-md-5">
                    <button class="btn btn-primary btn-block m-t-20">Update Details</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Profile"
    }
</script>

<style scoped>
    .profile-container {
        background: #fff;
        padding: 10px 30px 10px 30px;
        border-radius: 10px;
    }
</style>
