let proxyObj = {};

proxyObj['/ws']={
    ws: true, // 是否代理websockets
    target: 'ws://localhost:8081', // 要使用url模块解析的url字符串
};
proxyObj['/']={
  ws: false,
  target: 'http://localhost:8081',
  changeOrigin: true,
  pathRewrite:{
    '^/': ''
  }
};

module.exports={
  devServer:{
    host: 'localhost',
    port: 8080,
    proxy: proxyObj
  }
}