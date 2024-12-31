// 游客主界面展示
<template>
	<div id="user-index">
		<el-container class="all-screen" direction="vertical">
			<el-header height="50px">			
				<el-button style="float: right; padding-right: 40px;" type="text" @click="login" >
					登录
				</el-button>                    
			</el-header>
			<el-container>
				<el-aside width="200px">
					<el-menu
						default-active="1"
						class="el-menu-vertical-music"
						text-color="#fff"
						background-color="transparent"
						active-text-color="#f56c6c"
						router="true"
					>
						<el-menu-item route="/discover2" index="1">
							<i
								class="iconfont icon-discover"
								style="padding-right:10px;"
							></i>
							<template #title>音乐浏览</template>
						</el-menu-item>
						<el-menu-item route="/hotmusic2" index="2">
							<i
								class="iconfont icon-new-music"
								style="padding-right:10px;"
							></i>
							<template #title>热门音乐</template>
						</el-menu-item>						
						<el-menu-item route="/recommendsongs2" index="3">
							<i
								class="iconfont icon-musiclist"
								style="padding-right:10px;"
							></i>
							<template #title>私人推荐</template>
						</el-menu-item>
						<el-menu-item route="/recommendusers" index="4">
							<i
								class="iconfont icon-userlist"
								style="padding-right:10px;"
							></i>
							<template #title>相似用户</template>
						</el-menu-item>
						<el-menu-item route="/loveSong" index="5">
							<i
								class="iconfont icon-shoucang_huaban1"
								style="padding-right:10px;"
							></i>
							<template #title>收藏列表</template>
						</el-menu-item>
						<el-menu-item route="/songsheet" index="6">
							<i
								class="el-icon-s-fold"
								style="padding-right:0px;"
							></i>
							<template #title>我的歌单</template>
						</el-menu-item>
						<el-menu-item route="/MyRecord" index="6">
							<i
								class="el-icon-time"
								style="padding-right:0px;"
							></i>
							<template #title>播放记录</template>
						</el-menu-item>
					</el-menu>
				</el-aside>

				<el-container style="border-radius:5px;position:relative;">
					<el-main
						style="background-color:#fff;padding:10px;height:0;flex-grow:1;border-radius:5px;position:relative;"
					>
						<!-- 父组件监听子组件，传递过来的事件要放在router-view标签里面（这里就是playmusic） -->
						<router-view @playmusic="changMP3Url"></router-view>
					</el-main>
					<el-footer
						style="background-color:rgb(241,243,244)"
						height="50px"
					>
						<div>
							<div class="song-info">
								<img
									:src="picUrl"
									alt="图片丢失"
									style="display: inline-block;width:50px;height:50px;"
								/>
								<div>
									<span
										style="display:inline-block;width:100px;overflow:hidden;font-size:14px;"
									>
										{{ songName }}
									</span>
									<span
										style="display:inline-block;width:100px;overflow:hidden;font-size:14px;color:#9b9b9b;"
									>
										{{ "-  " + singerName }}
									</span>
								</div>

							</div>
							<audio
								id="audio"
								:src="MP3Url"
								controls="controls"
								loop="loop"
								@timeUpdate="audioTimeUpdate"
								style="width:1100px;height:50px;right: 0;position: absolute;"
							></audio>
						</div>
					</el-footer>
				</el-container>
			</el-container>
		</el-container>
		
		
	</div>
</template>
<script>
import { ref } from "vue"
import { useStore } from "vuex"
import { _updataPw, _updateUser, _addRecord } from "../network/request"

export default {
	name: "VisitorMain",
	created() {
		const user = useStore().state.user
		console.log(useStore().state)
		this.state = useStore().state
		this.uid = user.uid
		this.gender = user.gender
		this.age = user.age
		this.area = user.area
		this.des = user.des
		this.registerTime = user.registerTime
		this.name = user.name
		this.password = user.password
		console.log("用户名：" + user.name)

		// 页面加载时读取数据
		if (window.sessionStorage.getItem("user")) {
			console.log("页面加载")
			const session_user = JSON.parse(
				window.sessionStorage.getItem("user"),
			)
			console.log(session_user)
			this.$store.commit("SET_USER", user)
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
	data() {
		return {
			state: useStore().state,
			uid: ref("uid"),
			name: ref("初始名"),
			gender: ref("性别"),
			age: ref(""),
			area: ref(""),
			registerTime: ref(""),
			des: ref("个人描述"),
			password: ref(""),
			oldPassword: ref(""),
			newPassword: ref(""),
			repeatNewPassword: ref(""),

			iid: "",
			// 歌曲搜索关键词输入
			searchInput: ref(""),
			// 歌曲信息
			songName: ref("遇见"),
			singerName: ref("孙燕姿"),
			MP3Url: ref(
				"http://m10.music.126.net/20210601101449/f548450009d6fd68ac324f1fff77570c/ymusic/f189/538f/29a0/aa78032d24c11eb57283eb577a26a6dd.mp3",
			),
			picUrl: ref(
				"https://p1.music.126.net/KZ0VfIoFYsxpjz9sTQuLVQ==/17687843556430013.jpg",
			),

			// 是否正在播放歌曲
			audioIsPlaying: false,
			// 是否显示用户信息弹出框
			userinfoDialogVisibleEdit: false,
			// 是否显示修改密码弹出框
			pwDialogVisibleEdit: false,
			// 弹出框中输入框的宽度
			formLabelWidth: "120px",
			// 当前歌曲是否添加播放记录
			isAddRecord: false,
			// 密码显示与隐藏，默认隐藏
			pwType: "password",
		}
	},

	methods: {
		// 游客回到登录界面
		login(){
			this.$router.push({
				path: "/login",
			})
		},
		// 清空修改密码表单
		clearChangePwForm() {
			this.oldPassword = ref("")
			this.newPassword = ref("")
			this.repeatNewPassword = ref("")
		},
		// 更新用户信息
		updateInfo() {
			const user = this.state.user
			user.name = this.name
			user.area = this.area
			user.des = this.des
			user.age = this.age
			user.gender = this.gender
			console.log(user)
			this.$store.commit("SET_USER", user)
		},
		// audio标签播放
		autoPlay() {
			const audio = document.getElementById("audio")
			audio.play()
		},
		//  audio标签暂停并重新加载
		closePlay() {
			const audio = document.getElementById("audio")
			audio.pause()
			audio.load()
		},
		// 接受子组件传过来的MP3Url，并切换MP3Url，然后audio切歌
		changMP3Url(
			valueIid,
			valueMP3Url,
			valuePicUrl,
			valueSongName,
			valueSingerName,
		) {
			// changMP3Url(valueMP3Url) {
			console.log("开始接收事件及参数")
			this.iid = valueIid
			console.log(valueMP3Url)
			this.MP3Url = valueMP3Url
			console.log(valuePicUrl)
			this.picUrl = valuePicUrl
			console.log(valueSongName)
			this.songName = valueSongName
			console.log(valueSingerName)
			this.singerName = valueSingerName
			console.log("接收事件及参数结束")
			// 切换后播放新的音乐
			console.log("关闭正在播放的音乐")
			this.closePlay()
			console.log("播放新的音乐")
			this.autoPlay()
			// 切歌时修改播放记录标志为false
			this.isAddRecord = false
		},
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
					// 清空登录状态内容
					window.sessionStorage.clear()
					//   跳转到登录页
					this.$router.push("/login")
				})

				.catch(() => {
					this.$message({
						type: "info",
						message: "已取消退出",
					})
				})
		},
		// 更新用户信息，用户名、性别、年龄、所在地区、个人简介等
		updateUser() {
			this.$confirm("此操作将永久更新用户信息, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					_updateUser({
						uid: this.uid,
						name: this.name,
						gender: this.gender,
						age: this.age,
						area: this.area,
						des: this.des,
					}).then((response) => {
						if (response.data.code == 200) {
							this.$message({
								type: "success",
								message: "更新成功!",
							})
							this.userinfoDialogVisibleEdit = false
							// 更新状态管理中的用户信息
							this.updateInfo()
						} else {
							this.$message({
								type: "info",
								message: "更新失败!",
							})
							this.userinfoDialogVisibleEdit = false
						}
					})
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "已取消更新",
					})
				})
		},
		// 弹出更新密码提示框
		changePwDialog() {
            this.pwDialogVisibleEdit = true
			console.log("清空内容")
			// 清空修改密码表单内容
			this.clearChangePwForm()
		},
		// 更新密码
		updatePw() {
			this.$confirm("此操作将永久更新密码, 是否继续?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
					console.log(this.oldPassword == "")
					if (
						this.oldPassword == "" ||
						this.newPassword == "" ||
						this.repeatNewPassword == ""
					) {
						console.log("密码为空，不可修改")
						this.$message({
							type: "warning",
							message: "密码为空，修改失败！",
						})
						// 弹出框隐藏
						this.pwDialogVisibleEdit = false
						return false
					}
					if (this.newPassword === this.repeatNewPassword) {
						_updataPw({
							uid: this.uid,
							oldPw: this.oldPassword,
							newPw: this.newPassword,
						}).then((response) => {
							console.log(response.data)
							if (response.data.code == 200) {
								this.$message({
									type: "success",
									message: "更新成功!",
								})
								this.password = this.newPassword
								console.log("更新密码")
								this.pwDialogVisibleEdit = false
							} else {
								this.$message({
									type: "warning",
									message: "更新失败!",
								})
								console.log("更新失败")
								this.pwDialogVisibleEdit = false
							}
						})
					} else {
						this.$message({
							type: "warning",
							message: "两次新密码输入不同，更新失败!",
						})
					}
				})
				.catch(() => {
					this.$message({
						type: "info",
						message: "已取消更新",
					})
				})
		},
		// 搜索歌曲
		searchSongs() {
			console.log("关键字：" + this.searchInput)
			console.log("搜索歌曲")
			//
			this.$router.push({
				path: "/searchMusic",
				query: {
					keyword: this.searchInput,
				},
			})
		},
		// 点击清空时触发事件，跳转到discover界面
		clearSearchInput() {
			console.log("清空搜索框内容")
			this.$router.push("/discover")
		},
		// 听歌时间超过30秒则添加到播放记录中
		audioTimeUpdate() {
			console.log("准备添加播放记录")
			var audio = document.getElementById("audio")
			var currTime = audio.currentTime
			console.log(currTime)
			// 超过30秒则添加播放记录
			if (!this.isAddRecord && currTime > 30) {
				console.log("正在添加播放记录")
				_addRecord({
					uid: this.uid,
					iid: this.iid,
				}).then((response) => {
					console.log(response.data.data)
					if (response.data.code == 200) {
						console.log("添加成功")
						// 添加记录成功依次之后，本首歌时间更新则不再重复添加
						this.isAddRecord = true
					}
				})
			}
		},
		// 更改密码显示
		changePwType() {
			console.log("改变密码显示类型")
			if (this.pwType == "password") {
				console.log("改变密码显示类型为明文")
				this.pwType = "name"
			} else {
				console.log("改变密码显示类型为密文")
				this.pwType = "password"
			}
		},
	},
}
</script>

<style scoped src="../assets/css/main.css">

/* 密码显示与隐藏样式 */
#user-index >>> .display {
	cursor: pointer;
	color: #f56c6c;
}
#user-index >>> .hide {
	cursor: pointer;
}

</style>
