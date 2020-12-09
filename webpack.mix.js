let mix = require('laravel-mix');

mix.babelConfig({
    plugins: ['@babel/plugin-syntax-dynamic-import'],
});

mix
    .sass('resources/webapp/scss/style.scss', 'src/main/resources/static/webapp/assets/css/app.css')
    .sass('resources/auth/scss/style.scss', 'src/main/resources/static/auth/css/style.css')
    .sass('resources/dashboard/scss/style.scss', 'src/main/resources/static/dashboard/html/css/style.css')
    .styles(['resources/webapp/vendor/jquery-ui/jquery-ui.min.css', 'resources/webapp/vendor/jquery-ui/jquery-ui.theme.min.css'], 'src/main/resources/static/webapp/assets/css/vendor.css')
    .scripts([
        'node_modules/jquery/dist/jquery.js',
        'resources/webapp/vendor/jquery-ui/jquery-ui.min.js',
        'node_modules/bootstrap/dist/js/bootstrap.bundle.js',
        'node_modules/blueimp-md5/js/md5.min.js',
        'node_modules/masonry-layout/dist/masonry.pkgd.min.js',
        'node_modules/handlebars/dist/handlebars.runtime.js',
        'node_modules/imagesloaded/imagesloaded.pkgd.js',
        'node_modules/aos/dist/aos.js',
        'node_modules/vanilla-lazyload/dist/lazyload.js',
    ], 'src/main/resources/static/webapp/js/vendors.js')
    .babel([
        'resources/webapp/js/modules/utils.js',
        'resources/webapp/js/modules/error.js',
        'resources/webapp/js/modules/cache.js',
        'resources/webapp/js/modules/api.js',
        'resources/webapp/js/modules/navigation.js',
        'resources/webapp/js/modules/search.js',
        'resources/webapp/js/index.js'], 'src/main/resources/static/webapp/js/app.js')
    .options({
        processCssUrls: false
    });
