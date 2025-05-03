1. 初始化ant-design-vue 项目

官方文档：[Ant Design Vue](https://2x.antdv.com/docs/vue/getting-started-cn)

```
安装脚手架
npm install -g @vue/cli

vue create online-judge-web
```

初始化过程选择：

![初始化vue项目](../doc/images/初始化vue项目.png)

用webstorm打开，配置代码美化

![webstorm代码美化配置](../doc/images/webstorm代码美化配置.png)

**代码美化会自动报错，按住ctrl+alt+L就可以自动修正**

2. 引入vuex状态管理

第一步的时候忘记引入了，现在补充一下

```
npm install vuex --save
```

安装好后，在src下添加store文件夹，store文件夹下添加index.ts

```
import { createStore } from "vuex";

export default createStore({
  mutations: {},
  actions: {},
  modules: {},
});
```

修改main.ts

```
import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import store from "./store";

createApp(App).use(ArcoVue).use(store).mount("#app");
```

3. 安装组件库

官网：[Arco Design Vue](https://arco.design/vue/docs/start)

```
npm install --save-dev @arco-design/web-vue
```

修改main.ts

```
import { createApp } from "vue";
import App from "./App.vue";
import ArcoVue from "@arco-design/web-vue";
import "@arco-design/web-vue/dist/arco.css";
import store from "./store";
import router from "./router";

createApp(App).use(ArcoVue).use(store).use(router).mount("#app");
```

4. 安装axios

官网：[Axios中文文档 | Axios中文网](https://www.axios-http.cn/)

```
npm install axios
```

5. 自动生成后端接口请求代码

代码仓库：[github.com](https://github.com/ferdikoomen/openapi-typescript-codegen)

```
npm install openapi-typescript-codegen --save-dev
```

下载成功后执行

```
openapi --input http://localhost:8080/api/v3/api-docs --output ./generated --client axios
```

记着看生成文件夹下core/OpenAPI.ts的WITH_CREDENTIALS是否为true，为true能保证请求带cookie。
