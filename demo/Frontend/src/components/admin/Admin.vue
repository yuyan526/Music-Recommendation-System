<template>
	<div class="admin-background"></div>

	<div id="admin-login-form">
		<div
			style="position:absolute;top:5px;left:100px;font-weight:bolder;color:#606266"
		>
			个性化音乐推荐系统后台管理
		</div>
		<el-form
			:model="ruleForm"
			status-icon
			:rules="rules"
			ref="ruleForm"
			label-width="100px"
			class="demo-ruleForm"
		>
			<el-form-item label="用户名：" prop="username">
				<el-input
					type="name"
					v-model="ruleForm.username"
					autocomplete="off"
					placeholder="请输入用户名"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item label="密码：" prop="password">
				<el-input
					type="password"
					v-model="ruleForm.password"
					autocomplete="off"
					placeholder="请输入密码"
					clearable
				></el-input>
			</el-form-item>
			<el-form-item
				style="padding-top:25px;margin-bottom:0;margin-right:0;margin-left:-100px;"
			>
				<el-button type="primary" @click="submitForm('ruleForm')">
					登录
				</el-button>
				<el-button
					style="margin-left:20px;"
					@click="resetForm('ruleForm')"
				>
					重置
				</el-button>
			</el-form-item>
		</el-form>
	</div>
</template>

<script>
export default {
	name: "admin",
	data() {
		// 校验数据，见Element-Plus
		var validateUsername = (rule, value, callback) => {
			if (value === "") {
				callback(new Error("请输入用户名"))
			} else {
				if (this.ruleForm.username !== "") {
					this.$refs.ruleForm.validateField("username")
				}
				callback()
			}
		}
		var validatePass = (rule, value, callback) => {
			if (value === "") {
				callback(new Error("请输入密码"))
			} else {
				callback()
			}
		}
		return {
			// 表单数据
			ruleForm: {
				username: "",
				password: "",
			},
			// 表单验证规则，见Element-Plus
			rules: {
				username: [{ validator: validateUsername, trigger: "blur" }],
				password: [{ validator: validatePass, trigger: "blur" }],
			},
		}
	},
	methods: {
		// 重置表单内容
		resetForm(formName) {
			this.$refs[formName].resetFields()
		},
		login() {
			this.$axios
				.post(
					"/adminLogin",
					JSON.stringify({
						name: this.ruleForm.username,
						password: this.ruleForm.password,
					}),
					{ headers: { "content-type": "application/json" } },
				)
				.then((response) => {
					console.log(response)
					console.log(response.data.data.password)
					if (response.data.data.password == this.ruleForm.password) {
						this.$router.push({
							path: "/adminMenu",
							query: {
								name: this.ruleForm.username,
							},
						})
						this.$message({
							type: "success",
							message: "登录成功!",
						})
					} else {
						// 重置表单
						this.resetForm("ruleForm")
						// 弹出提示
						this.$message({
							type: "info",
							message: "用户名或密码错误，登录失败!",
						})
					}
				})
		},
		// 提交表单的事件处理
		submitForm(formName) {
			this.$refs[formName].validate((valid) => {
				if (valid) {
					this.login()
				} else {
					console.log("error submit!!")
					return false
				}
			})
		},
	},
}
</script>

<style scoped>

#admin-login-form {
	z-index: 1;
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	margin: auto;
	width: 400px;
	height: 300px;
	background-color: rgba(204, 204, 204, 0.6);
	border-radius: 10px;
}

#admin-login-form .el-form {
	position: absolute;
	top: 0;
	bottom: 0;
	left: 0;
	right: 0;
	margin: auto;
	height: 200px;
	width: 400px;
}
#admin-login-form .el-form .el-form-item {
	margin-right: 30px;
}
#admin-login-form .el-form .el-form-item .el-form-item__content {
	margin-left: 0 !important;
}

.admin-background {
	background: url("../../assets/imgs/admin_login.webp") no-repeat center
		center;
	background-size: cover;
	background-attachment: fixed;
	background-color: #cccccc;
	z-index: -1;
	position: absolute;
	width: 100%;
	height: 100%;
}
</style>
