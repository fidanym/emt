<template>
    <div id="sidebar" class="text-center">
        <h4>Username</h4>
        <hr>
        <div class="row">
            <div class="col-sm-6">
                <button class="btn btn-light"><font-awesome-icon icon="user" /> Profile</button>
            </div>
            <div class="col-sm-6">
                <button class="btn btn-light"><font-awesome-icon icon="running" /> Other</button>
            </div>
        </div>

        <div class="row cart">
            <p>This is going to be your cart... someday.</p>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Sidebar"
    }
</script>

<style scoped>
    #sidebar {
        background: #fff;
        padding: 20px;
        border-radius: 6px;
    }
</style>
