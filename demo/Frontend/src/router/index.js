import { createWebHistory, createRouter } from "vue-router"
import admin from "../components/admin/Admin.vue"
import adminMenu from "../components/admin/AdminMenu.vue"
import login from "../components/user/Login.vue"
import main from "../components/Main.vue"
import VisitorMain from "../components/VisitorMain.vue"
import recommendsongs from "../views/RecommendSongs.vue"
import recommendsongs2 from "../views/RecommendSongs2.vue"
import recommendusers from "../views/RecommendUsers.vue"
import discover from "../views/Discover.vue"
import discover2 from "../views/Discover2.vue"
import hotmusic from "../views/HotMusic.vue"
import hotmusic2 from "../views/HotMusic2.vue"
import searchMusic from "../views/SearchMusic.vue"
import loveSong from "../views/love-song.vue"
import userRecord from "../views/user-record.vue"
import MyRecord from "../views/MyRecord.vue"
import comment from "../views/Comment.vue"
import store from "../store/index.js"
import usermanagement from "../views/usermanagement.vue"
import songsheet from "../views/songsheet.vue"
import sheetmanagement from "../views/sheetmanagement.vue"
import mysong from "../views/mysong.vue"
import datastatistics from "../views/data-statistics.vue"

const routes = [
	{
		path: "/login",
		name: "login",
		component: login,
	},

	{
		path: "/admin",
		name: "admin",
		component: admin,
	},
	{
		path: "/adminMenu",
		name: "adminMenu",
		component: adminMenu,
	},
	{
		path: "/",
		name: "main",
		component: main,
		redirect: "/discover",
		meta: {
			requireLogin: true,
		},
		// 子路由
		children: [
			{
				path: "recommendsongs",
				name: "recommendsongs",
				component: recommendsongs,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "recommendusers",
				name: "recommendusers",
				component: recommendusers,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "discover",
				name: "discover",
				component: discover,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "hotmusic",
				name: "hotmusic",
				component: hotmusic,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "searchMusic",
				name: "searchMusic",
				component: searchMusic,
			},
            {
                path: "loveSong",
                name: "loveSong",
                component: loveSong,
            },
            {
                path: "userRecord",
                name: "userRecord",
                component: userRecord,
            },
			{
				path: "songsheet",
                name: "songsheet",
                component: songsheet,
			},
			{
				path: "mysong",
                name: "mysong",
                component: mysong,
			},
			{
                path: "MyRecord",
                name: "MyRecord",
                component: MyRecord,
            },
			{
                path: "comment",
                name: "comment",
                component: comment,
            },
		],
	},

	{
		path: "/",
		name: "VisitorMain",
		component: VisitorMain,
		redirect: "/discover2",
		meta: {
			requireLogin: true,
		},
		// 子路由
		children: [
			{
				path: "recommendsongs2",
				name: "recommendsongs2",
				component: recommendsongs2,
				meta: {
					requireLogin: true,
				},
			},
			
			{
				path: "discover2",
				name: "discover2",
				component: discover2,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "hotmusic2",
				name: "hotmusic2",
				component: hotmusic2,
				meta: {
					requireLogin: true,
				},
			},
			{
				path: "searchMusic2",
				name: "searchMusic2",
				component: searchMusic,
			},
			
		],
	},

	{
		path: "/usermanagement",
        name: "usermanagement",
        component: usermanagement,
	},
	{
		path: "/sheetmanagement",
        name: "sheetmanagement",
        component: sheetmanagement,
	},
	{
		path: "/datastatistics",
		name: "datastatistics",
		component: datastatistics,
	}
]

const router = createRouter({
	history: createWebHistory(),
	routes, 
})

// 登录验证
router.beforeEach((to, from, next) => {
	// 判断是否需要登录
	if (to.meta.requireLogin) {
        console.log("路由跳转验证")
        console.log(store.state)
		if (store.state.user || to.path === "/login") {
			next()
		} else {
			// 如果没有登录，则所有页面都需要跳转到登录界面
			next(`/login?redirect=${to.path}`)
		}
	} else {
		next()
	}
})

export default router
