import { createApp } from "vue"
import App from "./App.vue"
import axios from "axios"
import VueAxios from "vue-axios"
import router from "./router"
import store from "./store"

// 完整引入ElementUI和样式
import ElementPlus from "element-plus"
import "element-plus/lib/theme-chalk/index.css"
import installElementPlus from "./plugins/element.js"
// import './assets/index.css'
// 引入字体图标的css文件
import "./assets/iconfont/iconfont.css"

const app = createApp(App)
installElementPlus(app)
app.use(VueAxios, axios)
app.use(ElementPlus)
// 注入路由
app.use(router)
// vuex状态管理
app.use(store)

app.config.productionTip = false
// 设置全局axios
app.config.globalProperties.$axios = axios
// 设置基本的默认路径
axios.defaults.baseURL = "/api"
// 这里挂载要放在use的下面才能正确渲染

// 挂在放在最后执行
app.mount("#app")
