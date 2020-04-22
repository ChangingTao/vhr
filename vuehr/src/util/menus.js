import {getRequest} from "./api";

export const initMenu = (router, store) => {
    if (store.state.routes.length > 0) {
        return;
    }
    getRequest("/system/config/menu").then(data => {
        if (data) {
            // 格式化返 回数据
            let fmtRoutes = formatRoutes(data);
            // 将格式化后的数据存放到路由配置中
            router.addRoutes(fmtRoutes);
            // 将数据存放到VueX中
            store.commit('initRoutes', fmtRoutes)
        }
    })
}

export const formatRoutes = (routes) => {
    let fmRoutes = [];
    routes.forEach(router => {
        let {path, component, name, meta, iconCls, children} = router;
        // 如果children存在且是数组……（防止children为空且为null）
        if (children && children instanceof Array) {
            // 重新遍历一遍防止子组件children出现为空的情况
            children = formatRoutes(children)
        }
        let fmRouter = {
            path,
            name,
            iconCls,
            meta,
            children,
            component(resolve) {
                if (component.startsWith("Home")) {
                    require(['@/views/' + component + '.vue'], resolve)
                } else if (component.startsWith("Emp")) {
                    require(['@/views/emp/' + component + '.vue'], resolve)
                }  else if (component.startsWith("Per")) {
                    require(['@/views/per/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sal")) {
                    require(['@/views/sal/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sta")) {
                    require(['@/views/sta/' + component + '.vue'], resolve)
                } else if (component.startsWith("Sys")) {
                    require(['@/views/sys/' + component + '.vue'], resolve)
                }
            }
        };
        fmRoutes.push(fmRouter);
    })
    return fmRoutes;
}