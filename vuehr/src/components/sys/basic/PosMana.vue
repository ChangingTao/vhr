<template>
    <div>
        <div>
            <el-input
                    size="small"
                    class="addPosInput"
                    placeholder="添加职位..."
                    prefix-icon="el-icon-plus"
                    @keydown.enter.native="addPosition"
                    v-model="pos.name">
            </el-input>
            <el-button icon="el-icon-plus" size="small" type="primary" @click="addPosition">添加</el-button>
        </div>
        <div class="posManaMain">
            <el-table
                    :data="positions"
                    stripe
                    size="small"
                    border
                    style="width: 70%"
                    @selection-change="handleSelectionChange">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="id"
                        label="编号"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="职位名称"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="createDate"
                        width="150px"
                        label="创建时间">
                </el-table-column>
                <el-table-column label="操作">
                    <template slot-scope="scope">
                        <el-button
                                size="mini"
                                @click="showEditDialog(scope.$index, scope.row)">编辑
                        </el-button>
                        <el-button
                                size="mini"
                                type="danger"
                                @click="handleDelete(scope.$index, scope.row)">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <el-button type="danger" @click="deleteMany" size="small" style="margin-top: 8px;"
                       :disabled="multipleSelection.length==0">批量删除
            </el-button>
        </div>
        <el-dialog
                title="修改职位"
                :visible.sync="dialogVisible"
                width="30%">
            <div>
                <el-tag>职位名称</el-tag>
                <el-input class="updatePosInput" size="small" v-model="updatePos.name"
                          @keydown.enter.native="doUpdate"></el-input>
            </div>
            <span slot="footer" class="dialog-footer">
    <el-button size="small" @click="dialogVisible = false">取 消</el-button>
    <el-button size="small" type="primary" @click="doUpdate">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "PosMana",
        data() {
            return {
                pos: {
                    name: '',
                },
                updatePos: {
                    name: '',
                },
                positions: [],
                dialogVisible: false,
                multipleSelection: []
            }
        },
        mounted() {
            this.initPositions();
        },
        methods: {
            initPositions() {
                this.getRequest("/system/basic/pos/").then(res => {
                    if (res) {
                        this.positions = res;
                    }
                })
            },
            showEditDialog(index, data) {
                // 出现问题: 当直接将data赋值给updatePos时，updatePos 和 pos 的数据会捆绑在一起,input输入的时候表格中的数据会随着发生变化
                // 解决方法: 使用 Object.assign(目标，源数据) 将pos数据复制到updatePos即可
                Object.assign(this.updatePos, data)
                this.dialogVisible = true
            },
            doUpdate() {
                if (this.updatePos.name) {
                    this.putRequest("/system/basic/pos/", this.updatePos).then(res => {
                        if (res) {
                            this.initPositions()
                            this.updatePos.name = ''
                            this.dialogVisible = false
                        }
                    })
                } else {
                    this.$message.error("职位名称不可为空!")
                }
            },
            handleDelete(index, data) {
                this.$confirm('此操作将永久删除【' + data.name + '】职位, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/system/basic/pos/" + data.id).then(res => {
                        if (res) {
                            this.initPositions();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            addPosition() {
                if (this.pos.name) {
                    this.postRequest("/system/basic/pos/", this.pos).then(res => {
                        if (res) {
                            this.initPositions();
                            this.pos.name = ''
                        }
                    })
                } else {
                    this.$message.error("职位名称不可为空！")
                }
            },
            handleSelectionChange(val) {
                this.multipleSelection = val
            },
            deleteMany() {
                this.$confirm('此操作将永久删除【' + this.multipleSelection.length + '】几条记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    let ids = "?";
                    this.multipleSelection.forEach(item => {
                        ids += "ids=" + item.id + "&"
                    })
                    this.deleteRequest("/system/basic/pos/" + ids).then(res => {
                        if (res) {
                            this.initPositions();
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
    .addPosInput {
        width: 300px;
        margin-right: 5px;
    }

    .updatePosInput {
        width: 200px;
        margin-left: 8px;
    }

    .posManaMain {
        margin-top: 5px;
    }
</style>