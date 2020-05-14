<template>
    <div>
        <div style="margin-top: 10px;display: flex;justify-content: center;">
            <el-input v-model="keywords" placeholder="通过用户搜索用户…" prefix-icon="el-icon-search"
                      style="width: 400px;margin-right: 10px;" @keydown.enter.native="doSearch"></el-input>
            <el-button icon="el-icon-search" @click="doSearch">搜索</el-button>
        </div>
        <div class="hr-container">
            <el-card class="hr-card" v-for="(hr,index) in hrs" :key="index">
                <div slot="header" class="clearfix">
                    <span>{{hr.name}}</span>
                    <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-delete"
                               @click="deleteHr(hr)"></el-button>
                </div>
                <div>
                    <div class="img-container">
                        <img :src="hr.userface" :alt="hr.name" :title="hr.name" class="userface-img">
                    </div>
                    <div class="userinfo-container">
                        <div>用户名：{{hr.name}}</div>
                        <div>手机号码：{{hr.phone}}</div>
                        <div>电话号码：{{hr.telephone}}</div>
                        <div>地址：{{hr.address}}</div>
                        <div>用户状态：
                            <el-switch
                                    v-model="hr.enabled"
                                    active-text="启用"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949"
                                    inactive-text="禁用"
                                    @change="enabledChange(hr)"></el-switch>
                        </div>
                        <div>用户角色:
                            <el-tag type="success" style="margin-right: 4px;" v-for="(role, indexj) in hr.roles"
                                    :key="indexj">{{role.nameZh}}
                            </el-tag>
                            <!-- @show：打开弹出框时触发的事件(加载当前用户的角色数据)；@hide：关闭弹出框时触发的事件(更新) -->
                            <el-popover
                                    placement="bottom"
                                    title="角色列表"
                                    @show="showPopover(hr)"
                                    @hide="hidePopover(hr)"
                                    width="200"
                                    trigger="click">
                                <!-- selectedRoles: 当前用户拥有的角色数组 -->
                                <el-select v-model="selectedRoles" multiple placeholder="请选择">
                                    <!-- allroles: 所有角色信息 -->
                                    <el-option
                                            v-for="(r, indexk) in allroles"
                                            :key="indexk"
                                            :label="r.nameZh"
                                            :value="r.id">
                                    </el-option>
                                </el-select>
                                <el-button slot="reference" icon="el-icon-more" type="text"></el-button>
                            </el-popover>
                        </div>
                        <div>备注: {{hr.remark}}</div>
                    </div>
                </div>
            </el-card>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SysHr",
        data() {
            return {
                keywords: '',
                // 所有用户的数据存放处
                hrs: [],
                // 多选择器选中的角色
                selectedRoles: [],
                // 多选择器的源数据
                allroles: []
            }
        },
        mounted() {
            this.initHrs();
        },
        methods: {
            initHrs() {
                this.getRequest("/system/hr/?keywords=" + this.keywords).then(res => {
                    if (res) {
                        this.hrs = res
                    }
                })
            },
            enabledChange(hr) {
                delete hr.roles;
                this.putRequest("/system/hr/", hr).then(res => {
                    if (res) {
                        this.initHrs();
                    }
                })
            },
            initAllRoles() {
                this.getRequest("/system/hr/roles").then(res => {
                    if (res) {
                        this.allroles = res;
                    }
                })
            },
            showPopover(hr) {
                let roles = hr.roles
                this.selectedRoles = []
                roles.forEach(r => {
                    this.selectedRoles.push(r.id)
                })
                this.initAllRoles()
            },
            hidePopover(hr) {
                let roles = [];
                Object.assign(roles, hr.roles);
                // flag：是否进行更新操作，true：更新、false：不更新
                let flag = false;
                // 判断用户点开弹出框后是否有做修改动作：【1】修改前后的长度不同（有做修改需要更新）、【2】修改前后长度相同，但是操作添加和删除的标签数目相同（有做修改需要更新）
                if (roles.length !== this.selectedRoles.length) {
                    flag = true
                } else {
                    for (let i = 0; i < roles.length; i++) {
                        let role = roles[i]
                        // 如果之前选中的id（selected）和最新选择出来的id（roles）相同，则将此数据从 roles 中去除
                        for (let j = 0; j < this.selectedRoles.length; j++) {
                            let sr = this.selectedRoles[j];
                            if (role.id == sr) {
                                roles.splice(i, 1)
                                i--;
                                break;
                            }
                        }
                    }
                    // 如果循环后判断出 roles 的长度，如果长度不为0则说明有修改的数据，则进行更新操作
                    if (roles.length != 0) {
                        flag = true
                    }
                }
                if (flag) {
                    let url = '/system/hr/role?hrId=' + hr.id;
                    this.selectedRoles.forEach(rid => {
                        url += '&rids=' + rid
                    })
                    this.putRequest(url).then(res => {
                        if (res) {
                            this.initHrs()
                        }
                    })
                }
            },
            doSearch() {
                this.initHrs();
            },
            deleteHr(hr) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/hr/" + hr.id).then(res => {
                        if (res) {
                            this.initHrs();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            }
        }
    }
</script>

<style scoped>
    .hr-container {
        margin-top: 10px;
        display: flex;
        flex-wrap: wrap;
        justify-content: space-around;
    }

    .hr-card {
        width: 350px;
        margin-bottom: 20px;
    }

    .img-container {
        width: 100%;
        display: flex;
        justify-content: center;
    }

    .userface-img {
        width: 72px;
        height: 72px;
        border-radius: 72px;
    }

    .userinfo-container div {
        font-size: 12px;
        color: #409eff;
    }
</style>