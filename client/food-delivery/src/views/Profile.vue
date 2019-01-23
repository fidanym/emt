<template>
    <div class="profile-container">
        <h1 class="text-center font-weight-bold m-t-15">Your profile</h1>
        <form class="m-t-15">
            <div class="form-group row">
                <label for="first_name" class="col-sm-2 col-form-label">Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="first_name" placeholder="First Name">
                </div>
            </div>
            <div class="form-group row">
                <label for="last_name" class="col-sm-2 col-form-label">Last Name:</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="last_name" placeholder="Last Name">
                </div>
            </div>
            <div class="form-group row">
                <label for="birthday" class="col-sm-2 col-form-label"></label>
                <div class="col-sm-10">
                    <input type="date" class="form-control" id="birthday">
                </div>
            </div>
        </form>
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
