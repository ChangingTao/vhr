<template>
    <div style="width: 500px;">
        <el-input
                prefix-icon="el-icon-search"
                placeholder="输入部门名称进行搜索……"
                v-model="filterText">
        </el-input>

        <!--
            :filter-node-method：筛选数据值的方法
            :expand-on-click-node：是否点击当前列就进行展开/收缩操作（要点击小三角箭头才能展开/收缩）
        -->
        <el-tree
                :data="deps"
                :props="defaultProps"
                :expand-on-click-node="false"
                :filter-node-method="filterNode"
                ref="tree">
            <!-- node：元素对象、data：数据对象 -->
            <span class="custom-tree-node" style="display: flex;justify-content: space-between;width: 100%;"
                  slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button
                  type="primary"
                  size="mini"
                  class="depBtn"
                  @click="() => showAddDepView(data)">
            添加部门
          </el-button>
          <el-button
                  type="danger"
                  size="mini"
                  class="depBtn"
                  @click="() => deleteDep(data)">
            删除部门
          </el-button>
        </span>
        </span>
        </el-tree>
        <el-dialog
                title="添加部门"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <table>
                    <tr>
                        <td>
                            <el-tag>上级部门</el-tag>
                        </td>
                        <td>
                            <span>{{pname}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <el-tag>部门名称</el-tag>
                        </td>
                        <td>
                            <el-input v-model="dep.name" placeholder="请输入部门名称……"></el-input>
                        </td>
                    </tr>
                </table>

            </div>
            <span slot="footer" class="dialog-footer">
<el-button @click="dialogVisible = false">取 消</el-button>
<el-button type="primary" @click="doAddDep">确 定</el-button>
</span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "DepMana",
        data() {
            return {
                filterText: '',
                deps: [],
                // 树形控件的数据对象
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                dialogVisible: false,
                dep: {
                    name: '',
                    parentId: -1
                },
                pname: '',
            }
        },
        watch: {
            // 监听input搜索发生变化的时候触发
            filterText(val) {
                // 当值发生变化的时候就调用 tree 实例的filter方法，进行筛选数据值
                this.$refs.tree.filter(val);
            }
        },
        methods: {
            initDeps() {
                this.getRequest("/system/basic/department/").then(res => {
                    if (res) {
                        this.deps = res;
                    }
                })
            },
            // 树形控件的过滤方法，参数 value：输入的值；data：将要对比的数据源(树形控件的整个Json数据对象)
            // (被循环执行)过滤的步骤：1、当输入框输入值时，将data数据全部隐藏去除；2、通过 data.对比的属性.indexOf(value) 来筛选data中的值
            // 返回值说明： true：保留data某一数据 ； false：去除data某一数据
            filterNode(value, data) {
                if (!value) return true; // 进行判断：如果输入框中没值，返回 true(data数据不清除)；如果输入框中有值，则返回false(data数据全部清除)
                return data.name.indexOf(value) !== -1; // 判断默认信息中的name中是否能查找到，如果没有找到 `data.name.indexOf(value)` 将会返回 -1，找到则返回索引
            },
            // 删除按钮
            deleteDep(data) {
                if (data.parent) {
                    this.$message.error("无法删除父部门")
                } else {
                    this.$confirm('此操作将永久删除该部门, 是否继续?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(() => {
                        this.deleteRequest("/system/basic/department/" + data.id).then(res => {
                            if (res) {
                                this.removeDepFromDeps(null, this.deps, data.id)
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
            // 添加按钮
            showAddDepView(data) {
                this.pname = data.name
                this.dep.parentId = data.id
                this.dialogVisible = true
            },
            // 添加部门的确定按钮
            doAddDep() {
                this.postRequest("/system/basic/department/", this.dep).then(res => {
                    if (res) {
                        this.addDep2Deps(this.deps, res.obj);
                        this.dialogVisible = false;
                        // 初始化变量
                        this.initDep();
                    }
                })
            },
            // 初始化 dep
            initDep() {
                this.dep = {
                    name: '',
                    parentId: -1
                },
                    this.pname = ''
            },
            // 递归填充 添加对象 进deps数组中
            addDep2Deps(deps, dep) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id == dep.parentId) {
                        // concat：数组拼接函数
                        d.children = d.children.concat(dep);
                        if (d.children.length > 0) {
                            d.parent = true
                        }
                        return
                    } else {
                        // 如果不是其父id，则进行递归操作
                        this.addDep2Deps(d.children, dep);
                    }
                }
            },
            // 递归 从deps数组中移除 删除对象 数据
            removeDepFromDeps(p, deps, id) {
                for (let i = 0; i < deps.length; i++) {
                    let d = deps[i];
                    if (d.id === id) {
                        deps.splice(i, 1);
                        if (deps.length == 0) {
                            p.parent = false;
                        }
                        return
                    } else {
                        this.removeDepFromDeps(d, d.children, id);
                    }
                }
            }
        },
        mounted() {
            this.initDeps();
        },
    }
</script>

<style scoped>
    .depBtn {
        padding: 2px;
    }
</style>