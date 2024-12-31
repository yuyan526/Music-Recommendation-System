<template>  
	<div id="songsheet">
        <el-button
            id="new_songsheet"    
            size="small"
            type="primary"
            round
            @click="clearDialog"
        >
            新建歌单
        </el-button> 
		<el-table
			:data="usersData"
			stripe
			style="width: 100%"
			:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
		>
			<el-table-column align="center" prop="index" label="" width="80">
				<template #default="scope">
					<span
						style="font-size:16px;width:20px;display:inline-block;"
					>
						{{ scope.$index + 1 }}
					</span>

				</template>
			</el-table-column>
			<el-table-column
				align="center"
				prop="sheetName"
				label="歌单名"
				width="300"
			>
				<template #default="scope">
					<span
						style="cursor:pointer;"
						@click="songSheetDetail(scope.$index)"
					>
						{{ scope.row.sheetName }}
					</span>
				</template>
			</el-table-column>
            <el-table-column
				width="250"
			>
			</el-table-column>
            <el-table-column align="center" prop="address" label="操作" width="250">
				<template #default="scope">
					<el-button
						type="success"
						@click="handleEdit(scope.$index, scope.row)"
						style="margin-right:20px"
                        icon="el-icon-edit"
					>
						编辑
					</el-button>
					<el-button
						@click="
							handleDelete(
								scope.$index,
								scope.row,
								usersData,
							)
						"
						type="danger"
                        icon="el-icon-delete"
						plain
					>
						删除
					</el-button>
				</template>
			</el-table-column>		
		</el-table>        
    	<!-- 新建歌单的弹出框 -->
		<el-dialog title="新建歌单" modal="false" v-model="dialogFormVisibleAdd">
			<el-form :model="form">
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
		<!-- 编辑歌单信息 -->
		<el-dialog title="编辑歌单" modal="false" v-model="dialogFormVisibleEdit">
			<el-form :model="form">
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
	</div>
</template>

<script>
import { 
	getSongSheets,
	_addSheet,
	_deleteSheet,
	_updateSheet, 
} from "../network/request.js"
import { useStore } from "vuex"
export default {
	name: "songsheets",
	data()	{
		const item = {
			uid:"",
			sheetid:"",
			sheetName:"",
		}
		return {
			uid: useStore().state.user.uid,
			usersData: [],
			tableData: Array(10).fill(item),
			// 修改弹出框
			dialogFormVisibleEdit: false,
			// 添加数据弹出框
			dialogFormVisibleAdd: false,
			form: {
				uid:"",
				sheetid: "",
				sheetName: "",
			},
			// 弹出框中输入框的宽度
			formLabelWidth: "120px",
		}
	},
	beforeCreate() {
		const user = useStore().state.user
		console.log(user)
		var useruid = user.uid
		console.log(useruid)
		getSongSheets({ uid: useruid + "" }).then((response) => {
			this.usersData = response.data.data
			console.log(this.usersData)
		})

	},
	created() {
		const user = useStore().state.user
		console.log(user)
		var useruid = user.uid
		console.log(useruid)
		// 判断是否为游客
		if(user.uid == "0"){
			this.$router.push({
				path: "/login",
			})
			this.$message({
				type: "info",
				message: "请先登录或注册一个账号",
			})
		}
		// 页面加载时读取数据
		if (window.sessionStorage.getItem("user")) {
			console.log("页面加载")
			const session_user = JSON.parse(
				window.sessionStorage.getItem("user"),
			)
			console.log(session_user)
			this.$store.commit("SET_USER", session_user)
		}
		// 页面刷新时保存状态
		window.addEventListener("beforeunload", () => {
			console.log("页面刷新")
			console.log(this.$store.state.user)
			window.sessionStorage.setItem(
				"user",
				JSON.stringify(this.$store.state.user),
			)
		})
	},
	methods: {
		// 下一页
		handleCurrentChange(val) {
			console.log(`当前页: ${val}`)
			this.currentPage = val
			// 换页之后改变tableData，重新渲染
			this.usersData = this.usersData.slice(
				(this.currentPage - 1) * this.pageSize,
				this.currentPage * this.pageSize,
			)
			console.log(this.usersData)
		},
		//跳转到歌单信息页面
		songSheetDetail(index) {
			console.log("选择歌单的索引：" + index)
			const sheet = this.usersData[parseInt(index)]
			console.log(sheet)
			this.$router.push({
				name: "mysong",
				params: {
					sheetid: sheet.sheetid,
					sheetName: sheet.sheetName,
				},
			})
		},
		// 清空弹出框中的数据
		clearForm() {
			this.form.uid = ""
			this.form.sheetid = ""
			this.form.sheetName = ""
		},
		// 清空添加歌单对话框的数据
		clearDialog() {
			this.dialogFormVisibleAdd = true
			this.clearForm()
		},
		//添加新建的歌单
		addData() {
			this.dialogFormVisibleAdd = false
			_addSheet({
				uid: this.uid,
				sheetName: this.form.sheetName,
			}).then((response) => {
				if (response.data.code == 200) {
					var useruid = this.uid
					getSongSheets({ uid: useruid + "" }).then((response) => {
						this.usersData = response.data.data
						console.log(this.usersData)
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
					var useruid = this.uid
					// 重新渲染数据
					getSongSheets({
						uid: useruid + ""
					}).then((response) => {
						this.usersData = response.data.data
						console.log(this.usersData)
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
	},
}
</script>

<style scoped>

#new_songsheet{
    position: absolute;
    top: 10px;
    right: 0px;
    left: 1000px;
    width: 100px;
}
/* 字体图标鼠标覆盖变色 */
#songsheet i.iconfont.icon-bofang:hover {
	cursor: pointer;
	color: #f56c6c;
}

#songsheet {
	padding-top: 0;
	position: absolute;
	width: 960px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}
</style>
