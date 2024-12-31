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
					placeholder="请输入歌单名"
					v-model="inputSearch"
					prefix-icon="el-icon-search"
					clearable
					@keyup.enter="searchSheets"
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
						width="200"
					></el-table-column>
					<el-table-column
						align="center"
						prop="sheetid"
						label="歌单id"
						width="200"
					></el-table-column>
					<el-table-column
						align="center"
						prop="sheetName"
						label="歌单名"
						width="200"
					>
						<template #default="scope">
							{{ scope.row.sheetName }}
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
	<el-dialog title="添加歌单" modal="false" v-model="dialogFormVisibleAdd">
		<el-form :model="form">
			<el-form-item label="用户id" :label-width="formLabelWidth">
				<el-input
					v-model="form.uid"
					autocomplete="off"
					placeholder="请输入用户id"
				></el-input>
			</el-form-item>
			<el-form-item label="歌单名" :label-width="formLabelWidth">
				<el-input
					v-model="form.sheetName"
					autocomplete="off"
					placeholder="请输入歌单名"
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
	<el-dialog title="更新歌单" modal="false" v-model="dialogFormVisibleEdit">
		<el-form :model="form">
			<el-form-item label="用户id" :label-width="formLabelWidth">
				<el-input
					v-model="form.uid"
					autocomplete="off"
					disabled="true"
				></el-input>
			</el-form-item>
			<el-form-item label="歌单id" :label-width="formLabelWidth">
				<el-input
					v-model="form.sheetid"
					autocomplete="off"
					disabled="true"
				></el-input>
			</el-form-item>
			<el-form-item label="歌单名" :label-width="formLabelWidth">
				<el-input
					v-model="form.sheetName"
					autocomplete="off"
					placeholder="请输入歌单名"
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
    _getDisplaySheets,
    _addSheet,
	_deleteSheet,
	_updateSheet,
	_getSearchSheets,
} from "../network/request.js"
export default{
    name: "sheetmanagement",
    data() {
		const item = {
			uid: "",
            sheetid:"",
			sheetName: "",
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
                sheetid:"",
				sheetName: "",
			},
			// 弹出框中输入框的宽度
			formLabelWidth: "120px",
			// 当前页面
			currentPage: 1,
			pageSize: 10,
			totalNum: 20,
			// 选中行的数据
			multipleSelection: [],
		}
	},
    created() {
		this.adminName = this.$route.query.name
		// 获得所有档案数据
		_getDisplaySheets({ currentPage: "1" }).then((response) => {
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
			_getDisplaySheets({ currentPage: "1" }).then((response) => {
				this.tableData = response.data.data
			})
			this.inputSearch = ""
		},
		// 清空弹出框中的数据
		clearForm() {
			this.form.uid = ""
			this.form.sheetid = ""
            this.form.sheetName = ""
		},
		// 清空添加歌曲对话框的数据
		clearDialog() {
			this.dialogFormVisibleAdd = true
			this.clearForm()
		},
		// 点击弹出框的确定，添加数据
		addData() {
			this.dialogFormVisibleAdd = false
			_addSheet({
				uid: this.form.uid,
                sheetid: this.form.sheetid,
				sheetName: this.form.sheetName,
			}).then((response) => {
				if (response.data.code == 200) {
					_getDisplaySheets({ currentPage: "1" }).then((response) => {
						this.tableData = response.data.data
						console.log(this.tableData)
					})
					this.$alert(
						"歌单id：" + response.data.data.sheetid,
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
            this.form.sheetid = row.sheetid
			this.form.sheetName = row.sheetName
		},
		// 提交编辑
		submitEdit() {
			this.dialogFormVisibleEdit = false
			_updateSheet({
				uid:this.uid,
				sheetid: this.form.sheetid,
				sheetName: this.form.sheetName,
			}).then((response) => {
				if (response.data.code == 200) {
					// 重新渲染数据
					_getDisplaySheets({
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
                    console.log(row.sheetid)
					// 发出删除请求
					_deleteSheet({ sheetid: row.sheetid }).then((response) => {
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
						_deleteSheet({
							sheetid: this.multipleSelection[i].sheetid,
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
					_getDisplaySheets({ currentPage: "1" }).then((response) => {
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
			_getDisplaySheets({ currentPage: val }).then((response) => {
				this.tableData = response.data.data
				console.log(this.tableData)
			})
		},
		// 搜索用户
		searchSheets() {
			_getSearchSheets({ keyword: this.inputSearch }).then((response) => {
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