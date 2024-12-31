import axios from "axios"
axios.defaults.baseURL = "/api"

// 获得歌曲的详情信息
export function getSongDetail(id) {
	return axios.get("https://api.imjad.cn/cloudmusic/?type=detail&id=" + id)
}

// 登录
export function getLogin(params) {
	return axios.post("/login", params)
}

// 获得推荐歌曲
export function getRecommendSongs(param) {
	return axios.post("/recommendSongs", param)
}

// 获得推荐用户
export function getRecommendUsers(param) {
	return axios.post("/recommendUsers", param)
}

// 发现歌曲，新歌
export function getDiscoverSongs(param) {
	return axios.post("/discover", param)
}

//获得歌曲列表，用于管理员
export function _getDisplaySongs(param) {
	return axios.post("/display", param)
}

// 热门歌曲
export function getHotSongs(param) {
	return axios.post("/hot", param)
}

// 获得所有歌曲
export function _getAllSongs(param) {
	return axios.post("/songList", param)
}

// 搜索歌曲
export function _getSearchSongs(param) {
	return axios.post("/searchSongs", param)
}

// 添加歌曲
export function _addSong(param) {
	return axios.post("/addSong", param)
}

// 删除歌曲
export function _deleteSong(param) {
	return axios.post("/deleteSong", param)
}

// 更新歌曲
export function _updateSong(param) {
	return axios.post("/updateSong", param)
}

// 用户注册与添加
export function _register(param) {
	return axios.post("/register", param)
}

// 游客注册
export function _registerVisitor(param) {
	return axios.post("/registerVisitor", param)
}

// 更新用户
export function _updateUser(param) {
	return axios.post("/updateUser", param)
}

// 更新用户密码
export function _updataPw(param) {
	return axios.post("/updatePw", param)
}

// 添加播放记录，参数传入用户id和歌曲id即可
export function _addRecord(param) {
	return axios.post("/addRecord", param)
}

// 获得收藏歌曲列表
export function _getLoveSongList(param) {
	return axios.post("/loveSongList", param)
}

// 添加收藏记录
export function _addLoveSong(param) {
	return axios.post("/addLoveSong", param)
}

// 取消收藏
export function _cancelLoveSong(param) {
	return axios.post("/cancelLoveSong", param)
}

// 获得播放记录歌曲
export function _getRecordSong(param) {
	return axios.post("/getRecordSong", param)
}

// 搜索用户
export function _getSearchUsers(param) {
	return axios.post("/searchUsers", param)
}

// 添加用户
// export function _addUser(param) {
// 	return axios.post("/addUser", param)
// }

// 删除用户
export function _deleteUser(param) {
	return axios.post("/deleteUser", param)
}

// 更新用户
export function _updateUser1(param) {
	return axios.post("/updateUser1", param)
}

//获得用户列表，用于管理员
export function _getDisplayUsers(param) {
	return axios.post("/displayUsers", param)
}

// 获得歌单
export function getSongSheets(param) {
	return axios.post("/songSheets", param)
}

// 添加歌单
export function _addSheet(param) {
	return axios.post("/addSheet", param)
}

// 删除歌单
export function _deleteSheet(param) {
	return axios.post("/deleteSheet", param)
}

// 编辑歌单
export function _updateSheet(param) {
	return axios.post("/updateSheet", param)
}

//获得用户歌单列表，用于管理员
export function _getDisplaySheets(param) {
	return axios.post("/displaySheets", param)
}

// 搜索用户歌单
export function _getSearchSheets(param) {
	return axios.post("/searchSheets", param)
}

// 获得播放记录歌曲
export function _getMySong(param) {
	return axios.post("/getMySong", param)
}

// 添加歌曲到歌单中
export function _addMySong(param) {
	return axios.post("/addMySong", param)
}

// 删除歌单中的歌曲
export function _deleteMySong(param) {
	return axios.post("/deleteMySong", param)
}

// 获得总数统计
export function _gettotal(param) {
	return axios.post("/gettotal", param)
}

// 获得评论信息
export function _getComment(param) {
	return axios.post("/comment", param)
}

// 添加评论信息
export function _addComment(param) {
	return axios.post("/addcomment", param)
}