<template>
    <div
            v-loading="globalLoading"
            element-loading-text="正在加载中"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)"
    >
        <div class="permissManaTool">
            <el-input size="small" placeholder="请输入角色英文名" v-model="role.name">
                <template slot="prepend">ROLE_</template>
            </el-input>
            <el-input size="small" placeholder="请输入角色中文名" v-model="role.nameZh"
                      @keydown.native.enter="doAddRole"></el-input>
            <el-button size="small" type="primary" icon="el-icon-plus" @click="doAddRole">添加角色</el-button>
        </div>
        <div class="permissManaMain">
            <el-collapse
                    v-model="activeName"
                    accordion
                    v-loading="loading"
                    element-loading-text="正在加载中"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    @change="change">
                <el-collapse-item :title="r.nameZh" :name="r.id" v-for="(r, index) in roles" :key="index">
                    <el-card class="box-card">
                        <div slot="header" class="clearfix">
                            <span>可访问的资源</span>
                            <el-button style="float: right; padding: 3px;color: #ff0000" icon="el-icon-delete"
                                       type="text" @click="deleteRole(r)"></el-button>
                        </div>
                        <!-- show-checkbox： 在每个选项前创建一个复选框，如果此角色拥有当前权限则此选项为勾选状态 -->
                        <el-tree
                                show-checkbox
                                node-key="id"
                                :key="index"
                                :default-checked-keys="selectedMenus"
                                :data="allMenus"
                                :props="defaultProps" ref="tree"></el-tree>
                        <div style="display: flex;justify-content: flex-end">
                            <el-button size="mini" @click="cancelUpdate">取消修改</el-button>
                            <el-button size="mini" type="primary" @click="doUpdate(r.id, index)">确认修改</el-button>
                        </div>
                    </el-card>
                </el-collapse-item>
            </el-collapse>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PermissMana",
        data() {
            return {
                globalLoading: false,
                loading: false,
                role: {
                    name: '',
                    nameZh: ''
                },
                roles: {},
                allMenus: [],
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                // 角色所拥有的权限
                selectedMenus: [],
                activeName: -1
            }
        },
        methods: {
            // 点击卡片时触发的事件
            change(rid) {
                if (rid) {
                    this.initAllMenus();
                    this.initSelectMenus(rid)
                }
            },
            initRoles() {
                this.loading = true
                this.getRequest("/system/basic/permiss/").then(res => {
                    this.loading = false
                    if (res) {
                        this.roles = res;
                    }
                })
            },
            initAllMenus() {
                this.getRequest("/system/basic/permiss/menus").then(res => {
                    if (res) {
                        this.allMenus = res
                    }
                })
            },
            initSelectMenus(rid) {
                this.getRequest("/system/basic/permiss/mids/" + rid).then(res => {

                    if (res) {
                        this.selectedMenus = res
                    }
                })
            },
            doUpdate(rid, index) {
                let tree = this.$refs.tree[index];
                let selectedKeys = tree.getCheckedKeys(true);
                let url = '/system/basic/permiss/?rid=' + rid;
                selectedKeys.forEach(key => {
                    url += '&mids=' + key
                })
                this.putRequest(url).then(resp => {
                    if (resp) {
                        this.activeName = -1
                    }
                })
            },
            cancelUpdate() {
                this.activeName = -1
            },
            doAddRole() {
                this.globalLoading = true
                if (this.role.name && this.role.nameZh) {
                    this.postRequest("/system/basic/permiss/role", this.role).then(res => {
                        this.globalLoading = false
                        if (res) {
                            this.role.nameZh = ''
                            this.role.name = ''
                            this.initRoles();
                        }
                    })
                } else {
                    this.$message.error("数据不可为空！")
                }
            },
            deleteRole(role) {
                this.$confirm('此操作将永久【' + role.nameZh + '】角色, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/permiss/role/" + role.id).then(res => {
                        if (res) {
                            this.initRoles();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        },
        mounted() {
            this.initRoles()
        }
    }
</script>

<style scoped>
    .permissManaTool {
        display: flex;
        justify-content: flex-start;
    }

    .permissManaTool .el-input {
        width: 300px;
        margin-right: 6px;
    }

    .permissManaMain {
        margin-top: 10px;
        width: 800px;
    }
</style>