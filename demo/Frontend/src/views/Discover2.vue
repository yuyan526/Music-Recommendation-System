<template>
	<div id="songs-discover">
		<el-table
			:data="songsData"
			stripe
			style="width: 100%;"
			:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
		>
			<el-table-column align="center" prop="index" label="" width="120">
				<template #default="scope">
					<div :class="scope.row.downUrl == 'None' ? 'fail' : 'succ'">
						<sapn
							style="font-size:16px;width:20px;display:inline-block;"
						>
							{{ scope.$index + 1 }}
						</sapn>
						<i
							class="iconfont icon-bofang"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="startPlay(scope.$index)"
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						></i>
						<i
							class="iconfont icon-shoucang_huaban1"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="addLove(scope.$index)"
							:class="[
								scope.row.downUrl == 'None' ? 'fail' : 'succ',
								scope.row.isLove === true ? 'active' : '',
							]"
						></i>
						<i
							class="el-icon-s-comment"
							style="margin-left:10px;font-size:18px;width:15px;display:inline-block;"
							@click="getComment(scope.$index)"
							:class="
								scope.row.downUrl == 'None' ? 'fail' : 'succ'
							"
						></i>
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
						:class="scope.row.downUrl == 'None' ? 'fail' : 'succ'"
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
						:class="scope.row.downUrl == 'None' ? 'fail' : 'succ'"
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
						:class="scope.row.downUrl == 'None' ? 'fail' : 'succ'"
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
						:class="scope.row.downUrl == 'None' ? 'fail' : 'succ'"
					>
						{{ scope.row.songTime }}
					</span>
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
	</div>
</template>
<script>
import { useStore } from "vuex"
import {
	getDiscoverSongs,
} from "../network/request.js"
export default {
    name: "discover2",
	data() {
		const item = {
			iid: "",
			songName: "",
			singerName: "",
			album: "",
			songTime: "",
			downUrl: "",
			picUrl: "",
			// 是否收藏默认为false，不收藏
			isLove: false,
		}
		return {
			uid: "",
			downUrl: "",
			currentPage: 1,
			pageSize: 10,
			songsData: Array(10).fill(item),
			totalNum: 1000,
		}
	},
	created() {
		// 获得用户id
		this.uid = useStore().state.user.uid
		console.log("用户id：" + this.uid)
		console.log(this.currentPage)
		// 得到歌曲的详情信息
		getDiscoverSongs({ currentPage: "1" }).then((response) => {
			this.songsData = response.data.data
			console.log(this.songsData)
		})
        // 页面加载时读取数据
		if (window.sessionStorage.getItem("user")) {
			console.log("页面加载")
            const session_user = JSON.parse(window.sessionStorage.getItem("user"))
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
		getComment(){
			this.$message({
				type: "info",
				message: "请先登录或注册一个账号",
			})
		},
		getDiscoverSongsData() {
			getDiscoverSongs({ currentPage: this.currentPage + "" }).then(
				(response) => {
					this.songsData = response.data.data
					console.log(this.songsData)
				},
			)
		},
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
		// 下一页
		handleCurrentChange(val) {
			// 得到歌曲的详情信息
			getDiscoverSongs({ currentPage: val }).then((response) => {
				this.songsData = response.data.data
				console.log(this.songsData)
			})
		},
		// 添加或取消收藏歌曲
		addLove() {
			// 判断是否为游客
			if(this.uid == "0"){
				this.$message({
					type: "info",
					message: "请先登录或注册一个账号",
				})
			}
		},
	},
}
</script>
<style scoped>
#songs-discover {
	position: absolute;
	width: 1060px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}

/* 表格中不可播放的的禁止点击样式 */
#songs-discover .fail {
	color: #9b9b9b;
	cursor: not-allowed;
}
/* 可以播放则不做任何修改 */
#songs-discover .succ {
	/* pointer-events: none; */
	/* color: #9b9b9b;
	cursor: not-allowed; */
}
/* 收藏歌曲激活状态 */
#songs-discover .active {
	color: #f56c6c;
}

/* 播放的字体图标鼠标覆盖变色 */
#songs-discover i.iconfont.icon-bofang:hover {
	cursor: pointer;
	/* color: rgb(231, 56, 40); */
	color: #f56c6c;
}

/* 收藏的字体图标鼠标覆盖变色 */
#songs-discover i.iconfont.icon-shoucang_huaban1:hover {
	cursor: pointer;
}
</style>
