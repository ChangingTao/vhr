<template>
    <div>
        <div>
            <div style="display: flex;justify-content: space-between">
                <!-- 左边搜索框 -->
                <div>
                    <el-input placeholder="请输入员工名进行搜索，可以直接回车搜索…" prefix-icon="el-icon-search"
                              style="width: 350px;margin-right: 10px;" v-model="keyword"
                              @keydown.enter.native="initEmps"
                              clearable @clear="initEmps" :disabled="showAdvanceSearchView"></el-input>
                    <el-button icon="el-icon-search" type="primary" @click="initEmps" :disabled="showAdvanceSearchView">
                        搜索
                    </el-button>
                    <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
                        <i :class="showAdvanceSearchView ? 'fa fa-angle-double-up': 'fa fa-angle-double-down'"
                           aria-hidden="true"></i>
                        高级搜索
                    </el-button>
                </div>
                <!-- 右边三个按钮 -->
                <div>
                    <!-- 文件上传组件 -->
                    <el-upload
                            style="display: inline-flex; margin-right: 8px;"
                            action="/employee/basic/import"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="onSuccess"
                            :on-error="onError"
                            :disabled="importDataDisable">
                        <el-button :disabled="importDataDisable" type="success" :icon="importDataBtnIcon">
                            {{importDataBtnText}}
                        </el-button>
                    </el-upload>
                    <el-button type="success" @click="exportData" icon="el-icon-download">
                        导出数据
                    </el-button>
                    <el-button type="primary" icon="el-icon-plus" @click="showAddEmpView">
                        添加员工
                    </el-button>
                </div>
            </div>
            <!-- 高级搜索 -->
            <transition name="slide-fade">
                <div v-show="showAdvanceSearchView"
                     style="border: 1px solid #409eff;border-radius: 5px;box-sizing: border-box;padding: 5px;margin: 10px 0px;">
                    <el-row>
                        <el-col :span="5">
                            政治面貌:
                            <el-select v-model="searchValue.politicId" placeholder="政治面貌" size="mini"
                                       style="width: 130px;">
                                <el-option
                                        v-for="item in politicsstatus"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            民族:
                            <el-select v-model="searchValue.nationId" placeholder="民族" size="mini"
                                       style="width: 150px;">
                                <el-option
                                        v-for="item in nations"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            职位:
                            <el-select v-model="searchValue.posId" placeholder="职位" size="mini" style="width: 130px;">
                                <el-option
                                        v-for="item in positions"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="4">
                            职称:
                            <el-select v-model="searchValue.jobLevelId" placeholder="职称" size="mini"
                                       style="width: 130px;">
                                <el-option
                                        v-for="item in joblevels"
                                        :key="item.id"
                                        :label="item.name"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-col>
                        <el-col :span="7">
                            聘用形式:
                            <el-radio-group v-model="searchValue.engageForm">
                                <el-radio label="劳动合同">劳动合同</el-radio>
                                <el-radio label="劳务合同">劳务合同</el-radio>
                            </el-radio-group>
                        </el-col>
                    </el-row>
                    <el-row style="margin-top: 10px;">
                        <el-col :span="5">
                            所属部门:
                            <el-popover
                                    placement="right"
                                    title="请选择部门"
                                    width="200"
                                    trigger="manual"
                                    v-model="searchPopVisible">
                                <el-tree :data="allDeps" default-expand-all :props="defaultProps"
                                         @node-click="searchViewHandleNodeClick"></el-tree>
                                <div slot="reference" style="width: 130px;display: inline-flex;font-size: 13px;
                                border: 1px solid #dedede; height: 26px;border-radius: 5px;cursor: pointer;
                                align-items: center;padding-left: 8px;box-sizing: border-box;margin-left: 3px;"
                                     @click="searchPopVisible = !searchPopVisible">
                                    {{searchInputDepName}}
                                </div>
                            </el-popover>
                        </el-col>
                        <el-col :span="10">
                            入职日期:
                            <el-date-picker
                                    v-model="searchValue.beginDateScope"
                                    type="daterange"
                                    size="mini"
                                    unlink-panels
                                    value-format="yyyy-MM-dd"
                                    range-separator="至"
                                    start-placeholder="开始日期"
                                    end-placeholder="结束日期">
                            </el-date-picker>
                        </el-col>
                        <el-col :span="5" :offset="4">
                            <el-button size="mini">取消</el-button>
                            <el-button size="mini" icon="el-icon-search" type="primary" @click="initEmps('advanced')">搜索
                            </el-button>
                        </el-col>
                    </el-row>
                </div>
            </transition>
        </div>
        <!-- 表格 -->
        <div style="margin-top: 10px;">
            <el-table
                    :data="emps"
                    stripe
                    v-loading="loading"
                    element-loading-text="正在加载中…"
                    element-loading-spinner="el-icon-loading"
                    element-loading-background="rgba(0, 0, 0, 0.8)"
                    border
                    style="width: 100%">
                <el-table-column
                        type="selection"
                        width="55">
                </el-table-column>
                <el-table-column
                        prop="name"
                        label="姓名"
                        fixed
                        align="center"
                        width="90">
                </el-table-column>
                <el-table-column
                        prop="workID"
                        label="工号"
                        align="left"
                        width="85">
                </el-table-column>
                <el-table-column
                        prop="gender"
                        label="性别"
                        align="left"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="birthday"
                        label="出生日期"
                        align="center"
                        width="95">
                </el-table-column>
                <el-table-column
                        prop="idCard"
                        label="身份证号码"
                        align="center"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="wedlock"
                        label="婚姻状况"
                        align="center"
                        width="70">
                </el-table-column>
                <el-table-column
                        prop="nation.name"
                        label="民族"
                        align="center"
                        width="50">
                </el-table-column>
                <el-table-column
                        prop="nativePlace"
                        label="籍贯"
                        align="left"
                        width="80">
                </el-table-column>
                <el-table-column
                        prop="politicsstatus.name"
                        align="center"
                        label="政治面貌">
                </el-table-column>
                <el-table-column
                        prop="email"
                        label="电子邮件"
                        align="left"
                        width="180">
                </el-table-column>
                <el-table-column
                        prop="phone"
                        label="电话号码"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="address"
                        label="联系地址"
                        align="left"
                        width="220">
                </el-table-column>
                <el-table-column
                        prop="department.name"
                        label="所属部门"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="position.name"
                        label="职位"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="jobLevel.name"
                        label="职称"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="engageForm"
                        label="聘用形式"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="tiptopDegree"
                        label="最高学历"
                        align="left"
                        width="100">
                </el-table-column>
                <el-table-column
                        prop="specialty"
                        label="专业"
                        align="left"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="school"
                        label="毕业院校"
                        align="left"
                        width="150">
                </el-table-column>
                <el-table-column
                        prop="beginDate"
                        label="入职日期"
                        align="left"
                        width="95">
                </el-table-column>
                <el-table-column
                        prop="conversionTime"
                        label="转正日期"
                        align="left"
                        width="95">
                </el-table-column>
                <el-table-column
                        prop="beginContract"
                        label="合同起始日期"
                        align="left"
                        width="95">
                </el-table-column>
                <el-table-column
                        prop="endContract"
                        label="合同截止日期"
                        align="left"
                        width="95">
                </el-table-column>
                <el-table-column
                        label="合同期限"
                        align="left"
                        width="100">
                    <template slot-scope="scope">
                        <el-tag>{{scope.row.contractTerm}}</el-tag>
                        年
                    </template>
                </el-table-column>
                <el-table-column
                        fixed="right"
                        width="200"
                        label="操作">
                    <template slot-scope="scope">
                        <el-button @click="showEditEmpView(scope.row)" style="padding: 2px" size="mini">编辑</el-button>
                        <el-button style="padding: 2px" size="mini" type="primary">查看高级资料</el-button>
                        <el-button @click="deleteEmp(scope.row)" style="padding: 2px" size="mini" type="danger">删除
                        </el-button>
                    </template>
                </el-table-column>
            </el-table>
            <div style="display: flex;justify-content: flex-end;">
                <el-pagination
                        @current-change="currentChange"
                        @size-change="sizeChange"
                        background
                        layout="sizes, prev, pager, next, jumper, ->, total, slot"
                        :total="total">
                </el-pagination>
            </div>
        </div>
        <!-- 添加员工/编辑员工的弹窗 -->
        <el-dialog
                :title="dialogTitle"
                @close="closeDialog"
                :visible.sync="dialogVisible"
                width="80%">

            <div>
                <!-- 添加员工的表单 -->
                <el-form :model="emp" :rules="empRules" ref="empForm">
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="姓名:" prop="name">
                                <el-input size="mini" style="width: 150px;" prefix-icon="el-icon-edit"
                                          v-model="emp.name" placeholder="请输入员工新名…"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="性别:" prop="gender">
                                <el-radio-group v-model="emp.gender">
                                    <el-radio label="男">男</el-radio>
                                    <el-radio label="女">女</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="出生日期:" prop="birthday">
                                <el-date-picker
                                        v-model="emp.birthday"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 150px;"
                                        placeholder="出生日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="政治面貌:" prop="politicId">
                                <el-select v-model="emp.politicId" placeholder="政治面貌" size="mini" style="width: 200px;">
                                    <el-option
                                            v-for="item in politicsstatus"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="民族:" prop="nationId">
                                <el-select v-model="emp.nationId" placeholder="民族" size="mini" style="width: 200px;">
                                    <el-option
                                            v-for="item in nations"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="籍贯:" prop="nativePlace">
                                <el-input size="mini" style="width: 120px;" prefix-icon="el-icon-edit"
                                          v-model="emp.nativePlace" placeholder="请输入籍贯"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="电子邮箱:" prop="email">
                                <el-input size="mini" style="width: 150px;" prefix-icon="el-icon-message"
                                          v-model="emp.email" placeholder="请输入邮箱"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="联系地址:" prop="address">
                                <el-input size="mini" style="width: 200px;" prefix-icon="el-icon-edit"
                                          v-model="emp.address" placeholder="请输入联系地址"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="职位:" prop="posId">
                                <el-select v-model="emp.posId" placeholder="职位" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in positions"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="职称:" prop="jobLevelId">
                                <el-select v-model="emp.jobLevelId" placeholder="职称" size="mini" style="width: 150px;">
                                    <el-option
                                            v-for="item in joblevels"
                                            :key="item.id"
                                            :label="item.name"
                                            :value="item.id">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="所属部门:" prop="departmentId">
                                <el-popover
                                        placement="right"
                                        title="请选择部门"
                                        width="200"
                                        trigger="manual"
                                        v-model="popVisible">
                                    <el-tree :data="allDeps" default-expand-all :props="defaultProps"
                                             @node-click="handleNodeClick"></el-tree>
                                    <div slot="reference" style="width: 150px;display: inline-flex;font-size: 13px;
                                border: 1px solid #dedede; height: 26px;border-radius: 5px;cursor: pointer;
                                align-items: center;padding-left: 8px;box-sizing: border-box" @click="showDepView">
                                        {{inputDepName}}
                                    </div>
                                </el-popover>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="电话号码:" prop="phone">
                                <el-input size="mini" style="width: 200px;" prefix-icon="el-icon-phone"
                                          v-model="emp.phone" placeholder="电话号码"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="工号:" prop="workID">
                                <el-input size="mini" style="width: 150px;" prefix-icon="el-icon-edit"
                                          v-model="emp.workID" placeholder="工号" disabled></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="5">
                            <el-form-item label="学历:" prop="tiptopDegree">
                                <el-select v-model="emp.tiptopDegree" placeholder="学历" size="mini"
                                           style="width: 150px;">
                                    <el-option
                                            v-for="item in tiptopDegrees"
                                            :key="item"
                                            :label="item"
                                            :value="item">
                                    </el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="毕业院校:" prop="school">
                                <el-input size="mini" style="width: 150px;" prefix-icon="el-icon-edit"
                                          v-model="emp.school" placeholder="毕业院校名称"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="7">
                            <el-form-item label="专业名称:" prop="specialty">
                                <el-input size="mini" style="width: 200px;" prefix-icon="el-icon-phone"
                                          v-model="emp.specialty" placeholder="专业名称"></el-input>
                            </el-form-item>
                        </el-col>
                    </el-row>

                    <el-row>
                        <el-col :span="6">
                            <el-form-item label="入职日期:" prop="beginDate">
                                <el-date-picker
                                        v-model="emp.beginDate"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="入职日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="转正日期:" prop="conversionTime">
                                <el-date-picker
                                        v-model="emp.conversionTime"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="转正日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同起始日期:" prop="beginContract">
                                <el-date-picker
                                        v-model="emp.beginContract"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 130px;"
                                        placeholder="合同起始日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                        <el-col :span="6">
                            <el-form-item label="合同终止日期:" prop="endContract">
                                <el-date-picker
                                        v-model="emp.endContract"
                                        size="mini"
                                        type="date"
                                        value-format="yyyy-MM-dd"
                                        style="width: 150px;"
                                        placeholder="合同终止日期">
                                </el-date-picker>
                            </el-form-item>
                        </el-col>
                    </el-row>
                    <el-row>
                        <el-col :span="8">
                            <el-form-item label="身份证号码:" prop="idCard">
                                <el-input size="mini" style="width: 180px;" prefix-icon="el-icon-edit"
                                          v-model="emp.idCard" placeholder="请输入身份证号码"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="聘用形式:" prop="engageForm">
                                <el-radio-group v-model="emp.engageForm">
                                    <el-radio label="劳动合同">劳动合同</el-radio>
                                    <el-radio label="劳务合同">劳务合同</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                        <el-col :span="8">
                            <el-form-item label="婚姻状况:" prop="wedlock">
                                <el-radio-group v-model="emp.wedlock">
                                    <el-radio label="已婚">已婚</el-radio>
                                    <el-radio label="未婚">未婚</el-radio>
                                    <el-radio label="离异">离异</el-radio>
                                </el-radio-group>
                            </el-form-item>
                        </el-col>
                    </el-row>
                </el-form>
            </div>

            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="doAddEmp">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>

<script>
    export default {
        name: "EmpBasic",
        data() {
            return {
                dialogTitle: '',
                importDataBtnText: '导入数据',
                importDataBtnIcon: 'el-icon-upload2',
                importDataDisable: false,
                emps: [],
                allDeps: [],
                loading: false,
                total: 0,
                page: 1,
                size: 10,
                keyword: '',
                dialogVisible: false,
                nations: [],
                joblevels: [],
                politicsstatus: [],
                positions: [],
                emp: {
                    name: "rocketWizt",
                    gender: "男",
                    birthday: "1990-01-01",
                    idCard: "610122199001011256",
                    wedlock: "已婚",
                    nationId: 1,
                    nativePlace: "陕西",
                    politicId: 13,
                    email: "laowang@qq.com",
                    phone: "18565558897",
                    address: "深圳市南山区",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "劳务合同",
                    tiptopDegree: "本科",
                    specialty: "信息管理与信息系统",
                    school: "深圳大学",
                    beginDate: "2018-01-01",
                    workState: "在职",
                    workID: "00000070",
                    contractTerm: 2.0,
                    conversionTime: "2018-04-01",
                    notworkDate: null,
                    beginContract: "2018-01-01",
                    endContract: "2020-01-01",
                    workAge: null
                },
                tiptopDegrees: ['本科', '大专', '硕士', '博士', '高中', '初中', '小学', '其他'],
                popVisible: false,
                defaultProps: {
                    children: 'children',
                    label: 'name'
                },
                inputDepName: '所属部门',
                empRules: {
                    name: [{required: true, message: '请输入用户名', trigger: 'blur'}],
                    gender: [{required: true, message: '请选择性别', trigger: 'blur'}],
                    birthday: [{required: true, message: '请输入出生日期', trigger: 'blur'}],
                    idCard: [{
                        required: true,
                        message: '请输入身份证号码',
                        trigger: 'blur'
                    }, {
                        pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                        message: '身份证号码格式不正确',
                        trigger: 'blur'
                    }
                    ],
                    wedlock: [{required: true, message: '请选择婚姻状况', trigger: 'blur'}],
                    nationId: [{required: true, message: '请选择民族', trigger: 'blur'}],
                    nativePlace: [{required: true, message: '请输入籍贯', trigger: 'blur'}],
                    politicId: [{required: true, message: '请选择政治面貌', trigger: 'blur'}],
                    email: [{
                        required: true,
                        message: '请输入邮箱',
                        trigger: 'blur'
                    }, {
                        type: 'email',
                        message: '邮箱格式不正确',
                        trigger: 'blur'
                    }],
                    phone: [{required: true, message: '请输入手机号码', trigger: 'blur'}],
                    address: [{required: true, message: '请输入员工地址', trigger: 'blur'}],
                    departmentId: [{required: true, message: '请选择部门名称', trigger: 'blur'}],
                    jobLevelId: [{required: true, message: '请选择职称', trigger: 'blur'}],
                    posId: [{required: true, message: '请选择职位', trigger: 'blur'}],
                    engageForm: [{required: true, message: '请选择聘用形式', trigger: 'blur'}],
                    tiptopDegree: [{required: true, message: '请输入学位', trigger: 'blur'}],
                    specialty: [{required: true, message: '请输入专业', trigger: 'blur'}],
                    school: [{required: true, message: '请输入毕业院校', trigger: 'blur'}],
                    beginDate: [{required: true, message: '请输入入职日期', trigger: 'blur'}],
                    workState: [{required: true, message: '请输入工作状况', trigger: 'blur'}],
                    workID: [{required: true, message: '请输入工号', trigger: 'blur'}],
                    contractTerm: [{required: true, message: '请输入合同期限', trigger: 'blur'}],
                    conversionTime: [{required: true, message: '请输入转正日期', trigger: 'blur'}],
                    notworkDate: [{required: true, message: '请输入离职日期', trigger: 'blur'}],
                    beginContract: [{required: true, message: '请输入合同起始日期', trigger: 'blur'}],
                    endContract: [{required: true, message: '请输入合同结束日期', trigger: 'blur'}],
                    workAge: [{required: true, message: '请输入工龄', trigger: 'blur'}],
                },
                showAdvanceSearchView: false,
                // 高级搜索树内容
                searchInputDepName: '所属部门',
                // 高级搜索的树展开
                searchPopVisible: false,
                // 高级搜索存值对象
                searchValue: {
                    politicId: null,
                    nationId: null,
                    jobLevelId: null,
                    posId: null,
                    engageForm: null,
                    departmentId: null,
                    beginDateScope: null
                },
            }
        },
        mounted() {
            this.initEmps()
            this.initData()
            this.initPositions()
        },
        methods: {
            initPositions() {
                this.getRequest("/employee/basic/positions").then(res => {
                    if (res) {
                        this.positions = res
                    }
                })
            },
            initData() {
                if (!window.sessionStorage.getItem("nations")) {
                    this.getRequest("/employee/basic/nations").then(res => {
                        if (res) {
                            this.nations = res
                            window.sessionStorage.setItem("nations", JSON.stringify(res))
                        }
                    })
                } else {
                    this.nations = JSON.parse(window.sessionStorage.getItem("nations"))
                }
                if (!window.sessionStorage.getItem("politicsstatus")) {
                    this.getRequest("/employee/basic/politicsstatus").then(res => {
                        if (res) {
                            this.politicsstatus = res
                            window.sessionStorage.setItem("politicsstatus", JSON.stringify(res))
                        }
                    })
                } else {
                    this.politicsstatus = JSON.parse(window.sessionStorage.getItem("politicsstatus"))
                }
                if (!window.sessionStorage.getItem("joblevels")) {
                    this.getRequest("/employee/basic/jobLevels").then(res => {
                        if (res) {
                            this.joblevels = res
                            window.sessionStorage.setItem("joblevels", JSON.stringify(res))
                        }
                    })
                } else {
                    this.joblevels = JSON.parse(window.sessionStorage.getItem("joblevels"))
                }
                if (!window.sessionStorage.getItem("allDeps")) {
                    this.getRequest("/employee/basic/deps").then(res => {
                        if (res) {
                            this.allDeps = res
                            window.sessionStorage.setItem("allDeps", JSON.stringify(res))
                        }
                    })
                } else {
                    this.allDeps = JSON.parse(window.sessionStorage.getItem("allDeps"))
                }
            },
            initEmps(type) {
                this.loading = true
                let url = '/employee/basic/?page=' + this.page + '&size=' + this.size;
                // 如果类型是高级搜索则使用高级搜索请求，否则则使用普通搜索请求
                if (type && type == 'advanced') {
                    // 高级搜索中需要判断：如果存在值就添加这个查询字段，如果不存在就不添加
                    if (this.searchValue.politicId) {
                        url += '&politicId=' + this.searchValue.politicId;
                    }
                    if (this.searchValue.nationId) {
                        url += '&nationId=' + this.searchValue.nationId;
                    }
                    if (this.searchValue.jobLevelId) {
                        url += '&jobLevelId=' + this.searchValue.jobLevelId;
                    }
                    if (this.searchValue.posId) {
                        url += '&posId=' + this.searchValue.posId;
                    }
                    if (this.searchValue.engageForm) {
                        url += '&engageForm=' + this.searchValue.engageForm;
                    }
                    if (this.searchValue.departmentId) {
                        url += '&departmentId=' + this.searchValue.departmentId;
                    }
                    if (this.searchValue.beginDateScope) {
                        url += '&beginDateScope=' + this.searchValue.beginDateScope;
                    }
                } else {
                    url +=  "&name=" + this.keyword
                }
                console.log(url);
                this.getRequest(url).then(res => {
                        this.loading = false
                        if (res) {
                            this.total = res.total
                            this.emps = res.data
                        }
                    })
            },
            // 页发生变化
            currentChange(currentPage) {
                this.page = currentPage;
                this.initEmps()
            },
            // 条数发生变化
            sizeChange(currentSize) {
                this.size = currentSize;
                this.initEmps()
            },
            // 添加员工的弹出框
            showAddEmpView() {
                this.emptyEmp();
                this.dialogTitle = '添加员工'
                this.dialogVisible = true
                this.getMaxWorkID()
            },
            getMaxWorkID() {
                this.getRequest("/employee/basic/maxWorkID").then(res => {
                    if (res) {
                        this.emp.workID = res.obj
                    }
                })
            },
            // 所属部门点击打开弹出窗
            showDepView() {
                this.popVisible = !this.popVisible
            },
            // 树的点击事件
            handleNodeClick(data) {
                this.popVisible = !this.popVisible
                this.emp.departmentId = data.id
                this.inputDepName = data.name
            },
            // 添加员工的确认按钮
            doAddEmp() {
                if (this.emp.id) {
                    this.$refs.empForm.validate(valid => {
                        if (valid) {
                            this.putRequest("/employee/basic/", this.emp).then(res => {
                                if (res) {
                                    this.dialogVisible = false
                                    this.initEmps()
                                }
                            })
                        }
                    })
                } else {
                    this.$refs.empForm.validate(valid => {
                        if (valid) {
                            this.postRequest("/employee/basic/", this.emp).then(res => {
                                if (res) {
                                    this.dialogVisible = false
                                    this.initEmps()
                                }
                            })
                        }
                    })
                }
            },
            // 删除员工
            deleteEmp(data) {
                this.$confirm('此操作将永久删除【' + data.name + '】, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("/employee/basic/" + data.id).then(res => {
                        if (res) {
                            this.initEmps();
                        }
                    })
                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });
            },
            // 编辑员工
            showEditEmpView(data) {
                this.inputDepName = data.department.name
                this.initPositions();
                this.dialogVisible = true
                this.dialogTitle = '编辑员工信息'
                this.emp = data
            },
            // 清空emp
            emptyEmp() {
                this.emp = {
                    name: "",
                    gender: "",
                    birthday: "",
                    idCard: "",
                    wedlock: "",
                    nationId: 1,
                    nativePlace: "",
                    politicId: 13,
                    email: "",
                    phone: "",
                    address: "",
                    departmentId: null,
                    jobLevelId: 9,
                    posId: 29,
                    engageForm: "",
                    tiptopDegree: "本科",
                    specialty: "",
                    school: "",
                    beginDate: "",
                    workID: null,
                    contractTerm: 2.0,
                    conversionTime: "",
                    notworkDate: null,
                    beginContract: "",
                    endContract: "",
                    workAge: null
                }
                this.inputDepName = ''
            },
            closeDialog() {
                this.$refs.empForm.resetFields();
            },
            // 导出Excel
            exportData() {
                window.open('/employee/basic/export', '_parent')
            },
            // 上传成功
            onSuccess(response, file, fileList) {
                this.importDataBtnText = '导入数据'
                this.importDataBtnIcon = 'el-icon-upload2'
                this.importDataDisable = false
                this.initEmps()
            },
            // 上传失败
            onError() {
                this.importDataBtnText = '导入数据'
                this.importDataBtnIcon = 'el-icon-upload2'
                this.importDataDisable = false
            },
            // 上传之前
            beforeUpload() {
                this.importDataBtnText = '正在导入'
                this.importDataBtnIcon = 'el-icon-loading'
                this.importDataDisable = true
            },
            // 高级输入框的树点击事件（所属部门）
            searchViewHandleNodeClick(data){
                this.searchPopVisible = !this.searchPopVisible
                this.searchValue.departmentId = data.id
                this.searchInputDepName = data.name
            }
        }
    }
</script>

<style scoped>
    /* 可以设置不同的进入和离开动画 */
    /* 设置持续时间和动画函数 */
    .slide-fade-enter-active {
        transition: all .8s ease;
    }

    .slide-fade-leave-active {
        transition: all .8s cubic-bezier(1.0, 0.5, 0.8, 1.0);
    }

    .slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */
    {
        transform: translateX(10px);
        opacity: 0;
    }
</style>