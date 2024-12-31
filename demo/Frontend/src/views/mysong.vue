<template>
	<div id="user-detail">
		<!-- 歌单信息 -->
		<div id="user">
			<div class="user-img">
				<img src="../assets/imgs/10001.jpg" alt="加载失败" />
			</div>
			<div class="user-info">
				<div class="user-name-gender">
					<span>{{ sheetName }}</span>
				</div>
			</div>
		</div>
		<!-- 间隔 -->
		<div class="record-interval">
			<span>
				歌曲列表
			</span>
            <el-button
                id="add_song"    
                size="small"
                type="primary"
                round
                @click="clearDialog"
            >
                添加歌曲
            </el-button> 
		</div>
		<!-- 用户歌曲列表 -->
		<div id="user-record">
			<el-table
				:data="songsData"
				stripe
				style="width: 100%;"
				:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
			>
				<el-table-column
					align="center"
					prop="index"
					label=""
					width="170"
				>
					<template #default="scope">
						<div
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						>
							<span
								style="font-size:16px;width:20px;display:inline-block;"
							>
								{{ scope.$index + 1 }}
							</span>
							<i
								class="iconfont icon-bofang"
								style="margin-left:10px;font-size:18px;width:20px;display:inline-block;"
								@click="startPlay(scope.$index)"
								:class="
									scope.row.downUrl == 'None'
										? 'fail'
										: 'succ'
								"
							></i>
							<i
								class="iconfont icon-shoucang_huaban1"
								style="margin-left:10px;font-size:18px;width:20px;display:inline-block;"
								@click="deleteLove(scope.$index)"
								:class="[
									scope.row.downUrl == 'None'
										? 'fail'
										: 'succ',
									scope.row.isLove === true ? 'active' : '',
								]"
							></i>
                            <i
                                @click="
                                    handleDelete(
                                        scope.$index,
                                        scope.row,
                                        songsData,
                                    )
                                "
                                style="margin-left:10px;font-size:18px;display:inline-block;"
                                class="el-icon-delete"
                                plain
                            >
                            </i>
						</div>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="songName"
					label="歌曲名"
					width="220"
				>
					<template #default="scope">
						<span
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						>
							{{ scope.row.songName }}
						</span>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="singerName"
					label="歌手"
					width="220"
				>
					<template #default="scope">
						<span
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						>
							{{ scope.row.singerName }}
						</span>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="album"
					label="专辑"
					width="300"
				>
					<template #default="scope">
						<span
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						>
							{{ scope.row.album }}
						</span>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="songTime"
					label="时长"
					width="200"
				>
					<template #default="scope">
						<span
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						>
							{{ scope.row.songTime }}
						</span>
					</template>
				</el-table-column>
			</el-table>
		</div>
        <!-- 添加歌曲的弹出框 -->
		<el-dialog title="添加歌曲" modal="false" v-model="dialogFormVisibleAdd">
			<el-form :model="form">
				<el-form-item label="歌曲名" :label-width="formLabelWidth">
					<el-input
						v-model="form.songName"
						autocomplete="off"
						placeholder="请输入歌曲名"
					></el-input>
				</el-form-item>
                <el-form-item label="歌手名称" :label-width="formLabelWidth">
                    <el-input
                        v-model="form.singerName"
                        autocomplete="off"
                        placeholder="请输入歌手名称"
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
	</div>
</template>

<script>
import {
	_getMySong,
	_addLoveSong,
	_cancelLoveSong,
    _addMySong,
    _deleteMySong,
} from "../network/request.js"
export default {
	name: "mysong",
	data() {
		const item = {
			iid: "",
			songName: "",
			singerName: "",
			album: "",
			songTime: "",
			downUrl: "",
			picUrl: "",
			// 收藏列表默认为收藏
			isLove: false,
		}
		return {
			uid: "",
			sheetid: "",
			sheetName: "",
            songName: "",
			singerName: "",
			album: "",
			picUrl: "",
			songTime: "",
			songsData: Array(10).fill(item),
            // 添加数据弹出框
			dialogFormVisibleAdd: false,
            form: {
                sheetid:"",
				iid: "",
				songName: "",
				singerName: "",
				album: "",
				picUrl: "",
				songTime: "",
				downUrl: "",
			},
            // 弹出框中输入框的宽度
			formLabelWidth: "120px",
		}
	},
	created() {
		// 获得歌单信息
		console.log("传入的参数：" + this.$route.params)
		this.sheetid = this.$route.params.sheetid
		this.sheetName = this.$route.params.sheetName
		console.log("歌单id：" + this.$route.params.sheetid)
		console.log("歌单名：" + this.sheetName)
		// 得到歌曲的详情信息
		_getMySong({ sheetid: this.$route.params.sheetid }).then((response) => {
			this.songsData = response.data.data
			console.log(this.songsData)
		})
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
		startPlay(index) {
			console.log(index)
			var song = this.songsData[parseInt(index)]
			console.log(song)
			this.downUrl = song.downUrl
			console.log(this.downUrl)
			console.log(song.picUrl)
			var iid = song.iid
			var picUrl = song.picUrl
			var songName = song.songName
			var singerName = song.singerName
			var mp3Url = this.downUrl
			// 将事件传递给子组件，第一个参数为自定义事件名，后续参数为传递过去的数据
			console.log("开始emit")
			this.$emit("playmusic", iid, mp3Url, picUrl, songName, singerName)
			console.log("结束emit")
		},
		// 取消收藏歌曲
		deleteLove(index) {
			var isLove = this.songsData[parseInt(index)].isLove
			var songName = this.songsData[parseInt(index)].songName
			if (isLove === true) {
				this.songsData[parseInt(index)].isLove = false
				_cancelLoveSong({
					uid: this.uid,
					iid: this.songsData[parseInt(index)].iid,
				}).then((response) => {
					if (response.data.code === 200) {
						console.log("取消收藏")
						this.$message({
							type: "info",
							message: songName + " " + "取消收藏!",
						})
					}
				})
			} else {
				this.songsData[parseInt(index)].isLove = true
				_addLoveSong({
					uid: this.uid,
					iid: this.songsData[parseInt(index)].iid,
				}).then((response) => {
					if (response.data.code === 200) {
						console.log("收藏歌曲")
						this.$message({
							type: "success",
							message: songName + " " + "收藏成功!",
						})
					}
				})
			}
		},
        // 清空弹出框中的数据
		clearForm() {
            this.form.sheetid = ""
			this.form.iid = ""
			this.form.songName = ""
			this.form.singerName = ""
			this.form.album = ""
			this.form.picUrl = ""
			this.form.songTime = ""
			this.form.downUrl = ""
		},
		// 清空添加歌曲对话框的数据
		clearDialog() {
			this.dialogFormVisibleAdd = true
			this.clearForm()
		},
        // 点击弹出框的确定，添加数据
		addData() {
			this.dialogFormVisibleAdd = false
			_addMySong({
                sheetid: this.sheetid,
				songName: this.form.songName,
				singerName: this.form.singerName,
			}).then((response) => {
				if (response.data.code == 200) {
                    this.sheetid = this.$route.params.sheetid
                    console.log(this.sheetid)
					_getMySong({ sheetid: this.$route.params.sheetid }).then((response) => {
						this.songsData = response.data.data
						console.log(this.songsData)
					})
					this.$alert(
						"歌曲id：" + response.data.data.iid,
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
        // 单行删除，index为当前行在数组中的下标，row为当前行的内容，rows为所有行数组
		handleDelete(index, row, rows) {
			this.$confirm("是否删除?", "提示", {
				confirmButtonText: "确定",
				cancelButtonText: "取消",
				type: "warning",
			})
				.then(() => {
                    console.log(this.sheetid)
                    console.log(row.iid)
					// 发出删除请求
					_deleteMySong({ 
                        sheetid: this.sheetid,
                        iid: row.iid 
                    }).then((response) => {
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
#user-detail {
	position: absolute;
	width: 1130px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}

/* 用户信息 */
#user-detail #user {
	position: relative;
	height: 100px;
}

/* 用户头像格式 */
#user-detail #user .user-img {
	display: inline-block;
	position: absolute;
	left: 10px;
}
#user-detail #user .user-img > img {
	width: 100px;
	height: 100px;
	display: inline-block;
}

/* 用户信息 */
#user-detail #user .user-info {
	display: inline-block;
	position: absolute;
	left: 150px;
}

/* 用户姓名、性别 */
#user-detail #user .user-info .user-name-gender {
	position: absolute;
	width: 200px;
	top: 30px;
	padding-bottom: 120px;
    text-align: center;
}
#user-detail #user .user-info .user-name-gender .name {
	padding-right: 50px;
}
#user-detail #user .user-info .user-name-gender .gender {
	color: #f56c6c;
	font-size: 16px;
}

/* 用户所在区域 */
#user-detail #user .user-info .user-area {
    text-align: left;
	padding-bottom: 20px;
}
#user-detail #user .user-info .user-area .area {
	color: #9b9b9b;
}

/* 用户个人简介 */
#user-detail #user .user-info .user-des {
    text-align: left;
}
#user-detail #user .user-info .user-des .des {
	color: #9b9b9b;
}

/* 播放记录间隔 */
#user-detail .record-interval {
	background-color: initial;
	text-align: left;
	padding-top: 20px;
	border-bottom: #a55050 3px solid;
}

/* 表格中不可播放的的禁止点击样式 */
#user-detail .fail {
	color: #9b9b9b;
	cursor: not-allowed;
}
/* 可以播放则不做任何修改 */
#user-detail .succ {
	/* pointer-events: none; */
	/* color: #9b9b9b;
	cursor: not-allowed; */
}
/* 收藏歌曲激活状态 */
#user-detail .active {
	color: #f56c6c;
}

/* 字体图标鼠标覆盖变色 */
#user-detail i.iconfont.icon-bofang:hover {
	cursor: pointer;
	color: #f56c6c;
}

/* 字体图标鼠标覆盖变色 */
#user-detail i.iconfont.icon-shoucang_huaban1:hover {
	cursor: pointer;
}

#add_song{
    position: absolute;
    top: 10px;
    right: 0px;
    left: 1040px;
    width: 100px;
}
</style>


