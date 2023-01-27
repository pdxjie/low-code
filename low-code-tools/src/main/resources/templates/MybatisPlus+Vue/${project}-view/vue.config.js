const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  lintOnSave:false,
  transpileDependencies: true,
  configureWebpack: {
    resolve: {
      fallback: {
        path: require.resolve('path-browserify')
      }
    }
  },
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:8088/',
        ws: true,
        changeOrigin: true,
        pathRewrite: { '^/api': '' }
      }
    }
  },
  publicPath: './'
})
