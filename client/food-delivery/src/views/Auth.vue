<template>
    <div id="auth">
        <div class="container">
            <div class="row">
                <div class="col-md-6 offset-md-3 authForm">
                    <img class="img-fluid m-b-20 col-md-6 offset-md-3" alt="logo" src="../assets/logo.png" width="40%">
                    <router-view/>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Auth"
    }
</script>

<style scoped>
    #auth {
        height: 100%;
        display: flex;
        align-items: center;
    }
    .authForm {
        margin-top: 50px;
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }
</style>
