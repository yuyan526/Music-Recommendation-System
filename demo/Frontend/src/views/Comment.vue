<template>
	<div id="song-detail">
		<!-- 歌曲信息 -->
		<div id="song">
			<div class="song-img">
				<img src="../assets/imgs/10001.jpg" alt="加载失败" />
			</div>
			<div class="song-info">
				<div class="songName">
					<span class="name">{{ songName }}</span>	
				</div>
				<div class="song-singer">
					<span class="singerName">歌手：</span>
					<span>{{ singerName }}</span>
				</div>
				<div class="song-album">
					<span class="album">专辑：</span>
					<span>{{ album }}</span>
				</div>
			</div>
		</div>
		<!-- 间隔 -->
		<div class="comment-interval">
			<span>
				用户评论
			</span>
		</div>
		<!-- 用户歌曲评论 -->
		<div id="user-comment">
			<el-button type="primary" style="float: right; padding-right: 40px;" @click="WriteSubmitForm">
			写评论
			</el-button>
			<el-table
				:data="commentData"
				stripe
				style="width: 100%;"
				:header-cell-style="{ fontWeight: 'normal', border: 'none' }"
			>
				<el-table-column
					align="center"
					prop="index"
					label=""
					width="120"
				>
					<template #default="scope">
						<div>
							<sapn
								style="font-size:16px;width:20px;display:inline-block;"
							>
								{{ scope.$index + 1 }}
							</sapn>
						</div>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="name"
					label="用户名"
					width="220"
				>
					<template #default="scope">
						<span>
							{{ scope.row.name }}
						</span>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="sid"
					label="用户id"
					width="220"
				>
					<template #default="scope">
						
						<span>
							{{ scope.row.uid }}
						</span>
					</template>
				</el-table-column>
				<el-table-column
					align="center"
					prop="comments"
					label="评论"
					width="500"
				>
					<template #default="scope">
						<span>
							{{ scope.row.comment }}
						</span>
					</template>
				</el-table-column>
			</el-table>
			<el-dialog
			class="write-dialog"
			title="写评论"
			modal="false"
			v-model="registerDialogVisibleEdit"
			>
				<el-form :model="form">
					<el-form-item label="请输入" :label-width="formLabelWidth">
						<el-input
							clearable
							v-model="form.comment"
							autocomplete="off"
							placeholder=""
						></el-input>
					</el-form-item>
				</el-form>
				<template #footer>
					<span class="dialog-footer">
						<el-button @click="registerDialogVisibleEdit = false">
							取 消
						</el-button>
						<el-button type="primary" @click="addComment">
							提 交
						</el-button>
					</span>
				</template>
			</el-dialog>
		</div>

	</div>
</template>

<script>
import {
	_getComment,
	_addComment,
} from "../network/request.js"
import { useStore } from "vuex"
import { ref } from "vue"

export default {
	name: "comment",
	data() {
		const item = {
			uid: "",
			name: "",
            comments: "",
		}
		return {
            iid: "",
			songName: "",
			singerName: "",
			album: "",
			picUrl: "",
			commentData: Array(10).fill(item),

			form: {
				iid: this.$route.params.iid,
				name: useStore().state.user.name,
				uid: useStore().state.user.uid,
				comments: ref(""),
			},
			registerDialogVisibleEdit: false,
			formLabelWidth: "120px",
		}
	},
	created() {
        console.log("传入的参数：" + this.$route.params)
        this.iid = this.$route.params.iid
        this.picUrl = this.$route.params.picUrl
        this.singerName = this.$route.params.singerName
        this.album = this.$route.params.album
        this.songName = this.$route.params.songName
        // 获得评论信息
		_getComment({ iid: this.iid }).then((response) => {
			this.commentData = response.data.data
			console.log(this.commentData)
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
		WriteSubmitForm(){
			this.registerDialogVisibleEdit = true
			this.clearForm()
		},
		// 清空弹出框中的数据
		clearForm() {
			this.form.comments = ""
		},
		addComment(){
			_addComment(this.form).then((response)=>{
				const data = response.data.data
				const code = response.data.code
				const msg = response.data.msg
				console.log(data)
				if (code == 200) {
					_getComment({ iid: this.iid }).then((response) => {
					this.commentData = response.data.data
					console.log(this.commentData)
					})
						this.$message({
							type: "success",
							message: "添加成功",
						})
						this.registerDialogVisibleEdit = false
						this.clearForm()
				} else {
					this.$message({
						type: "info",
						message: msg,
					})
				}
			})
		},
	},
	
}
</script>

<style scoped>
#song-detail {
	position: absolute;
	width: 1060px !important;
	right: 0;
	left: 0;
	margin-left: auto;
	margin-right: auto;
}

/* 歌曲信息 */
#song-detail #song {
	position: relative;
	height: 100px;
}

/* 歌曲照片格式 */
#song-detail #song .song-img {
	display: inline-block;
	position: absolute;
	left: 10px;
}
#song-detail #song .song-img > img {
	width: 100px;
	height: 100px;
	display: inline-block;
}

/* 歌曲信息 */
#song-detail #song .song-info {
	display: inline-block;
	position: absolute;
	left: 150px;
}

/* 歌名 */
#song-detail #song .song-info .songName {
	padding-bottom: 20px;
    text-align: left;
}
#song-detail #song .song-info .songName .name {
	padding-right: 50px;
}


/* 歌手所在区域 */
#song-detail #song .song-info .song-singer {
    text-align: left;
	padding-bottom: 20px;
}
#song-detail #song .song-info .song-singer .singerName {
	color: #9b9b9b;
}

/* 专辑 */
#song-detail #song .song-info .song-album {
    text-align: left;
}
#song-detail #song .song-info .song-album .album {
	color: #9b9b9b;
}

/* 用户评论间隔 */
#song-detail .comment-interval {
	background-color: initial;
	text-align: left;
	padding-top: 20px;
	border-bottom: #a55050 3px solid;
}

</style>
