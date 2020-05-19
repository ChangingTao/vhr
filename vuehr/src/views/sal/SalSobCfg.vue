<template>
    <div>
        <div>
            <el-table :data="emps" border stripe size="mini">
                <el-table-column type="selection" align="left" width="55"></el-table-column>
                <el-table-column prop="name" label="姓名" fixed width="120" align="left"></el-table-column>
                <el-table-column prop="workID" label="工号" width="120" align="left"></el-table-column>
                <el-table-column prop="email" label="电子邮件" width="200" align="left"></el-table-column>
                <el-table-column prop="phone" label="电话号码" width="120" align="left"></el-table-column>
                <el-table-column prop="department.name" label="所属部门" width="120" align="left"></el-table-column>
                <el-table-column label="所属部门" align="center">
                    <template slot-scope="scope">
                        <el-tooltip placement="right" v-if="scope.row.salary">
                            <div slot="content">
                                <table>
                                    <tr>
                                        <td>基本工资</td>
                                        <td>{{scope.row.salary.basicSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>交通补助</td>
                                        <td>{{scope.row.salary.trafficSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>午餐补助</td>
                                        <td>{{scope.row.salary.lunchSalary}}</td>
                                    </tr>
                                    <tr>
                                        <td>奖金</td>
                                        <td>{{scope.row.salary.bonus}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金比率</td>
                                        <td>{{scope.row.salary.pensionPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>养老金基数</td>
                                        <td>{{scope.row.salary.pensionBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险比率</td>
                                        <td>{{scope.row.salary.medicalPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>医疗保险基数</td>
                                        <td>{{scope.row.salary.medicalBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金比率</td>
                                        <td>{{scope.row.salary.accumulationFundPer}}</td>
                                    </tr>
                                    <tr>
                                        <td>公积金基数</td>
                                        <td>{{scope.row.salary.accumulationFundBase}}</td>
                                    </tr>
                                    <tr>
                                        <td>启用时间</td>
                                        <td>{{scope.row.salary.createDate}}</td>
                                    </tr>
                                </table>
                            </div>
                            <el-tag>{{scope.row.salary.name}}</el-tag>
                        </el-tooltip>
                        <el-tag v-else>暂未设置</el-tag>
                    </template>
                </el-table-column>
                <el-table-column label="操作" align="center">
                    <template slot-scope="scope">
                        <el-popover
                                placement="left"
                                title="修改工资账套"
                                @show="showPop(scope.row)"
                                @hide="hidePop(scope.row)"
                                width="200"
                                trigger="click">
                            <div>
                                <el-select v-model="currentSalary" size="mini" placeholder="请选择">
                                    <el-option
                                            v-for="item in salaries"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </div>
                            <el-button slot="reference" type="danger">修改工资账套</el-button>
                        </el-popover>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end;">
                <el-pagination
                        background
                        @size-change="sizeChange"
                        @current-change="currentChange"
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "SalSobCfg",
        data() {
            return {
                emps: [],
                salaries: [],
                currentSalary: null,
                currentPage: 1,
                currentSize: 10,
                total: 0
            }
        },
        mounted() {
            this.initEmps()
            this.initSalaries()
        },
        methods: {
            initEmps() {
                this.getRequest("/salary/sobcfg/?page=" + this.currentPage + "&size=" + this.currentSize).then(res => {
                    if (res) {
                        this.emps = res.data;
                        this.total = res.total;
                    }
                })
            },
            sizeChange(size) {
                this.currentSize = size;
                this.initEmps()
            },
            currentChange(page) {
                this.currentPage = page;
                this.initEmps();
            },
            initSalaries() {
                this.getRequest("/salary/sobcfg/salaries").then(res => {
                    if (res) {
                        this.salaries = res
                    }
                })
            },
            // 显示Pop事件
            showPop(data) {
                // 如果员工有账套，则赋值，如果没有则设置为空
                if (data.salary) {
                    this.currentSalary = data.salary.id
                } else {
                    this.currentSalary = null;
                }
            },
            // 关闭Pop事件
            hidePop(data) {
                if (data.salary) {
                    if (this.currentSalary == data.salary.id) {
                        return
                    } else {
                        this.putRequest('/salary/sobcfg/?eid=' + data.id + '&sid=' + this.currentSalary).then(res => {
                            if (res) {
                                this.initEmps()
                            }
                        })
                    }
                } else if (this.currentSalary) {
                    this.putRequest('/salary/sobcfg/?eid=' + data.id + '&sid=' + this.currentSalary).then(res => {
                        if (res) {
                            this.initEmps()
                        }
                    })
                }
            }
        }
    }
</script>

<style scoped>

</style>