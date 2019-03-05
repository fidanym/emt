<template>
    <div class="container item-container">
        <h1 class="text-center m-t-20">Update Item</h1>
        <div class="row">
            <div class="col-md-6">
                <div class="form-group">
                    <label for="item_name">Name:</label>
                    <input type="text" :class="{error: $v.newItem.name.$invalid && $v.newItem.name.$dirty}" @blur="$v.newItem.name.$touch()" class="form-control" id="item_name" name="item_name" v-model="newItem.name" placeholder="Item name">
                </div>
                <div class="errorText" v-if="!$v.newItem.name.required && $v.newItem.name.$dirty">Item name can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.name.minLength && $v.newItem.name.$dirty">Item name must have at least {{$v.newItem.name.$params.minLength.min}} letters</div>
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" :class="{error: $v.newItem.price.$invalid && $v.newItem.price.$dirty}" @blur="$v.newItem.price.$touch()" class="form-control" id="price" name="price" v-model="newItem.price" placeholder="Price">
                </div>
                <div class="errorText" v-if="!$v.newItem.price.required && $v.newItem.price.$dirty">Price can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.price.numeric && $v.newItem.price.$dirty">Price can only include digits</div>
                <div class="errorText" v-if="!$v.newItem.price.minLength && $v.newItem.price.$dirty">Price must have at least {{$v.newItem.price.$params.minLength.min}} digits</div>
            </div>
            <div class="col-md-6">
                <div class="form-group">
                    <label for="description">Short description:</label>
                    <textarea :class="{error: $v.newItem.description.$invalid && $v.newItem.description.$dirty}" @blur="$v.newItem.description.$touch()"  v-model="newItem.description" class="form-control" id="description" name="description" rows="5"></textarea>
                    <p class="text-muted m-t-5">{{ 200 - newItem.description.length }} character{{ 200 - newItem.description === 1 ? '' : 's' }} remaining (200 max.)</p>
                </div>
                <div class="errorText" v-if="!$v.newItem.description.required && $v.newItem.description.$dirty">Item description can not be blank</div>
                <div class="errorText" v-if="!$v.newItem.description.minLength && $v.newItem.description.$dirty">Item description must have at least {{$v.newItem.description.$params.minLength.min}} letters</div>
            </div>
        </div>
        <div class="row m-t-20">
            <div class="col-md-2 offset-4">
                <button @click="updateItem" class="btn btn-primary">Update Item</button>
            </div>
            <div class="col-md-2">
                <button @click="deleteItem" class="btn btn-danger"><font-awesome-icon icon="trash-alt"/> Delete Item</button>
            </div>
        </div>

    </div>
</template>

<script>
    import { required, minLength, numeric } from 'vuelidate/lib/validators'
    export default {
        name: "Item",
        props: {
            item: Object
        },
        data: function () {
            return {
                newItem: {
                    id: "",
                    name: "",
                    price: "",
                    description: ""
                }
            }
        },
        methods: {
            updateItem: function () {
                this.$http.post('/item/update', this.newItem, { emulateJSON: true })
                    .then(function () {
                        this.$notify({
                            group: "notifications",
                            type: "success",
                            title: "Success!",
                            text: "Item details have been updated"
                        });
                    })
            },
            deleteItem: function () {
                this.$http.post('/item/delete', { id: this.item.id }, { emulateJSON: true })
                    .then(function () {
                        this.$router.push('/restaurants/' + this.item.company.id);
                    })
            }
        },
        mounted: function () {
            this.newItem.id = this.item.id;
            this.newItem.name = this.item.name;
            this.newItem.price = this.item.price;
            this.newItem.description = this.item.description;
        },
        validations: {
            newItem: {
                name: {
                    required,
                    minLength: minLength(3)
                },
                price: {
                    required,
                    minLength: minLength(1),
                    numeric
                },
                description: {
                    required,
                    minLength: minLength(10)
                }
            }
        }
    }
</script>

<style scoped>
    .item-container {
        background: #fff;
        padding-bottom: 10px;
        padding-top: 10px;
        border-radius: 10px;
    }
    .text-muted {
        font-size: .8em;
    }
    .error {
        border: 1px solid #ff5654;
    }
    .errorText {
        font-size: 0.75em;
        color: #ff5654;
    }

    .form-control:focus {
        border-color: #43b00e;
        box-shadow: none;
    }
</style>
