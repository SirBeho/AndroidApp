const webpack = require("@nativescript/webpack");

module.exports = (env) => {
	webpack.init(env);

	webpack.chainWebpack((config) => {
		config.resolve.alias
		  .set('vue', 'nativescript-vue')
		  .set('nativescript-vue', 'nativescript-vue');
	  });
	// Learn how to customize:
	// https://docs.nativescript.org/webpack

	return webpack.resolveConfig();
};
