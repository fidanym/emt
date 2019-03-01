<template>
    <transition name="modal">
        <div class="modal-mask">
            <div class="modal-wrapper">
                <div class="modal-container">

                    <div class="modal-header">
                        <h3>Update {{user.firstName + " " + user.lastName}}'s settings</h3>
                    </div>

                    <div class="modal-body">
                        <div class="row">

                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="first_name" class="col-form-label text-right">First Name:</label>
                                    <div class="col-sm-12">
                                        <input type="text" v-model="user.firstName" class="form-control" id="first_name">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="last_name" class="col-form-label text-right">Last Name:</label>
                                    <div class="col-sm-12">
                                        <input type="text" v-model="user.lastName" class="form-control" id="last_name">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="address" class="col-form-label text-right">Address:</label>
                                    <div class="col-sm-12">
                                        <input type="text" v-model="user.address" class="form-control" id="address">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="phone" class="col-form-label text-right">Phone:</label>
                                    <div class="col-sm-12">
                                        <input type="tel" v-model="user.phone" class="form-control" id="phone">
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="modal-footer">
                        <button class="btn btn-light" @click="$emit('closeUserModal')">Cancel</button>
                        <button @click="updateUserDetails" class="btn btn-primary">Update Details</button>
                    </div>
                </div>

            </div>
        </div>
    </transition>
</template>

<script>
    export default {
        name: "UserSettings",
        props: {
            user: Object
        },
        computed: {
            newUser: function () {
                let tmpUser = {};
                tmpUser.firstName = this.user.firstName;
                tmpUser.lastName = this.user.lastName;
                tmpUser.address = this.user.address;
                tmpUser.phone = this.user.phone;
                return tmpUser;
            }
        },
        methods: {
            updateUserDetails: function () {
                let self = this;
                this.$http.post("/user/update", this.newUser, {emulateJSON: true})
                    .then(function () {
                        self.$notify({
                            group: 'notifications',
                            type: 'success',
                            title: 'Success',
                            text: 'User details updated'
                        });
                    })
            }
        }
    }
</script>

<style scoped>
    .modal-mask {
        position: fixed;
        z-index: 9998;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, .5);
        display: table;
        transition: opacity .2s ease;
    }

    .modal-wrapper {
        display: table-cell;
    }

    .modal-container {
        width: 800px;
        max-height: 90%;
        margin: 0 auto;
        padding: 20px 30px;
        background-color: #fff;
        border-radius: 2px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, .33);
        transition: all .2s ease;
        font-family: Helvetica, Arial, sans-serif;
        overflow-y: scroll;
    }

    .modal-body {
        margin: 20px 0;
    }

    .modal-enter {
        opacity: 0;
    }

    .modal-leave-active {
        opacity: 0;
    }

    .modal-enter .modal-container,
    .modal-leave-active .modal-container {
        -webkit-transform: scale(1.05);
        transform: scale(1.05);
    }

    .items-list {
        max-height: 400px;
        overflow-x: hidden;
        overflow-y: scroll;
    }

    @media only screen and (max-width: 700px) {
        .modal-container {
            width: 95%;
        }
    }
</style>
