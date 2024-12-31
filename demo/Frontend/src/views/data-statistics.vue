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
				<!-- 统计项 -->
				<el-table
					:data="totalData"
				>
                    <el-table-column
						width="50"
					>
                    </el-table-column>
                    <el-table-column
						align="center"
						prop="totalsongs"
						label="歌曲总数"
						width="200"
					>
                    	<template #default="scope">
							{{ scope.row.totalsongs }}
						</template>
                    </el-table-column>
					<el-table-column
						align="center"
						prop="totalusers"
						label="用户总数"
						width="200"
					>
                        <template #default="scope">
							{{ scope.row.totalusers }}
						</template>
                    </el-table-column>
					<el-table-column
						align="center"
						prop="totalsheets"
						label="歌单总数"
						width="200"
					>
						<template #default="scope">
							{{ scope.row.totalsheets }}
						</template>
					</el-table-column>
                    <el-table-column
						align="center"
						prop="totalsingers"
						label="歌手总数"
						width="200"
					>
						<template #default="scope">
							{{ scope.row.totalsingers }}
						</template>
					</el-table-column>
                    <el-table-column
						align="center"
						prop="totaladmins"
						label="管理员总数"
						width="200"
					>
						<template #default="scope">
							{{ scope.row.totaladmins }}
						</template>
					</el-table-column>
				</el-table>
			</el-main>
		</el-container>
    </el-container>
    
</template>

<script>
import { 
    _gettotal,
} from "../network/request.js"
export default{
    name: "datastatistics",
    data() {
		const item = {
			totalsongs: "",
            totalusers:"",
			totalsheets: "",
            totalsingers: "",
            totaladmins: "",
		}
		return {
			adminName: "管理员",
			totalData: Array(1).fill(item),

		}
	},
    created() {
		this.adminName = this.$route.query.name
		// 获得所有档案数据
		_gettotal({
            totalsongs: this.totalsongs,
            totalusers: this.totalusers,
			totalsheets: this.totalsheets,
            totalsingers: this.totalsingers,
            totaladmins: this.totaladmins,
        }).then((response) => {
			this.totalData = response.data.data
			console.log(this.totalData)
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