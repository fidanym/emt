<template>
    <div class="card">
        <div class="row">
            <div class="col-md-5"><font-awesome-icon icon="user"/> {{ user.firstName + " " + user.lastName }}</div>
            <div class="col-md-3"><font-awesome-icon icon="phone"/> {{ user.phone }}</div>
            <div class="col-md-3"><font-awesome-icon icon="envelope"/> {{ user.email }}</div>
            <div class="col-md-1"><button @click="showSettingsModal" class="btn btn-secondary"><font-awesome-icon icon="cog"/></button></div>
        </div>
        <modal v-if="showModal" @closeUserModal="showModal = false" :user="user"></modal>
    </div>
</template>

<script>
    import UserSettings from "./UserSettings";

    export default {
        name: "User",
        components: {
          modal: UserSettings
        },
        data: function () {
            return {
                showModal: false
            }
        },
        props: {
            user: Object
        },
        methods: {
            showSettingsModal: function () {
                this.showModal = true;
            }
        }
    }
</script>

<style scoped>
    .card {
        padding: 10px 20px 10px 20px;
    }
</style>
