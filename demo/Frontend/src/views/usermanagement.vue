<template>
    <div class="admin-background"></div>
	<el-container id="all-screen">
		<el-aside>
			<el-menu
				:default-openeds="['1', '3']"
				text-color="#fff"
				active-text-color="rgba(48, 49, 51, 0.5)"
				background-color="rgba(48, 49, 51, 0.5)"
				router="true"
			>
				<el-submenu index="1" router="true">
					<template #title>
						<i class="el-icon-setting"></i>
						个性化音乐推荐系统后台管理
					</template>
					<el-menu-item-group>
						<el-menu-item route="/adminMenu" index="1-1">
						<template #title>音乐管理</template>
						</el-menu-item>
					</el-menu-item-group>
					<el-menu-item-group>
						<el-menu-item route="/usermanagement" index="1-2">
						<template #title>用户管理</template>
						</el-menu-item>
					</el-menu-item-group>
					<el-menu-item-group>
						<el-menu-item route="/sheetmanagement" index="1-3">
						<template #title>歌单管理</template>
						</el-menu-item>
					</el-menu-item-group>
					<el-menu-item-group>
						<el-menu-item route="/datastatistics" index="1-4">
						<template #title>数据统计</template>
						</el-menu-item>
					</el-menu-item-group>
				</el-submenu>
			</el-menu>
		</el-aside>

		<el-container>
			<el-header
				style="text-align: right; font-size: 15px;position:relative;"
			>
				<el-input
					style="margin-left:10px;width:400px;height:50px;position:absolute;right:500px;"
					placeholder="请输入用户名"
					v-model="inputSearch"
					prefix-icon="el-icon-search"
					clearable
					@keyup.enter="searchUsers"
					@clear="clearSearchInput"
				></el-input>
				<div
					style="display:inline-block;position:absolute;left:10px;width:200px;text-align:center;"
				>
					<el-button
						style="margin-right:10px;"
						size="small"
						type="primary"
						round
						@click="clearDialog"
					>
						添加数据
					</el-button>

					<el-button
						size="small"
						type="danger"
						round
						@click="handleDeleteMore"
					>
						批量删除
					</el-button>
				</div>

				<el-dropdown
					style="position:absolute;top:5px;right:20px;width:100px;height:50px;text-align:center;color:#fff;"
				>
					<i
						class="el-icon-user-solid"
						style="margin-right:10px;"
					></i>
					<span>{{ adminName }}</span>
					<template #dropdown>
						<el-dropdown-menu>
							<el-dropdown-item @click="logOut">
								退出
							</el-dropdown-item>
						</el-dropdown-menu>
					</template>
				</el-dropdown>
			</el-header>

			<el-main>
				<!-- 可以进行多选的表格 -->
				<el-table
					ref="multipleTable"
					:data="tableData"
					@selection-change="handleSelectionChange"
					:header-cell-style="{
						fontWeight: 'normal',
						border: 'none',
					}"
				>
					<el-table-column
						align="center"
						type="selection"
						width="50"
					></el-table-column>

					<el-table-column
						align="center"
						prop="uid"
						label="用户id"
						width="150"
					></el-table-column>
					<el-table-column
						align="center"
						prop="name"
						label="用户名"
						width="200"
					>
						<template #default="scope">
							{{ scope.row.name }}
						</template>
					</el-table-column>
					<el-table-column
						align="center"
						prop="gender"
						label="性别"
						width="150"
					>
						<template #default="scope">
							<span>{{ scope.row.gender }}</span>
						</template>
					</el-table-column>
					<el-table-column
						align="center"
						prop="age"
						label="年龄"
						width="200"
					>
						<template #default="scope">
							<span>{{ scope.row.age }}</span>
						</template>
					</el-table-column>
					<el-table-column
						align="center"
						prop="area"
						label="所在区域"
						width="150"
					>
						<template #default="scope">
							<span>{{ scope.row.area }}</span>
						</template>
					</el-table-column>

					<el-table-column align="center" prop="address" label="操作">
						<template #default="scope">
							<el-button
								type="primary"
								@click="handleEdit(scope.$index, scope.row)"
								style="margin-right:20px"
							>
								编辑
							</el-button>

							<el-button
								@click="
									handleDelete(
										scope.$index,
										scope.row,
										tableData,
									)
								"
								type="danger"
								plain
							>
								删除
							</el-button>
						</template>
					</el-table-column>
				</el-table>
				<!-- 分页 -->
				<el-pagination
					background
					layout="prev, pager, next"
					:total="totalNum"
					:current-page="currentPage"
					style="padding-top:30px;"
					@current-change="handleCurrentChange"
				></el-pagination>
			</el-main>
		</el-container>
    </el-container>
    <!-- 添加数据的弹出框 -->
	<el-dialog title="添加用户" modal="false" v-model="dialogFormVisibleAdd">
		<el-form :model="form">
			<el-form-item label="用户名" :label-width="formLabelWidth">
				<el-input
					v-model="form.name"
					autocomplete="off"
					placeholder="请输入姓名"
				></el-input>
			</el-form-item>
			<el-form-item label="性别" :label-width="formLabelWidth">
				<el-input
					v-model="form.gender"
					autocomplete="off"
					placeholder="请输入性别"
				></el-input>
			</el-form-item>
			<el-form-item label="年龄" :label-width="formLabelWidth">
				<el-input
					v-model="form.age"
					autocomplete="off"
					placeholder="请输入年龄"
				></el-input>
			</el-form-item>
			<el-form-item label="所在区域" :label-width="formLabelWidth">
				<el-input
					v-model="form.area"
					autocomplete="off"
					placeholder="请输入所在区域"
				></el-input>
			</el-form-item>
            <el-form-item label="个人介绍" :label-width="formLabelWidth">
				<el-input
					v-model="form.des"
					autocomplete="off"
					placeholder="请输入个人介绍"
				></el-input>
			</el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth">
				<el-input
					v-model="form.password"
					autocomplete="off"
					placeholder="请输入密码"
				></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="dialogFormVisibleAdd = false">
					取 消
				</el-button>
				<el-button type="primary" @click="addData">
					确 定
				</el-button>
			</span>
		</template>
	</el-dialog>
    <!-- 更新用户信息 -->
	<el-dialog title="更新用户" modal="false" v-model="dialogFormVisibleEdit">
		<el-form :model="form">
			<el-form-item label="用户id" :label-width="formLabelWidth">
				<el-input
					v-model="form.uid"
					autocomplete="off"
					disabled="true"
				></el-input>
			</el-form-item>
			<el-form-item label="用户名" :label-width="formLabelWidth">
				<el-input
					v-model="form.name"
					autocomplete="off"
					placeholder="请输入姓名"
				></el-input>
			</el-form-item>
			<el-form-item label="性别" :label-width="formLabelWidth">
				<el-input
					v-model="form.gender"
					autocomplete="off"
					placeholder="请输入性别"
				></el-input>
			</el-form-item>
			<el-form-item label="年龄" :label-width="formLabelWidth">
				<el-input
					v-model="form.age"
					autocomplete="off"
					placeholder="请输入年龄"
				></el-input>
			</el-form-item>
			<el-form-item label="所在区域" :label-width="formLabelWidth">
				<el-input
					v-model="form.area"
					autocomplete="off"
					placeholder="请输入所在区域"
				></el-input>
			</el-form-item>
		</el-form>
		<template #footer>
			<span class="dialog-footer">
				<el-button @click="dialogFormVisibleEdit = false">
					取 消
				</el-button>
				<el-button type="primary" @click="submitEdit">
					确 定
				</el-button>
			</span>
		</template>
	</el-dialog>
</template>

<script>
import { 
    _getDisplayUsers,
	_deleteUser,
	_updateUser,
	_register,
	_getSearchUsers,
} from "../network/request.js"
export default{
    name: "usermanagement",
    data() {
		const item = {
			uid: "",
			name: "",
			gender: "",
			age: "",
			area: "",
		}
		return {
			adminName: "管理员",
			tableData: Array(10).fill(item),
            // 搜索框输入
			inputSearch: "",
			// 修改弹出框
			dialogFormVisibleEdit: false,
			// 添加数据弹出框
			dialogFormVisibleAdd: false,
			form: {
				uid: "",
				name: "",
				gender: "",
				age: "",
				area: "",
			},
			// 弹出框中输入框的宽度
			formLabelWidth: "120px",
			// 当前页面
			currentPage: 1,
			pageSize: 10,
			totalNum: 3580,
			// 选中行的数据
			multipleSelection: [],
		}
	},
    created() {
		this.adminName = this.$route.query.name
		// 获得所有档案数据
		_getDisplayUsers({ currentPage: "1" }).then((response) => {
			this.tableData = response.data.data
			console.log(this.tableData)
		})
	},
    methods: {
		// 退出登录
		logOut() {
			this.$confirm("是否退出?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					this.$message({
						type: "success",
						message: "退出成功!",
					})
					// 跳转到登录页
					this.$router.push("/admin")
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "已取消退出",
					})
				})
		},
		// 取消查询，恢复所有数据
		clearSearchInput() {
			_getDisplayUsers({ currentPage: "1" }).then((response) => {
				this.tableData = response.data.data
			})
			this.inputSearch = ""
		},
		// 清空弹出框中的数据
		clearForm() {
			this.form.uid = ""
			this.form.name = ""
			this.form.gender = ""
			this.form.age = ""
			this.form.area = ""
		},
		// 清空添加用户对话框的数据
		clearDialog() {
			this.dialogFormVisibleAdd = true
			this.clearForm()
		},
		// 点击弹出框的确定，添加数据
		addData() {
			this.dialogFormVisibleAdd = false
			_register({
				name: this.form.name,
				gender: this.form.gender,
				age: this.form.age,
				area: this.form.area,
                des: this.form.des,
                password: this.form.password,
			}).then((response) => {
				if (response.data.code == 200) {
					_getDisplayUsers({ currentPage: "1" }).then((response) => {
						this.tableData = response.data.data
						console.log(this.tableData)
					})
					this.$alert(
						"用户id：" + response.data.data.uid,
						"添加提示",
						{
							confirmButtonText: "确定",
							callback: () => {
								this.$message({
									type: "success",
									message: "添加成功",
								})
							},
						},
					)
				} else {
					// 弹出提示
					this.$message({
						type: "info",
						message: "添加失败!",
					})
				}
			})
		},
		// 点击编辑按钮开始编辑
		handleEdit(index, row) {
			// 打印行号和行的内容
			console.log(index, row)
			this.dialogFormVisibleEdit = true
			this.form.uid = row.uid
			this.form.name = row.name
			this.form.gender = row.gender
			this.form.age= row.age
			this.form.area = row.area
		},
		// 提交编辑
		submitEdit() {
			this.dialogFormVisibleEdit = false
			_updateUser({
				uid: this.form.uid,
				name: this.form.name,
				gender: this.form.gender,
				age: this.form.age,
                area: this.form.area,
			}).then((response) => {
				if (response.data.code == 200) {
					// 重新渲染数据
					_getDisplayUsers({
						currentPage: this.currentPage + "",
					}).then((response) => {
						this.tableData = response.data.data
						console.log(this.tableData)
					})
					this.$message({
						type: "success",
						message: "更新成功!",
					})
				} else {
					// 弹出提示
					this.$message({
						type: "info",
						message: "更新失败!",
					})
				}
			})
		},
		// 单行删除，index为当前行在数组中的下标，row为当前行的内容，rows为所有行数组
		handleDelete(index, row, rows) {
			this.$confirm("是否删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
                    console.log(row.uid)
					// 发出删除请求
					_deleteUser({ uid: row.uid }).then((response) => {
						if (response.data.code == 200) {
							// 页面上删除
							rows.splice(index, 1)
							// 删除成功提示信息
							this.$message({
								type: "success",
								message: "删除成功!",
							})
						} else {
							// 删除失败提示信息
							this.$message({
								type: "info",
								message: "删除失败!",
							})
						}
					})
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "取消删除",
					})
				})
		},
		// 批量删除
		handleDeleteMore() {
			// 判断是否选中
			if (this.multipleSelection.length == 0) {
				// 弹出提示
				this.$message({
					type: "info",
					message: "未选择数据，请重试!",
				})
				return false
			}
			this.$confirm("是否删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					for (var i = 0; i < this.multipleSelection.length; i++) {
						_deleteUser({
							uid: this.multipleSelection[i].uid,
						}).then((response) => {
							if (response.data.code == 200) {
								this.$message({
									type: "success",
									message: "正在删除",
								})
							} else {
								// 弹出提示
								this.$message({
									type: "info",
									message: "删除失败!",
								})
							}
						})
					}
					// 删除完成
					this.$message({
						type: "success",
						message: "删除完成",
					})
					// 刷新所有档案数据
					_getDisplayUsers({ currentPage: "1" }).then((response) => {
						this.tableData = response.data.data
						console.log(this.tableData)
					})
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "已取消删除",
					})
				})
		},
		// 多选处理
		handleSelectionChange(val) {
			console.log(val)
			this.multipleSelection = val
			console.log(this.multipleSelection)
		},
		// 下一页
		handleCurrentChange(val) {
			// 得到用户的详情信息
			_getDisplayUsers({ currentPage: val }).then((response) => {
				this.tableData = response.data.data
				console.log(this.tableData)
			})
		},
		// 搜索用户
		searchUsers() {
			_getSearchUsers({ keyword: this.inputSearch }).then((response) => {
				this.tableData = response.data.data
				console.log(this.tableData)
			})
		},
	},
}
</script>


<style scoped>
#all-screen {
	position: absolute;
	padding: 0px;
	margin: 0px;
	height: 100%;
	width: 100%;
	bottom: 0;
}
.el-header {
	background-color: rgba(48, 49, 51, 0.5);
	color: #fff;
	line-height: 60px;
}

#all-screen .el-aside {
	color: #fff;
	background-color: rgba(48, 49, 51, 0.5);
	width: 300px;
}

#all-screen .el-aside .el-menu {
	color: #fff;
	margin-left: -30px;
	background-color: rgba(48, 49, 51, 0.5);
	border: none;
}
#all-screen .el-aside .el-menu:hover {
	background-color: rgba(48, 49, 51, 0.5);
}

#all-screen .el-aside .el-menu .el-submenu__title:hover {
	background-color: rgba(48, 49, 51, 0.5);
	color: #fff !important;
}

#all-screen .el-aside .el-menu .el-menu-item {
	color: #fff;
	background-color: #303133;
}

/* 表格样式 */
#all-screen .fail {
	color: #9b9b9b;
	cursor: not-allowed;
}
#all-screen .succ {
	/* pointer-events: none; */
	/* color: #9b9b9b;
	cursor: not-allowed; */
}

.v-modal {
	background-color: rgba(0, 0, 0, 0.2);
}
.admin-background {
	background: url("~@/assets/imgs/admin_back.webp") no-repeat center center;
	background-size: 100% 100%;
	background-attachment: fixed;
	background-color: #cccccc;
	z-index: -1;
	position: absolute;
	width: 100%;
	height: 100%;
}
</style>