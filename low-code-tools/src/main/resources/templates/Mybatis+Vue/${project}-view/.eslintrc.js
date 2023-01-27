module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: [
    'plugin:vue/essential',
    '@vue/standard'
  ],
  parserOptions: {
    parser: '@babel/eslint-parser'
  },
  rules: {
    'no-console': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'no-debugger': process.env.NODE_ENV === 'production' ? 'warn' : 'off',
    'vue/multi-word-component-names': 'off',
    'no-unused-expressions': 'off',
    'no-sequences': 'off',
    'no-var': 'off',
    'no-use-before-define': 'off',
    'no-redeclare': 'off',
    'no-self-assign': 'off',
    eqeqeq: 'off',
    'no-void': 'off',
    'space-before-function-paren':'off'
  }
}
